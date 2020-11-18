package com.member;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberDao {
    Dotenv dotenv;
    private String driver = "com.mysql.jdbc.Driver";
    private String url;
    private String user;
    private String pw;

    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public MemberDao(){
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
                member.setAuthor(rs.getString("author"));
            }
            if(member.getName()!=null)
                return member;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public String insertMember(Member member, String author){
        try{
            String sql = "INSERT INTO member (name, team, author) VALUES (?,?,?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, member.getName());
            psmt.setString(2, member.getTeam());
            psmt.setString(3, author);
            psmt.executeUpdate();
        }
        catch (SQLException e){
            if(e instanceof MySQLIntegrityConstraintViolationException){
                e.printStackTrace();
                System.out.println(e.getSQLState());
                System.out.println(e.getMessage());
                System.out.println(e.getErrorCode());
                return "Duplicate PK";
            }
            return "DB Error";
        }
        return "Success";
    }
}
