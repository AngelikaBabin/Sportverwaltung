/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Exceptions.RegisterExcpetion;
import Exceptions.AccountNotFoundException;
import Exceptions.FilterException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Kraschl
 */
public class Database {

    private static final String CONNECTSTRING = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    //private static final String CONNECTSTRING = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    private static final String USER = "d4a07";
    private static final String PASSWD = "d4a";
    private Connection conn = null;
    private static final int NUM_SELECTED_TEILNEHMER = 3;
    private static final String EVENT_COLUMNS = "veranstaltung.id as veranstaltung_id, veranstaltung.name as veranstaltung_name, "
            + "veranstaltung.sportart as veranstaltung_sportart, veranstaltung.location as veranstaltung_location, "
            + "veranstaltung.datetime as veranstaltung_datetime, "
            + "veranstaltung.details as veranstaltung_details, nvl(veranstaltung.max_teilnehmer,0) as veranstaltung_max_teilnehmer, "
            + "account.id as account_id, account.name as account_name, account.email as account_email, account.password as account_password";

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

    public Account addAccount(Account a) throws Exception {
        conn = createConnection();
        int accountId = -1;
        String select = "select seq_account_id.nextval from dual";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            a.setId(rs.getInt(1));
        }

        select = "INSERT INTO account (id, name, email, password, isVerified) VALUES(?,?,?,?,?)";
        stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        stmt.setString(2, a.getName());
        stmt.setString(3, a.getEmail());
        stmt.setString(4, a.getPassword());
        stmt.setBoolean(5, false);
        try {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1) {
                throw new RegisterExcpetion("Account already exists!");
            } else {
                throw ex;
            }
        }
        conn.close();
        return a;
    }

    public void addTeilnehmerToAccount(Account a) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO teilnehmer (id_account) VALUES(?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        stmt.executeUpdate();
        conn.close();
    }

    public void addVeranstalterToAccount(Account a) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO veranstalter VALUES(?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        stmt.executeUpdate();
        conn.close();
    }

    public Account login(Account a) throws Exception {
        ResultSet rs;
        conn = createConnection();
        String select = "SELECT id, email, password FROM account WHERE email = ? and password = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getEmail());
        stmt.setString(2, a.getPassword());
        rs = stmt.executeQuery();
        if (rs.next()) {
            a = new Account(rs.getInt("id"), rs.getString("email"));
        } else {
            throw new AccountNotFoundException("Account/email not founds");
        }
        conn.close();
        return a;
    }

    public Account getAccountById(Account a) throws Exception {
        ResultSet rs;
        conn = createConnection();
        String select = "SELECT * FROM account WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        rs = stmt.executeQuery();
        if (rs.next()) {
            a = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
        }
        conn.close();
        return a;
    }

    public Teilnehmer getTeilnehmer(Account a) throws Exception {
        ResultSet rs;
        Teilnehmer t = null;
        conn = createConnection();
        String select = "SELECT * FROM account join teilnehmer "
                + "on teilnehmer.id_account = account.id "
                + "WHERE account.email = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getEmail());
        rs = stmt.executeQuery();
        if (rs.next()) {
            t = new Teilnehmer(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getDouble("score"));
        }
        conn.close();
        return t;
    }
    
    public Account getVeranstalter(Account a) throws Exception {
        ResultSet rs;
        Account res = null;
        conn = createConnection();
        String select = "SELECT * FROM account join veranstalter "
                + "on veranstalter.id_account = account.id "
                + "WHERE account.email = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getEmail());
        rs = stmt.executeQuery();
        if (rs.next()) {
            res = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
        }
        conn.close();
        return res;
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
        String select = "SELECT " + EVENT_COLUMNS +  " FROM veranstaltung inner join account"
                + " on veranstaltung.id_veranstalter = account.id";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(createEventFromResultSet(rs));
        }
        conn.close();
        return collVeranstaltung;
    }

    public ArrayList<Event> getEventsByDate(Account a, LocalDate date, Filter filter) throws Exception {
        ArrayList<Event> collVeranstaltung = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT " + EVENT_COLUMNS + " FROM veranstaltung inner join account "
                + "on veranstaltung.id_veranstalter = account.id "
                + " inner join teilnahme"
                + " on teilnahme.id_veranstaltung = veranstaltung.id"
                + " where datetime {comp} ? and id_teilnehmer = ? order by veranstaltung_datetime";
        if (filter == Filter.PAST) {
            select = select.replace("{comp}", "<");
        } else if (filter == Filter.CURRENT) {
            select = select.replace("{comp}", ">=");
        }
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setDate(1, Date.valueOf(date));
        stmt.setInt(2, a.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(createEventFromResultSet(rs));
        }
        conn.close();
        return collVeranstaltung;
    }

    private ArrayList<Event> getEventsByTeilnehmer(Account a) throws Exception {
        ArrayList<Event> collVeranstaltung = new ArrayList<>();

        conn = createConnection();
        String select = "select " + EVENT_COLUMNS + " from teilnahme inner join veranstaltung "
                + "on teilnahme.id_veranstaltung = veranstaltung.id "
                + "where teilnahme.id_teilnehmer = ? order by veranstaltung_datetime";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(createEventFromResultSet(rs));
        }
        conn.close();
        return collVeranstaltung;
    }

    public ArrayList<Event> getEvents(Filter filter, Account a) throws Exception {
        ArrayList<Event> collEvents = null;
        switch (filter) {
            case PAST:
                collEvents = getEventsByDate(a, LocalDate.now(), filter);
                break;
            case CURRENT:
                collEvents = getEventsByDate(a, LocalDate.now(), filter);
                break;
            case ALL:
                collEvents = getFutureEvents();
                break;
            case VERANSTALTER:
                collEvents = getEventsOfVeranstalter(a);
                break;
            default:
                throw new FilterException("Unknown filter");
        }
        return collEvents;
    }
    
    /**
     * @Kumnig
     * @Rajic
     */
    public Event getEventById (int id) throws Exception{        
        conn = createConnection();
        String select = "select * from veranstaltung where id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        Event e = createEventFromResultSet(rs);
        conn.close();
        return e;
    }

    public void updateEvent(Event e) throws Exception {
        conn = createConnection();
        String select = "UPDATE veranstaltung SET name = ?, datetime = ?, "
                + "details = ?, max_teilnehmer = ?, min_teilnehmer = ?, sportart = ? where id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, e.getName());
        stmt.setDate(2, Date.valueOf(e.getDatetime()));
        stmt.setString(3, e.getDetails());
        stmt.setInt(4, e.getMaxTeilnehmer());
        stmt.setInt(5, 0);
        stmt.setString(6, e.getSportart());
        stmt.setInt(7, e.getId());
        stmt.executeUpdate();
        conn.close();
    }

    /**
     * @Kumnig
     * @Rajic
     */
    public void deleteEvent(Event e) throws Exception { //löschen von teilnahmen?
        conn = createConnection();
        String select = "delete from veranstaltung where id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, e.getId());
        stmt.executeUpdate();
        conn.close();
    }
    
    /**
     * @Kumnig
     * @Rajic
     */
    public void deleteCompleteEvent(Event e) throws Exception{
        deleteTeilnahmenFromEvent(e);
        deleteEvent(e);
    }

    public void insertEvent(Event e) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO veranstaltung VALUES(seq_veranstaltung_id.nextVal, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, e.getName());
        stmt.setString(2, e.getSportart());
        stmt.setInt(3, e.getVeranstalter().getId());
        stmt.setString(4, e.getLocation());
        stmt.setDate(5, Date.valueOf(e.getDatetime()));
        stmt.setString(6, e.getDetails());
        stmt.setInt(7, 0);
        stmt.setInt(8, e.getMaxTeilnehmer());
        stmt.execute();
        conn.close();
    }

    public void addTeilnahme(int eventId, Account a) throws Exception {
        int startNum = 0;

        ResultSet rs;
        conn = createConnection();
        String select = "SELECT count(*) FROM teilnahme inner join veranstaltung "
                + "on teilnahme.ID_veranstaltung = veranstaltung.id "
                + "group by veranstaltung.id";
        PreparedStatement stmt = conn.prepareStatement(select);
        rs = stmt.executeQuery();
        if (rs.next()) {
            startNum = rs.getInt(1);
        }

        startNum++;
        select = "INSERT INTO teilnahme "
                + "(id_veranstaltung, id_teilnehmer, STARTING_NUMBER) VALUES(?,?,?)";
        stmt = conn.prepareStatement(select);
        stmt.setInt(1, eventId);
        stmt.setInt(2, a.getId());
        stmt.setInt(3, startNum);
        stmt.executeUpdate();
        conn.close();
    }

    public void verifyAccount(Account a) throws Exception {
        conn = createConnection();
        String select = "UPDATE account SET isVerified = ? where email = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setBoolean(1, true);
        stmt.setString(2, a.getEmail());
        stmt.executeUpdate();
        conn.close();
    }

    public void deleteTeilnahme(int eventId, Account a) throws Exception, SQLException {
        conn = createConnection();
        String select = "delete from teilnahme where id_veranstaltung = ? and id_teilnehmer = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, eventId);
        stmt.setInt(2, a.getId());
        stmt.executeUpdate();
    }

    /*
    private ArrayList<Teilnahme> getTeilnahmen(Account a, int eventId) throws Exception {
        ArrayList<Teilnahme> collTeilnahmen = new ArrayList<>();

        conn = createConnection();
        String select = "select * from "
                + "(select * from teilnahme inner join teilnehmer "
                + "on teilnahme.id_teilnehmer = teilnehmer.id_account "
                + "where id_veranstaltung = ? and id_teilnehmer = ? "
                + "order by teilnahme.score "
                + ") where rownum <= ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, eventId);
        stmt.setInt(2, a.getId());
        stmt.setInt(3, NUM_SELECTED_TEILNEHMER);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(new Event(rs.getInt("id"),
                    rs.getInt("id_veranstalter"), rs.getString("name"), rs.getDate("datetime").toLocalDate(),
                    rs.getString("details"), "", rs.getInt("max_teilnehmer"),
                    rs.getInt("min_teilnehmer"), rs.getString("sportart")));
        }
        conn.close();
        return collTeilnahmen;
    }
     */
 /*
     select * from 
(select * from teilnahme inner join teilnehmer
on teilnahme.id_teilnehmer = teilnehmer.id_account
where id_veranstaltung = 1
order by teilnahme.score
) where rownum <= 3;*/
    private ArrayList<Event> getFutureEvents() throws Exception {
        ArrayList<Event> collVeranstaltung = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT " + EVENT_COLUMNS + " FROM veranstaltung inner join account "
                + "on veranstaltung.id_veranstalter = account.id "
                + " where datetime > ? order by veranstaltung_datetime";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setDate(1, Date.valueOf(LocalDate.now()));
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(createEventFromResultSet(rs));

        }
        conn.close();
        return collVeranstaltung;
    }

    public int getNumberOfTeilnehmer(int eventId) throws Exception {
        int result = 0;
        conn = createConnection();
        String select = "select count(*) from veranstaltung inner join teilnahme "
                + " on teilnahme.id_veranstaltung = veranstaltung.id "
                + " where veranstaltung.id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, eventId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1);
        }
        conn.close();
        return result;
    }

    public Account getAccountByEmail(Account a) throws Exception {
        ResultSet rs;
        conn = createConnection();
        String select = "SELECT * FROM account WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, a.getEmail());
        rs = stmt.executeQuery();
        if (rs.next()) {
            a = new Account(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
        } else {
            throw new AccountNotFoundException();
        }
        conn.close();
        return a;
    }

    private Event createEventFromResultSet(ResultSet rs) throws Exception {
        Event e = new Event(rs.getInt("veranstaltung_id"),
                new Account(rs.getInt("account_id"), rs.getString("account_name"),
                        rs.getString("account_email"), rs.getString("account_password")), rs.getString("veranstaltung_name"),
                rs.getDate("veranstaltung_datetime").toLocalDate(), rs.getString("veranstaltung_details"),
                rs.getString("veranstaltung_location"), 
                rs.getInt("veranstaltung_max_teilnehmer"), 
                rs.getString("veranstaltung_sportart"));
        e.setCountTeilnehmer(getNumberOfTeilnehmer(e.getId()));
        return e;
    }

    /**
     * @Kumnig
     * @Rajic
     */
    private void deleteTeilnahmenFromEvent(Event e) throws Exception{
        conn = createConnection();
        String select = "delete from teilnahme where id_veranstaltung = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, e.getId());
        stmt.executeUpdate();
        conn.close();
    }
    
    private ArrayList<Event> getEventsOfVeranstalter(Account a) throws Exception{
        ArrayList<Event> collVeranstaltung = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT " + EVENT_COLUMNS + " FROM veranstaltung inner join account " +
                " on veranstaltung.id_veranstalter = account.id " +
                " inner join veranstalter " +
                " on veranstalter.id_account = account.id"
                + " where account.id = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, a.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collVeranstaltung.add(createEventFromResultSet(rs));
        }
        conn.close();
        return collVeranstaltung;
    }
}
