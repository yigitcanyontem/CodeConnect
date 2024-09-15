package com.yigitcanyontem.content.service;

import com.yigitcanyontem.content.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.content.dto.ReplyDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
    private final ReplyRepository replyRepository;

}
