package martelc.bowling.domain.frames;

import martelc.bowling.domain.factories.BallFactory;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class RegularFrameTest {

    @Test
    public void addBall_withZeroBalls_addsBallAsFirstBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        Boolean addedBall = lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertTrue(addedBall);
        Assert.assertEquals(1,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withOneBallWithMaximumPoints_doesntAddBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBall = lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertFalse(addedBall);
        Assert.assertEquals(1,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withIncompleteSpare_addsBallAsSecondBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

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
    public void addBall_withCompleteSpareAndZeroBonusBalls_doesntAddBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        ScoringStrategy scoringStrategy = Mockito.mock(ScoringStrategy.class);
        when(scoringStrategy.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Ball firstBallOfSpareFrame = BallFactory.createBallWithRandomNumberOfPointsLessThanMaximum(scoringStrategy);
        Ball secondBallOfSpareFrame = BallFactory.createSecondBallOfSpareFrame(scoringStrategy, firstBallOfSpareFrame);

        lastFrameUnderTest.addBall(firstBallOfSpareFrame);
        lastFrameUnderTest.addBall(secondBallOfSpareFrame);
        Boolean addedBall = lastFrameUnderTest.addBall(secondBallOfSpareFrame);

        Assert.assertFalse(addedBall);
        Assert.assertEquals(2,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBall_withIncompleteOpen_addsBallAsSecondBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

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
    public void addBall_withCompleteOpen_doesntAddBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

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
    public void addBonusBall_withZeroBalls_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertFalse(addedBonusBall);
        Assert.assertEquals(0,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(0,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withOneBallWithMaximumPoints_addsBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

        lastFrameUnderTest.addBall(new Ball(maximumNumberOfPointsPerBall));
        Boolean addedBonusBall = lastFrameUnderTest.addBonusBall(new Ball(maximumNumberOfPointsPerBall));

        Assert.assertTrue(addedBonusBall);
        Assert.assertEquals(1,lastFrameUnderTest.getBalls().size());
        Assert.assertEquals(1,lastFrameUnderTest.getBonusBalls().size());
    }

    @Test
    public void addBonusBall_withIncompleteSpare_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

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
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

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
    public void addBonusBall_withIncompleteOpen_doesntAddBonusBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

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
    public void addBonusBall_withCompleteOpen_doesntAddBall() {
        Integer maximumNumberOfBallsPerFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;
        Frame lastFrameUnderTest = new RegularFrame(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);

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
