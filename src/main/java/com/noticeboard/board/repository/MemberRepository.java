package com.noticeboard.board.repository;

import com.noticeboard.board.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public List<Member> findAll() {
        return em.createQuery("select m from member m", Member.class)
                .getResultList();
    }

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Optional<Member> findByNickname(String nickname) {
        return Optional.ofNullable(em.find(Member.class, nickname));
    }

    public Optional<Member> idChk(String id) {
        List<Member> result = em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id",id)
                .getResultList();

        return result.stream().findAny();
    }
}
