package system.queuing.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import system.queuing.Config.UserStatus;
import system.queuing.Model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    String table = "user";

    //Queries
    String selectUsers = "SELECT id, name, username, status FROM "+ table +" WHERE role='user';";
    String getUser = "SELECT id, username, name, status FROM "+ table +" WHERE id=?;";
    String getUserByName = "SELECT id, username, name, status FROM "+ table +" WHERE username=?;";
    String getUserStatus = "SELECT status FROM "+ table +" WHERE username=?;";

    String updateStatus = "UPDATE " + table + " SET status=:#{#status.name()} WHERE username=?1";

    //Query execution
    @Query(nativeQuery = true, value = selectUsers)
    List<User> getUsers();

    @Query(nativeQuery = true, value = getUser)
    User getUser(int id);

    @Query(nativeQuery = true, value = getUserByName)
    User getUserByName(String username);

    @Query(nativeQuery = true, value = getUserStatus)
    String getUserStatus(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = updateStatus)
    void updateStatus(String username, @Param("status")UserStatus status);

}
