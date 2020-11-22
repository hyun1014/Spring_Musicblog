package com.track;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TrackDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getAllTracks(){
        List<String> trackList = new ArrayList<>();
        try{
            String sql = "SELECT title FROM track";
            trackList = jdbcTemplate.query(sql, new RowMapper<String>() {
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
    public Track getTrackDetail(String target){
        Track track;
        try{
            String sql = "SELECT * FROM track WHERE title=?";
            track = jdbcTemplate.queryForObject(sql, new Object[]{target}, new RowMapper<Track>() {
                @Override
                public Track mapRow(ResultSet resultSet, int i) throws SQLException {
                    Track track = new Track();
                    track.setTitle(resultSet.getString("title"));
                    track.setArtist(resultSet.getString("artist"));
                    track.setAlbum(resultSet.getString("album"));
                    track.setLyrics(resultSet.getString("lyrics"));
                    track.setYoutubeId(resultSet.getString("youtubeId"));
                    track.setAuthor(resultSet.getString("author"));
                    return track;
                }
            });
            if(track.getTitle()!=null)
                return track;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public String insertTrack(Track track, String author){
        try{
            String sql = "INSERT INTO track (title, artist, album, lyrics, youtubeId, author) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, track.getTitle(), track.getArtist(), track.getAlbum(), track.getLyrics(), track.getYoutubeId(), author);
        }
        catch (DataAccessException e){
            return "DB Error";
        }
        return "Success";
    }
    public String updateTrack(String target, Track track){
        try{
            String sql = "UPDATE track SET title=?, artist=?, album=?, lyrics=?, youtubeId=? WHERE title=?";
            jdbcTemplate.update(sql, track.getTitle(), track.getArtist(), track.getAlbum(), track.getLyrics(), track.getYoutubeId(), target);
        }
        catch (DataAccessException e){
            return "DB error";
        }
        return "Success";
    }
}
