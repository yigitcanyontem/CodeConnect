package com.yigitcanyontem.content.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yigitcanyontem.content.domain.Reply;
import com.yigitcanyontem.content.domain.ReplyVote;
import com.yigitcanyontem.content.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.content.dto.ReplyCreateDto;
import org.yigitcanyontem.clients.content.dto.ReplyDto;
import org.yigitcanyontem.clients.content.enums.VoteType;
import org.yigitcanyontem.clients.shared.dto.GenericResponse;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;
import org.yigitcanyontem.clients.users.dto.UsersDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final TopicService topicService;
    private final ObjectMapper objectMapper;

    public PaginatedResponse getRepliesByTopicId(Long topicId, int page, int size) {
        Sort sort = Sort.by(Sort.Order.asc("createdAt"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Reply> replyPage = replyRepository.findAllByTopicId(topicId, pageRequest);
        return convertToPaginatedResponse(replyPage);
    }

    public PaginatedResponse getLatestReplies(int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Reply> replyPage = replyRepository.findAll(pageRequest);
        return convertToPaginatedResponse(replyPage);
    }

    public PaginatedResponse getCurrentUserReplies(UsersDto user, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Reply> replyPage = replyRepository.findAllByCreatedByUserId(user.getId(), pageRequest);
        return convertToPaginatedResponse(replyPage);
    }

    public PaginatedResponse getChildRepliesByReplyId(Long replyId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Reply> replyPage = replyRepository.findAllByParentReplyId(replyId, pageRequest);
        return convertToPaginatedResponse(replyPage);
    }

    public PaginatedResponse getRepliesByUserId(Integer userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Reply> replyPage = replyRepository.findAllByCreatedByUserId(userId, pageRequest);
        return convertToPaginatedResponse(replyPage);
    }

    private PaginatedResponse convertToPaginatedResponse(Page<Reply> replyPage) {
        List<ReplyDto> replyDtos = replyPage.getContent().stream().parallel()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return PaginatedResponse.builder()
                .data(replyDtos)
                .page(replyPage.getNumber())
                .size(replyPage.getSize())
                .totalElements(replyPage.getTotalElements())
                .totalPages(replyPage.getTotalPages())
                .build();
    }

    public ReplyDto update(ReplyDto replyDto, UsersDto user) {
        Reply reply = throwIfNotAuthorizedOrReturnReply(replyDto.getId(), user);

        if (replyDto.getContent() != null) {
            reply.setContent(replyDto.getContent());
        }

        reply.setUpdatedAt(new Date());
        replyRepository.saveAndFlush(reply);
        log.info("Reply with id {} updated by user {}", reply.getId(), user.getId());
        return convertToDto(reply);
    }

    public ReplyDto save(ReplyCreateDto createDto, UsersDto user) {
        throwIfTopicDoesNotExistOrParentReplyDoesNotExist(createDto);

        Reply reply = Reply.builder()
                .content(createDto.getContent())
                .parentReplyId(createDto.getParentReplyId())
                .topicId(createDto.getTopicId())
                .createdByUserId(user.getId())
                .createdByUsername(user.getUsername())
                .createdAt(new Date())
                .updatedAt(new Date())
                .content(createDto.getContent())
                .upvoteCount(0L)
                .downvoteCount(0L)
                .build();

        replyRepository.saveAndFlush(reply);
        log.info("Reply created by user {}", user.getId());
        return convertToDto(reply);
    }

    private void throwIfTopicDoesNotExistOrParentReplyDoesNotExist(ReplyCreateDto createDto) {
        boolean topicExists = topicService.isTopicExists(createDto.getTopicId());

        if (!topicExists) {
            throw new RuntimeException("Topic not found");
        }

        if (createDto.getParentReplyId() != null && !replyRepository.existsById(createDto.getParentReplyId())) {
            throw new RuntimeException("Parent reply not found");
        }
    }

    public void delete(Long id, UsersDto user) {
        throwIfNotAuthorizedOrReturnReply(id, user);
        replyRepository.deleteById(id);
    }

    private Reply throwIfNotAuthorizedOrReturnReply(Long id, UsersDto user) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        if (!reply.getCreatedByUserId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this reply");
        }

        return reply;
    }

    private ReplyDto convertToDto(Reply reply) {
        if (reply == null) {
            return null;
        }
        return ReplyDto.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .parentReplyId(reply.getParentReplyId())
                .topicId(reply.getTopicId())
                .createdByUserId(reply.getCreatedByUserId())
                .createdByUsername(reply.getCreatedByUsername())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .upvoteCount(reply.getUpvoteCount())
                .downvoteCount(reply.getDownvoteCount())
                .topic(topicService.getTopicById(reply.getTopicId()))
                .build();
    }

    public ReplyDto getReplyById(Long id) {
        return convertToDto(replyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reply not found")));
    }

    public void updateVoteCount(GenericResponse response) {
        ReplyVote replyVote =  objectMapper.convertValue(response.getData(), ReplyVote.class);

        Reply reply = replyRepository.findById(replyVote.getReplyId())
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        if (response.getMessage().equals("deleted")) {
            if (replyVote.getVoteType() == VoteType.UPVOTE) {
                reply.setUpvoteCount(reply.getUpvoteCount() - 1);
            } else {
                reply.setDownvoteCount(reply.getDownvoteCount() - 1);
            }
        } else {
            if (response.getMessage().equals("upvoted")) {
                reply.setUpvoteCount(reply.getUpvoteCount() + 1);
            } else {
                reply.setDownvoteCount(reply.getDownvoteCount() + 1);
            }
        }
        replyRepository.saveAndFlush(reply);
        log.info("Reply vote count updated: {}", reply);
    }

}