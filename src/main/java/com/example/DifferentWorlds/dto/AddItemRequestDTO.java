package com.example.DifferentWorlds.dto;

import com.example.DifferentWorlds.Enums.Category;
import com.example.DifferentWorlds.Enums.ContentTypes;
import com.example.DifferentWorlds.Enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String about;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "inStore flag is required")
    private Boolean inStore;

    @NotNull(message = "Initial stock count is required")
    private Integer counts;

    @NotNull(message = "Author ID is required")
    private Integer authorId;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Content type is required")
    private ContentTypes contentType;

    @NotNull(message = "Genre is required")
    private Genre genre;
}