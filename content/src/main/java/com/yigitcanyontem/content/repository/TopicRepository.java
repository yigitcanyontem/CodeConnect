package com.yigitcanyontem.content.repository;

import com.yigitcanyontem.content.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yigitcanyontem.clients.content.dto.TopicDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t WHERE t.name LIKE %:query%")
    Page<Topic> findAllByNameRegex(@Param("query") String query, Pageable pageable);

    Optional<Topic> findBySlug(String slug);

    List<Topic> findAllByParentTopicId(Long id);
}
