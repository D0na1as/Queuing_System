package system.queuing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    @Value("${registered}")
    String registered;
    @Value("${ongoing}")
    String ongoing;
    @Value("${canceled}")
    String cancel;
    @Value("${ended}")
    String ended;


    public Client register(String name) throws ParseException {
        String date =  utils.getDate();
        int count = clientRepo.getCount(name, date);
        if (count<utils.maxSlots()) {
            Client client = new Client();
            client.setUser(name);
            client.setSerial(genSerial());
            client.setQueNr(createQueNr(name));
            client.setStatus(registered);
            client.setDate(date);
            return clientRepo.save(client);
        } else return null;
    }

    public Client getClient(String serial) {
        return clientRepo.getClient(serial);
    }

    public void startMeeting(String serial) {
        clientRepo.updateStatus(serial, ongoing);
    }

    public void endMeeting(String serial) {
        clientRepo.updateStatus(serial, ended);
    }

    public void cancelMeeting(String serial) {
        clientRepo.updateStatus(serial, cancel);
    }

    public List<Client> getClientS(String name, String date) {
        return clientRepo.getClientS(name, date);
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
    private int createQueNr(String name) {
        int a = clientRepo.getLastQuer(name, utils.getDate());
        return clientRepo.getLastQuer(name, utils.getDate())+1;
    }

    //Get que nr by status
    public int getQueNr(String name, String date, String status) {
        return clientRepo.getQueNr(name, date, status);
    }

    //Get first "count" persons in que
    public List<Integer> getQue(String name, String date, int count) {
        List<Integer> list = clientRepo.getQue(name, date, count);
        if (list.size()<count) {
            for (int i=list.size(); i<count; i++) {
                list.add(0);
            }
        }
        return list;
    }
}
