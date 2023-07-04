package com.noticeboard.board.controller;

import com.noticeboard.board.domain.Board;
import com.noticeboard.board.domain.Member;
import com.noticeboard.board.model.MemberCreateForm;
import com.noticeboard.board.service.HomeService;
import com.noticeboard.board.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    final static String LOGIN_MEMBER = "login_member";
    private final RegisterService registerService;
    private final HomeService homeService;

    @Autowired
    public HomeController(RegisterService registerService, HomeService homeService) {
        this.registerService = registerService;
        this.homeService = homeService;
    }

    @GetMapping("/main")
    public String Home(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int page,
                       Model model, HttpServletRequest httpServletRequest) {
        String loginStatus;
        HttpSession session = httpServletRequest.getSession(false);

        int nowPage = page;
        model.addAttribute("nowPage", nowPage);

        long totalPage = homeService.totalBoard();
        if (totalPage%10==0){
            totalPage = totalPage/10;
        }
        else {
            totalPage = totalPage/10+1;
        }
        model.addAttribute("totalPage", totalPage);

        List<Board> boards = homeService.findAll((nowPage-1)*10);
        model.addAttribute("boards", boards);

        if (session == null) {
            loginStatus = "logout";
            model.addAttribute("loginStatus", loginStatus);
            return "main/home";
        }
        if (session != null) {
            Optional<Member> loginMember = (Optional<Member>) session.getAttribute(LOGIN_MEMBER);
            loginStatus = "login";
            model.addAttribute("loginUser", loginMember.get().getNickname());
            model.addAttribute("loginStatus", loginStatus);

            return "main/home";
        }
        return "main/home";
    }

    @GetMapping("main/search")
    public String searchHome(@RequestParam(value = "nowPage", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "search", required = false) String search, Model model, HttpServletRequest httpServletRequest) {
        String loginStatus;
        HttpSession session = httpServletRequest.getSession(false);

        int nowPage = page;
        model.addAttribute("nowPage", nowPage);

        long totalPage = homeService.totalSearch(search)/10 + 1;
        model.addAttribute("totalPage", totalPage);

        List<Board> searchResult = homeService.findSearch(search, (nowPage-1)*10);
        model.addAttribute("searches", searchResult);

        model.addAttribute("search", search);

        if (session == null) {
            loginStatus = "logout";
            model.addAttribute("loginStatus", loginStatus);
            return "main/search";
        }
        if (session != null) {
            Optional<Member> loginMember = (Optional<Member>) session.getAttribute(LOGIN_MEMBER);
            loginStatus = "login";
            model.addAttribute("loginUser", loginMember.get().getNickname());
            model.addAttribute("loginStatus", loginStatus);

            return "main/search";
        }
        return "main/search";
    }


    @GetMapping("/main/register")
    public String register() {
        return "main/register";
    }

    @PostMapping("/main/register")
    public String register(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "main/loginError";
        }

        if (!memberCreateForm.getPasswd().equals(memberCreateForm.getPasswd2())) {
            bindingResult.rejectValue("passwd2", "passwordInCorrect",
                    "패스워드가 일치하지 않습니다.");
            return "main/loginError";
        }

        registerService.create(memberCreateForm.getId(), memberCreateForm.getName(), memberCreateForm.getPasswd(),
                memberCreateForm.getEmail(), memberCreateForm.getNickname(), memberCreateForm.getPhone());

        return "redirect:/main";
    }

    @GetMapping("/main/login")
    public String login() {
        return "main/login";
    }

    @PostMapping("/main/login")
    public String login(String id, String passwd, HttpServletRequest httpServletRequest) {
        try {
            Optional<Member> member = registerService.loginChk(id, passwd);

            if (member.isPresent()) {

                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute(LOGIN_MEMBER, member);

                return "redirect:/main";
            }
        } catch (Exception e) {
            System.out.println("여기문제");

            return "main/loginError";
        }
        System.out.println("저어어기문제");

        return "main/loginError";
    }

    @GetMapping("/main/logout")
    public String logout(HttpServletRequest httpServletRequest) {

        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();   // 세션 날림
        }

        return "redirect:/main";
    }


}
