package com.artist;

import com.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String artistUpdate(Model model, HttpServletRequest request, @ModelAttribute("art") Artist artist){
        Artist formerArtist = dao.getArtistDetail(request.getParameter("target"));
        artist.setName(formerArtist.getName());
        artist.setCompany(formerArtist.getCompany());
        artist.setArtistInfo(formerArtist.getArtistInfo());
        model.addAttribute("target", formerArtist.getName());
        return "artist/artistUpdate";
    }
    @RequestMapping(value = "/updatecheck", method = RequestMethod.POST)
    public String artistUpdateCheck(Model model, HttpServletRequest request, @ModelAttribute("art") Artist artist, RedirectAttributes rattr){
        Artist formerArtist = dao.getArtistDetail(request.getParameter("target"));
        if(artist.getCompany().equals(""))
            artist.setCompany(null);
        if(artist.getArtistInfo().equals(""))
            artist.setArtistInfo(null);
//        // 0. sql문에 value를 직접 다 입력 (바뀐 값만 업데이트)
//        Map<String, String> map = new HashMap<>();
//        if(!artist.getName().equals(formerArtist.getName()))
//            map.put("name", artist.getName());
//        if(artist.getCompany()==null)
//            map.put("company", null);
//        else if(!artist.getCompany().equals(formerArtist.getCompany()))
//            map.put("company", artist.getCompany());
//        if(artist.getArtistInfo()==null)
//            map.put("artistInfo", null);
//        else if(!artist.getArtistInfo().equals(formerArtist.getArtistInfo()))
//            map.put("artistInfo", artist.getArtistInfo());
//        dao.updateArtist(formerArtist.getName(), map);
        // 1. 그냥 싹 다 업데이트
        dao.updateArtist(formerArtist.getName(), artist);
        rattr.addAttribute("target", artist.getName());
        return "redirect:/artist/detail";
    }
}
