package martelc.bowling.domain.scoringstrategies;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;

import java.util.List;

/*
    Once reached the end of the games, subsequent attempts to add balls to the games will
    result in zero additional points.
 */
public interface ScoringStrategy {
    Integer getMaximumNumberOfFrames();
    Integer getMaximumNumberOfBallsPerRegularFrame();
    Integer getMaximumNumberOfPointsPerBall();
    Integer addBallToFramesAndCalculatePoints(List<Frame> frames, Ball ball);
}
