package com.yigitcanyontem.content.repository;

import com.yigitcanyontem.content.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yigitcanyontem.clients.content.dto.ReplyDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findAllByTopicId(Long id, Pageable pageable);

    Page<Reply> findAllByCreatedByUserId(Integer id, Pageable pageable);

    Page<Reply> findAllByParentReplyId(Long id, Pageable pageable);

    Long countByTopicId(Long id);
}
