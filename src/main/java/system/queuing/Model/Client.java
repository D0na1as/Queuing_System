package system.queuing.Model;

import system.queuing.Config.ClientStatus;

import javax.persistence.*;

@Entity
public class Client {

    @Id
    private int id;
    @Column(name="que_nr")
    private int queNr;
    private String serial;
    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    private String date;
    //Receiving person
    private String user;

    public Client() {
    }

    public Client(int id, int queNr, String serial, ClientStatus status, String date, String user) {
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

    public int getQueNr() {
        return queNr;
    }

    public void setQueNr(int queNr) {
        this.queNr = queNr;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
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
