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

import java.text.ParseException;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientSrv;
    @Autowired
    UserService userSrv;

    //TODO
    // 1. Appointment reservation
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

    @RequestMapping( value = "/cancel", method = RequestMethod.POST)
    public String cancel(@RequestParam("serial") String serial, Model model) throws ParseException {
        clientSrv.cancelMeeting(serial);
        Client client = clientSrv.getClient(serial);
        model.addAttribute("client", client);
        return "Client/client";
    }

    @GetMapping("/check")
    public String check(@RequestParam("serial") String serial, Model model) throws ParseException {
        Client client = clientSrv.getClient(serial);
        if (client != null) {
            model.addAttribute("client", client);
            return "Client/client";

        } else return "index";
    }
    // 1.1 Return client info page
    // 2. Checking appointment
    // 2.2 Return client info page

}
