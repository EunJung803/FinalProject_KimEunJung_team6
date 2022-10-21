package com.ll.exam.mutbooks.app.post.form.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class PostDto {
    @NotEmpty
    private String subject;
    @NotEmpty
    private String content;
    @NotEmpty
    private String contentHtml;
    private String keywords;
}