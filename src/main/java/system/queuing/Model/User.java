package system.queuing.Model;

import system.queuing.Config.Roles;
import system.queuing.Config.UserStatus;

import javax.persistence.*;

@Entity
public class User {

    @Id
    private int id;
    private String username;
    private String name;
    @Transient
    private String password;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Transient
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Transient
    private boolean enabled;

    public User() {
    }

    public User(int id, String username, String name, String password, UserStatus status, Roles role, boolean enabled) {
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
