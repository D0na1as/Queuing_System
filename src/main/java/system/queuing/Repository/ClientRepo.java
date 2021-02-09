package system.queuing.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import system.queuing.Model.Client;

@Repository
public interface ClientRepo extends CrudRepository<Client, Integer> {

    String table = "client";

    //Queries
    String getClient = "SELECT * FROM "+ table +" WHERE serial=?;";
    String getCount = "SELECT COUNT(id) FROM "+ table +" WHERE user=?1 AND NOT status='canceled' AND date=?2";
    String getLastInQue = "SELECT IFNULL((SELECT que_nr FROM "+ table +" WHERE user=?1 AND NOT status='canceled' AND date=?2 ORDER BY que_nr DESC LIMIT 1), 0)";

    //Query execution
    @Query(nativeQuery = true, value = getClient)
    Client getClient(String serial);

    @Query(nativeQuery = true, value = getCount)
    int getCount(String name, String date);

    @Query(nativeQuery = true, value = getLastInQue)
    int getLastQuer(String name, String date);
}


