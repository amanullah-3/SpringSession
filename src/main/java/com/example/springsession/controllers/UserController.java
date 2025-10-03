package com.example.springsession.controllers;
import com.example.springsession.entities.MyUser;
import com.example.springsession.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Register new myUser
    @PostMapping("/register")
    public String register(@RequestBody MyUser myUser) {
        userService.register(myUser);
        return "User registered successfully!";
    }

    // Login and create session
    @PostMapping("/login")
    public String login(@RequestParam String gmail,
                        @RequestParam String password,
                        HttpSession session) {
        MyUser user = userService.login(gmail, password); // check password properly
        if (user != null) {
            // 1️⃣ Store custom session info
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());

            // 2️⃣ Create a UserDetails object using Spring Security's User class
            UserDetails userDetails = User.withUsername(user.getName())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();

            // 3️⃣ Set Authentication in SecurityContext
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);

            // 4️⃣ Store SecurityContext in session so Spring Security recognizes it
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);

            return "Login successful! Session created with ID: " + session.getId();
        }
        return "Invalid credentials!";
    }


    // Update user details (only if logged in)
    @PutMapping("/update")
    public String updateUser(@RequestBody MyUser updatedMyUser, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "Please login first!";
        }

        updatedMyUser.setId(userId);
        userService.register(updatedMyUser); // save updated user
        return "User updated successfully!";
    }

    // Check session info
    @GetMapping("/me")
    public String getUserInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String name = (String) session.getAttribute("userName");

        if (userId == null) {
            return "Not logged in!";
        }
        return "Logged in as " + name + " (ID: " + userId + ")";
    }

    // Logout and invalidate session
    @PostMapping("/signOut")
    public String logout(HttpSession session) {
        // 1️⃣ Invalidate the session
        session.invalidate();

        // 2️⃣ Clear Spring Security context
        SecurityContextHolder.clearContext();

        // 3️⃣ Return success message
        return "Logged out successfully!";
    }
    @GetMapping("/hello")
    public String func(){
        return "hello spring boot session";
    }

}
