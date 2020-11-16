package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    UserDao dao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpSession session){
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            model.addAttribute("user", loginUser.getNickname());
        return "home";
    }
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session, @ModelAttribute("user") User user){
        return "login";
    }
    @RequestMapping(value = "logincheck", method = RequestMethod.POST)
    public String loginCheck(Model model, @ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes){
        User loginUser = dao.getUserInfo(user.getId(), user.getPw());
        if(loginUser!=null){
            loginUser.setPw(null);
            session.setAttribute("user", loginUser);
        }
        else{
            redirectAttributes.addFlashAttribute("loginError", true);
        }
        return "redirect:/";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String joinUser(Model model, @ModelAttribute("user") User user){
        return "joinUser";
    }
    @RequestMapping(value = "/joincheck", method = RequestMethod.POST)
    public String joinCheck(Model model, @ModelAttribute("user") User user){
        String sqlResult = dao.insertUser(user);
        return "joinCheck";
    }
}
