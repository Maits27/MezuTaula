package helper.db;

import helper.info.MessageInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class MySQLdb {
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String passwd = "root";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private Connection conn;

    public MySQLdb() throws SQLException{
        try{
            Class.forName(this.driver).newInstance(); //TODO Nuevo
            this.conn = DriverManager.getConnection(this.url,this.user,this.passwd);
            System.out.println("KONEXIOA EZARRITA DB-RA");
        }
        catch(Exception e){
            System.out.println("Errorea egon da datu basea kargatzean. Mesedez datu-datu basera konektatu eta saiatu berriro");
            System.out.println("Errorea: " +e.toString()+" "+e.getMessage());
            System.exit(0);
        }
    }

    //users taulan erabiltzaile berri bat sartu
    public void setUserInfo(String email, String password, String username) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO mezutaula.users VALUES(?, ?, ?)"); //TODO SERGIO
        ps.setString(1, email);
        ps.setString(2, password);
        ps.setString(3, username);
        ps.executeUpdate();
    }

    //users taulatik erabiltzaile baten izena atera
    public String getUsername(String email, String password) throws SQLException{
        PreparedStatement ps = conn.prepareStatement(
                "SELECT mezutaula.username " +
                        "FROM mezutaula.users " +
                        "WHERE email like ? AND password like ?"
        );
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) return rs.getString(1);
        else return "IT DOESN'T EXIST";
    }

    //messages taulan mezu berri bat sartu
    public void setMessageInfo(String message, String username) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                "mezutaula.messages (message, username) VALUES(?, ?)");
        ps.setString(1, message);
        ps.setString(2, username);
        ps.executeUpdate();
    }

    //messages taulatik mezu guztiak atera
    public ArrayList<MessageInfo> getAllMessages() throws SQLException{
        ArrayList<MessageInfo> emaitza = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM mezutaula.messages ");
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            MessageInfo e = new MessageInfo(rs.getString(2), rs.getString(3));
            emaitza.add(e);
        }
        return emaitza;
    }

}
