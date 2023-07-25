package com.example.linktree.dto;


import com.example.linktree.domains.Linknest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LinknestDTO {


    private String ownerId, name, textForVisitors;
    private List<LinkDTO> links;

    public LinknestDTO(Linknest linknest){
        this.textForVisitors = linknest.getTextForVisitors();
        this.name = linknest.getId().getName();
        this.ownerId = linknest.getId().getAppUser().getId();
        this.links = linknest.getLinks().stream().map((link -> new LinkDTO(link))).toList();
    }
}
