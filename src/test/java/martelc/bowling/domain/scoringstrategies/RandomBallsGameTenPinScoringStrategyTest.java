package martelc.bowling.domain.scoringstrategies;

import martelc.bowling.domain.factories.GameFactory;
import martelc.bowling.domain.factories.RandomBallsGameFactory;
import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.games.Game;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RandomBallsGameTenPinScoringStrategyTest {

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAfterLastBallOfLastFrame_returnsZeroCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = RandomBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();

        Integer roll = 5;
        Integer expectedCalculatedPoints = 0;
        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsLastBallOfLastFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = RandomBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);

        Integer roll = 5;
        Integer expectedCalculatedPoints = 0;
        if (lastFrame.isStrike() || lastFrame.isSpare()) {
            GameFactory.removeBonusBall(lastFrame);
            expectedCalculatedPoints = roll;
        }

        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsSecondBallOfLastFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = RandomBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        Frame secondLastFrame = frames.get(frames.size() - 2);

        Integer roll = 5;
        Integer expectedCalculatedPoints = roll;
        if (lastFrame.isStrike() || lastFrame.isSpare()) {
            GameFactory.removeBonusBall(lastFrame);
        }
        GameFactory.removeBall(lastFrame);

        if (secondLastFrame.isStrike()) {
            GameFactory.removeBonusBall(secondLastFrame);
            expectedCalculatedPoints = roll * 2;
        }

        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsFirstBallOfLastFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = RandomBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        Frame secondLastFrame = frames.get(frames.size() - 2);
        Frame thirdLastFrame = frames.get(frames.size() - 3);

        Integer roll = 5;
        Integer expectedCalculatedPoints = roll;
        if (lastFrame.isStrike() || lastFrame.isSpare()) {
            GameFactory.removeBonusBall(lastFrame);
        }
        GameFactory.removeBall(lastFrame);
        GameFactory.removeBall(lastFrame);

        if (secondLastFrame.isStrike()) {
            GameFactory.removeBonusBall(secondLastFrame);
            GameFactory.removeBonusBall(secondLastFrame);
            expectedCalculatedPoints = roll * 2;
            if (thirdLastFrame.isStrike()) {
                GameFactory.removeBonusBall(thirdLastFrame);
                expectedCalculatedPoints = roll * 3;
            }
        } else if (secondLastFrame.isSpare()) {
            GameFactory.removeBonusBall(secondLastFrame);
            expectedCalculatedPoints = roll * 2;
        }

        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsLastBallOfRegularFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = RandomBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        Frame secondLastFrame = frames.get(frames.size() - 2);
        Frame thirdLastFrame = frames.get(frames.size() - 3);

        Integer roll = 5;
        Integer expectedCalculatedPoints = roll;
        frames.remove(lastFrame);
        if (secondLastFrame.isStrike()) {
            GameFactory.removeBonusBall(secondLastFrame);
            GameFactory.removeBonusBall(secondLastFrame);
            expectedCalculatedPoints = roll * 2;
            if (thirdLastFrame.isStrike()) {
                GameFactory.removeBonusBall(thirdLastFrame);
                expectedCalculatedPoints = roll * 3;
            }
        } else if (secondLastFrame.isSpare()) {
            GameFactory.removeBonusBall(secondLastFrame);
            expectedCalculatedPoints = roll * 2;
        } else {
            GameFactory.removeBall(secondLastFrame);
        }

        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsFirstBallOfRegularFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = RandomBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        Frame secondLastFrame = frames.get(frames.size() - 2);
        Frame thirdLastFrame = frames.get(frames.size() - 3);
        Frame fourthLastFrame = frames.get(frames.size() - 4);

        frames.remove(lastFrame);
        frames.remove(secondLastFrame);

        Integer roll = 5;
        Integer expectedCalculatedPoints = roll;

        if (thirdLastFrame.isStrike()) {
            GameFactory.removeBonusBall(thirdLastFrame);
            GameFactory.removeBonusBall(thirdLastFrame);
            expectedCalculatedPoints = roll * 2;
            if (fourthLastFrame.isStrike()) {
                GameFactory.removeBonusBall(fourthLastFrame);
                expectedCalculatedPoints = roll * 3;
            }
        } else if (thirdLastFrame.isSpare()) {
            GameFactory.removeBonusBall(thirdLastFrame);
            expectedCalculatedPoints = roll * 2;
        }

        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }
}
