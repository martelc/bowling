package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.frames.LastFrame;
import martelc.bowling.domain.frames.RegularFrame;
import martelc.bowling.domain.games.Game;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class RandomBallsGameFactory extends GameFactory {

    public static Game createGame(ScoringStrategy scoringStrategy) {
        List<Frame> frames = buildFrames(scoringStrategy);

        Game game = new Game(scoringStrategy);
        game.getFrames().addAll(frames);
        game.setScore(GameFactory.calculateScoreSuboptimal(frames));

        return game;
    }

    private static List<Frame> buildFrames(ScoringStrategy scoringStrategy) {
        List<Frame> frames = new ArrayList<>();

        populateBallsOfRegularFrames(frames, scoringStrategy);
        populateBallsOfLastFrame(frames, scoringStrategy);
        populateBonusBallsOfRegularFrames(frames, scoringStrategy);
        populateBonusBallsOfLastFrame(frames, scoringStrategy);

        return frames;
    }

    private static void populateBallsOfRegularFrames(List<Frame> frames, ScoringStrategy scoringStrategy) {
        Integer maximumNumberOfFrames = scoringStrategy.getMaximumNumberOfFrames() - 1;
        for (int frameCount = 0; frameCount < maximumNumberOfFrames; frameCount++) {

            Frame regularFrame = new RegularFrame(
                    scoringStrategy.getMaximumNumberOfBallsPerRegularFrame(),
                    scoringStrategy.getMaximumNumberOfPointsPerBall());
            frames.add(regularFrame);

            Integer randomTypeOfRegularFrame = new Random().nextInt();
            Ball randomPointsBall = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
            if (randomTypeOfRegularFrame % 4 == 0) {
                // strike
                regularFrame.addBall(new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall()));
            } else if (randomTypeOfRegularFrame % 3 == 0) {
                // spare
                regularFrame.addBall(randomPointsBall);
                regularFrame.addBall(BallFactory.createSecondBallOfSpareFrame(scoringStrategy, randomPointsBall));
            } else if (randomTypeOfRegularFrame % 2 == 0) {
                // open
                regularFrame.addBall(randomPointsBall);
                regularFrame.addBall(BallFactory.createSecondBallOfOpenFrame(scoringStrategy, randomPointsBall));
            } else {
                // missed
                regularFrame.addBall(randomPointsBall);
                regularFrame.addBall(new Ball(0));
            }
        }
    }

    private static void populateBallsOfLastFrame(List<Frame> frames, ScoringStrategy scoringStrategy) {
        Frame lastFrame = new LastFrame(
                scoringStrategy.getMaximumNumberOfBallsPerRegularFrame(),
                scoringStrategy.getMaximumNumberOfPointsPerBall());
        frames.add(lastFrame);

        Integer randomTypeOfLastFrame = new Random().nextInt();
        Ball randomPointsBall = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        if (randomTypeOfLastFrame % 4 == 0) {
            // strike
            lastFrame.addBall(new Ball(scoringStrategy.getMaximumNumberOfPointsPerBall()));
            lastFrame.addBall(randomPointsBall);
            lastFrame.addBall(BallFactory.createSecondBallOfOpenFrame(scoringStrategy, randomPointsBall));
        } else if (randomTypeOfLastFrame % 3 == 0) {
            // spare
            lastFrame.addBall(randomPointsBall);
            lastFrame.addBall(BallFactory.createSecondBallOfSpareFrame(scoringStrategy, randomPointsBall));
        } else if (randomTypeOfLastFrame % 2 == 0) {
            // open
            lastFrame.addBall(randomPointsBall);
            lastFrame.addBall(BallFactory.createSecondBallOfOpenFrame(scoringStrategy, randomPointsBall));
        } else {
            // missed
            lastFrame.addBall(new Ball(0));
            lastFrame.addBall(randomPointsBall);
        }
    }

    private static void populateBonusBallsOfRegularFrames(List<Frame> frames, ScoringStrategy scoringStrategy) {
        for (int frameCount = 0; frameCount < scoringStrategy.getMaximumNumberOfFrames() - 1; frameCount++) {
            Frame frame = frames.get(frameCount);
            if ((frame.isStrike() || frame.isSpare()) && frameCount < scoringStrategy.getMaximumNumberOfFrames() - 1) {
                Frame nextFrame = frames.get(frameCount + 1) ;
                frame.addBonusBall(nextFrame.getBalls().get(0));
            }
            if (frame.isStrike()) {
                if (frameCount < scoringStrategy.getMaximumNumberOfFrames() - 2) {
                    Frame nextFrame = frames.get(frameCount + 1);
                    if (nextFrame.isStrike()) {
                        Frame nextNextFrame = frames.get(frameCount + 2);
                        frame.addBonusBall(nextNextFrame.getBalls().get(0));
                    } else {
                        frame.addBonusBall(nextFrame.getBalls().get(1));
                    }
                } else {
                    Frame nextFrame = frames.get(frameCount + 1);
                    frame.addBonusBall(nextFrame.getBalls().get(0));
                    frame.addBonusBall(nextFrame.getBalls().get(1));
                }
            }
        }
    }
    private static void populateBonusBallsOfLastFrame(List<Frame> frames, ScoringStrategy scoringStrategy) {
        Frame lastFrame = frames.get(frames.size() - 1);
        if (lastFrame.isStrike() || lastFrame.isSpare()) {
            lastFrame.addBonusBall(BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy));
        }
    }
}
