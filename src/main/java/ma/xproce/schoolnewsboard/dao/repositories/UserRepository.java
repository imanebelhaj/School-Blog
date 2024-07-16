package ma.xproce.schoolnewsboard.dao.repositories;

import ma.xproce.schoolnewsboard.dao.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
