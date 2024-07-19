package ma.xproce.schoolnewsboard.web;


import ma.xproce.schoolnewsboard.dao.entites.Article;
import ma.xproce.schoolnewsboard.service.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl articleService;


    @PostMapping("/process_createArticle")
    public String createArticle(@ModelAttribute("article") Article article, Model model) {
        try {
            article.setCreationDate(new Date());
            article.setLastUpdateDate(new Date());
            articleService.saveArticle(article);
            return "redirect:/dashboardArticles";
        } catch (Exception e) {
            // Handle error and redirect to dashboard with error message
            model.addAttribute("errorMessage", "An error occurred while creating the article: " + e.getMessage());
            return "redirect:/dashboardArticles";
        }
    }
    @GetMapping("/createArticle")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "createArticle";
    }




    //update Article
    @GetMapping("/updateArticle/{articleId}")
    public String UpdateArticleForm(@PathVariable("articleId") Long articleId, Model model) {
        Article article = articleService.getArticleById(articleId);
        if (article == null) {
            return "redirect:/dashboardArticles";
        }
        model.addAttribute("article", article);
        return "updateArticle";
    }

    @PostMapping("/updateArticle/{articleId}")
    public String updateArticle(@PathVariable("articleId") Long articleId, @ModelAttribute("article")  Article updatedArticle, BindingResult result) {
        if (result.hasErrors()) {
            return "updateArticle";
        }
        // Retrieve the original article from the database
        Article originalArticle = articleService.getArticleById(articleId);
        // Update the properties of the original article with the values from the updated one
        originalArticle.setTitle(updatedArticle.getTitle());
        originalArticle.setContent(updatedArticle.getContent());
        originalArticle.setImageUrl(updatedArticle.getImageUrl());
        originalArticle.setAuthor(updatedArticle.getAuthor());
        originalArticle.setLastUpdateDate(new Date());

        // Save the updated article
        articleService.saveArticle(originalArticle);

        return "redirect:/dashboardArticles";
    }


    @PostMapping("/deleteArticle/{articleId}")
    public String deleteArticle(@PathVariable("articleId") Long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/dashboardArticles";
    }

    @GetMapping("/articleDetails/{articleId}")
    public String articleDetails(@PathVariable("articleId") Long articleId, Model model) {
        Article article = articleService.getArticleById(articleId);
        model.addAttribute("article", article);

        List<Article> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);

        return "articleDetails";
    }
    @GetMapping("/articleDetailsAdmin/{articleId}")
    public String articleDetailsAdmin(@PathVariable("articleId") Long articleId, Model model) {
        Article article = articleService.getArticleById(articleId);
        model.addAttribute("article", article);

        List<Article> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);

        return "articleDetailsAdmin";
    }



    @GetMapping("/dashboardArticles")
    public String index(Model model) {
        List<Article> articles =articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "dashboardAdminArticles";
    }


}
