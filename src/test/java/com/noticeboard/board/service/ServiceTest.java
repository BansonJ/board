package com.noticeboard.board.service;

import com.noticeboard.board.domain.Board;
import com.noticeboard.board.domain.Comment;
import com.noticeboard.board.domain.Member;
import com.noticeboard.board.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    RegisterService registerService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardService boardService;

    @Test
    void create() {
        //given
        Member member = new Member();
        member.setId("aaa");
        member.setName("aaaaa");
        member.setEmail("aaaaa@aaaaa");
        member.setPasswd("aaaa");
        member.setNickname("aaaaha");
        member.setPhone("aaaaa");

        //when
        Member saveId = registerService.create(member.getId(),member.getName(),member.getEmail(),
                member.getPasswd(),member.getNickname(),member.getPhone());

        //then
        org.assertj.core.api.Assertions.assertThat(member.getId()).isEqualTo("aaa");
        org.assertj.core.api.Assertions.assertThat(member.getName()).isEqualTo("aaaaa");
        org.assertj.core.api.Assertions.assertThat(member.getEmail()).isEqualTo("aaaaa@aaaaa");
        org.assertj.core.api.Assertions.assertThat(member.getPasswd()).isEqualTo("aaaa");
        org.assertj.core.api.Assertions.assertThat(member.getNickname()).isEqualTo("aaaaha");
        org.assertj.core.api.Assertions.assertThat(member.getPhone()).isEqualTo("aaaaa");

    }

    @Test
    void login() {
        //givin
        Member member = new Member();
        member.setId("aa");
        member.setName("aaaaa");
        member.setEmail("aaaaa@aaaaa");
        member.setPasswd("aaaa");
        member.setNickname("aaaaha");
        member.setPhone("aaaaa");

        //when
        Member saveId = registerService.create(member.getId(),member.getName(),member.getPasswd(),
                member.getEmail(), member.getNickname(),member.getPhone());
        Optional<Member> chk = registerService.loginChk("aa", "aaaa");
        System.out.println(chk);

        //then
        org.assertj.core.api.Assertions.assertThat(chk.get().getId()).isEqualTo(saveId.getId());
        org.assertj.core.api.Assertions.assertThat(chk.get().getPasswd()).isEqualTo(saveId.getPasswd());

    }

    @Test
    void createComment() {
        //givin
        Comment comment = new Comment();
        comment.setComment("aa");
        comment.setBoard_number(2);
        comment.setMem_nickname("aaa");

        //when
        boardService.createComment(comment);
        List<Comment> comment1 = boardService.findByComment(2);

                //then
        org.assertj.core.api.Assertions.assertThat(comment).isEqualTo(comment1);
    }


}
