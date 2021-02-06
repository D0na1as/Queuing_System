package system.queuing.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import system.queuing.Model.User;

public interface UserRepo extends CrudRepository<User, Integer> {

    @Value("${users_table}")
    String table = "";

}
