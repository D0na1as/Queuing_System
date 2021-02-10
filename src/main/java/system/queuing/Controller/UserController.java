package system.queuing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import system.queuing.Model.Client;
import system.queuing.Model.User;
import system.queuing.Service.ClientService;
import system.queuing.Service.UserService;
import system.queuing.Utils.Utils;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    ClientService clientSrv;
    @Autowired
    UserService userSrv;
    @Autowired
    Utils utils;

    //TODO
    // 1. Login
    // 2. Clients list
    @GetMapping("/")
    public String index(Model model) throws ParseException {
        String name = "Don Johnson";
        User user = userSrv.getUser(name);
        List<Client> clients = clientSrv.getClientS(name, utils.getDate());
        model.addAttribute("clients", clients);
        model.addAttribute("user", user);
        return "User/user";
    }

    @RequestMapping( value = "/cancel", method = RequestMethod.POST)
    public String cancel(@RequestParam("serial") String serial, Model model) throws ParseException {
        clientSrv.cancelMeeting(serial);
        return "redirect:/user/";
    }
    // 2.1. Mark visit begun
    // 2.2. Mark visit ended
    // 2.3. Mark visit canceled
    // 3. Info Screen page

}
