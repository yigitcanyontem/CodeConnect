package com.yigitcanyontem.content.controller;

import com.yigitcanyontem.content.service.ReplyService;
import com.yigitcanyontem.content.service.ReplyVoteService;
import com.yigitcanyontem.content.service.TagService;
import com.yigitcanyontem.content.service.TopicService;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.auth.AuthClient;
import org.yigitcanyontem.clients.content.dto.*;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@RestController
@RequestMapping("api/v1/content")
@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final TopicService topicService;
    private final TagService tagService;
    private final ReplyService replyService;
    private final ReplyVoteService replyVoteService;
    private final AuthClient authClient;

    @GetMapping("/topics")
    public ResponseEntity<PaginatedResponse> getTopics(@RequestParam(defaultValue = "0", name = "page") int page,
                                                       @RequestParam(defaultValue = "10", name = "size") int size) {
        try {
            return ResponseEntity.ok(topicService.getTopics(page, size));
        }catch (Exception e) {
            log.error("Error while getting topics", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/topics/search")
    public ResponseEntity<PaginatedResponse> searchTopics(@RequestParam(name = "query") String query,
                                                          @RequestParam(defaultValue = "0", name = "page") int page,
                                                          @RequestParam(defaultValue = "10", name = "size") int size) {
        try {
            return ResponseEntity.ok(topicService.searchByName(query, page, size));
        } catch (Exception e) {
            log.error("Error while searching topics", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/topic/{slug}")
    public ResponseEntity<TopicDto> getTopicBySlug(@PathVariable("slug") String slug) {
        try {
            return ResponseEntity.ok(topicService.getTopicBySlug(slug));
        }catch (Exception e) {
            log.error("Error while getting topic", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
//
//    @GetMapping("/reply/{id}")
//    public ResponseEntity<ReplyDto> getReplyById(@PathVariable("id") Long id) {
//        try {
//            return ResponseEntity.ok(replyService.getReplyById(id));
//        }catch (Exception e) {
//            log.error("Error while getting reply", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/replies/{topicId}")
//    public ResponseEntity<PaginatedResponse> getRepliesByTopicId(@PathVariable("topicId") Long topicId,
//                                                                 @RequestParam(defaultValue = "0") int page,
//                                                                 @RequestParam(defaultValue = "10") int size) {
//        try {
//            return ResponseEntity.ok(replyService.getRepliesByTopicId(topicId, page, size));
//        }catch (Exception e) {
//            log.error("Error while getting replies", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/user-replies/{userId}")
//    public ResponseEntity<PaginatedResponse> getRepliesByUserId(@PathVariable("userId") Long userId,
//                                                                @RequestParam(defaultValue = "0") int page,
//                                                                @RequestParam(defaultValue = "10") int size) {
//        try {
//            return ResponseEntity.ok(replyService.getRepliesByUserId(userId, page, size));
//        }catch (Exception e) {
//            log.error("Error while getting replies", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/tags")
//    public ResponseEntity<PaginatedResponse> getTags(@RequestParam(defaultValue = "0") int page,
//                                                     @RequestParam(defaultValue = "10") int size) {
//        try {
//            return ResponseEntity.ok(tagService.getPaginatedTags(page, size));
//        }catch (Exception e) {
//            log.error("Error while getting tags", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/tags/search/{name}")
//    public ResponseEntity<PaginatedResponse> searchTagByName(@PathVariable("name") String name,
//                                                  @RequestParam(defaultValue = "0") int page,
//                                                  @RequestParam(defaultValue = "10") int size) {
//        try {
//            return ResponseEntity.ok(tagService.searchTagByName(name, page, size));
//        }catch (Exception e) {
//            log.error("Error while getting tag", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//
    @PutMapping("/topic")
    public ResponseEntity<TopicDto> updateTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody TopicCreateDto topicUpdateDto) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            return ResponseEntity.ok(topicService.update(topicUpdateDto, user));
        }catch (Exception e) {
            log.error("Error while updating topic", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/tag")
//    public ResponseEntity<TagDto> updateTag(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody TagCreateDto tagDto) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            return ResponseEntity.ok(tagService.update(tagDto, user));
//        }catch (Exception e) {
//            log.error("Error while updating tag", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/reply")
//    public ResponseEntity<ReplyDto> updateReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody ReplyCreateDto replyDto) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            return ResponseEntity.ok(replyService.update(replyDto, user));
//        }catch (Exception e) {
//            log.error("Error while updating reply", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/reply-vote")
//    public ResponseEntity<ReplyVoteDto> updateReplyVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody ReplyVoteDto replyVoteDto) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            return ResponseEntity.ok(replyVoteService.update(replyVoteDto, user));
//        } catch (Exception e) {
//            log.error("Error while updating reply vote", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/topic")
    public ResponseEntity<Void> createTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody TopicCreateDto topicCreateDto) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            topicService.save(topicCreateDto, user);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("Error while creating topic", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
//
//    @PostMapping("/tag")
//    public ResponseEntity<TagDto> createTag(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody String tag) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            return ResponseEntity.ok(tagService.save(tag, user));
//        }catch (Exception e) {
//            log.error("Error while creating tag", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping("/reply")
//    public ResponseEntity<ReplyDto> createReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody String reply) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            return ResponseEntity.ok(replyService.save(reply, user));
//        }catch (Exception e) {
//            log.error("Error while creating reply", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping("/reply-vote")
//    public ResponseEntity<ReplyVoteDto> createReplyVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody String replyVote) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            return ResponseEntity.ok(replyVoteService.save(replyVote, user));
//        }catch (Exception e) {
//            log.error("Error while creating reply vote", e);
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/reply-vote/{id}")
//    public ResponseEntity<Void> deleteReplyVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable Long id) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            replyVoteService.delete(id, user);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            log.error("Error while deleting reply vote", e);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//    @DeleteMapping("/reply/{id}")
//    public ResponseEntity<Void> deleteReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable Long id) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            replyService.delete(id, user);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            log.error("Error while deleting reply", e);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
    @DeleteMapping("/topic/{id}")
    public ResponseEntity<Void> deleteTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("id") Long id) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            topicService.delete(id, user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error while deleting topic", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//
//    @DeleteMapping("/tag/{id}")
//    public ResponseEntity<Void> deleteTag(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable Long id) {
//        try {
//            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
//            tagService.delete(id, user);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            log.error("Error while deleting tag", e);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    public UsersDto throwIfJwtTokenIsInvalidElseReturnUser(String jwtToken) {
        UsersDto user = authClient.validateToken(jwtToken).getBody();

        if (user == null) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        return user;
    }
}
