package com.member;

import com.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberDao dao;

    @RequestMapping("")
    public ModelAndView memberList(HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        List<String> memberList = dao.getAllMembers();
        mav.addObject("memberList", memberList);
        mav.setViewName("member/memberList");
        return mav;
    }
    @RequestMapping("/detail")
    public ModelAndView memberDetail(HttpServletRequest request, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        String target = request.getParameter("target").replace("-", " ");
        Member member = dao.getMemberDetail(target);
        mav.addObject("target", member);
        mav.setViewName("member/memberDetail");
        return mav;
    }
    @RequestMapping("/register")
    public ModelAndView memberRegister(@ModelAttribute("mem") Member member, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        mav.setViewName("member/memberRegister");
        return mav;
    }
    @RequestMapping(value = "/registercheck", method = RequestMethod.POST)
    public ModelAndView memberRegisterCheck(@ModelAttribute("mem") Member member, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        String sqlResult = dao.insertMember(member, loginUser.getNickname());
        if(sqlResult.equals("Duplicate PK"))
            mav.addObject("error", "이미 존재하는 멤버입니다.");
        mav.setViewName("member/memberRegisterCheck");
        return mav;
    }
}
