package com.yigitcanyontem.content.repository;

import com.yigitcanyontem.content.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yigitcanyontem.clients.content.dto.ReplyDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByTopicId(Long id);
}
