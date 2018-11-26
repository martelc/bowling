package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.frames.LastFrame;
import martelc.bowling.domain.frames.RegularFrame;
import martelc.bowling.domain.games.Game;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class AllOpenGameFactory extends GameFactory {

    public static Game createGame(ScoringStrategy scoringStrategy) {
        List<Frame> frames = buildFrames(scoringStrategy);

        Game game = new Game(scoringStrategy);
        game.getFrames().addAll(frames);
        game.setScore(calculateScore(scoringStrategy, frames));

        return game;
    }

    private static List<Frame> buildFrames(ScoringStrategy scoringStrategy) {
        List<Frame> frames = new ArrayList<>();

        populateBallsOfRegularFrames(frames, scoringStrategy);
        populateBallsOfLastFrame(frames, scoringStrategy);

        return frames;
    }

    private static void populateBallsOfRegularFrames(List<Frame> frames, ScoringStrategy scoringStrategy) {
        for (int frameCount = 0;
             frameCount < scoringStrategy.getMaximumNumberOfFrames() - 1;
             frameCount++) {

            Frame regularFrame = new RegularFrame(
                    scoringStrategy.getMaximumNumberOfBallsPerRegularFrame(),
                    scoringStrategy.getMaximumNumberOfPointsPerBall());
            frames.add(regularFrame);

            Ball firstBall = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
            regularFrame.addBall(firstBall);
            regularFrame.addBall(BallFactory.createSecondBallOfOpenFrame(scoringStrategy, firstBall));
        }
    }

    private static void populateBallsOfLastFrame(List<Frame> frames, ScoringStrategy scoringStrategy) {
        Frame lastFrame = new LastFrame(
                scoringStrategy.getMaximumNumberOfBallsPerRegularFrame(),
                scoringStrategy.getMaximumNumberOfPointsPerBall());
        frames.add(lastFrame);

        Ball firstBall = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        lastFrame.addBall(firstBall);
        lastFrame.addBall(BallFactory.createSecondBallOfOpenFrame(scoringStrategy, firstBall));
    }

    private static Integer calculateScore(ScoringStrategy scoringStrategy, List<Frame> frames) {
        Integer score = scoringStrategy.getMaximumNumberOfFrames() * scoringStrategy.getMaximumNumberOfPointsPerBall();
        for (Frame frame : frames) {
            for (Ball ball : frame.getBalls()) {
                score += ball.getNumberOfPoints();
            }
        }
        return score;
    }
}