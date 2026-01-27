package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController {


    private final UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return  ResponseEntity.ok(userService.fetchAllUser()) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.fetchUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return  new ResponseEntity<>(user,HttpStatus.OK);

    }


    @PostMapping("createuser")
    public ResponseEntity<String> createUser(@RequestBody User user){
        try {
            userService.addUser(user);
            return  new ResponseEntity<> ("User Added", HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<> ( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("updateuser")
    public ResponseEntity<String> updateUser(@RequestBody User user){
        boolean resp = userService.updateUser(user);
        if(resp){
            return  new ResponseEntity<> ("User Updated", HttpStatus.OK);
        } else {
            return  new ResponseEntity<> ("User Not Found", HttpStatus.NOT_FOUND);
        }

    }
}
