package system.queuing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.queuing.Model.Client;
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

    @GetMapping("/check")
    public String cancel(@RequestParam("serial") String serial, Model model) throws ParseException {
        Client client = clientSrv.getClient(serial);
        if (client != null) {
            model.addAttribute("client", client);
            return "Client/client";

        } else return "index";
    }
}
