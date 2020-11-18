package com.artist;

import com.Test;
import com.member.Member;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ArtistDao {
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public ArtistDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getAllArtists(){
        List<String> artistList = new ArrayList<>();
        String sql = "SELECT name FROM artist";
        artistList = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("name");
            }
        });
        if(artistList.isEmpty())
            return null;
        return artistList;
    }
    public Artist getArtistDetail(String target){
        String sql = "SELECT * FROM artist WHERE name=? LIMIT 1";
        try{
            Artist artist = jdbcTemplate.queryForObject(sql, new Object[]{target}, new RowMapper<Artist>(){
                @Override
                public Artist mapRow(ResultSet rs, int i) throws SQLException {
                    Artist artist = new Artist();
                    artist.setName(rs.getString("name"));
                    artist.setCompany(rs.getString("company"));
                    artist.setArtistInfo(rs.getString("artistInfo"));
                    artist.setAuthor(rs.getString("author"));
                    return artist;
                }
            });
            if(artist.getName()!=null)
                return artist;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getMembersInArtist(String artist){
        List<String> list = new ArrayList<>();
        try{
            String sql = "SELECT name FROM member WHERE team=?";
            list = jdbcTemplate.query(sql, new Object[]{artist}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("name");
                }
            });
            if(!list.isEmpty())
                return list;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> getAlbumsFromArtist(String artist){
        List<String> list = new ArrayList<>();
        try{
            String sql = "SELECT title FROM album WHERE artist=?";
            list = jdbcTemplate.query(sql, new Object[]{artist}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("title");
                }
            });
            if(!list.isEmpty())
                return list;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public String insertArtist(Artist artist, String author){
        int res = 0;
        try{
            String sql = "INSERT INTO artist (name, company, artistInfo, author) values (?, ?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, artist.getName());
            if(artist.getCompany()!=null)
                psmt.setString(2, artist.getCompany());
            else
                psmt.setString(2, null);
            if(artist.getArtistInfo()!=null)
                psmt.setString(3, artist.getArtistInfo());
            else
                psmt.setString(3, null);
            psmt.setString(4, author);
            res = psmt.executeUpdate();
        }
        catch (SQLException e){
            if(e instanceof MySQLIntegrityConstraintViolationException)
                return "Duplicate PK";
            return "DB Error";
        }
        return "Success";
    }
}
