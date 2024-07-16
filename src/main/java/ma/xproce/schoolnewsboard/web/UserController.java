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
            return "redirect:/dashboard";
        } else {
            User user = userRepository.findByUsername(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                // Login successful
                session.setAttribute("loggedInUser", user);
                model.addAttribute("username", username);
                return "redirect:/dashboard";
            } else {
                // Login failed
                model.addAttribute("error", "Invalid username or password.");
                return "redirect:/users/signin";
            }
        }
    }


    @PostMapping("/process_createUser")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        try {
            // Encrypt the user's password before saving
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreationDate(new Date());
            user.setLastUpdateDate(new Date());
            userService.save(user);
            return "redirect:/dashboard";
        } catch (Exception e) {
            // Handle error and redirect to dashboard with error message
            model.addAttribute("errorMessage", "An error occurred while creating the user: " + e.getMessage());
            return "redirect:/dashboard";
        }
    }
    @GetMapping("/createUser")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }


    // Admin can update a user
//    @PutMapping("/admin/update/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User userDetails) {
//        User updatedUser = userService.updateUser(id, userDetails);
//        return ResponseEntity.ok(updatedUser);
//    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Long userId, @ModelAttribute("user")  User updatedUser, BindingResult result) {
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
        originalUser.setLastUpdateDate(new Date()); // Update last update date
        // Save the updated user
        userService.saveUser(originalUser);
        return "redirect:/dashboard"; // Redirect to home after updating the user
    }

    @GetMapping("/updateUser/{userId}")
    public String showUpdateTaskForm(@PathVariable("userId") Long userId, Model model) {
        // Retrieve the user by ID
        User user = userService.getUserById(userId);
        // Check if the user exists
        if (user == null) {
            // Handle case where user with given ID is not found
            return "redirect:/dashboard";
        }
        // Add the task and user lists to the model
        model.addAttribute("user", user);
        return "updateUser"; // Return the name of the HTML template for updating a user
    }


    // Admin can delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
