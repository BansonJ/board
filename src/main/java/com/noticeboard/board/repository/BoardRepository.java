package com.noticeboard.board.repository;

import com.noticeboard.board.domain.Board;
import com.noticeboard.board.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    public BoardRepository(EntityManager em) {
        this.em = em;
    }
    @PersistenceContext
    private final EntityManager em;

    public Board createBoard(Board board) {
        em.persist(board);
        return board;
    }

    public Board findByNumber(int number) {
        Board board = em.find(Board.class, number);
        em.flush();
        return board;
    }

    public Board deleteBoard(int number) {
        Board board = this.findByNumber(number);
        em.remove(board);
        return board;
    }

    public Comment createComment(Comment comment) {
        em.persist(comment);
        em.flush();
        return comment;
    }

    public List<Comment> findCommentOfPage(int board_number, int commentPage) {
        List<Comment> resultList = em.createQuery("select c from Comment c where c.board_number = :board_number", Comment.class)
                .setParameter("board_number", board_number)
                .setFirstResult(commentPage)
                .setMaxResults(10)
                .getResultList();
        return resultList;
    }

    public List<Comment> findAllComment(int board_number) {
        List<Comment> resultList = em.createQuery("select c from Comment c where c.board_number = :board_number", Comment.class)
                .setParameter("board_number", board_number)
                .getResultList();
        return resultList;
    }

    public List<Comment> deleteAllComment(int board_number) {
        List<Comment> comments = em.createQuery("select c from Comment c where c.board_number = :board_number", Comment.class)
                .setParameter("board_number", board_number)
                .getResultList();
        comments.forEach(comment -> em.remove(comment));
        return comments;
    }

    public Comment findByCommentId(int id) {
        Comment comment = em.createQuery("select c from Comment c where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getSingleResult();
        return comment;
    }

    public Comment deleteOneComment(int id) {
        Comment comment = this.findByCommentId(id);
        em.remove(comment);
        return comment;
    }
}
