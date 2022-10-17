package com.ll.exam.mutbooks.app.member.controller;

import com.ll.exam.mutbooks.app.member.entity.Member;
import com.ll.exam.mutbooks.app.member.form.JoinForm;
import com.ll.exam.mutbooks.app.member.service.MemberService;
import com.ll.exam.mutbooks.app.security.dto.MemberContext;
import com.ll.exam.mutbooks.app.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/member/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
        memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getEmail());

        return "redirect:/member/login?msg=" + Ut.url.encode("회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modify() {
        return "member/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, String email, String nickname) {
        Member member = memberService.findByUserId(memberContext.getId());

        memberService.modify(member, email, nickname);

        return "redirect:/member/modify?msg=" + Ut.url.encode("회원정보수정이 완료되었습니다.");
    }
}