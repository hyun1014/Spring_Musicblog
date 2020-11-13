package com.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    ArtistDao dao;

    @RequestMapping("")
    public String artistList(Model model){
        List<String> artistList = dao.getAllArtists();
        model.addAttribute("artistList", artistList);
        return "artist/artistList";
    }
    @RequestMapping("/detail")
    public String artistDetail(Model model, HttpServletRequest request){
        String target = request.getParameter("target").replaceAll("-", " ");
        Artist artist = dao.getArtistDetail(target);
        model.addAttribute("target", artist);
        List<String> members = dao.getMembersInArtist(target);
        if(members!=null){
            model.addAttribute("memberList", members);
        }
        List<String> albums = dao.getAlbumsFromArtist(target);
        if(albums!=null){
            model.addAttribute("albumList", albums);
        }
        List<String> tracks = dao.getMembersInArtist(target);
        if(tracks!=null){
            model.addAttribute("trackList", tracks);
        }
        return "artist/artistDetail";
    }
}
