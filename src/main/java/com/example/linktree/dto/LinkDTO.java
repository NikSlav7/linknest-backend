package com.example.linktree.dto;


import com.example.linktree.domains.Link;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LinkDTO {

    @NotNull @NotBlank @NotEmpty
    private String id, link, linkLogoName, linkName;

    public LinkDTO(Link link){
        this.link = link.getLink();
        this.linkLogoName = link.getLinkLogoName();
        this.linkName = link.getLinkName();
        this.id = link.getId();
    }
}
