package com.thomas.airline.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDto {
    @Schema(
            description = "Firstname of the user",
            example = "John"
    )
    @NotBlank
    private String firstName;
    @Schema(
            description = "Lastname of the user",
            example = "Doe"
    )
    @NotBlank
    private String lastName;
    @Schema(
            description = "Email of the user",
            example = "JohnDoe@gmail.com"
    )
    @NotBlank
    @Email
    private String email;
    @Schema(
            description = "Password of the user",
            example = "John@123"
    )
    @NotBlank
    private String password;
    @Schema(
            description = "Phone number of the user",
            example = "1234567890"
    )
    @NotBlank
    private String phoneNumber;

    public UserRequestDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
