package system.queuing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Collection;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('user')")
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
        String username = getUsername();
        User user = userSrv.getUserByName(username);
        List<Client> clients = clientSrv.getClientS(username,utils.getDate());
        model.addAttribute("clients", clients);
        model.addAttribute("user", user);
        model.addAttribute("time", utils.getTime());
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

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/";
    }

    //Get current username
    private static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //Check if it's user or screen
    private boolean hasRole (String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }
}
