package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.frames.LastFrame;
import martelc.bowling.domain.frames.RegularFrame;
import martelc.bowling.domain.games.Game;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class PerfectGameFactory extends GameFactory {

    public static Game createGame(ScoringStrategy scoringStrategy) {
        Game game = new Game(scoringStrategy);
        game.getFrames().addAll(buildFrames(scoringStrategy));
        game.setScore(calculateScore(scoringStrategy));

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

            Ball onlyBall = new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall());
            regularFrame.addBall(onlyBall);

            addBonusBallToPreviousFrame(frames, onlyBall);
            addBonusBallToPreviousPreviousFrame(frames, onlyBall);
        }
    }

    private static void populateBallsOfLastFrame(List<Frame> frames, ScoringStrategy scoringStrategy) {
        Frame lastFrame = new LastFrame(
                scoringStrategy.getMaximumNumberOfBallsPerRegularFrame(),
                scoringStrategy.getMaximumNumberOfPointsPerBall());
        frames.add(lastFrame);

        Ball firstBall = new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall());
        Ball secondBall = new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall());

        lastFrame.addBall(firstBall);
        lastFrame.addBall(secondBall);
        lastFrame.addBall(new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall()));

        addBonusBallToPreviousFrame(frames, firstBall);
        addBonusBallToPreviousFrame(frames, secondBall);
        addBonusBallToPreviousPreviousFrame(frames, firstBall);
    }

    private static Integer calculateScore(ScoringStrategy scoringStrategy) {
        Integer score = scoringStrategy.getMaximumNumberOfFrames() * scoringStrategy.getMaximumNumberOfPointsPerBall();
        score *= 3;
        return score;
    }
}
