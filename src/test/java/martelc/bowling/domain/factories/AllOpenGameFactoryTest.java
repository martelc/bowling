package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.games.Game;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AllOpenGameFactoryTest {
    @Test
    public void createGame_withTenFramesTwoBallsAndZeroBonusBallsPerFrameLessThanTenPointsPerTwoBalls_returnsValidGame() {
        Integer maximumNumberOfFrames = 10;
        Integer maximumNumberOfBallsPerRegularFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;

        ScoringStrategy scoringStrategyMock = Mockito.mock(ScoringStrategy.class);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfFrames()).thenReturn(maximumNumberOfFrames);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfBallsPerRegularFrame()).thenReturn(maximumNumberOfBallsPerRegularFrame);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Game game = AllOpenGameFactory.createGame(scoringStrategyMock);

        Integer expectedNumberOfBallsPerFrame = 2;
        Integer expectedNumberOfBonusBallsPerFrame = 0;

        Assert.assertEquals(maximumNumberOfFrames.intValue(), game.getFrames().size());
        for (Frame frame : game.getFrames()) {
            Assert.assertEquals(expectedNumberOfBallsPerFrame.intValue(), frame.getBalls().size());
            Assert.assertEquals(expectedNumberOfBonusBallsPerFrame.intValue(), frame.getBonusBalls().size());
            Integer ballSum = 0;
            for (Ball ball : frame.getBalls()) {
                ballSum += ball.getNumberOfPoints();
            }
            Assert.assertTrue(ballSum < maximumNumberOfPointsPerBall);
        }
    }
}
