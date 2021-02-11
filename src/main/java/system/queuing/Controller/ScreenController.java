package system.queuing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import system.queuing.Model.User;
import system.queuing.Service.UserService;
import system.queuing.Utils.Utils;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/screen")
public class ScreenController {

    @Autowired
    UserService userSrv;
    @Autowired
    Utils utils;

    @GetMapping("/")
    public String screen(Model model) throws ParseException {
        List<User> userList = userSrv.getUsers();
        Map<String, List<Integer>> data = userSrv.getScreen();
        String time = utils.getTime();
        model.addAttribute("users", userList);
        model.addAttribute("data", data);
        model.addAttribute("time", time);
        return "User/screen";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/";
    }
}
