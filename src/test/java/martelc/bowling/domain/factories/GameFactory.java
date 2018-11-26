package martelc.bowling.domain.factories;

import martelc.bowling.domain.frames.Ball;
import martelc.bowling.domain.frames.Frame;

import java.util.List;

public abstract class GameFactory {
    public static void removeBall(Frame frame) {
        if (!frame.getBalls().isEmpty()) {
            frame.getBalls().remove(frame.getBalls().size() - 1);
        }
    }

    public static void removeBonusBall(Frame frame) {
        if (!frame.getBonusBalls().isEmpty()) {
            frame.getBonusBalls().remove(frame.getBonusBalls().size() - 1);
        }
    }

    public static Integer calculateScoreSuboptimal(List<Frame> frames) {
        Integer score = 0;
        for (Frame frame : frames) {
            for (Ball ball : frame.getBalls()) {
                score += ball.getNumberOfPoints();
            }
            for (Ball bonusBall : frame.getBonusBalls()) {
                score += bonusBall.getNumberOfPoints();
            }
        }
        return score;
    }

    protected static void addBonusBallToPreviousFrame(List<Frame> frames, Ball ball) {
        if (frames.size() > 1) {
            Frame previousFrame = frames.get(frames.size() - 2);
            previousFrame.addBonusBall(ball);
        }
    }

    protected static void addBonusBallToPreviousPreviousFrame(List<Frame> frames, Ball ball) {
        if (frames.size() > 2) {
            Frame previousFrame = frames.get(frames.size() - 3);
            previousFrame.addBonusBall(ball);
        }
    }
}
