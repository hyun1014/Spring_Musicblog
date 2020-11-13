package com.member;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberDao {
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/musicblog?useSSL=false";
    private String user = "user";
    private String pw = "1014";

    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public MemberDao(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public List<String> getAllMembers(){
        List<String> memberList = new ArrayList<>();
        try{
            String sql = "SELECT name FROM member";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while(rs.next()){
                memberList.add(rs.getString("name"));
            }
            if(!memberList.isEmpty())
                return memberList;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public Member getMemberDetail(String target){
        Member member = new Member();
        try{
            String sql = "SELECT * FROM member WHERE name=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, target);
            rs = psmt.executeQuery();
            while(rs.next()){
                member.setName(rs.getString("name"));
                member.setTeam(rs.getString("team"));
            }
            if(member.getName()!=null)
                return member;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
