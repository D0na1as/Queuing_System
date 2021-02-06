package system.queuing.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import system.queuing.Model.User;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    @Value("${users_table}")
    String table = "";

    //Queries
    String selectUsers = "SELECT name FROM " + table + " where role='user';";

    //Query execution
    @Query(nativeQuery = true, value = selectUsers)
    List<String> getUsers();
}
