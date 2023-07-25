package com.example.linktree.domains;

import com.example.linktree.dto.LinkDTO;
import com.example.linktree.requests.AddNewLinkRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Link {

    @Id
    private String id;
    private String link;
    private String linkName;
    private String linkLogoName;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Linknest linknest;


    public Link(AddNewLinkRequest addNewLinkRequest, Linknest linknest){
        this.id = UUID.randomUUID().toString();
        this.link = addNewLinkRequest.getLink();
        this.linkLogoName = addNewLinkRequest.getLinkType();
        this.linkName = addNewLinkRequest.getLinkName();
        this.linknest = linknest;
    }

    public Link updateFromDTO(LinkDTO linkDTO){
        this.link = linkDTO.getLink();
        this.linkLogoName = linkDTO.getLinkLogoName();
        this.linkName = linkDTO.getLinkName();
        return this;
    }
}
