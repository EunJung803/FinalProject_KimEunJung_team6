package com.ll.exam.mutbooks.app.member.form.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class JoinDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirm;
    @NotEmpty
    private String email;
    private String nickname;
}