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

    @Update({
            "<script>",
            "<foreach collection='list' item='progress' separator=';'>",
            "UPDATE GREX_PROGRESS",
            "SET",
            "g1 = #{progress.g1}, g2 = #{progress.g2}, g3 = #{progress.g3}, g4 = #{progress.g4}, g5 = #{progress.g5},",
            "g6 = #{progress.g6}, g7 = #{progress.g7}, g8 = #{progress.g8}, g9 = #{progress.g9}, g10 = #{progress.g10},",
            "g11 = #{progress.g11}, g12 = #{progress.g12}, g13 = #{progress.g13}, g14 = #{progress.g14}, g15 = #{progress.g15},",
            "g16 = #{progress.g16}, g17 = #{progress.g17}, g18 = #{progress.g18}, g19 = #{progress.g19}, g20 = #{progress.g20},",
            "g21 = #{progress.g21}, g22 = #{progress.g22}, g23 = #{progress.g23}, g24 = #{progress.g24}, g25 = #{progress.g25},",
            "g26 = #{progress.g26}, g27 = #{progress.g27}, g28 = #{progress.g28}, g29 = #{progress.g29}, g30 = #{progress.g30},",
            "g31 = #{progress.g31}, g32 = #{progress.g32}, g33 = #{progress.g33}, g34 = #{progress.g34}, g35 = #{progress.g35},",
            "g36 = #{progress.g36}, g37 = #{progress.g37}, g38 = #{progress.g38}, g39 = #{progress.g39}, g40 = #{progress.g40},",
            "g41 = #{progress.g41}, g42 = #{progress.g42}, g43 = #{progress.g43}, g44 = #{progress.g44}, g45 = #{progress.g45},",
            "g46 = #{progress.g46}, g47 = #{progress.g47}, g48 = #{progress.g48}, g49 = #{progress.g49}, g50 = #{progress.g50},",
            "g51 = #{progress.g51}, g52 = #{progress.g52}, g53 = #{progress.g53}, g54 = #{progress.g54}, g55 = #{progress.g55},",
            "g56 = #{progress.g56}, g57 = #{progress.g57}, g58 = #{progress.g58}, g59 = #{progress.g59}, g60 = #{progress.g60},",
            "g61 = #{progress.g61}, g62 = #{progress.g62}, g63 = #{progress.g63}, g64 = #{progress.g64}, g65 = #{progress.g65},",
            "g66 = #{progress.g66}, g67 = #{progress.g67}, g68 = #{progress.g68}, g69 = #{progress.g69}, g70 = #{progress.g70},",
            "g71 = #{progress.g71}, g72 = #{progress.g72}, g73 = #{progress.g73}, g74 = #{progress.g74}, g75 = #{progress.g75},",
            "g76 = #{progress.g76}, g77 = #{progress.g77}, g78 = #{progress.g78}, g79 = #{progress.g79}, g80 = #{progress.g80},",
            "g81 = #{progress.g81}, g82 = #{progress.g82}, g83 = #{progress.g83}, g84 = #{progress.g84}, g85 = #{progress.g85},",
            "g86 = #{progress.g86}, g87 = #{progress.g87}, g88 = #{progress.g88}, g89 = #{progress.g89}, g90 = #{progress.g90},",
            "g91 = #{progress.g91}, g92 = #{progress.g92}, g93 = #{progress.g93}, g94 = #{progress.g94}, g95 = #{progress.g95},",
            "g96 = #{progress.g96}, g97 = #{progress.g97}, g98 = #{progress.g98}, g99 = #{progress.g99}, g100 = #{progress.g100},",
            "g101 = #{progress.g101}, g102 = #{progress.g102}, g103 = #{progress.g103}, g104 = #{progress.g104}, g105 = #{progress.g105},",
            "g106 = #{progress.g106}, g107 = #{progress.g107}, g108 = #{progress.g108}, g109 = #{progress.g109}, g110 = #{progress.g110},",
            "g111 = #{progress.g111}, g112 = #{progress.g112}, g113 = #{progress.g113}, g114 = #{progress.g114}, g115 = #{progress.g115},",
            "g116 = #{progress.g116}, g117 = #{progress.g117}, g118 = #{progress.g118}, g119 = #{progress.g119}, g120 = #{progress.g120},",
            "g121 = #{progress.g121}, g122 = #{progress.g122}, g123 = #{progress.g123}, g124 = #{progress.g124}, g125 = #{progress.g125},",
            "g126 = #{progress.g126}, g127 = #{progress.g127}, g128 = #{progress.g128}, g129 = #{progress.g129}, g130 = #{progress.g130},",
            "g131 = #{progress.g131}, g132 = #{progress.g132}, g133 = #{progress.g133}, g134 = #{progress.g134}, g135 = #{progress.g135},",
            "g136 = #{progress.g136}, g137 = #{progress.g137}, g138 = #{progress.g138}, g139 = #{progress.g139}, g140 = #{progress.g140},",
            "g141 = #{progress.g141}, g142 = #{progress.g142}, g143 = #{progress.g143}, g144 = #{progress.g144}, g145 = #{progress.g145},",
            "g146 = #{progress.g146}, g147 = #{progress.g147}, g148 = #{progress.g148}, g149 = #{progress.g149}, g150 = #{progress.g150},",
            "g151 = #{progress.g151}, g152 = #{progress.g152}, g153 = #{progress.g153}, g154 = #{progress.g154}, g155 = #{progress.g155},",
            "g156 = #{progress.g156}, g157 = #{progress.g157}, g158 = #{progress.g158}, g159 = #{progress.g159}, g160 = #{progress.g160},",
            "g161 = #{progress.g161}, g162 = #{progress.g162}, g163 = #{progress.g163}, g164 = #{progress.g164}, g165 = #{progress.g165},",
            "g166 = #{progress.g166}, g167 = #{progress.g167}, g168 = #{progress.g168}, g169 = #{progress.g169}, g170 = #{progress.g170},",
            "g171 = #{progress.g171}, g172 = #{progress.g172}, g173 = #{progress.g173}, g174 = #{progress.g174}, g175 = #{progress.g175},",
            "g176 = #{progress.g176}, g177 = #{progress.g177}, g178 = #{progress.g178}, g179 = #{progress.g179}, g180 = #{progress.g180},",
            "g181 = #{progress.g181}, g182 = #{progress.g182}, g183 = #{progress.g183}, g184 = #{progress.g184}, g185 = #{progress.g185},",
            "g186 = #{progress.g186}, g187 = #{progress.g187}, g188 = #{progress.g188}, g189 = #{progress.g189}, g190 = #{progress.g190},",
            "g191 = #{progress.g191}, g192 = #{progress.g192}, g193 = #{progress.g193}, g194 = #{progress.g194}, g195 = #{progress.g195},",
            "g196 = #{progress.g196}, g197 = #{progress.g197}, g198 = #{progress.g198}, g199 = #{progress.g199}, g200 = #{progress.g200}",
            "WHERE user_stage_name = #{progress.stageName}",
            "</foreach>",
            "</script>"
    })
    void batchUpdateProgress(@Param("list") List<Progress> progressList);


}
