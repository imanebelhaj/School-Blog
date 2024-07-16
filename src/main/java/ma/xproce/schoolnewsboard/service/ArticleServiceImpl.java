package ma.xproce.schoolnewsboard.service;

import ma.xproce.schoolnewsboard.dao.entites.Article;
import ma.xproce.schoolnewsboard.dao.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl {

    @Autowired
    private ArticleRepository articleRepository;

    public Article getArticleById(Long articleId) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        return articleOptional.orElse(null);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);

    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
