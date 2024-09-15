package com.yigitcanyontem.content.controller;

import com.yigitcanyontem.content.service.ReplyService;
import com.yigitcanyontem.content.service.ReplyVoteService;
import com.yigitcanyontem.content.service.TagService;
import com.yigitcanyontem.content.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/content")
@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final TopicService topicService;
    private final TagService tagService;
    private final ReplyService replyService;
    private final ReplyVoteService replyVoteService;

}
