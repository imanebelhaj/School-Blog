package ma.xproce.schoolnewsboard.web;


import ma.xproce.schoolnewsboard.dao.entites.Article;
import ma.xproce.schoolnewsboard.service.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
            return "redirect:/dashboard";
        } catch (Exception e) {
            // Handle error and redirect to dashboard with error message
            model.addAttribute("errorMessage", "An error occurred while creating the article: " + e.getMessage());
            return "redirect:/dashboard";
        }
    }
    @GetMapping("/createArticle")
    public String createArticleForm(Model model) {
        model.addAttribute("article", new Article());
        return "createArticle";
    }




    // Update an article
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long articleId, @ModelAttribute("article")  Article updatedArticle, BindingResult result) {
        if (result.hasErrors()) {
            return "updatArticle";
        }

        // Retrieve the original task from the database
        Article originalArticle = articleService.getArticleById(articleId);
        // Update the properties of the original task with the values from the updated task
        originalArticle.setTitle(updatedArticle.getTitle());
        originalArticle.setContent(updatedArticle.getContent());
        originalArticle.setImageUrl(updatedArticle.getImageUrl());

        originalArticle.setLastUpdateDate(new Date()); // Update last update date
        // Save the updated task
        articleService.saveArticle(originalArticle);

        return "redirect:/dashboard"; // Redirect to dashboard after updating the task
    }


}
