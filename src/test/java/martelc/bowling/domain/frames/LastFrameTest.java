package martelc.bowling.domain.frames;

import martelc.bowling.domain.factories.BallFactory;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class LastFrameTest {

    @Test
    public void addBall_withZeroBallsAndZeroBonusBalls_addsBallAsFirstBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        Boolean addedBall = lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertTrue(addedBall);
        Assert.assertEquals(1,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withOneBallWithMaximumPointsAndZeroBonusBalls_addsBallAsSecondBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBall = lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertTrue(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withTwoBallsWithMaximumPoints_addsBallAsBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBall = lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertTrue(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(1,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withTwoBallsWithMaximumPointsAndOneBonusBall_doesntAddBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBall = lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertFalse(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(1,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withIncompleteSpareAndZeroBonusBalls_addsBallAsSecondBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfSpareFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfSpareFrame = BallFactory.createSecondBallOfSpareFrame(scoringStrategy, firstBallOfSpareFrame);

        lastFrameUnderTest.addBall(firstBallOfSpareFrame);
        Boolean addedBall = lastFrameUnderTest.addBall(secondBallOfSpareFrame);

        Assert.assertTrue(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withCompleteSpareAndZeroBonusBalls_addsBallAsBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfSpareFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfSpareFrame = BallFactory.createSecondBallOfSpareFrame(scoringStrategy, firstBallOfSpareFrame);

        lastFrameUnderTest.addBall(firstBallOfSpareFrame);
        lastFrameUnderTest.addBall(secondBallOfSpareFrame);
        Boolean addedBall = lastFrameUnderTest.addBall(secondBallOfSpareFrame);

        Assert.assertTrue(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(1,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withIncompleteOpenAndZeroBonusBalls_addsBallAsSecondBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfOpenFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfOpenFrame = BallFactory.createSecondBallOfOpenFrame(scoringStrategy, firstBallOfOpenFrame);

        lastFrameUnderTest.addBall(firstBallOfOpenFrame);
        Boolean addedBall = lastFrameUnderTest.addBall(secondBallOfOpenFrame);

        Assert.assertTrue(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withCompleteOpenAndZeroBonusBalls_doesntAddBallAsBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfOpenFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfOpenFrame = BallFactory.createSecondBallOfOpenFrame(scoringStrategy, firstBallOfOpenFrame);

        lastFrameUnderTest.addBall(firstBallOfOpenFrame);
        lastFrameUnderTest.addBall(secondBallOfOpenFrame);
        Boolean addedBall = lastFrameUnderTest.addBall(secondBallOfOpenFrame);

        Assert.assertFalse(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withZeroBallsAndZeroBonusBalls_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertFalse(addedBonusBall);
        Assert.assertEquals(0,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withOneBallWithMaximumPointsAndZeroBonusBalls_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertFalse(addedBonusBall);
        Assert.assertEquals(1,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withTwoBallsWithMaximumPoints_addsBallAsBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertTrue(addedBonusBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(1,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withTwoBallsWithMaximumPointsAndOneBonusBall_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertFalse(addedBonusBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(1,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withIncompleteSpareAndZeroBonusBalls_doesntAddsBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfSpareFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfSpareFrame = BallFactory.createSecondBallOfSpareFrame(scoringStrategy, firstBallOfSpareFrame);

        lastFrameUnderTest.addBall(firstBallOfSpareFrame);
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(secondBallOfSpareFrame);

        Assert.assertFalse(addedBonusBall);
        Assert.assertEquals(1,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withCompleteSpareAndZeroBonusBalls_addsBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfSpareFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfSpareFrame = BallFactory.createSecondBallOfSpareFrame(scoringStrategy, firstBallOfSpareFrame);

        lastFrameUnderTest.addBall(firstBallOfSpareFrame);
        lastFrameUnderTest.addBall(secondBallOfSpareFrame);
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(secondBallOfSpareFrame);

        Assert.assertTrue(addedBonusBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(1,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withIncompleteOpenAndZeroBonusBalls_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfOpenFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfOpenFrame = BallFactory.createSecondBallOfOpenFrame(scoringStrategy, firstBallOfOpenFrame);

        lastFrameUnderTest.addBall(firstBallOfOpenFrame);
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(secondBallOfOpenFrame);

        Assert.assertFalse(addedBonusBall);
        Assert.assertEquals(1,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withCompleteOpenAndZeroBonusBalls_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new LastFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfOpenFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfOpenFrame = BallFactory.createSecondBallOfOpenFrame(scoringStrategy, firstBallOfOpenFrame);

        lastFrameUnderTest.addBall(firstBallOfOpenFrame);
        lastFrameUnderTest.addBall(secondBallOfOpenFrame);
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(secondBallOfOpenFrame);

        Assert.assertFalse(addedBonusBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }
}
