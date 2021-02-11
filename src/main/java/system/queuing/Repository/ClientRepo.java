package system.queuing.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import system.queuing.Model.Client;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClientRepo extends CrudRepository<Client, Integer> {

    String table = "client";

    //Queries
    String getClient = "SELECT * FROM "+ table +" WHERE serial=?;";
    String getClientS = "SELECT * FROM "+ table +" WHERE user=?1 AND date=?2";
    String getCount = "SELECT COUNT(id) FROM "+ table +" WHERE user=?1 AND NOT status='canceled' AND date=?2";
    String getLastInQue = "SELECT IFNULL((SELECT que_nr FROM "+ table +" WHERE user=?1 AND NOT status='canceled' AND date=?2 ORDER BY que_nr DESC LIMIT 1), 0)";
    String getQueNr = "SELECT que_nr FROM "+ table +" WHERE user=?1 AND date=?2 AND status=?3";
    String getQue = "SELECT IFNULL(que_nr, 0) from (SELECT que_nr FROM "+ table +" WHERE user=?1 AND status='registered' AND date=?2) as tbl  ORDER BY que_nr ASC LIMIT ?3";
    String getQueLeft = "SELECT COUNT(id) FROM "+ table +" WHERE user=?1 AND status='registered' AND que_nr<=?2";

    String updateStatus = "UPDATE " + table + " SET status=?2 WHERE serial=?1";

    //Query execution
    @Query(nativeQuery = true, value = getClient)
    Client getClient(String serial);

    @Query(nativeQuery = true, value = getClientS)
    List<Client> getClientS(String username, String date);

    @Query(nativeQuery = true, value = getCount)
    int getCount(String username, String date);

    @Query(nativeQuery = true, value = getLastInQue)
    int getLastQuer(String name, String date);

    @Query(nativeQuery = true, value = getQueNr)
    int getQueNr(String username, String date, String status);

    @Query(nativeQuery = true, value = getQue)
    List<Integer> getQue(String username, String date, int count);

    @Query(nativeQuery = true, value = getQueLeft)
    int getQueLeft(String name, int queNr);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = updateStatus)
    void updateStatus(String serial,String status);

}


