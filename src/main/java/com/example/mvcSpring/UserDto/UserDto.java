package com.example.mvcSpring.UserDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
    @JsonProperty private Long id;
    @JsonProperty private String username;
    @JsonProperty private Long phone;
    @JsonProperty private String firstName;
    @JsonProperty private String lastName;
    @JsonProperty private String email;


}
