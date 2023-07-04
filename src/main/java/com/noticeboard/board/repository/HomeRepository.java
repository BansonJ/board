package com.noticeboard.board.repository;

import com.noticeboard.board.domain.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Repository
public class HomeRepository {

    private final EntityManager em;

    public HomeRepository(EntityManager em) {
        this.em = em;
    }

    public List<Board> findAll(int startPage) {
        return em.createQuery("select b from Board b", Board.class)
                .setFirstResult(startPage)
                .setMaxResults(10)
                .getResultList();
    }

    public long totalBoard() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList()
                .size();
    }

    public List<Board> findSearch(String search, int startPage) {
        List<Board> board = em.createQuery("SELECT b FROM Board b WHERE b.title LIKE CONCAT('%',:search,'%')", Board.class)
                .setParameter("search", search)
                .setFirstResult(startPage)
                .setMaxResults(10)
                .getResultList();
        return board;
    }

    public long totalSearch(String search) {
        return em.createQuery("SELECT b FROM Board b WHERE b.title LIKE CONCAT('%',:search,'%')", Board.class)
                .setParameter("search", search)
                .getResultList()
                .size();
    }
}
