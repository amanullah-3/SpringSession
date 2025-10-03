package com.example.springsession.services;

import com.example.springsession.entities.MyUser;
import com.example.springsession.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public MyUser register(MyUser myUser) {
        return userRepository.save(myUser);
    }

    public MyUser login(String gmail, String password) {
        MyUser myUser = userRepository.findByGmail(gmail);
        if (myUser != null && myUser.getPassword().equals(password)) {
            return myUser;
        }
        return null;
    }
}
