package com.example.linktree.services;

import com.example.linktree.domains.AppUser;
import com.example.linktree.exceptions.NoSuchEntityException;
import com.example.linktree.repo.UserRepository;
import com.example.linktree.requests.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkIfCredentialsInUse(String email, String username){
        return userRepository.getUserByUsernameOrEmail(username, email).isPresent();
    }
    public AppUser getAppUserById(String id) throws NoSuchEntityException {
        return userRepository.getUserById(id).orElseThrow(()-> new NoSuchEntityException("No user with such name was found"));
    }

    public AppUser getAppUserByUsername(String username){
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username wasn't found"));
    }

    public AppUser createAndSaveUserFromRequest(UserRegisterRequest request){
        AppUser appUser = new AppUser(request);
        //encoding password
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return saveUser(appUser);
    }

    public AppUser saveUser(AppUser appUser){
        return userRepository.save(appUser);
    }
}
