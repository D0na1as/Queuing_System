package system.queuing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import system.queuing.Model.User;
import system.queuing.Repository.UserRepo;
import system.queuing.Utils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    ClientService clientSrv;
    @Autowired
    UserService userSrv;
    @Autowired
    Utils utils;
    @Value("${free}")
    String free;
    @Value("${occupied}")
    String occupied;
    @Value("${ongoing}")
    String ongoing;


    public List<String> getUsers() {
        return userRepo.getUsers();
    }

    public User getUser(String username) {
        return userRepo.getUser(username);
    }

    public String getUserStatus(String name) {
        return userRepo.getUserStatus(name);
    }

    public void startMeeting(String username) {
        userRepo.updateStatus(username, occupied);
    }

    public void endMeeting(String username) {
        userRepo.updateStatus(username, free);
    }

    //Info screen data
    public Map<String, List<Integer>> getScreen() {

        List<String> userList = getUsers();
        Map<String,  List<Integer>> data = new HashMap<>();

        for (String name:userList) {
            List<Integer> waitingLn = clientSrv.getQue(name, utils.getDate(), 5);
            String status = userSrv.getUserStatus(name);
            List<Integer> current = new ArrayList<>();

            if (status.equals(occupied)) {
                current.add(clientSrv.getQueNr(name, utils.getDate(), ongoing));
            } else {
                current.add(0);
            }
            waitingLn = Stream.of(current, waitingLn)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            data.put(name, waitingLn);
        }
    return data;
    }
}
