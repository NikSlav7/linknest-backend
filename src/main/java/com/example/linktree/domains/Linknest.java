package com.example.linktree.domains;


import com.example.linktree.requests.LinknestCreationRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Linknest {

    @EmbeddedId
    private LinknestId id;

    private String textForVisitors;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "linknest")
    private List<Link> links;

    public Linknest(LinknestCreationRequest request, AppUser appUser){
        this.id = new LinknestId(appUser, request.getName());

    }

}
