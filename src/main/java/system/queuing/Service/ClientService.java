package system.queuing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import system.queuing.Config.ClientStatus;
import system.queuing.Config.UserStatus;
import system.queuing.Model.Client;
import system.queuing.Repository.ClientRepo;
import system.queuing.Utils.Utils;

import java.text.ParseException;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepo clientRepo;
    @Autowired
    Utils utils;


    public Client register(String username) throws ParseException {
        String date =  utils.getDate();
        int count = clientRepo.getCount(username, date);
        if (count<utils.maxSlots()) {
            Client client = new Client();
            client.setUser(username);
            client.setSerial(genSerial());
            client.setQueNr(createQueNr(username));
            client.setStatus(ClientStatus.registered);
            client.setDate(date);
            return clientRepo.save(client);
        } else return null;
    }

    public Client getClient(String serial) {
        return clientRepo.getClient(serial);
    }

    public void startMeeting(String serial) {
        clientRepo.updateStatus(serial, ClientStatus.ongoing);
    }

    public void endMeeting(String serial) {
        clientRepo.updateStatus(serial, ClientStatus.ended);
    }

    public void cancelMeeting(String serial) {
        clientRepo.updateStatus(serial, ClientStatus.canceled);
    }

    public List<Client> getClientS(String username, String date) {
        return clientRepo.getClientS(username, date);
    }

    public int getQueLeft(String name, int queNr) {
        return clientRepo.getQueLeft(name, queNr, ClientStatus.registered);
    }

    public String checkTime(Client client) throws ParseException {
        if (client.getDate().equals(utils.getDate())) {
            int queLeftReg = clientRepo.getQueLeft(client.getUser(), client.getQueNr(), ClientStatus.registered);
            int queLeftOng = clientRepo.getQueLeft(client.getUser(), client.getQueNr(), ClientStatus.ongoing);
            int queLeft = queLeftReg + queLeftOng;
            return utils.timeLeft(queLeft);
        } else {
            return "Day Passed";
        }
    }

    private String genSerial() {
        String serial;
        while(true) {
            serial = utils.generator();
            clientRepo.getClient(serial);
            if (clientRepo.getClient(serial)==null) {
                return serial;
            }
        }
    }
    private int createQueNr(String username) {
        return clientRepo.getLastQuer(username, utils.getDate())+1;
    }

    //Get que nr by status
    public int getQueNr(String username, String date, ClientStatus status) {
        return clientRepo.getQueNr(username, date, status);
    }

    //Get first "count" persons in que (for screen)
    public List<Integer> getQue(String username, String date, int count) {
        List<Integer> list = clientRepo.getQue(username, date, count);
        if (list.size()<count) {
            for (int i=list.size(); i<count; i++) {
                list.add(0);
            }
        }
        return list;
    }
}
