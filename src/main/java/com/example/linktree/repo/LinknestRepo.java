package com.example.linktree.repo;

import com.example.linktree.domains.Linknest;
import com.example.linktree.domains.LinknestId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinknestRepo extends JpaRepository<Linknest, LinknestId> {
    Optional<Linknest> getLinknestById(LinknestId id);
    void deleteById(LinknestId id);

}
