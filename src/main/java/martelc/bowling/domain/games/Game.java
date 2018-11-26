package martelc.bowling.domain.games;

import com.google.inject.Inject;
import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final ScoringStrategy scoringStrategy;
    private final List<Frame> frames = new ArrayList<>();
    private Integer score;

    @Inject
    public Game(ScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }

    public ScoringStrategy getScoringStrategy() {
        return scoringStrategy;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer calculateScoreFromASequenceOfRolls(List<Integer> rolls) {
        Integer calculatedPoints = 0;

        for (Integer roll : rolls) {
            calculatedPoints += scoringStrategy.addBallToFramesAndCalculatePoints(frames, new Ball(roll));
        }

        this.score = calculatedPoints;
        return calculatedPoints;
    }
}
