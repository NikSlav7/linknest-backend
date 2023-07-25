package com.example.linktree.repo;

import com.example.linktree.domains.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> getUserById(String id);
    Optional<AppUser> getUserByUsername(String username);

    Optional<AppUser> getUserByUsernameOrEmail(String username, String email);
}
