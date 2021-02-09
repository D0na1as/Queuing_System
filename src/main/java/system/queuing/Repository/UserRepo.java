package system.queuing.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import system.queuing.Model.User;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    String table = "user";

    //Queries
    String selectUsers = "SELECT name FROM "+ table +" WHERE role='user';";
    String getUser = "SELECT id, name, status FROM "+ table +" WHERE name=?;";

    //Query execution
    @Query(nativeQuery = true, value = selectUsers)
    List<String> getUsers();

    @Query(nativeQuery = true, value = getUser)
    User getUser(String name);

}
