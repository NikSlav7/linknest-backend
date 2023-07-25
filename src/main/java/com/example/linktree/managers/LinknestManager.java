package com.example.linktree.managers;


import com.example.linktree.dto.LinknestDTO;
import com.example.linktree.domains.AppUser;
import com.example.linktree.domains.Linknest;
import com.example.linktree.domains.LinknestId;
import com.example.linktree.exceptions.MaxExceededException;
import com.example.linktree.exceptions.NameInUseException;
import com.example.linktree.exceptions.NoSuchEntityException;
import com.example.linktree.requests.LinknestCreationRequest;
import com.example.linktree.requests.LinknestUpdateRequest;
import com.example.linktree.requests.RemoveLinknestRequest;
import com.example.linktree.services.LinknestService;
import com.example.linktree.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/linknest")
public class LinknestManager {

    private final UserService userService;

    private final LinknestService linknestService;

    @Value("${linknest.max-count}")
    private int maxLinknestCount;

    @Autowired
    public LinknestManager(UserService userService, LinknestService linknestService) {
        this.userService = userService;
        this.linknestService = linknestService;
    }

    @PostMapping("/create")
    public void createLinknest(@RequestBody LinknestCreationRequest request) throws NameInUseException, MaxExceededException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser appUser = userService.getAppUserByUsername(username);
        checkIfCanCreate(appUser);
        linknestService.checkIfNameNotInUse(appUser, request.getName());
        Linknest linknest = new Linknest(request, appUser);
        linknestService.saveLinknest(linknest);
    }

    @GetMapping("/get-max-count")
    public int getMaxLinknestCount(){
        return maxLinknestCount;
    }
    @PostMapping("/remove")
    public void removeLinknest(@RequestBody RemoveLinknestRequest request) throws NoSuchEntityException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser appUser = userService.getAppUserByUsername(username);
        Linknest linknest = linknestService.getLinknest(appUser, request.getName());
        linknestService.deleteLinknest(linknest);
    }

    @PostMapping("/update")
    public void updateLinknest(@RequestBody LinknestUpdateRequest linknestUpdateRequest) throws NoSuchEntityException, NameInUseException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser appUser = userService.getAppUserByUsername(username);
        Linknest linknest = linknestService.getLinknest(appUser, linknestUpdateRequest.getOldName());

        //if user changed the name
        if (!linknestUpdateRequest.getOldName().equals(linknestUpdateRequest.getNewName())) {
            linknestService.checkIfNameNotInUse(appUser, linknestUpdateRequest.getNewName());
            linknestService.deleteLinknestById(new LinknestId(appUser, linknestUpdateRequest.getOldName()));
        }

        linknest.setTextForVisitors(linknestUpdateRequest.getTextForVisitors());
        linknest.getId().setName(linknestUpdateRequest.getNewName());
        linknestService.saveLinknest(linknest);
    }

    @GetMapping("/exists")
    public void checkIfNestExists(@RequestParam String linknestName) throws NoSuchEntityException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser appUser = userService.getAppUserByUsername(username);
        linknestService.checkIfNameExists(appUser, linknestName);
    }

    @GetMapping("/get")
    public LinknestDTO getLinknest(@RequestParam String linknestName) throws NoSuchEntityException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser appUser = userService.getAppUserByUsername(username);
        return new LinknestDTO(linknestService.getLinknest(appUser, linknestName));
    }
    @GetMapping("/get-share")
    public LinknestDTO getLinknestShare(@RequestParam String linknestName, @RequestParam String ownerId) throws NoSuchEntityException {
        AppUser appUser = userService.getAppUserById(ownerId);
        return new LinknestDTO(linknestService.getLinknest(appUser, linknestName));
    }

    @GetMapping("/get-all")
    public List<LinknestDTO> getAllLinknests(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser appUser = userService.getAppUserByUsername(username);
        return appUser.getLinknests().stream().map(LinknestDTO::new).collect(Collectors.toList());
    }

    private void checkIfCanCreate(AppUser appUser) throws MaxExceededException {
        if (appUser.getLinknests().size() >= maxLinknestCount) throw new MaxExceededException("Max count of linknests is reached");
    }
}
