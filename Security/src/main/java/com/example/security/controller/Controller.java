package com.example.security.controller;

import com.example.security.dtos.LoginRequestDTO;
import com.example.security.utils.JwUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwUtil jwUtil;

    @GetMapping("/hello")
    public ResponseEntity<String> getBCryptPasswordEncoder(){
        return ResponseEntity.ok("Hello from the other side");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        authenticationManager.authenticate(token);
        String jwt=jwUtil.generate(request.getUsername());
        return ResponseEntity.ok(jwt);
    }
}
