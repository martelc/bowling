package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.games.Game;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RandomBallsGameFactoryTest {
    @Test
    public void createGame_withTenFramesRandomBallsAndCorrespondingBonusBallsPerFrame_returnsValidGame() {
        Integer maximumNumberOfFrames = 10;
        Integer maximumNumberOfBallsPerRegularFrame = 2;
        Integer maximumNumberOfPointsPerBall = 10;

        ScoringStrategy scoringStrategyMock = Mockito.mock(ScoringStrategy.class);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfFrames()).thenReturn(maximumNumberOfFrames);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfBallsPerRegularFrame()).thenReturn(maximumNumberOfBallsPerRegularFrame);
        Mockito.when(scoringStrategyMock.getMaximumNumberOfPointsPerBall()).thenReturn(maximumNumberOfPointsPerBall);

        Game game = RandomBallsGameFactory.createGame(scoringStrategyMock);

        Assert.assertEquals(maximumNumberOfFrames.intValue(), game.getFrames().size());
        for (Frame frame : game.getFrames()) {
            if (frame.isStrike()) {
                if (frame == game.getFrames().get(game.getFrames().size() - 1)) {
                    Assert.assertEquals(2, frame.getBalls().size());
                    Assert.assertEquals(1, frame.getBonusBalls().size());

                } else {
                    Assert.assertEquals(1, frame.getBalls().size());
                    Assert.assertEquals(2, frame.getBonusBalls().size());
                }
                Assert.assertEquals(maximumNumberOfPointsPerBall, frame.getBalls().get(0).getNumberOfPoints());
            } else if (frame.isSpare()) {
                Assert.assertEquals(2, frame.getBalls().size());
                Assert.assertEquals(1, frame.getBonusBalls().size());

                Integer ballSum = 0;
                for (Ball ball : frame.getBalls()) {
                    ballSum += ball.getNumberOfPoints();
                }
                Assert.assertEquals(maximumNumberOfPointsPerBall, ballSum);

            } else if (frame.isOpen()) {
                Assert.assertEquals(2, frame.getBalls().size());
                Assert.assertEquals(0, frame.getBonusBalls().size());

                Integer ballSum = 0;
                for (Ball ball : frame.getBalls()) {
                    ballSum += ball.getNumberOfPoints();
                }
                Assert.assertTrue(ballSum < maximumNumberOfPointsPerBall);
            }
        }
    }
}
