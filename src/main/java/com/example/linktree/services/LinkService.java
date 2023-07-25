package com.example.linktree.services;


import com.example.linktree.domains.Link;
import com.example.linktree.exceptions.NoSuchEntityException;
import com.example.linktree.repo.LinkRepo;
import org.springframework.stereotype.Component;


@Component
public class LinkService {


    private final LinkRepo linkRepo;

    public LinkService(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    public Link saveLink(Link link){
        return linkRepo.save(link);
    }

    public Link getLinkById(String linkId) throws NoSuchEntityException {
        return linkRepo.getLinkById(linkId).orElseThrow(()-> new NoSuchEntityException("No link with such name was found"));
    }

    public void removeLink(Link link){
        linkRepo.delete(link);
    }

}
