package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.frames.RegularFrame;
import martelc.bowling.domain.games.Game;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PerfectGameFactoryTest {

    @Test
    public void createGame_withTenFramesOneBallAndTwoBonusBallsPerFrameTenPointsPerBall_returnsValidGame() {
        Integer maximumNumberOfFrames = 10;
        Integer maximumNumberOfBallsPerRegularFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;

        ScoringStrategy scoringStrategyMock = Mockito.mock(ScoringStrategy.class);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfFrames()).thenReturn(maximumNumberOfFrames);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfBallsPerRegularFrame()).thenReturn(maximumNumberOfBallsPerRegularFrame);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Game game = PerfectGameFactory.createGame(scoringStrategyMock);

        Integer expectedScore = 300;
        Integer expectedNumberOfBallsPerFrame = 1;
        Integer expectedNumberOfBonusBallsPerFrame = 2;

        Assert.assertEquals(expectedScore, game.getScore());
        Assert.assertEquals(maximumNumberOfFrames.intValue(), game.getFrames().size());
        for (Frame frame : game.getFrames()) {
            if (frame instanceof RegularFrame) {
                Assert.assertEquals(expectedNumberOfBallsPerFrame.intValue(), frame.getBalls().size());
                Assert.assertEquals(expectedNumberOfBonusBallsPerFrame.intValue(), frame.getBonusBalls().size());
            } else {
                Assert.assertEquals(expectedNumberOfBallsPerFrame + 1, frame.getBalls().size());
                Assert.assertEquals(expectedNumberOfBonusBallsPerFrame - 1, frame.getBonusBalls().size());
            }
            for (Ball ball : frame.getBalls()) {
                Assert.assertEquals(maximumNumberOfPointsPerBall, ball.getNumberOfPoints());
            }
            for (Ball bonusBall : frame.getBonusBalls()) {
                Assert.assertEquals(maximumNumberOfPointsPerBall, bonusBall.getNumberOfPoints());
            }
        }
    }
}
