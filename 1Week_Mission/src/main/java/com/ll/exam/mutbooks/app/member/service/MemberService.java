package com.ll.exam.mutbooks.app.member.service;

import com.ll.exam.mutbooks.app.member.entity.Member;
import com.ll.exam.mutbooks.app.member.exception.AlreadyJoinException;
import com.ll.exam.mutbooks.app.member.repository.MemberRepository;
import com.ll.exam.mutbooks.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String email) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyJoinException();
        }

        int authLevel = 3;  // 일반 회원

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .authLevel(authLevel)
                .build();

        memberRepository.save(member);

        return member;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member findByUserId(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public void modify(Member member, String email, String nickname) {
        member.setEmail(email);
        member.setNickname(nickname);   // -> 작가 회원으로 변경 (권한 변동 되어야함)

        memberRepository.save(member);
    }

    public boolean passwordCheck(Member member, String oldPassword) {
        if (passwordEncoder.matches(oldPassword, member.getPassword())) {
            return true;
        }
        else {
            return false;
        }
    }

    public void modifyPassword(Member member, String newPassword) {
        member.setPassword(passwordEncoder.encode(newPassword));

        memberRepository.save(member);
    }
}
