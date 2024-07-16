package ma.xproce.schoolnewsboard.dao.repositories;

import ma.xproce.schoolnewsboard.dao.entites.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
