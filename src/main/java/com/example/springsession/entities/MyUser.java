package com.example.springsession.entities;
import jakarta.persistence.*;

@Entity
@Table
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gmail;
    private String password;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGmail() { return gmail; }
    public void setGmail(String gmail) { this.gmail = gmail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
