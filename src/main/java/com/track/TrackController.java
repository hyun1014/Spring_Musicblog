package com.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("detail")
    public ModelAndView trackDetail(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        String target = request.getParameter("target").replace("-", " ");
        Track track = dao.getTrackDetail(target);
        mav.addObject("target", track);
        mav.setViewName("track/trackDetail");
        return mav;
    }
}
