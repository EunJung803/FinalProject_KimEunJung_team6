package com.ll.exam.mutbooks.app.member.controller;

import com.ll.exam.mutbooks.app.member.entity.Member;
import com.ll.exam.mutbooks.app.member.form.dto.JoinDto;
import com.ll.exam.mutbooks.app.member.service.MemberService;
import com.ll.exam.mutbooks.app.security.dto.MemberContext;
import com.ll.exam.mutbooks.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

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
    public String join(@Valid JoinDto joinDto) {
        memberService.join(joinDto.getUsername(), joinDto.getPassword(), joinDto.getEmail());

        return "redirect:/member/login?msg=" + Ut.url.encode("회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberService.findByUserId(memberContext.getId());

        model.addAttribute("member", member);

        return "member/profile";
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String modifyPassword() {
        return "member/modifyPassword";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal MemberContext memberContext, String oldPassword, String newPassword) {
        Member member = memberService.findByUserId(memberContext.getId());

        if (!memberService.passwordCheck(member, oldPassword)) {
            return "redirect:/member/modifyPassword?errorMsg=" + Ut.url.encode("비밀번호가 일치하지 않습니다.");
        }

        memberService.modifyPassword(member, newPassword);

        return "redirect:/member/modifyPassword?msg=" + Ut.url.encode("비밀번호 변경이 완료되었습니다.");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public String findUsername() {
        return "member/findUsername";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/findUsername")
    public String findUsername(String email) {
        Optional<Member> memberUsername = memberService.findByEmail(email);

        if (memberUsername.isEmpty()) {
            return "redirect:/member/findUsername?errorMsg=" + Ut.url.encode("존재하지 않는 이메일 입니다.");
        }

        return "redirect:/member/findUsername?msg=" + Ut.url.encode("회원님의 아이디는 " + memberUsername.get().getUsername() + " 입니다.");
    }
}