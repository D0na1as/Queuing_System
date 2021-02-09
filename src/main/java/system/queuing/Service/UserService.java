package system.queuing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.queuing.Model.User;
import system.queuing.Repository.UserRepo;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public List<String> getUsers() {
        return userRepo.getUsers();
    }

    public User getUser(String name) {
        return userRepo.getUser(name);
    }

}
