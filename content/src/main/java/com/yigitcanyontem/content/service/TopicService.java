package com.yigitcanyontem.content.service;

import com.yigitcanyontem.content.domain.Tag;
import com.yigitcanyontem.content.domain.Topic;
import com.yigitcanyontem.content.repository.ReplyRepository;
import com.yigitcanyontem.content.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.amqp.RabbitMQMessageProducer;
import org.yigitcanyontem.clients.content.dto.TagDto;
import org.yigitcanyontem.clients.content.dto.TopicCreateDto;
import org.yigitcanyontem.clients.content.dto.TopicDto;
import org.yigitcanyontem.clients.shared.dto.GenericRabbitMQMessage;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;
import org.yigitcanyontem.clients.users.dto.UsersDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicService {
    private final TopicRepository topicRepository;
    private final TagService tagService;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final ReplyRepository replyRepository;

    public PaginatedResponse getTrendingTopics(int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("viewCountLastWeek"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Topic> topicPage = topicRepository.findAll(pageRequest);

        return returnPaginatedResponse(topicPage);
    }

    public PaginatedResponse searchByName(String query, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Topic> topicPage = topicRepository.findAllByNameRegex(query,pageRequest);

        return returnPaginatedResponse(topicPage);
    }

    private PaginatedResponse returnPaginatedResponse(Page<Topic> topicPage) {
        List<TopicDto> topicDtos = topicPage.getContent().stream().parallel()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return PaginatedResponse.builder()
                .data(topicDtos)
                .page(topicPage.getNumber())
                .size(topicPage.getSize())
                .totalElements(topicPage.getTotalElements())
                .totalPages(topicPage.getTotalPages())
                .build();
    }

    private TopicDto convertToDto(Topic topic) {
        if (topic == null) {
            return null;
        }
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .createdByUserId(topic.getCreatedByUserId())
                .createdByUsername(topic.getCreatedByUsername())
                .updatedByUserId(topic.getUpdatedByUserId())
                .updatedByUsername(topic.getUpdatedByUsername())
                .updatedAt(topic.getUpdatedAt())
                .createdAt(topic.getCreatedAt())
                .viewCountTotal(topic.getViewCountTotal())
                .viewCountLastWeek(topic.getViewCountLastWeek())
                .parentTopicId(topic.getParentTopicId())
                .replyCount(getReplyCount(topic.getId()))
                .slug(topic.getSlug())
                .tags(topic.getTags().stream()
                        .map(tag -> TagDto.builder()
                                .id(tag.getId())
                                .name(tag.getName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    private Long getReplyCount(Long id) {
        return replyRepository.countByTopicId(id);
    }


    public void save(TopicCreateDto topicCreateDto, UsersDto user) {
        Topic topic = Topic.builder()
                .name(topicCreateDto.getName())
                .description(topicCreateDto.getDescription())
                .createdByUserId(user.getId())
                .createdByUsername(user.getUsername())
                .updatedByUserId(user.getId())
                .updatedByUsername(user.getUsername())
                .createdAt(new Date())
                .viewCountTotal(0L)
                .viewCountLastWeek(0L)
                .parentTopicId(topicCreateDto.getParentTopicId())
                .slug(topicCreateDto.getName().toLowerCase().replace(" ", "-"))
                .build();

        if (topicCreateDto.getTags() != null && !topicCreateDto.getTags().isEmpty()) {
            topic.setTags(tagService.getTags(topicCreateDto.getTags()));
        }

        topicRepository.saveAndFlush(topic);
    }

    public TopicDto getTopicBySlug(String slug) {
        Topic topic = topicRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        rabbitMQMessageProducer.publish(
                new GenericRabbitMQMessage("api/v1/content/topic-increment-view", topic.getId().toString()),
                "internal.exchange",
                "internal.content.routing-key"
        );
        return convertToDto(topic);
    }

    public TopicDto update(TopicCreateDto topicUpdateDto, UsersDto user) {
        Topic topic = throwIfNotAuthorizedOrReturnTopic(topicUpdateDto.getId(), user);

        if (topicUpdateDto.getName() != null) {
            topic.setName(topicUpdateDto.getName());
            topic.setSlug(topicUpdateDto.getName().toLowerCase().replace(" ", "-"));
        }

        if (topicUpdateDto.getDescription() != null) {
            topic.setDescription(topicUpdateDto.getDescription());
        }

        if (topicUpdateDto.getParentTopicId() != null) {
            topic.setParentTopicId(topicUpdateDto.getParentTopicId());
        }

        if (topicUpdateDto.getTags() != null && !topicUpdateDto.getTags().isEmpty()) {
            topic.setTags(tagService.getTags(topicUpdateDto.getTags()));
        }

        topicRepository.saveAndFlush(topic);

        return convertToDto(topic);
    }

    public void delete(Long id, UsersDto user) {
        throwIfNotAuthorizedOrReturnTopic(id, user);
        topicRepository.deleteById(id);
    }

    private Topic throwIfNotAuthorizedOrReturnTopic(Long id, UsersDto user) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        if (!topic.getCreatedByUserId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this topic");
        }

        return topic;
    }

    public void incrementViewCount(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        topic.setViewCountTotal(topic.getViewCountTotal() + 1);
        topic.setViewCountLastWeek(topic.getViewCountLastWeek() + 1);

        topicRepository.saveAndFlush(topic);
    }

    public TopicDto getTopicById(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        return convertToDto(topic);
    }

    public boolean isTopicExists(Long topicId) {
        return topicRepository.existsById(topicId);
    }
}
