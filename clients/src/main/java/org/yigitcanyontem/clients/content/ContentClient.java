package org.yigitcanyontem.clients.content;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.content.dto.*;
import org.yigitcanyontem.clients.shared.dto.GenericResponse;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;

@FeignClient(name = "content")
public interface ContentClient {

    @GetMapping("/api/v1/content/topics-trending")
    ResponseEntity<PaginatedResponse> getTrendingTopics(@RequestParam(defaultValue = "0", name = "page") int page,
                                                        @RequestParam(defaultValue = "10", name = "size") int size);

    @GetMapping("/api/v1/content/topics/search")
    ResponseEntity<PaginatedResponse> searchTopics(@RequestParam(name = "query") String query,
                                                   @RequestParam(defaultValue = "0", name = "page") int page,
                                                   @RequestParam(defaultValue = "10", name = "size") int size);

    @GetMapping("/api/v1/content/topic/{slug}")
    ResponseEntity<TopicDto> getTopicBySlug(@PathVariable("slug") String slug);

    @PutMapping("/api/v1/content/topic")
    ResponseEntity<TopicDto> updateTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody TopicCreateDto topicUpdateDto);

    @PostMapping("/api/v1/content/topic")
    ResponseEntity<Void> createTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody TopicCreateDto topicCreateDto);

    @DeleteMapping("/api/v1/content/topic/{id}")
    ResponseEntity<Void> deleteTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("id") Long id);

    @GetMapping("/api/v1/content/reply/{id}")
    ResponseEntity<ReplyDto> getReplyById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/content/replies/topic/{topicId}")
    ResponseEntity<PaginatedResponse> getRepliesByTopicId(@PathVariable("topicId") Long topicId,
                                                          @RequestParam(defaultValue = "0", name = "page") int page,
                                                          @RequestParam(defaultValue = "10", name = "size") int size);

    @GetMapping("/api/v1/content/replies/user/{userId}")
    ResponseEntity<PaginatedResponse> getRepliesByUserId(@PathVariable("userId") Integer userId,
                                                         @RequestParam(defaultValue = "0", name = "page") int page,
                                                         @RequestParam(defaultValue = "10", name = "size") int size);

    @GetMapping("/api/v1/content/replies/reply/{replyId}")
    ResponseEntity<PaginatedResponse> getChildRepliesByReplyId(@PathVariable("replyId") Long replyId,
                                                               @RequestParam(defaultValue = "0", name = "page") int page,
                                                               @RequestParam(defaultValue = "10", name = "size") int size);

    @GetMapping("/api/v1/content/current-user-replies")
    ResponseEntity<PaginatedResponse> getCurrentUserReplies(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
                                                            @RequestParam(defaultValue = "0", name = "page") int page,
                                                            @RequestParam(defaultValue = "10", name = "size") int size);

    @PutMapping("/api/v1/content/reply")
    ResponseEntity<ReplyDto> updateReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody ReplyDto replyDto);

    @PostMapping("/api/v1/content/reply")
    ResponseEntity<ReplyDto> createReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody ReplyCreateDto createDto);

    @DeleteMapping("/api/v1/content/reply/{id}")
    ResponseEntity<Void> deleteReply(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable Long id);

    @PostMapping("/api/v1/content/reply-vote")
    ResponseEntity<GenericResponse> createReplyVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody ReplyVoteCreateDto createDto);

    @GetMapping("/api/v1/content/tags")
    ResponseEntity<PaginatedResponse> getPaginatedTags(@RequestParam(defaultValue = "0", name = "page") int page,
                                                       @RequestParam(defaultValue = "10", name = "size") int size);

    @GetMapping("/api/v1/content/tags/search/{name}")
    ResponseEntity<PaginatedResponse> searchTagByName(@PathVariable("name") String name,
                                                      @RequestParam(defaultValue = "0", name = "page") int page,
                                                      @RequestParam(defaultValue = "10", name = "size") int size);

    @PutMapping("/api/v1/content/tag")
    ResponseEntity<TagDto> updateTag(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody TagDto tagDto);

    @PostMapping("/api/v1/content/tag")
    ResponseEntity<TagDto> createTag(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody TagCreateDto tag);

    @DeleteMapping("/api/v1/content/tag/{id}")
    ResponseEntity<Void> deleteTag(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("id") Long id);
}