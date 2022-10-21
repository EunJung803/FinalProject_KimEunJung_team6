package com.ll.exam.mutbooks.app.postHashTag.repository;

import com.ll.exam.mutbooks.app.postHashTag.entity.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
    Optional<PostHashTag> findByPostIdAndPostKeywordId(Long id, Long id1);
}