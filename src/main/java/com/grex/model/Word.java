package com.grex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private String wordId;
    private String word;
    private String meaning;
    private List<String> synonyms;
    private List<String> examples;
}


