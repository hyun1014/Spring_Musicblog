package com.album;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlbumDao {
    Dotenv dotenv;
    private String driver = "com.mysql.jdbc.Driver";
    private String url;
    private String user;
    private String pw;

    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public AlbumDao(){
        try{
            dotenv = Dotenv.configure().directory("./").filename(".env").load();
            url = dotenv.get("url");
            user = dotenv.get("user");
            pw = dotenv.get("pw");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public List<String> getAllAlbums(){
        List<String> albumList = new ArrayList<>();
        try{
            String sql = "SELECT title FROM album";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while(rs.next()){
                albumList.add(rs.getString("title"));
            }
            if(!albumList.isEmpty())
                return albumList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Album getAlbumDetail(String target){
        Album album = new Album();
        try{
            String sql = "SELECT * FROM album WHERE title=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, target);
            rs = psmt.executeQuery();
            while(rs.next()){
                album.setTitle(rs.getString("title"));
                album.setArtist(rs.getString("artist"));
            }
            if(album.getTitle()!=null)
                return album;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getTracksFromAlbum(String target){
        List<String> trackList = new ArrayList<>();
        try{
            String sql = "SELECT title FROM track WHERE album=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, target);
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
    public String insertAlbum(Album album, String author){
        try{
            String sql = "INSERT into album (title, artist, author) VALUES (?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, album.getTitle());
            psmt.setString(2, album.getArtist());
            psmt.setString(3, author);
            psmt.executeUpdate();
        }
        catch (SQLException e){
            if(e instanceof MySQLIntegrityConstraintViolationException)
                return "Duplicate PK";
            return "DB Error";
        }
        return "Success";
    }
}
