package system.queuing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import system.queuing.Model.User;
import system.queuing.Service.ClientService;
import system.queuing.Service.UserService;
import system.queuing.Utils.Utils;

import java.text.ParseException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userSrv;
    @Autowired
    ClientService clientSrv;
    @Autowired
    Utils utils;

    @GetMapping("/")
    public String index(Model model) throws ParseException {
        List<User> userList = userSrv.getUsers();
        model.addAttribute("users", userList);
        return "index";
    }
}
