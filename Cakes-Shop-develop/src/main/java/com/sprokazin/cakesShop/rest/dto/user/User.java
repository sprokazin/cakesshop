package com.sprokazin.cakesShop.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Schema(description = "User's data")
@Validated
public class User {

    @Null
    @Schema(description = "id of the cake", required = false)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @Schema(description = "User's phone number", required = true)
    @JsonProperty("number")
    private String number;

    @NotNull
    @Schema(description = "User's name", required = true)
    @JsonProperty("name")
    private String name;
}
