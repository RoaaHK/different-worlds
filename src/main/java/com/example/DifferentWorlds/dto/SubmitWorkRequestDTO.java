package com.example.DifferentWorlds.dto;

import com.example.DifferentWorlds.Enums.Category;
import com.example.DifferentWorlds.Enums.ContentTypes;
import com.example.DifferentWorlds.Enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitWorkRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String about;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Content type is required")
    private ContentTypes contentType;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "Author ID is required")
    private Integer authorId;
}