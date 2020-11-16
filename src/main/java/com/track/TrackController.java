package com.track;

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
@RequestMapping("/track")
public class TrackController {
    @Autowired
    TrackDao dao;

    @RequestMapping("")
    public ModelAndView trackList(HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        List<String> trackList = dao.getAllTracks();
        mav.addObject("trackList", trackList);
        mav.setViewName("track/trackList");
        return mav;
    }
    @RequestMapping("/detail")
    public ModelAndView trackDetail(HttpServletRequest request, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        String target = request.getParameter("target").replace("-", " ");
        Track track = dao.getTrackDetail(target);
        mav.addObject("target", track);
        mav.setViewName("track/trackDetail");
        return mav;
    }
    @RequestMapping("/register")
    public ModelAndView trackRegister(@ModelAttribute("track") Track track, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        mav.setViewName("track/trackRegister");
        return mav;
    }
    @RequestMapping(value = "/registercheck", method = RequestMethod.POST)
    public ModelAndView trackRegisterCheck(@ModelAttribute("track") Track track, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(track.getAlbum().equals(""))
            track.setAlbum(null);
        if(track.getLyrics().equals(""))
            track.setLyrics(null);
        if(track.getYoutubeId().equals(""))
            track.setYoutubeId(null);
        String sqlResult = dao.insertTrack(track, loginUser.getNickname());
        if(sqlResult.equals("Duplicate PK"))
            mav.addObject("error", "이미 존재하는 곡입니다.");
        mav.setViewName("track/trackRegisterCheck");
        return mav;
    }
}
