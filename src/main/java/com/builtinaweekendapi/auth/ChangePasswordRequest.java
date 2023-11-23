package com.builtinaweekendapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @JsonProperty("existing_password")
    @NotNull(message = "existing_password cannot be null")
    @NotBlank(message = "existing password must not be empty")
    private String existingPassword;

    @JsonProperty("new_password")
    @NotNull(message = "new_password cannot be null")
    @NotBlank(message = "new password must not be empty")
    private String newPassword;

    @JsonProperty("confirmation_password")
    @NotNull(message = "confirmation_password cannot be null")
    @NotBlank(message = "confirmation password must not be empty")
    private String confirmationPassword;

}
