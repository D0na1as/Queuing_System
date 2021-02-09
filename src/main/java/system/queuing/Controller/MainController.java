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
public class MainController {

    @Autowired
    UserService userSrv;
    @Autowired
    ClientService clientSrv;
    @Autowired
    Utils utils;

    @GetMapping("/")
    public String index(Model model) throws ParseException {
        List<String> userList = userSrv.getUsers();
        model.addAttribute("users", userList);
        return "index";
    }
    //Create item and add
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("Specialist") String name, Model model) throws ParseException {
        User user = userSrv.getUser(name);
        if (user!=null) {
            Client client = clientSrv.register(name);
            model.addAttribute("client", client);
            return "Client/client";
        }
        return "index";
    }

}
