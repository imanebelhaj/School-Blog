package ma.xproce.schoolnewsboard.web;

import ma.xproce.schoolnewsboard.dao.entites.Article;
import ma.xproce.schoolnewsboard.dao.entites.User;
import ma.xproce.schoolnewsboard.service.ArticleServiceImpl;
import ma.xproce.schoolnewsboard.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
    public String userDashboard(Model model) {
        List<Article> articles =articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "userDashboard";
    }

    @GetMapping("/userHome")
    public String userHome(Model model) {
        List<Article> articles =articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "userHome";
    }


    @GetMapping("/todayArticles")
    public String getTodayArticles(Model model) {
        List<Article> allArticles = articleService.getAllArticles();
        LocalDate today = LocalDate.now();
        List<Article> todayArticles = allArticles.stream()
                .filter(article -> {
                    Date creationDate = article.getCreationDate();
                    LocalDate localCreationDate = creationDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localCreationDate.isEqual(today);
                })
                .collect(Collectors.toList());
        model.addAttribute("articles", todayArticles);
        return "todayArticles";
    }

    @GetMapping("/WeekArticles")
    public String getWeekArticles(Model model) {
        List<Article> allArticles = articleService.getAllArticles();
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = today.get(weekFields.weekOfWeekBasedYear());

        List<Article> weekArticles = allArticles.stream()
                .filter(article -> {
                    Date creationDate = article.getCreationDate();
                    LocalDate localCreationDate = creationDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    int articleWeek = localCreationDate.get(weekFields.weekOfWeekBasedYear());
                    return articleWeek == currentWeek;
                })
                .collect(Collectors.toList());

        model.addAttribute("articles", weekArticles);
        return "WeekArticles";
    }

    @GetMapping("/monthArticles")
    public String getMonthArticles(Model model) {
        List<Article> allArticles = articleService.getAllArticles();
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        int currentYear = today.getYear();

        List<Article> monthArticles = allArticles.stream()
                .filter(article -> {
                    Date creationDate = article.getCreationDate();
                    LocalDate localCreationDate = creationDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localCreationDate.getMonth() == currentMonth && localCreationDate.getYear() == currentYear;
                })
                .collect(Collectors.toList());

        model.addAttribute("articles", monthArticles);
        return "monthArticles";
    }

}