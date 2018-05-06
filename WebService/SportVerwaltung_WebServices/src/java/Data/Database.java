/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Exceptions.RegisterExcpetion;
import Exceptions.AccountNotFoundException;
import com.oracle.jrockit.jfr.Producer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Gerald
 */
public class Database {
    //private static final String CONNECTSTRING = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    private static final String CONNECTSTRING = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    private static final String USER = "d4a07";
    private static final String PASSWD = "d4a";
    private Connection conn = null;

    /**
     * Singleton
     */
    private static Database database = null;

    public static Database newInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private Database() {
    }

    private Connection createConnection() throws Exception {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        return DriverManager.getConnection(CONNECTSTRING, USER, PASSWD);
    }
    
    public Account addAccount(Account a) throws Exception{
        conn = createConnection();
        int accountId = -1;
        String select = "select seq_account_id.nextval from dual";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        if (rs.next())
            a.setId(rs.getInt(1));
        
        select = "INSERT INTO account VALUES(?,?,?,?)";
        stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        stmt.setString(2, a.getName());
        stmt.setString(3, a.getEmail());
        stmt.setString(4, a.getPassword());
        try{
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            if(ex.getErrorCode() == 1)
                throw new RegisterExcpetion("Account already exists!");
            else
                throw ex;
        }
        conn.close();
        return a;
    }
    
    public void addTeilnehmerToAccount(Account a) throws Exception{
        conn = createConnection();
        String select = "INSERT INTO teilnehmer (id_account) VALUES(?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        stmt.executeUpdate();
        conn.close();
    }
    
    public void addVeranstalterToAccount(Account a) throws Exception{
        conn = createConnection();
        String select = "INSERT INTO veranstalter VALUES(?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        stmt.executeUpdate();
        conn.close();
    }

    public void login(Account a) throws Exception{
        ResultSet rs;
        conn = createConnection();
        String select = "SELECT * FROM account WHERE email = ? and password = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getEmail());
        stmt.setString(2, a.getPassword());
        rs = stmt.executeQuery();
        if (!rs.next()) {
            throw new AccountNotFoundException("Account/email not founds");
        }
        conn.close();
    }
    
    public Account getAccount(Account a) throws Exception{
        ResultSet rs;
        conn = createConnection();
        String select = "SELECT * FROM account WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getEmail());
        rs = stmt.executeQuery();
        if (rs.next()) {
            a = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("email"), null);
        }
        conn.close();
        return a;
    }
    
    public Teilnehmer getTeilnehmer(Account a) throws Exception{
        ResultSet rs;
        Teilnehmer t = null;
        conn = createConnection();
        String select = "SELECT * FROM account join teilnehmer \n" +
                            "on teilnehmer.id_account = account.id\n" +
                            "WHERE account.email = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getEmail());
        rs = stmt.executeQuery();
        if (rs.next()) {
            t = new Teilnehmer(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getDouble("score"));
        }
        conn.close();
        return t;
    }
    
    public void updateAccount(Account a) throws Exception {
        conn = createConnection();
        String select = "UPDATE account SET name = ?, password = ? where email = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getName());
        stmt.setString(2, a.getPassword());
        stmt.setString(3, a.getEmail());
        stmt.executeUpdate();
        conn.close();
    }
    
    public void updateTeilnehmer(Teilnehmer t) throws Exception {
        conn = createConnection();
        String select = "UPDATE teilnehmer SET score = ? where id_account = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setDouble(1, t.getScore());
        stmt.setDouble(2, t.getId());
        stmt.executeUpdate();
        conn.close();
    }
    
    /*
    public void deleteTeilnehmer(Account t) throws Exception { //fix implementation
        conn = createConnection();
        String select = "delete from teilnehmer where id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, t.getId());
        stmt.executeUpdate();
        
        select = "delete from teilnahme where id_teilnehmer = ?";
        stmt = conn.prepareStatement(select);
        stmt.setInt(1, t.getId());
        stmt.executeUpdate();
        conn.close();
    }
    
    public void deleteVeranstalter(Account v) throws Exception { //fix implementation
        conn = createConnection();
        String select = "delete from account where id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, v.getId());
        stmt.executeUpdate();
        
        select = "delete from teilnahme where id_teilnehmer = ?";
        stmt = conn.prepareStatement(select);
        stmt.setInt(1, v.getId());
        stmt.executeUpdate();
        conn.close();
    }
    */
    
     public Collection<Event> getEventsByDistance() throws Exception {
        ArrayList<Event> collVeranstaltung = new ArrayList<>();
         
        conn = createConnection();
        String select = "SELECT * FROM veranstaltung";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(new Event(rs.getInt("id"), 
                    rs.getInt("id_veranstalter"), rs.getString("name"), rs.getDate("datetime").toLocalDate(), 
                    rs.getString("details"), "", rs.getInt("max_teilnehmer"), 
                    rs.getInt("min_teilnehmer"), rs.getString("sportart")));
        } 
        conn.close();
        return collVeranstaltung;
    }
     
    public Collection<Event> getEventsByDateTime() throws Exception {
        ArrayList<Event> collVeranstaltung = new ArrayList<>();
         
        conn = createConnection();
        String select = "SELECT * FROM veranstaltung";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(new Event(rs.getInt("id"), 
                    rs.getInt("id_veranstalter"), rs.getString("name"), rs.getDate("datetime").toLocalDate(), 
                    rs.getString("details"), "", rs.getInt("max_teilnehmer"), 
                    rs.getInt("min_teilnehmer"), rs.getString("sportart")));
        } 
        conn.close();
        return collVeranstaltung;
    }
    
    public Collection<Event> getEventsByTeilnehmer() throws Exception {
        ArrayList<Event> collVeranstaltung = new ArrayList<>();
         
        conn = createConnection();
        String select = "SELECT * FROM veranstaltung";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(new Event(rs.getInt("id"), 
                    rs.getInt("id_veranstalter"), rs.getString("name"), rs.getDate("datetime").toLocalDate(), 
                    rs.getString("details"), "", rs.getInt("max_teilnehmer"), 
                    rs.getInt("min_teilnehmer"), rs.getString("sportart")));
        } 
        conn.close();
        return collVeranstaltung;
    }
     
     public void updateEvent(Event e) throws Exception{
         conn = createConnection();
        String select = "UPDATE veranstatlung SET name = ?, datetime = ?, "
                + "details = ? max_teilnehmer = ?, min_teilnehmer = ?, sportart = ? where id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, e.getName());
        stmt.setDate(2, Date.valueOf(e.getDatetime()));
        stmt.setString(3, e.getDetails());
        stmt.setInt(4, e.getMax_teilnehmer());
        stmt.setInt(5, e.getMin_teilnehmer());
        stmt.setString(6, e.getSportart());
        stmt.setInt(7, e.getId());
        stmt.executeUpdate();
     }
     
     public void deleteEvent(Event e) throws Exception  { //löschen von teilnahmen?
         conn = createConnection();
        String select = "delete from veranstaltung where id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, e.getId());
        stmt.executeUpdate();
        conn.close();
     }
     
     public void insertEvent(Event e) throws Exception{
        conn = createConnection();
        String select = "INSERT INTO veranstaltung VALUES(seq_veranstaltung_id.nextVal, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, e.getName());
        stmt.setString(2, e.getSportart());
        stmt.setInt(3, e.getId_veranstalter());
        stmt.setInt(4, 0); //location
        stmt.setDate(5, Date.valueOf(e.getDatetime()));
        stmt.setString(6, e.getDetails());
        stmt.setInt(7, e.getMin_teilnehmer());
        stmt.setInt(8, e.getMax_teilnehmer());
        stmt.executeUpdate();
        conn.close();
     }
             
}
