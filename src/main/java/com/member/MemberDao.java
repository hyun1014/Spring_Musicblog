package com.member;

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
public class MemberDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getAllMembers(){
        List<String> memberList;
        try{
            String sql = "SELECT name FROM member";
            memberList = jdbcTemplate.query(sql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("name");
                }
            });
            if(!memberList.isEmpty())
                return memberList;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public Member getMemberDetail(String target){
        Member member;
        try{
            String sql = "SELECT * FROM member WHERE name=?";
            member = jdbcTemplate.queryForObject(sql, new Object[]{target}, new RowMapper<Member>() {
                @Override
                public Member mapRow(ResultSet resultSet, int i) throws SQLException {
                    Member member = new Member();
                    member.setName(resultSet.getString("name"));
                    member.setTeam(resultSet.getString("team"));
                    member.setAuthor(resultSet.getString("author"));
                    return member;
                }
            });
            if(member.getName()!=null)
                return member;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public String insertMember(Member member, String author){
        try{
            String sql = "INSERT INTO member (name, team, author) VALUES (?,?,?)";
            jdbcTemplate.update(sql, member.getName(), member.getTeam(), author);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            return "DB Error";
        }
        return "Success";
    }
    public String updateMember(String target, Member member){
        int res = 0;
        try{
            String sql = "UPDATE member SET name=?, team=? WHERE name=?";
            jdbcTemplate.update(sql, member.getName(), member.getTeam(), target);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            return "DB error";
        }
        return "Success";
    }
}
