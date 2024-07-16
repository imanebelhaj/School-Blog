package ma.xproce.schoolnewsboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboardController {

    @GetMapping("/dashboard")
    public String index() {
        return "dashboard";
    }

    @GetMapping("/userDashboard")
    public String userDashboard() {
        return "userDashboard";
    }
}