package com.example.linktree.domains;

import com.example.linktree.requests.UserRegisterRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class AppUser implements UserDetails {

    @Id
    private String id;

    private String password;

    private String username;

    private String email;

    private boolean isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.appUser")
    private List<Linknest> linknests;

    public AppUser(UserRegisterRequest userRegisterRequest){
        this.email = userRegisterRequest.getEmail();
        this.username = userRegisterRequest.getUsername();
        this.password = userRegisterRequest.getPassword();
        generateId();
        activateAccount();
    }
    public void activateAccount(){
        this.isEnabled = true;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
    }
    public void generateId(){
        this.id = UUID.randomUUID().toString();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
