package martelc.bowling.domain.frames;

public class LastFrame extends Frame {

    public LastFrame(Integer maximumNumberOfBallsPerFrame, Integer maximumNumberOfPointsPerBall) {
        super(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);
    }

    public LastFrame(LastFrame lastFrame) {
        super(lastFrame.getMaximumNumberOfBallsPerFrame(), lastFrame.getMaximumNumberOfPointsPerBall());
        for (Ball ball : lastFrame.getBalls()) {
            balls.add(new Ball(ball));
        }
        for (Ball bonusBall : lastFrame.getBonusBalls()) {
            bonusBalls.add(new Ball(bonusBall));
        }
    }

    @Override
    public Boolean addBall(Ball rolledBall) {
        Boolean addedBall = Boolean.TRUE;
        if (balls.size() < maximumNumberOfBallsPerFrame) {
            balls.add(rolledBall);
        } else if (balls.size() == maximumNumberOfBallsPerFrame && !isOpen()) {
            addedBall = addBonusBall(rolledBall);
        } else {
            addedBall = Boolean.FALSE;
        }
        return addedBall;
    }

    @Override
    public Boolean addBonusBall(Ball bonusBall) {
        Boolean addedBonusBall = Boolean.TRUE;
        if ((isStrike() || isSpare()) && bonusBalls.isEmpty() && balls.size() == maximumNumberOfBallsPerFrame) {
            bonusBalls.add(bonusBall);
        } else {
            addedBonusBall = Boolean.FALSE;
        }
        return addedBonusBall;
    }

    @Override
    public Boolean isComplete() {
        return isOpen() || !bonusBalls.isEmpty();
    }

    @Override
    public Boolean isStrike() {
        return (balls.size() == 1 || balls.size() == maximumNumberOfBallsPerFrame) &&
                balls.get(0).getNumberOfPoints().equals(maximumNumberOfPointsPerBall);
    }
}
