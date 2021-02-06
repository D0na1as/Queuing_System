package system.queuing.Model;

import javax.persistence.*;

@Entity
public class Client {

    @Id
    private int id;
    private String queNr;
    private String serial;
    private int status;
    private String date;
    //Recieving person
    private String user;

    public Client() {
    }

    public Client(int id, String queNr, String serial, int status, String date, String user) {
        this.id = id;
        this.queNr = queNr;
        this.serial = serial;
        this.status = status;
        this.date = date;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQueNr() {
        return queNr;
    }

    public void setQueNr(String queNr) {
        this.queNr = queNr;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
