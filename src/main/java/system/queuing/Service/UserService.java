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


    public List<User> getUsers() {
        return userRepo.getUsers();
    }

    public User getUserById(int id) {
        return userRepo.getUser(id);
    }

    public User getUserByName(String username) {
        return userRepo.getUserByName(username);
    }

    public String getUserStatus(String username) {
        return userRepo.getUserStatus(username);
    }

    public void startMeeting(String username) {
        userRepo.updateStatus(username, occupied);
    }

    public void endMeeting(String username) {
        userRepo.updateStatus(username, free);
    }

    //Info screen data
    public Map<String, List<Integer>> getScreen() {

        List<User> userList = getUsers();
        Map<String,  List<Integer>> data = new HashMap<>();

        for (User user:userList) {
            List<Integer> waitingLn = clientSrv.getQue(user.getUsername(), utils.getDate(), 5);
            String status = userSrv.getUserStatus(user.getUsername());
            List<Integer> current = new ArrayList<>();

            if (status.equals(occupied)) {
                current.add(clientSrv.getQueNr(user.getUsername(), utils.getDate(), ongoing));
            } else {
                current.add(0);
            }
            waitingLn = Stream.of(current, waitingLn)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            data.put(user.getUsername(), waitingLn);
        }
    return data;
    }
}
