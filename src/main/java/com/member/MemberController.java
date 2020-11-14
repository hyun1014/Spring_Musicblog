package com.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberDao dao;

    @RequestMapping("")
    public ModelAndView memberList(){
        ModelAndView mav = new ModelAndView();
        List<String> memberList = dao.getAllMembers();
        mav.addObject("memberList", memberList);
        mav.setViewName("member/memberList");
        return mav;
    }
    @RequestMapping("/detail")
    public ModelAndView memberDetail(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        String target = request.getParameter("target").replace("-", " ");
        Member member = dao.getMemberDetail(target);
        mav.addObject("target", member);
        mav.setViewName("member/memberDetail");
        return mav;
    }
    @RequestMapping("/register")
    public ModelAndView memberRegister(@ModelAttribute("mem") Member member){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("member/memberRegister");
        return mav;
    }
    @RequestMapping(value = "/registercheck", method = RequestMethod.POST)
    public ModelAndView memberRegisterCheck(@ModelAttribute("mem") Member member){
        ModelAndView mav = new ModelAndView();
        String sqlResult = dao.insertMember(member);
        if(sqlResult.equals("Duplicate PK"))
            mav.addObject("error", "이미 존재하는 멤버입니다.");
        mav.setViewName("member/memberRegisterCheck");
        return mav;
    }
}
