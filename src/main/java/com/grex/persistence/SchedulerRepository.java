package com.grex.persistence;

import com.grex.model.Progress;
import com.grex.model.Rank;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchedulerRepository {

    @Insert("""
            INSERT INTO GREX_RANK (rank, user_stage_name, user_country, learn_score, test_score, challenge_score, other_score, total_score)
            SELECT 
                RANK() OVER (ORDER BY (learn_score + test_score + challenge_score + other_score) DESC) AS rank,
                user_stage_name,
                user_country,
                learn_score,
                test_score,
                challenge_score,
                other_score,
                (learn_score + test_score + challenge_score + other_score) AS total_score
            FROM GREX_SCORE
            ON CONFLICT (user_stage_name) 
            DO UPDATE SET 
                rank = EXCLUDED.rank,
                learn_score = EXCLUDED.learn_score,
                test_score = EXCLUDED.test_score,
                challenge_score = EXCLUDED.challenge_score,
                other_score = EXCLUDED.other_score,
                total_score = EXCLUDED.total_score; """)
    void updateRanks();


    @Update("""
            UPDATE GREX_SCORE
            SET learn_score = subquery.total_score
            FROM (
                SELECT
                    user_stage_name,
                    (g1 + g2 + g3 + g4 + g5 + g6 + g7 + g8 + g9 + g10 + g11 + g12 + g13 + g14 + g15 + g16 + g17 + g18 + g19 + g20 +
                     g21 + g22 + g23 + g24 + g25 + g26 + g27 + g28 + g29 + g30 + g31 + g32 + g33 + g34 + g35 + g36 + g37 + g38 + g39 + g40 +
                     g41 + g42 + g43 + g44 + g45 + g46 + g47 + g48 + g49 + g50 + g51 + g52 + g53 + g54 + g55 + g56 + g57 + g58 + g59 + g60 +
                     g61 + g62 + g63 + g64 + g65 + g66 + g67 + g68 + g69 + g70 + g71 + g72 + g73 + g74 + g75 + g76 + g77 + g78 + g79 + g80 +
                     g81 + g82 + g83 + g84 + g85 + g86 + g87 + g88 + g89 + g90 + g91 + g92 + g93 + g94 + g95 + g96 + g97 + g98 + g99 + g100 +
                     g101 + g102 + g103 + g104 + g105 + g106 + g107 + g108 + g109 + g110 + g111 + g112 + g113 + g114 + g115 + g116 + g117 + g118 +
                     g119 + g120 + g121 + g122 + g123 + g124 + g125 + g126 + g127 + g128 + g129 + g130 + g131 + g132 + g133 + g134 + g135 + g136 +
                     g137 + g138 + g139 + g140 + g141 + g142 + g143 + g144 + g145 + g146 + g147 + g148 + g149 + g150 + g151 + g152 + g153 + g154 +
                     g155 + g156 + g157 + g158 + g159 + g160 + g161 + g162 + g163 + g164 + g165 + g166 + g167 + g168 + g169 + g170 + g171 + g172 +
                     g173 + g174 + g175 + g176 + g177 + g178 + g179 + g180 + g181 + g182 + g183 + g184 + g185 + g186 + g187 + g188 + g189 + g190 +
                     g191 + g192 + g193 + g194 + g195 + g196 + g197 + g198 + g199 + g200) AS total_score
                FROM GREX_PROGRESS
            ) AS subquery
            WHERE GREX_SCORE.user_stage_name = subquery.user_stage_name;
            """)
    void updateLearnScore();



}
