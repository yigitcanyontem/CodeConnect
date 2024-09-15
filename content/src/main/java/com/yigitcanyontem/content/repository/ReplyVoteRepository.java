package com.yigitcanyontem.content.repository;

import com.yigitcanyontem.content.domain.ReplyVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyVoteRepository extends JpaRepository<ReplyVote, Long> {
}
