package ma.xproce.schoolnewsboard.service;

import ma.xproce.schoolnewsboard.dao.entites.User;
import ma.xproce.schoolnewsboard.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  {

    @Autowired
    private UserRepository userRepository;


    public User save(User user) { return userRepository.save(user);}

    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {userRepository.save(user); }

    public void deleteUser(Long userId) {userRepository.deleteById(userId);
    }
    public User updateUser(Long id, User user) {
        return userRepository.save(user);

    }


    public User createUser(User user) {
        user.setCreationDate(new Date());
        user.setLastUpdateDate(null);
        return userRepository.save(user);


    }
}
