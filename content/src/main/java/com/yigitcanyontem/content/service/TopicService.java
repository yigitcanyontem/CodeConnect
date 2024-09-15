package com.yigitcanyontem.content.service;

import com.yigitcanyontem.content.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicService {
    private final TopicRepository topicRepository;
}
