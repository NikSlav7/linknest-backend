package com.example.linktree.repo;

import com.example.linktree.domains.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepo extends JpaRepository<Link, String> {

    Optional<Link> getLinkById(String id);

}
