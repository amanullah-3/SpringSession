package com.example.springsession.repositories;

import com.example.springsession.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<MyUser, Long> {
    MyUser findByGmail(String gmail);
}
