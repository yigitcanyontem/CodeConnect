package com.yigitcanyontem.content.service;

import com.yigitcanyontem.content.domain.ReplyVote;
import com.yigitcanyontem.content.repository.ReplyVoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.amqp.RabbitMQMessageProducer;
import org.yigitcanyontem.clients.content.dto.ReplyVoteCreateDto;
import org.yigitcanyontem.clients.content.dto.ReplyVoteDto;
import org.yigitcanyontem.clients.content.enums.VoteType;
import org.yigitcanyontem.clients.shared.dto.GenericRabbitMQMessage;
import org.yigitcanyontem.clients.shared.dto.GenericResponse;
import org.yigitcanyontem.clients.users.dto.UsersDto;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyVoteService {
    private final ReplyVoteRepository replyVoteRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public GenericResponse save(ReplyVoteCreateDto createDto, UsersDto user) {
        GenericResponse response;
        ReplyVote replyVote = replyVoteRepository.findByUserIdAndReplyId(user.getId(), createDto.getReplyId())
                .orElseGet(() -> ReplyVote.builder()
                        .userId(user.getId())
                        .replyId(createDto.getReplyId())
                        .createdAt(new Date())
                        .build());

        if (replyVote.getVoteType() == createDto.getVoteType()) {
            log.info("Vote deleted: {}", replyVote);
            replyVoteRepository.delete(replyVote);
            response = new GenericResponse("deleted", replyVote, true);
        } else {
            replyVote.setVoteType(createDto.getVoteType());
            log.info("Vote saved: {}", replyVote);
            replyVoteRepository.save(replyVote);
            String message = replyVote.getVoteType() == VoteType.UPVOTE ? "upvoted" : "downvoted";
            response = new GenericResponse(message, replyVote, true);
        }

        rabbitMQMessageProducer.publish(
                new GenericRabbitMQMessage("api/v1/content/update-reply-votes", response),
                "internal.exchange",
                "internal.content.routing-key"
        );
        return response;
    }

    private ReplyVoteDto convertToDto(ReplyVote replyVote) {
        return ReplyVoteDto.builder()
                .id(replyVote.getId())
                .userId(replyVote.getUserId())
                .replyId(replyVote.getReplyId())
                .voteType(replyVote.getVoteType())
                .createdAt(replyVote.getCreatedAt())
                .build();
    }
}
