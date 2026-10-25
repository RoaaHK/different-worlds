package com.example.DifferentWorlds.dto;

import com.example.DifferentWorlds.Enums.Category;
import com.example.DifferentWorlds.Enums.ContentTypes;
import com.example.DifferentWorlds.Enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkResponseDTO {
    private Integer itemId;
    private String title;
    private String about;
    private Double price;
    private Boolean inStore;
    private Integer counts;
    private Category category;
    private ContentTypes contentType;
    private Genre genre;
    private Integer authorId;
    private String authorName;
}