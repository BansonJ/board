package com.noticeboard.board.controller;

import com.noticeboard.board.domain.Board;
import com.noticeboard.board.domain.Comment;
import com.noticeboard.board.domain.Member;
import com.noticeboard.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.noticeboard.board.controller.HomeController.LOGIN_MEMBER;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boardDetail")
    public String boardHome(@RequestParam("number") int number,
                            @RequestParam(value = "commentPage" ,required = false, defaultValue = "1") int commentPage,
                            Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        Board board = boardService.findByNumber(number);

        int CPage = commentPage;
        model.addAttribute("CPage", CPage);

        int totalCPage = boardService.findAllComment(number).size();
        if (totalCPage%10==0){
            totalCPage = totalCPage/10;
        }
        else {
            totalCPage = totalCPage/10+1;
        }
        model.addAttribute("totalCPage", totalCPage);

        List<Comment> comments = boardService.findCommentOfPage(number, (CPage-1)*10);
        model.addAttribute("comments", comments);

        if (session != null) {
            Optional<Member> loginMember = (Optional<Member>) session.getAttribute(LOGIN_MEMBER);
            model.addAttribute("loginUser", loginMember.get().getNickname());
            model.addAttribute("board", board);
            model.addAttribute("commentUser", loginMember.get().getNickname());
            if (loginMember.get().getNickname().equals(board.getMem_nickname())) {
                model.addAttribute("user", "yes");
            }
            return "board/boardDetail";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/boardDetail")
    public String boardHome(@RequestParam(value = "num", required = false) int number, Model model, HttpServletRequest httpServletRequest,
                            String comment) {
        HttpSession session = httpServletRequest.getSession(false);
        Board board = boardService.findByNumber(number);

        if (session != null) {
            Optional<Member> loginMember = (Optional<Member>) session.getAttribute(LOGIN_MEMBER);
            model.addAttribute("loginUser", loginMember.get().getNickname());

            Comment comment1 = new Comment();
            comment1.setComment(comment);
            comment1.setBoard_number(number);
            comment1.setMem_nickname(loginMember.get().getNickname());

            boardService.createComment(comment1);

            model.addAttribute("board", board);
            return "redirect:/board/boardDetail?number=" + number;
        } else {
            return "redirect:/main";
        }
    }

    @GetMapping("/createBoard")
    public String createBoard(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null) {
            return "board/createBoard";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/createBoard")
    public String createBoard(Model model, String title, String word, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null) {
            Optional<Member> loginMember = (Optional<Member>) session.getAttribute(LOGIN_MEMBER);
            model.addAttribute("loginUser", loginMember.get().getNickname());

            Board board = new Board();
            board.setTitle(title);
            board.setWord(word);
            board.setMem_nickname(loginMember.get().getNickname());

            boardService.createBoard(board);

            return "redirect:/main";
        } else {
            return "redirect:board/boardDetail";
        }
    }

    @GetMapping("/editBoard")
    public String editBoard(@RequestParam("number") int number, Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        Board board = boardService.findByNumber(number);


        if (session != null || session.getAttributeNames().equals(board.getMem_nickname())) {
            Optional<Member> loginMember = (Optional<Member>) session.getAttribute(LOGIN_MEMBER);
            model.addAttribute("loginUser", loginMember.get().getNickname());

            model.addAttribute("board", board);
            return "board/editBoard";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("/editBoard")
    public String editBoarding(@RequestParam("number") int number, String title, String word, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null) {
            boardService.editBoard(number, title, word);
            return "redirect:/board/boardDetail?number="+number;
        } else {
            return "redirect:/main";
        }
    }

    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("number") int number) {
        boardService.deleteBoard(number);
        return "redirect:/main";
    }

    @GetMapping("/deleteComment")
    public String deleteOneComment(@RequestParam("commentId") int commentId, @RequestParam("number") int number) {
        boardService.deleteOneComment(commentId);
        return "redirect:/board/boardDetail?number="+number;
    }

    @GetMapping("/editComment")
    public String editOneComment(@RequestParam("commentId") int commentId, @RequestParam("number") int number,
                                 @RequestParam(value = "commentPage" ,required = false, defaultValue = "1") int commentPage,
                                 Model model, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        Board board = boardService.findByNumber(number);

        if (session != null) {
            Optional<Member> loginMember = (Optional<Member>) session.getAttribute(LOGIN_MEMBER);
            model.addAttribute("loginUser", loginMember.get().getNickname());
            model.addAttribute("board", board);
            model.addAttribute("commentUser", loginMember.get().getNickname());
            model.addAttribute("commentId", commentId);

            if (loginMember.get().getNickname().equals(board.getMem_nickname())) {
                model.addAttribute("user", "yes");
            }

            model.addAttribute("comments", boardService.findCommentOfPage(number, (commentPage-1)*10));
            return "board/editComment";
        } else {
            return "redirect:/main";
        }
    }

    @PostMapping("editComment")
    public String editComment(@RequestParam("number") int number, @RequestParam("commentId") int commentId,
                              @RequestParam("editComment") String editComment) {
        Comment comment1 = boardService.editComment(commentId, editComment);

        return "redirect:/board/boardDetail?number="+number;
    }
}
