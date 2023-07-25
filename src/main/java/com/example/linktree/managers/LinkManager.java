package com.example.linktree.managers;

import com.example.linktree.dto.LinkDTO;
import com.example.linktree.domains.AppUser;
import com.example.linktree.domains.Link;
import com.example.linktree.domains.Linknest;
import com.example.linktree.exceptions.NoRightsException;
import com.example.linktree.exceptions.NoSuchEntityException;
import com.example.linktree.requests.AddNewLinkRequest;
import com.example.linktree.requests.RemoveLinkRequest;
import com.example.linktree.services.LinkService;
import com.example.linktree.services.LinknestService;
import com.example.linktree.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/link")
public class LinkManager {

    private final UserService userService;

    private final LinknestService linknestService;

    private final LinkService linkService;

    public LinkManager(UserService userService, LinknestService linknestService, LinkService linkService) {
        this.userService = userService;
        this.linknestService = linknestService;
        this.linkService = linkService;
    }

    @PostMapping("/add")
    public void addNewLink(@RequestBody AddNewLinkRequest request) throws NoSuchEntityException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser appUser = userService.getAppUserByUsername(username);
        List<Linknest> list = appUser.getLinknests().stream().filter(nest -> nest.getId().getName().equals(request.getLinknestName())).collect(Collectors.toList());
        if (list.size() == 0) throw new NoSuchEntityException("No Linknest with such name existing");
        Linknest nest = list.get(0);
        Link link = new Link(request, nest);
        linkService.saveLink(link);
    }
    @PostMapping("/update")
    public void updateLink(@RequestBody @Valid LinkDTO linkDTO) throws NoSuchEntityException, NoRightsException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Link link = linkService.getLinkById(linkDTO.getId());
        if (!link.getLinknest().getId().getAppUser().getUsername().equals(username)) throw new NoRightsException("This user doesn't have right to do that");
        link.updateFromDTO(linkDTO);
        linkService.saveLink(link);
    }

    @PostMapping("/remove")
    public void removeLink(@RequestBody RemoveLinkRequest request) throws NoSuchEntityException, NoRightsException {
        Link link = linkService.getLinkById(request.getLinkId());
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (!link.getLinknest().getId().getAppUser().getUsername().equals(username)) throw new NoRightsException("This user doesn't have right to do that");
        linkService.removeLink(link);
    }

}
