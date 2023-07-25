package com.example.linktree.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterRequest {

    @NotNull @NotBlank @NotEmpty
    private String email, username, password;
}
