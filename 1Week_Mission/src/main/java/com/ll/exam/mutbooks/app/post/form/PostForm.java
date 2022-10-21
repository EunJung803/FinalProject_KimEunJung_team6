package com.ll.exam.mutbooks.app.post.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class PostForm {
    @NotEmpty
    private String subject;
    @NotEmpty
    private String content;
    @NotEmpty
    private String contentHtml;
    private String keywords;
}