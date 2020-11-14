package com.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/track")
public class TrackController {
    @Autowired
    TrackDao dao;

    @RequestMapping("")
    public ModelAndView trackList(){
        ModelAndView mav = new ModelAndView();
        List<String> trackList = dao.getAllTracks();
        mav.addObject("trackList", trackList);
        mav.setViewName("track/trackList");
        return mav;
    }
    @RequestMapping("/detail")
    public ModelAndView trackDetail(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        String target = request.getParameter("target").replace("-", " ");
        Track track = dao.getTrackDetail(target);
        mav.addObject("target", track);
        mav.setViewName("track/trackDetail");
        return mav;
    }
    @RequestMapping("/register")
    public ModelAndView trackRegister(@ModelAttribute("track") Track track){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("track/trackRegister");
        return mav;
    }
    @RequestMapping(value = "/registercheck", method = RequestMethod.POST)
    public ModelAndView trackRegisterCheck(@ModelAttribute("track") Track track){
        ModelAndView mav = new ModelAndView();
        if(track.getAlbum().equals(""))
            track.setAlbum(null);
        if(track.getLyrics().equals(""))
            track.setLyrics(null);
        if(track.getYoutubeId().equals(""))
            track.setYoutubeId(null);
        String sqlResult = dao.insertTrack(track);
        if(sqlResult.equals("Duplicate PK"))
            mav.addObject("error", "이미 존재하는 곡입니다.");
        mav.setViewName("track/trackRegisterCheck");
        return mav;
    }
}
