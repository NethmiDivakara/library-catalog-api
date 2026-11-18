package com.training.library_catalog_api.user.repository;

import com.training.library_catalog_api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail (String email);
}
