package system.queuing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import system.queuing.Model.Client;
import system.queuing.Repository.ClientRepo;
import system.queuing.Utils.Utils;

import java.text.ParseException;

@Service
public class ClientService {

    @Autowired
    ClientRepo clientRepo;
    @Autowired
    Utils utils;
    @Value("${registered}")
    String registered;
    @Value("${canceled}")
    String cancel;


    public Client register(String name) throws ParseException {
        String date =  utils.getDate();
        int count = clientRepo.getCount(name, date);
        if (count<utils.maxSlots()) {
            Client client = new Client();
            client.setUser(name);
            client.setSerial(genSerial());
            client.setQueNr(getQueNr(name));
            client.setStatus(registered);
            client.setDate(date);
            return clientRepo.save(client);
        } else return null;
    }

    public Client getClient(String serial) {
        return clientRepo.getClient(serial);
    }

    public void cancelMeeting(String serial) {
        clientRepo.cancel(serial, cancel);
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
    private int getQueNr(String name) {
        int a = clientRepo.getLastQuer(name, utils.getDate());
        return clientRepo.getLastQuer(name, utils.getDate())+1;
    }

}
