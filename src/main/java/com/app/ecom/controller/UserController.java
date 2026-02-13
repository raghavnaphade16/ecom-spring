package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import com.app.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return  ResponseEntity.ok(userService.fetchAllUser()) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
      return userService.fetchUserById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("createuser")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        try {
            userService.addUser(userRequest);
            return  new ResponseEntity<> ("User Added", HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<> ( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("updateuser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest user){
        boolean resp = userService.updateUser(id, user);
        if(resp){
            return  new ResponseEntity<> ("User Updated", HttpStatus.OK);
        } else {
            return  new ResponseEntity<> ("User Not Found", HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId){
        boolean resp = userService.deleteUser(userId);
        return resp
                ? ResponseEntity.ok("User deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found");
    }
}
