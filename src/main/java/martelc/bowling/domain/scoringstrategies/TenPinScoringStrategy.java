package martelc.bowling.domain.scoringstrategies;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;
import martelc.bowling.domain.frames.LastFrame;
import martelc.bowling.domain.frames.RegularFrame;

import java.util.List;

public class TenPinScoringStrategy implements ScoringStrategy {
    private static final Integer MAXIMUM_NUMBER_OF_FRAMES = 10;
    private static final Integer MAXIMUM_NUMBER_OF_BALLS_PER_REGULAR_FRAME = 2;
    private static final Integer MAXIMUM_NUMBER_OF_POINTS_PER_BALL = 10;

    @Override
    public Integer getMaximumNumberOfFrames() {
        return MAXIMUM_NUMBER_OF_FRAMES;
    }

    @Override
    public Integer getMaximumNumberOfBallsPerRegularFrame() {
        return MAXIMUM_NUMBER_OF_BALLS_PER_REGULAR_FRAME;
    }

    @Override
    public Integer getMaximumNumberOfPointsPerBall() {
        return MAXIMUM_NUMBER_OF_POINTS_PER_BALL;
    }

    @Override
    public Integer addBallToFramesAndCalculatePoints(List<Frame> frames, Ball ball) {
        Integer calculatedPoints = 0;

        if (!isEndOfGame(frames)) {
            Frame frame = getFrameToAddBall(frames);

            if (frame.addBall(ball)) {
                calculatedPoints += ball.getNumberOfPoints();
                calculatedPoints += addBallToPreviousFrame(frames, ball);
                calculatedPoints += addBallToPreviousPreviousFrame(frames, ball);
            }
        }

        return calculatedPoints;
    }

    private Boolean isEndOfGame(List<Frame> frames) {
        Boolean isEndOfCalculation = Boolean.FALSE;

        if (frames.size() == MAXIMUM_NUMBER_OF_FRAMES && frames.get(MAXIMUM_NUMBER_OF_FRAMES - 1).isComplete()) {
            isEndOfCalculation = Boolean.TRUE;
        }

        return isEndOfCalculation;
    }

    private Frame getFrameToAddBall(List<Frame> frames) {
        if (frames.isEmpty()) {
            return addFrame(frames);

        } else {
            Frame frame = frames.get(frames.size() - 1);
            if (frame.isComplete()) {
                frame = addFrame(frames);
            }
            return frame;
        }
    }

    private Frame addFrame(List<Frame> frames) {
        if (frames.size() < MAXIMUM_NUMBER_OF_FRAMES - 1) {
            frames.add(new RegularFrame(MAXIMUM_NUMBER_OF_BALLS_PER_REGULAR_FRAME, MAXIMUM_NUMBER_OF_POINTS_PER_BALL));

        } else if (frames.size() == MAXIMUM_NUMBER_OF_FRAMES - 1) {
            frames.add(new LastFrame(MAXIMUM_NUMBER_OF_BALLS_PER_REGULAR_FRAME, MAXIMUM_NUMBER_OF_POINTS_PER_BALL));
        }

        return frames.get(frames.size() - 1);
    }

    private Integer addBallToPreviousFrame(List<Frame> frames, Ball ball) {
        Integer calculatedPoints = 0;

        if (frames.size() >= 2){
            Frame previousFrame = frames.get((frames.size() - 2));
            if (previousFrame.addBonusBall(ball)) {
                calculatedPoints += ball.getNumberOfPoints();
            }
        }

        return calculatedPoints;
    }

    private Integer addBallToPreviousPreviousFrame(List<Frame> frames, Ball ball) {
        Integer calculatedPoints = 0;

        if (frames.size() >= 3) {
            Frame previousPreviousFrame = frames.get((frames.size() - 3));
            if (previousPreviousFrame.addBonusBall(ball)) {
                calculatedPoints += ball.getNumberOfPoints();
            }
        }

        return calculatedPoints;
    }
}
