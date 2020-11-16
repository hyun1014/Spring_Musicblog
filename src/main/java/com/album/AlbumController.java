package com.album;

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
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumDao dao;

    @RequestMapping("")
    public ModelAndView albumList(HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        List<String> albumList = dao.getAllAlbums();
        mav.addObject("albumList", albumList);
        mav.setViewName("album/albumList");
        return mav;
    }
    @RequestMapping("/detail")
    public ModelAndView albumDetail(HttpServletRequest request, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        String target = request.getParameter("target").replace("-", " ");
        Album album = dao.getAlbumDetail(target);
        mav.addObject("target", album);
        mav.setViewName("album/albumDetail");
        List<String> trackList = dao.getTracksFromAlbum(target);
        mav.addObject("trackList", trackList);
        return mav;
    }
    @RequestMapping("/register")
    public ModelAndView albumRegister(@ModelAttribute("album") Album album, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            mav.addObject("user", loginUser.getNickname());
        mav.setViewName("album/albumRegister");
        return mav;
    }
    @RequestMapping(value = "/registercheck", method = RequestMethod.POST)
    public ModelAndView albumRegisterCheck(@ModelAttribute("album") Album album, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User loginUser = (User)session.getAttribute("user");
        String sqlResult = dao.insertAlbum(album, loginUser.getNickname());
        if(sqlResult.equals("Duplicate PK"))
            mav.addObject("error", "이미 존재하는 앨범입니다.");
        mav.setViewName("album/albumRegisterCheck");
        return mav;
    }
}
