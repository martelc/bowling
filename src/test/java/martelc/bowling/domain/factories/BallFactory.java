package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;

import java.util.Random;

public abstract class BallFactory {
    public static Ball createBallWithRandomNumberOfPointsLessThanMaximum(ScoringStrategy scoringStrategy) {
        return new Ball(new Random().nextInt(scoringStrategy.getMaximumNumberOfPointsPerBall()));
    }

    public static Ball createSecondBallOfSpareFrame(ScoringStrategy scoringStrategy, Ball firstBall) {
        return new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall() - firstBall.getNumberOfPoints());
    }

    public static Ball createSecondBallOfOpenFrame(ScoringStrategy scoringStrategy, Ball firstBall) {
        return new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall() - firstBall.getNumberOfPoints() - 1);
    }
}
