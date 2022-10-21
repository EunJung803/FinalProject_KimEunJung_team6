package com.ll.exam.mutbooks.app.postHashTag.repository;

import com.ll.exam.mutbooks.app.postHashTag.entity.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
}