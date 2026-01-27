package com.app.ecom;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private  List<User> userList = new ArrayList<>();

    public List<User> fetchAllUser(){
        return  userList;
    }

    public User fetchUserById(Long id){
        User user = userList.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
        return user;
    }

    public List<User> addUser(User user){
        userList.add(user);
        return userList;
    }
    public boolean updateUser(User user){

        return userList.stream().filter(x -> x.getId().equals(user.getId()))
                .findFirst()
                .map(x -> {
                    x.setFirstName(user.getFirstName());
                    x.setLastName(user.getLastName());
                    return true;
                }).orElse(false);
    }
}
