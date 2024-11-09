package org.example.model;

import javax.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "user")
public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username; // User's login name

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // User's role in the application

    // Constructors, getters, and setters
    public User() {}

    // Constructor with all required fields including role
    public User(String name, String username, String email, String password, String role) {
        setName(name);
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    // ID
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Name
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            logger.error("Attempted to set null or empty name to User");
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    // Username
    public String getUsername() { return username; }
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            logger.error("Attempted to set null or empty username to User");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    // Email
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.error("Attempted to set null or empty email to User");
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }

    // Password
    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            logger.error("Attempted to set null or empty password to User");
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    // Role
    public String getRole() { return role; }
    public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            logger.error("Attempted to set null or empty role to User");
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        this.role = role;
    }
}
