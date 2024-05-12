package dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Update {
    private String gameId;
    private String player;
    private String team;
//    private String option;
    private Integer situation;
    private String text;
//    private String team_home;
//    private String team_away;
    private Timestamp updateTime;
}

