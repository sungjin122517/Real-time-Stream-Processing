package org.example.comp4651.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gamestats")
public class GameStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String gameid;

//    @ManyToOne
//    @JoinColumn(name = "team_home", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_GameStats_HomeTeam"))
//    private Team homeTeam;

//    @Column(name = "home_win_prob")
//    private double homeWinProb;

    @Column(name = "home_score")
    private int homeScore;

    @Column(name = "home_foul")
    private int homeFoul;

//    @Column(name = "home_importance_value")
//    private double homeImportanceValue;

//    @ManyToOne
//    @JoinColumn(name = "team_away", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_GameStats_AwayTeam"))
//    private Team awayTeam;

//    @Column(name = "away_win_prob")
//    private double awayWinProb;

    @Column(name = "away_score")
    private int awayScore;

    @Column(name = "away_foul")
    private int awayFoul;

//    @Column(name = "away_importance_value")
//    private double awayImportanceValue;

    @Column(name = "situation")
    private int situation;
}
