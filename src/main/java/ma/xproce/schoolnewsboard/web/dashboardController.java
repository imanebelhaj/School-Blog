package ma.xproce.schoolnewsboard.web;

import ma.xproce.schoolnewsboard.dao.entites.User;
import ma.xproce.schoolnewsboard.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class dashboardController {
    private final UserServiceImpl userService;

    public dashboardController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboardArticles")
    public String index() {
        return "dashboardAdminArticles";
    }

    @GetMapping("/dashboardUsers")
    public String usersAdminDashboard(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "dashboardAdminUsers";
    }


    @GetMapping("/userDashboard")
    public String userDashboard() {
        return "userDashboard";
    }

}