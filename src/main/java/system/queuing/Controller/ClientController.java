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

    //Create item and add
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("Specialist") int id, Model model) throws ParseException {
        User user = userSrv.getUserById(id);
        if (user!=null) {
            Client client = clientSrv.register(user.getUsername());
            String name = userSrv.getUserById(id).getName();
            String time = clientSrv.checkTime(client);
            model.addAttribute("client", client);
            model.addAttribute("name", name);
            model.addAttribute("timeLeft", time);
            return "Client/client";
        }
        return "index";
    }

    @RequestMapping( value = "/cancel", method = RequestMethod.POST)
    public String cancel(@RequestParam("serial") String serial, Model model) throws ParseException {
        clientSrv.cancelMeeting(serial);
        return "redirect:/client/check?serial="+serial;
    }

    @GetMapping("/check")
    public String check(@RequestParam("serial") String serial, Model model) throws ParseException {
        Client client = clientSrv.getClient(serial.toUpperCase().trim());
        if (client != null) {
            String time = clientSrv.checkTime(client);
            String name = userSrv.getUserByName(client.getUser()).getName();
            model.addAttribute("client", client);
            model.addAttribute("name", name);
            model.addAttribute("timeLeft", time);
            return "Client/client";

        } else return "index";
    }

}
