package com.ll.exam.mutbooks.app.post.entity;

import com.ll.exam.mutbooks.app.base.entity.BaseEntity;
import com.ll.exam.mutbooks.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.File;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Post extends BaseEntity {
    @ManyToOne
    private Member author;
    @Column(unique = true)
    private String subject;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;
}