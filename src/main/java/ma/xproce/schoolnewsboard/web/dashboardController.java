package ma.xproce.schoolnewsboard.web;

import ma.xproce.schoolnewsboard.dao.entites.Article;
import ma.xproce.schoolnewsboard.dao.entites.User;
import ma.xproce.schoolnewsboard.service.ArticleServiceImpl;
import ma.xproce.schoolnewsboard.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class dashboardController {
    private final UserServiceImpl userService;
    private final ArticleServiceImpl articleService;

    public dashboardController(UserServiceImpl userService, ArticleServiceImpl articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/dashboardArticles")
    public String index(Model model) {
        List<Article> articles =articleService.getAllArticles();
        model.addAttribute("articles", articles);
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