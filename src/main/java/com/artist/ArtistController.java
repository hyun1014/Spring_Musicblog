package com.artist;

import com.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    ArtistDao dao;

    @RequestMapping("")
    public String artistList(Model model, HttpSession session){
        List<String> artistList = dao.getAllArtists();
        model.addAttribute("artistList", artistList);
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            model.addAttribute("user", loginUser.getNickname());
        return "artist/artistList";
    }
    @RequestMapping("/detail")
    public String artistDetail(Model model, HttpServletRequest request, HttpSession session){
        // 쿼리스트링에서 -로 바꿨던걸 다시 공백으로 변환 (실제 db 저장값은 공백이므로)
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            model.addAttribute("user", loginUser.getNickname());
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
    @RequestMapping("/register")
    public String artistRegister(Model model, @ModelAttribute("art") Artist artist, HttpSession session){
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            model.addAttribute("user", loginUser.getNickname());
        return "artist/artistRegister";
    }
    @RequestMapping(value = "/registercheck", method = RequestMethod.POST)
    public String artistRegisterCheck(@ModelAttribute("art") Artist artist, Model model, HttpSession session){
        User loginUser = (User)session.getAttribute("user");
        if(loginUser!=null)
            model.addAttribute("user", loginUser.getNickname());
        // 입력값이 비어있으면 null로 바꿈
        if(artist.getCompany().equals(""))
            artist.setCompany(null);
        if(artist.getArtistInfo().equals(""))
            artist.setArtistInfo(null);
        String sqlResult = dao.insertArtist(artist, loginUser.getNickname());
        if(sqlResult.equals("Duplicate PK"))
            model.addAttribute("error", "이미 존재하는 아티스트입니다.");
        return "artist/artistRegisterCheck";
    }
}
