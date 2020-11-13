package com.artist;

import com.member.Member;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ArtistDao {
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/musicblog?useSSL=false";
    private String user = "user";
    private String pw = "1014";

    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public List<String> getAllArtists(){
        List<String> artistList = new ArrayList<>();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT name FROM artist";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while(rs.next()){
                artistList.add(rs.getString(1));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return artistList;
    }
    public Artist getArtistDetail(String target){
        Artist artist = new Artist();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM artist WHERE name=? LIMIT 1";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, target);
            rs = psmt.executeQuery();
            while(rs.next()){
                artist.setName(rs.getString("name"));
                if(rs.getString("company")!=null)
                    artist.setCompany(rs.getString("company"));
                if(rs.getString("artistInfo")!=null)
                    artist.setArtistInfo(rs.getString("artistInfo"));
            }
            if(artist.getName()!=null)
                return artist;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getMembersInArtist(String artist){
        List<String> list = new ArrayList<>();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT name FROM member WHERE team=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, artist);
            rs = psmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("name"));
            }
            if(!list.isEmpty())
                return list;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getAlbumsFromArtist(String artist){
        List<String> list = new ArrayList<>();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT title FROM album WHERE artist=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, artist);
            rs = psmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("title"));
            }
            if(!list.isEmpty())
                return list;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getTracksFromArtist(String artist){
        List<String> list = new ArrayList<>();
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT title FROM track WHERE artist=? LIMIT 5";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, artist);
            rs = psmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("title"));
            }
            if(!list.isEmpty())
                return list;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
