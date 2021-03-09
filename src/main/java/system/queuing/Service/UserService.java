package system.queuing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.queuing.Config.ClientStatus;
import system.queuing.Config.UserStatus;
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

    public List<User> getUsers() {
        return userRepo.getUsers();
    }

    public User getUserById(int id) {
        return userRepo.getUser(id);
    }

    public User getUserByName(String username) {
        return userRepo.getUserByName(username);
    }

    public UserStatus getUserStatus(String username) {
        return UserStatus.valueOf(userRepo.getUserStatus(username));
    }

    public void startMeeting(String username) {
        userRepo.updateStatus(username, UserStatus.occupied);
    }

    public void endMeeting(String username) {
        userRepo.updateStatus(username, UserStatus.free);
    }

    //Info screen data
    public Map<String, List<Integer>> getScreen() {

        List<User> userList = getUsers();
        Map<String, List<Integer>> data = new HashMap<>();

        for (User user : userList) {
            List<Integer> waitingLn = clientSrv.getQue(user.getUsername(), utils.getDate(), 5);
            UserStatus status = userSrv.getUserStatus(user.getUsername());
            List<Integer> current = new ArrayList<>();

            if (status.equals(UserStatus.occupied)) {
                current.add(clientSrv.getQueNr(user.getUsername(), utils.getDate(), ClientStatus.ongoing));
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
