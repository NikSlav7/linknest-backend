package com.example.linktree.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LinknestUpdateRequest {

    private String oldName, newName, textForVisitors;
}
