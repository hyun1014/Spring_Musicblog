package com.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlbumDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getAllAlbums(){
        List<String> albumList;
        try{
            String sql = "SELECT title FROM album";
            albumList = jdbcTemplate.query(sql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("title");
                }
            });
            if(!albumList.isEmpty())
                return albumList;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public Album getAlbumDetail(String target){
        Album album;
        try{
            String sql = "SELECT * FROM album WHERE title=?";
            album = jdbcTemplate.queryForObject(sql, new Object[]{target}, new RowMapper<Album>() {
                @Override
                public Album mapRow(ResultSet resultSet, int i) throws SQLException {
                    Album album = new Album();
                    album.setTitle(resultSet.getString("title"));
                    album.setArtist(resultSet.getString("artist"));
                    album.setAuthor(resultSet.getString("author"));
                    return album;
                }
            });
            if(album.getTitle()!=null)
                return album;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getTracksFromAlbum(String target){
        List<String> trackList = new ArrayList<>();
        try{
            String sql = "SELECT title FROM track WHERE album=?";
            trackList = jdbcTemplate.query(sql, new Object[]{target}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("title");
                }
            });
            if(!trackList.isEmpty())
                return trackList;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public String insertAlbum(Album album, String author){
        int sqlResult = 0;
        try{
            String sql = "INSERT into album (title, artist, author) VALUES (?, ?, ?)";
            sqlResult = jdbcTemplate.update(sql, album.getTitle(), album.getArtist(), author);
        }
        catch (DataAccessException e){
            return "DB Error";
        }
        return "Success";
    }
}
