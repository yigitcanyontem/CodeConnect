package com.yigitcanyontem.content.repository;

import com.yigitcanyontem.content.domain.Tag;
import com.yigitcanyontem.content.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Set<Tag> findAllByIdIn(Set<Long> tags);

    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:query%")
    Page<Tag> findAllByNameRegex(@Param("query") String query, Pageable pageable);

}
