package com.example.DifferentWorlds.Entity;

import com.example.DifferentWorlds.Enums.Category;
import com.example.DifferentWorlds.Enums.ContentTypes;
import com.example.DifferentWorlds.Enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiteraryWorksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    private String title;
    private String about;
    private Double price;
    private Boolean inStore;
    private Integer counts;
//    private boolean approvedByAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthorEntity author;

//    @Enumerated(EnumType.STRING)
//    private LiteraryWorkStatus approvalStatus = LiteraryWorkStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ContentTypes contentType;

    @Enumerated(EnumType.STRING)
    private Genre genre;

}
