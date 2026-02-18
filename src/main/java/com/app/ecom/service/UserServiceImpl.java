package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private  List<User> userList = new ArrayList<>();

    public List<UserResponse> fetchAllUser(){
        return  userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUserById(Long id){
//        User user = userList.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
//        return user;
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user,userRequest);
        userRepository.save(user);
        userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    public boolean updateUser(Long id, UserRequest user){

        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser,user);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }
    public  boolean deleteUser(long userId){
        if(!userRepository.existsById(userId)){
            return false;
        }
        userRepository.deleteById(userId);
        return true;
    }

    private  void updateUserFromRequest(User user, UserRequest userRequest){

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());

        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZip(userRequest.getAddress().getZip());
            address.setState(userRequest.getAddress().getState());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        if(user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZip(user.getAddress().getZip());
            addressDTO.setState(user.getAddress().getState());
            response.setAddressDTO(addressDTO);
        }
        return response;

    }
}


