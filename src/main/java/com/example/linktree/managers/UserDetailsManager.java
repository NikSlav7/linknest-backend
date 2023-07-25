package com.example.linktree.managers;

import com.example.linktree.exceptions.CredentialsInUseException;
import com.example.linktree.requests.UserRegisterRequest;
import com.example.linktree.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Component
@RequestMapping("/api/details")
@RestController
public class UserDetailsManager {

    private final UserService userService;

    public UserDetailsManager(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserRegisterRequest request) throws Exception {
        if (!canRegister(request)) throw new Exception();
        userService.createAndSaveUserFromRequest(request);
    }

    private boolean canRegister(UserRegisterRequest request){
        return !userService.checkIfCredentialsInUse(request.getEmail(), request.getUsername());
    }
}
