package com.yigitcanyontem.content.service;

import com.yigitcanyontem.content.domain.Tag;
import com.yigitcanyontem.content.domain.Topic;
import com.yigitcanyontem.content.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.content.dto.TagDto;
import org.yigitcanyontem.clients.content.dto.TopicCreateDto;
import org.yigitcanyontem.clients.content.dto.TopicDto;
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
    private final ReplyService replyService;

    public PaginatedResponse getTopics(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Topic> topicPage = topicRepository.findAll(pageRequest);

        List<TopicDto> topicDtos = topicPage.getContent().stream()
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
        return TopicDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .createdByUserId(topic.getCreatedByUserId())
                .createdByUsername(topic.getCreatedByUsername())
                .updatedByUserId(topic.getUpdatedByUserId())
                .updatedByUsername(topic.getUpdatedByUsername())
                .createdAt(topic.getCreatedAt())
                .viewCountTotal(topic.getViewCountTotal())
                .viewCountLastWeek(topic.getViewCountLastWeek())
                .parentTopicId(topic.getParentTopicId())
                .slug(topic.getSlug())
                .tags(topic.getTags().stream()
                        .map(tag -> TagDto.builder()
                                .id(tag.getId())
                                .name(tag.getName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
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
}
