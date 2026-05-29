package com.ronaldo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository r, PasswordEncoder passE, JwtService s) {
        this.userRepo = r;
        this.passEncoder = passE;
        this.jwtService = s;
    }

    public void register(RegisterRequest req) {
        //if there exists email(taken), throw email already in use
        //hash password
        //create user obj
        //save user to db
        if(userRepo.findByEmail(req.email()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        String encodedPass = passEncoder.encode(req.password()); // encoded/hashed pass
        User user = new User(req.email(), encodedPass);
        //role assignment
        user.setRole("ROLE_USER");
        userRepo.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        //find user by email. if user DNE, throw invalid credentials
        //compare raw password with hashed password to see if theyre equal. if not, throw invalid credentials
        //login successful, return ok || jwt token
        User user = userRepo.findByEmail(req.email()).orElseThrow(() -> new RuntimeException("Email not found."));
        if(!passEncoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
