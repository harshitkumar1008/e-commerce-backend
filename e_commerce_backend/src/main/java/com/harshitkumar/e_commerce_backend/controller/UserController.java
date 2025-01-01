package com.harshitkumar.e_commerce_backend.controller;

import com.harshitkumar.e_commerce_backend.entity.User;
import com.harshitkumar.e_commerce_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("User Registered Successfully!!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error Occurred: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){

        Optional<User> userOptional = userService.findByUsername(user.getUsername());

        if(userOptional.isEmpty()){
            return ResponseEntity.badRequest().body("User Does Not Exists!!");
        }

        User currUser = userOptional.get();

        if(userService.isPassSame(currUser.getPassword(), user.getPassword())){
            return ResponseEntity.ok(currUser);
        }else{
            return ResponseEntity.badRequest().body("User Not Found");
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username){
        Optional<User> userOptional = userService.findByUsername(username);
        return userOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email){
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id){
        Optional<User> userOptional = userService.findById(Long.parseLong(id));
        return userOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id){
        try{
            userService.deleteUser(Long.parseLong(id));
            return ResponseEntity.ok("User Deleted Successfully!!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error Occurred: " + e.getMessage());
        }
    }
}
