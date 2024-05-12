package dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Gamestat {
    private String gameId;
    private Integer situation;
    private Integer home_score;
    private Integer away_score;
    private Integer home_foul;
    private Integer away_foul;

}

