package com.michael.usersService.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {
    @NotBlank(message = "Username cannot be null or empty")
    @Size(min = 2, message = "Username must not be less than two characters")
    private String username;
    @NotBlank(message = "First name cannot be null or empty")
    @Size(min = 2, message = "First name must not be less than two characters")
    private String firstName;
    @NotBlank(message = "Last name cannot be null or empty")
    @Size(min = 2, message = "Last name must not be less than two characters")
    private String lastName;
    @Email
    @NotBlank(message = "Email cannot be null or empty")
    private String email;
    @NotBlank(message = "Password cannot be null or empty")
    @Size(min = 8, max = 16, message = "Password must be equal or grater than 8 characters and less than 16 characters")
    private String password;
}
