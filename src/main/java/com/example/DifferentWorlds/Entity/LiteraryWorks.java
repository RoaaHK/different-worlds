package com.example.DifferentWorlds.Entity;

import com.example.DifferentWorlds.Enums.ContentTypes;
import com.example.DifferentWorlds.Enums.FictionGenres;
import com.example.DifferentWorlds.Enums.LiteraryWorkStatus;
import com.example.DifferentWorlds.Enums.NonFictionGenres;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiteraryWorks {

    private String title;
    private String about;
    private double price;
    private boolean inStore;
    private int counts;
//    private boolean approvedByAdmin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

//    @Enumerated(EnumType.STRING)
//    private LiteraryWorkStatus approvalStatus = LiteraryWorkStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private ContentTypes contentType;

    @Enumerated(EnumType.STRING)
    private NonFictionGenres nonFictionGenre;

    @Enumerated(EnumType.STRING)
    private FictionGenres fictionGenre;

}
