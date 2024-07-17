package ma.xproce.schoolnewsboard.web;

import jakarta.servlet.http.HttpSession;
import ma.xproce.schoolnewsboard.dao.entites.User;
import ma.xproce.schoolnewsboard.dao.repositories.UserRepository;
import ma.xproce.schoolnewsboard.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";



    //user and admin login
    @GetMapping("/signin")
    public String showSignInForm(Model model) {
        return "signin";
    }

    @PostMapping("/process_login")
    public String processLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            // Create a dummy admin user object
            User adminUser = new User();
            adminUser.setUsername(ADMIN_USERNAME);
            adminUser.setFullName("Administrator");
            session.setAttribute("loggedInUser", adminUser);
            model.addAttribute("username", username);
            return "redirect:/dashboardUsers";
        } else {
            User user = userRepository.findByUsername(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                // Login successful
                session.setAttribute("loggedInUser", user);
                model.addAttribute("username", username);
                return "redirect:/userDashboard";
            } else {
                // Login failed
                model.addAttribute("error", "Invalid username or password.");
                return "redirect:/users/signin";
            }
        }
    }

    //create User
    @PostMapping("/process_createUser")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        try {
          //   Encrypt the user's password before saving
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreationDate(new Date());
            user.setLastUpdateDate(new Date());
            userService.save(user);
            return "redirect:/dashboardUsers";
        } catch (Exception e) {
            // Handle error and redirect to dashboard with error message
            model.addAttribute("errorMessage", "An error occurred while creating the user: " + e.getMessage());
            return "redirect:/dashboardUsers";
        }
    }
    @GetMapping("/createUser")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }



    //update User
    @GetMapping("/updateUser/{userId}")
    public String UpdateUserForm(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/dashboardUsers";
        }
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/updateUser/{userId}")
    public String updateUser(@PathVariable("userId") Long userId, @ModelAttribute("user")  User updatedUser, BindingResult result) {
        if (result.hasErrors()) {
            return "updateUser";
        }
        // Retrieve the original user from the database
        User originalUser = userService.getUserById(userId);
        // Update the properties of the original user with the values from the updated user
        originalUser.setFullName(updatedUser.getFullName());
        originalUser.setEmail(updatedUser.getEmail());
        originalUser.setImageUrl(updatedUser.getImageUrl());
        if (!updatedUser.getPassword().equals(originalUser.getPassword())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            originalUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        originalUser.setLastUpdateDate(new Date());
        userService.saveUser(originalUser);
        return "redirect:/dashboardUsers";
    }

    @PostMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/dashboardUsers";
    }





}
