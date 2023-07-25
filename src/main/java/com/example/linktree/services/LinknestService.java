package com.example.linktree.services;

import com.example.linktree.domains.AppUser;
import com.example.linktree.domains.Linknest;
import com.example.linktree.domains.LinknestId;
import com.example.linktree.exceptions.NameInUseException;
import com.example.linktree.exceptions.NoSuchEntityException;
import com.example.linktree.repo.LinknestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LinknestService {

    private final LinknestRepo linknestRepo;

    @Autowired
    public LinknestService(LinknestRepo linknestRepo) {
        this.linknestRepo = linknestRepo;
    }

    public Linknest saveLinknest(Linknest linknest){
        return linknestRepo.save(linknest);
    }

    public void deleteLinknest(Linknest linknest){
        linknestRepo.delete(linknest);
    }

    public void checkIfNameNotInUse(AppUser appUser, String name) throws NameInUseException {
        LinknestId id = new LinknestId(appUser, name);
        if (linknestRepo.getLinknestById(id).isPresent()) throw new NameInUseException("Such name is already used");
    }

    public void checkIfNameExists(AppUser appUser, String name) throws NoSuchEntityException {
        LinknestId id = new LinknestId(appUser, name);
        if (linknestRepo.getLinknestById(id).isEmpty()) throw new NoSuchEntityException( "No linknest with such name");
    }

    public Linknest getLinknest(AppUser appUser, String name) throws NoSuchEntityException {
        LinknestId id = new LinknestId(appUser, name);
        return linknestRepo.getLinknestById(id).orElseThrow(()-> new NoSuchEntityException("No linknest with such name"));
    }

    public void deleteLinknestById(LinknestId linknestId){
        linknestRepo.deleteById(linknestId);
    }
}
