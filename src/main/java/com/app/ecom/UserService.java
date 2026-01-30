package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private  List<User> userList = new ArrayList<>();

    public List<User> fetchAllUser(){
        return  userRepository.findAll();
    }

    public User fetchUserById(Long id){
//        User user = userList.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
//        return user;
        return userRepository.findById(id)  .orElse(new User());
    }

    public List<User> addUser(User user){
        userRepository.save(user);
        return userRepository.findAll();
    }
    public boolean updateUser(User user){

        return userRepository.findById(user.getId())
                .map(x -> {
                    x.setFirstName(user.getFirstName());
                    x.setLastName(user.getLastName());
                    userRepository.save(x);
                    return true;
                }).orElse(false);
    }
}
