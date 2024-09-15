package com.yigitcanyontem.content.service;

import com.yigitcanyontem.content.domain.Tag;
import com.yigitcanyontem.content.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.content.dto.TagCreateDto;
import org.yigitcanyontem.clients.content.dto.TagDto;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;
import org.yigitcanyontem.clients.users.dto.UsersDto;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;

    protected Set<Tag> getTags(Set<Long> tags) {
        return tagRepository.findAllByIdIn(tags);
    }

    public PaginatedResponse getPaginatedTags(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Tag> tagPage = tagRepository.findAll(pageRequest);
        return convertToPaginatedResponse(tagPage);
    }

    public PaginatedResponse searchTagByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Tag> tagPage = tagRepository.findAllByNameRegex(name, pageRequest);
        return convertToPaginatedResponse(tagPage);
    }

    private PaginatedResponse convertToPaginatedResponse(Page<Tag> tagPage) {
        return PaginatedResponse.builder()
                .data(tagPage.getContent().stream().parallel().map(this::convertToDto).collect(Collectors.toList()))
                .page(tagPage.getNumber())
                .size(tagPage.getSize())
                .totalElements(tagPage.getTotalElements())
                .totalPages(tagPage.getTotalPages())
                .build();
    }

    public TagDto update(TagDto tagDto, UsersDto user) {
        Tag tag = tagRepository.findById(tagDto.getId())
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        if (!tag.getCreatedByUserId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to update this tag");
        }

        tag.setName(tagDto.getName());
        tagRepository.saveAndFlush(tag);
        log.info("Tag with id {} updated by user {}", tag.getId(), user.getId());
        return convertToDto(tag);
    }

    public TagDto save(TagCreateDto tagCreateDto, UsersDto user) {
        Tag tag = Tag.builder()
                .name(tagCreateDto.getName())
                .createdAt(new Date())
                .createdByUserId(user.getId())
                .build();

        tagRepository.saveAndFlush(tag);
        log.info("Tag created by user {}", user.getId());
        return convertToDto(tag);
    }

    public void delete(Long id, UsersDto user) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        if (!tag.getCreatedByUserId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to delete this tag");
        }

        tagRepository.deleteById(id);
        log.info("Tag with id {} deleted by user {}", id, user.getId());
    }

    private TagDto convertToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
