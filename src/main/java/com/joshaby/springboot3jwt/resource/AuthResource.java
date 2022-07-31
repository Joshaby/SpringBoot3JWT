package com.joshaby.springboot3jwt.resource;

import com.joshaby.springboot3jwt.dto.JwtDTO;
import com.joshaby.springboot3jwt.dto.LoginDTO;
import com.joshaby.springboot3jwt.dto.MessageDTO;
import com.joshaby.springboot3jwt.dto.SignupDTO;
import com.joshaby.springboot3jwt.entity.User;
import com.joshaby.springboot3jwt.repository.UserRepository;
import com.joshaby.springboot3jwt.security.UserDetailsImpl;
import com.joshaby.springboot3jwt.security.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signin")
    public JwtDTO authenticateUser(@RequestBody LoginDTO login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtDTO(token, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signup) {
        if (userRepository.existsByUsername(signup.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageDTO("Error: Usarname is already taken!"));
        }
        if (userRepository.existsByEmail(signup.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageDTO("Error: Email is already in use!"));
        }
        User user = new User(signup.getUsername(), signup.getName(), signup.getEmail(), encoder.encode(signup.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageDTO("User registered sucessfully"));
    }
}
