//package com.example.DifferentWorlds.Controller;
//import com.example.DifferentWorlds.Enums.Roles;
//import com.example.DifferentWorlds.JWT.JwtUtils;
//import com.example.DifferentWorlds.dto.RequestDTO;
//import com.example.DifferentWorlds.dto.ResponseDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@RestController
//public class AuthController {
//    //@PreAuthorize("")
//    @GetMapping("/hello")
//    public String Hello(){
//        return "hello";
//    }
////
////    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/user")
//    public String userEndpoint(){
//        return "Hello User";
//    }
//    private final AuthenticationManager authenticationManager;
//
//    private final JwtUtils jwtUtils;
//
//    @Autowired
//    AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils){
//        this.authenticationManager = authenticationManager;
//        this.jwtUtils= jwtUtils;
//    }
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    /// auth, first , if valid ,,,set the context,,,with the help of user details we generate jwt token
//    /// jwt token only is generated if the user is auth.
//    /// we need to pass this rules in response
//    /// response crafted
//    /// create login obj
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody RequestDTO loginRequest) {
//        Authentication authentication;
//        try {
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
//        }
//        catch (AuthenticationException exception){
//            Map<String, Object> map = new HashMap<>();
//            map.put("msg", "bad credentials");
//            map.put("status", false);
//            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
//        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String jwt = jwtUtils.generateToken(userDetails);
//        List<Roles> roles = userDetails.getAuthorities().stream()
//                .map(auth -> Roles.valueOf(auth.getAuthority()))
//                .collect(Collectors.toList());
//        ResponseDTO response = new ResponseDTO(jwt, userDetails.getUsername(),roles);
//        return ResponseEntity.ok(response);
//    }
////
////    @PreAuthorize("hasRole('Admin')")
////    @GetMapping("/admin")
////    public String adminEndpoint(){
////        return "Hello Admin";
////    }
//}

// TODO remove unused classes
