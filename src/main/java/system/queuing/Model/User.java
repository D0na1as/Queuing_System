package system.queuing.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

    @Id
    private int id;
    @Transient
    private String username;
    private String name;
    @Transient
    private String password;
    private String status;
    @Transient
    private String role;
    @Transient
    private boolean enabled;

    public User() {
    }

    public User(int id, String username, String name, String password, String status, String role, boolean enabled) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.status = status;
        this.role = role;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
