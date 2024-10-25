package com.grex.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rank {

    private int rank;
    private String stageName;
    private String countryCode;
    private int learnScore;
    private int testScore;
    private int challengeScore;
    private int otherScore;
    private int totalScore;

}
