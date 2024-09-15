package com.yigitcanyontem.content.service;

import com.yigitcanyontem.content.domain.Tag;
import com.yigitcanyontem.content.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;

    public Set<Tag> getTags(Set<Long> tags) {
        return tagRepository.findAllByIdIn(tags);
    }
}
