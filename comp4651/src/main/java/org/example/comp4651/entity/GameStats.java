package org.example.comp4651.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_stats")
public class GameStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_GameStats_HomeTeam"))
    private Team homeTeam;

//    @Column(name = "home_win_prob")
//    private double homeWinProb;

    @Column(name = "home_score")
    private int homeScore;

    @Column(name = "home_fouls")
    private int homeFouls;

//    @Column(name = "home_importance_value")
//    private double homeImportanceValue;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_GameStats_AwayTeam"))
    private Team awayTeam;

//    @Column(name = "away_win_prob")
//    private double awayWinProb;

    @Column(name = "away_score")
    private int awayScore;

    @Column(name = "away_fouls")
    private int awayFouls;

//    @Column(name = "away_importance_value")
//    private double awayImportanceValue;

    @Column(name = "event_type")
    private int eventType;
}
