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
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    ClientService clientSrv;
    @Autowired
    UserService userSrv;
    @Autowired
    Utils utils;

    @GetMapping("/")
    public String index(Model model) throws ParseException {
        String name = "don";
        User user = userSrv.getUser(name);
        List<Client> clients = clientSrv.getClientS("Don Johnson", utils.getDate());
        model.addAttribute("clients", clients);
        model.addAttribute("user", user);
        return "User/user";
    }

    @RequestMapping( value = "/start", method = RequestMethod.POST)
    public String start(@RequestParam("serial") String serial,
                        @RequestParam("username") String username,
                        Model model) throws ParseException {
        clientSrv.startMeeting(serial);
        userSrv.startMeeting(username);
        return "redirect:/user/";
    }

    @RequestMapping( value = "/end", method = RequestMethod.POST)
    public String end(@RequestParam("serial") String serial,
                      @RequestParam("username") String username,
                      Model model) throws ParseException {
        clientSrv.endMeeting(serial);
        userSrv.endMeeting(username);
        return "redirect:/user/";
    }

    @RequestMapping( value = "/cancel", method = RequestMethod.POST)
    public String cancel(@RequestParam("serial") String serial, Model model) throws ParseException {
        clientSrv.cancelMeeting(serial);
        return "redirect:/user/";
    }

    @GetMapping("/screen")
    public String screen(Model model) throws ParseException {
        List<String> userList = userSrv.getUsers();
        Map<String, List<Integer>> data = userSrv.getScreen();
        model.addAttribute("users", userList);
        model.addAttribute("data", data);
        return "User/screen";
    }

}
