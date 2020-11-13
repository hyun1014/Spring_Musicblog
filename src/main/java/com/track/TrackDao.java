package com.track;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TrackDao {
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/musicblog?useSSL=false";
    private String user = "user";
    private String pw = "1014";

    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public TrackDao(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public List<String> getAllTracks(){
        List<String> trackList = new ArrayList<>();
        try{
            String sql = "SELECT title FROM track";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while(rs.next()){
                trackList.add(rs.getString("title"));
            }
            if(!trackList.isEmpty())
                return trackList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Track getTrackDetail(String target){
        Track track = new Track();
        try{
            String sql = "SELECT * FROM track WHERE title=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, target);
            rs = psmt.executeQuery();
            while(rs.next()){
                track.setTitle(rs.getString("title"));
                track.setArtist(rs.getString("artist"));
                track.setAlbum(rs.getString("album"));
                track.setLyrics(rs.getString("lyrics"));
                track.setYoutubeId(rs.getString("youtubeId"));
            }
            if(track.getTitle()!=null)
                return track;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
