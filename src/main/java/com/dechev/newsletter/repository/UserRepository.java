package com.dechev.newsletter.repository;

import com.dechev.newsletter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    @Override
    List<User> findAll();
}
