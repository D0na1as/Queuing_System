package system.queuing.Repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import system.queuing.Model.Client;

@Repository
public interface ClientRepo extends CrudRepository<Client, Integer> {

    @Value("${clients_table}")
    String table = "";
}


