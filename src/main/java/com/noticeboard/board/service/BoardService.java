package com.noticeboard.board.service;

import com.noticeboard.board.domain.Board;
import com.noticeboard.board.domain.Comment;
import com.noticeboard.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(Board board) {
        boardRepository.createBoard(board);
        return board;
    }

    public Board findByNumber(int number) {
        return boardRepository.findByNumber(number);
    }

    public Board editBoard(int number, String title, String word) {
        Board board = boardRepository.findByNumber(number);

        board.setTitle(title);
        board.setWord(word);

       return board;
    }

    public Board deleteBoard(int number) {
        boardRepository.deleteAllComment(number);
        return boardRepository.deleteBoard(number);
    }

    public Comment createComment(Comment comment) {
        return boardRepository.createComment(comment);
    }

    public List<Comment> findCommentOfPage(int board_number, int commentPage) {
        return boardRepository.findCommentOfPage(board_number, commentPage);
    }

    public List<Comment> findAllComment(int board_number) {
        return boardRepository.findAllComment(board_number);
    }

    public Comment deleteOneComment(int id) {
        return boardRepository.deleteOneComment(id);
    }

    public Comment editComment(int commentId, String editComment) {
        Comment comment = boardRepository.findByCommentId(commentId);
        comment.setComment(editComment);
        return comment;
    }
}
