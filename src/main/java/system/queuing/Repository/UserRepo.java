package system.queuing.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import system.queuing.Model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    String table = "user";

    //Queries
    String selectUsers = "SELECT name FROM "+ table +" WHERE role='user';";
    String getUser = "SELECT id, username, name, status FROM "+ table +" WHERE username=?;";
    String getUserStatus = "SELECT status FROM "+ table +" WHERE name=?;";

    String updateStatus = "UPDATE " + table + " SET status=?2 WHERE username=?1";

    //Query execution
    @Query(nativeQuery = true, value = selectUsers)
    List<String> getUsers();

    @Query(nativeQuery = true, value = getUser)
    User getUser(String name);

    @Query(nativeQuery = true, value = getUserStatus)
    String getUserStatus(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = updateStatus)
    void updateStatus(String name,String status);
}
