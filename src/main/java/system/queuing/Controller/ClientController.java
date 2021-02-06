package system.queuing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import system.queuing.Service.ClientService;
import system.queuing.Service.UserService;

@Controller
@RequestMapping("/client")
public class ClientController {

    //TODO
    // 1. Appointment reservation
    // 1.1 Return client info page
    // 2. Checking appointment
    // 2.2 Return client info page

    @Autowired
    ClientService clientSrv;
    @Autowired
    UserService userSrv;



}
