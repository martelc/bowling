package martelc.bowling.domain.frames;

import java.util.ArrayList;
import java.util.List;

public abstract class Frame {
    protected final Integer maximumNumberOfBallsPerFrame;
    protected final Integer maximumNumberOfPointsPerBall;

    protected List<Ball> balls = new ArrayList<>();
    protected List<Ball> bonusBalls = new ArrayList<>();

    protected Frame() {
        this.maximumNumberOfBallsPerFrame = 0;
        this.maximumNumberOfPointsPerBall = 0;
    }

    protected Frame(Integer maximumNumberOfBallsPerFrame, Integer maximumNumberOfPointsPerBall) {
        this.maximumNumberOfBallsPerFrame = maximumNumberOfBallsPerFrame;
        this.maximumNumberOfPointsPerBall = maximumNumberOfPointsPerBall;
    }

    public abstract Boolean addBall(Ball ball);
    public abstract Boolean addBonusBall(Ball ball);
    public abstract Boolean isComplete();
    public abstract Boolean isStrike();

    public Integer getMaximumNumberOfBallsPerFrame() {
        return maximumNumberOfBallsPerFrame;
    }

    public Integer getMaximumNumberOfPointsPerBall() {
        return maximumNumberOfPointsPerBall;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<Ball> getBonusBalls() {
        return bonusBalls;
    }

    public Boolean isSpare() {
        return balls.size() == 2 &&
                balls.get(0).getNumberOfPoints() + balls.get(1).getNumberOfPoints() == maximumNumberOfPointsPerBall;
    }

    public Boolean isOpen() {
        return balls.size() == 2 &&
                balls.get(0).getNumberOfPoints() + balls.get(1).getNumberOfPoints() < maximumNumberOfPointsPerBall;
    }
}
