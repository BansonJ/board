package com.noticeboard.board.service;

import com.noticeboard.board.domain.Member;
import com.noticeboard.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RegisterService {

    PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    private final MemberRepository memberRepository;

    public Member create(String id, String name, String passwd, String email, String nickname, String phone) {
        validateDuplicateNickname(nickname);
        validateDuplicateId(id);

        String passwdEncoder = passwordEncoder.encode(passwd);

        Member member = new Member();
        member.setEmail(email);
        member.setPasswd(passwdEncoder);
        member.setName(name);
        member.setId(id);
        member.setNickname(nickname);
        member.setPhone(phone);
        this.memberRepository.save(member);
        return member;
    }

    private void validateDuplicateNickname(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new IllegalStateException("닉네임이 이미 있소이다.");
                });
    }

    private void validateDuplicateId(String id) {
        memberRepository.idChk(id)
                .ifPresent(m -> {
                    throw new IllegalStateException("id가 이미 있소이다.");
                });
    }

    public Optional<Member> loginChk(String id, String passwd) {
        Optional<Member> member = memberRepository.idChk(id);
        if (!passwordEncoder.matches(passwd,member.get().getPasswd())){
            return null;
        }
        return member;
    }

}
