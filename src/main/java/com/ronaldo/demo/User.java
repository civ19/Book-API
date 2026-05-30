package com.ronaldo.demo;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "_user") // Named '_user' because 'user' is a reserved keyword in PostgreSQL
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role; // Stores "ROLE_USER" or "ROLE_ADMIN"

    // Keep your standard constructors here!
    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // -------------------------------------------------------------------------
    // 🔒 THE REQUIRED SPRING SECURITY METHODS (Bridges your data to the framework)
    // -------------------------------------------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converts your database string role into a framework authority object
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email; // Your subject/email acts as the unique username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hardcoded to true so accounts don't instantly lock out
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Standard getters/setters for id, email, password, and role...
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getEmail() { return email; }
}
