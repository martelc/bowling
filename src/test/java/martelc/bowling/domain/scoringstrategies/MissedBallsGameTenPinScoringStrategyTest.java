package martelc.bowling.domain.scoringstrategies;

import martelc.bowling.domain.factories.GameFactory;
import martelc.bowling.domain.factories.MissedBallsGameFactory;
import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.games.Game;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MissedBallsGameTenPinScoringStrategyTest {

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAfterLastBallOfLastFrame_returnsZeroCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = MissedBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();

        Integer roll = 5;
        Integer expectedCalculatedPoints = 0;
        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsLastBallOfLastFrame_returnsZeroCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = MissedBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);
        List<Frame> frames = game.getFrames();

        Integer roll = 5;
        Integer expectedCalculatedPoints = 0;
        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(expectedCalculatedPoints, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsSecondBallOfLastFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = MissedBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        GameFactory.removeBall(lastFrame);

        Integer roll = 5;
        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(roll, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsFirstBallOfLastFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = MissedBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        GameFactory.removeBall(lastFrame);
        GameFactory.removeBall(lastFrame);

        Integer roll = 5;
        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(roll, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsLastBallOfRegularFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = MissedBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        Frame secondLastFrame = frames.get(frames.size() - 2);

        frames.remove(lastFrame);
        GameFactory.removeBall(secondLastFrame);

        Integer roll = 5;
        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(roll, calculatedPoints);
    }

    @Test
    public void addBallToFramesAndCalculatePoints_withRollAddedAsFirstBallOfRegularFrame_returnsCalculatedPoints() {
        ScoringStrategy tenPinScoringStrategyUnderTest = new TenPinScoringStrategy();
        Game game = MissedBallsGameFactory.createGame(tenPinScoringStrategyUnderTest);

        List<Frame> frames = game.getFrames();
        Frame lastFrame = frames.get(frames.size() - 1);
        Frame secondLastFrame = frames.get(frames.size() - 2);

        frames.remove(lastFrame);
        GameFactory.removeBall(secondLastFrame);
        GameFactory.removeBall(secondLastFrame);

        Integer roll = 5;
        Integer calculatedPoints = tenPinScoringStrategyUnderTest.addBallToFramesAndCalculatePoints(
                frames, new Ball(roll));

        Assert.assertEquals(roll, calculatedPoints);
    }
}
