package com.senla.project.socialnetwork.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEventDTO {

    @Size(min = 8)
    @NotBlank
    private String name;

    @Size(min = 13)
    @NotBlank
    private String description;

    @Future
    private LocalDateTime date;

}
