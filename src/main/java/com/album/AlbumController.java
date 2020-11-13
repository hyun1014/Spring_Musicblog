package com.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumDao dao;

    @RequestMapping("")
    public ModelAndView albumList(){
        ModelAndView mav = new ModelAndView();
        List<String> albumList = dao.getAllAlbums();
        mav.addObject("albumList", albumList);
        mav.setViewName("album/albumList");
        return mav;
    }
    @RequestMapping("/detail")
    public ModelAndView albumDetail(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        String target = request.getParameter("target").replace("-", " ");
        Album album = dao.getAlbumDetail(target);
        mav.addObject("target", album);
        mav.setViewName("album/albumDetail");
        List<String> trackList = dao.getTracksFromAlbum(target);
        mav.addObject("trackList", trackList);
        return mav;
    }
}
