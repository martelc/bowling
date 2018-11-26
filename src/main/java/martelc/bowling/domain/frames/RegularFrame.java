package martelc.bowling.domain.frames;

public class RegularFrame extends Frame {

    public RegularFrame(Integer maximumNumberOfBallsPerFrame, Integer maximumNumberOfPointsPerBall) {
        super(maximumNumberOfBallsPerFrame, maximumNumberOfPointsPerBall);
    }

    public RegularFrame(RegularFrame regularFrame) {
        super(regularFrame.getMaximumNumberOfBallsPerFrame(), regularFrame.getMaximumNumberOfPointsPerBall());
        for (Ball ball : regularFrame.getBalls()) {
            balls.add(new Ball(ball));
        }
        for (Ball bonusBall : regularFrame.getBonusBalls()) {
            bonusBalls.add(new Ball(bonusBall));
        }
    }

    @Override
    public Boolean addBall(Ball rolledBall) {
        Boolean addedBall = Boolean.TRUE;
        if (balls.size() < maximumNumberOfBallsPerFrame && !isStrike()) {
            balls.add(rolledBall);
        } else {
            addedBall = Boolean.FALSE;
        }
        return addedBall;
    }

    @Override
    public Boolean addBonusBall(Ball bonusBall) {
        Boolean bonusBallAdded = Boolean.TRUE;
        if ((isStrike() && bonusBalls.size() <= 1) || (isSpare() && bonusBalls.size() <= 0)) {
            bonusBalls.add(bonusBall);
        } else {
            bonusBallAdded = Boolean.FALSE;
        }
        return bonusBallAdded;
    }

    @Override
    public Boolean isComplete() {
        return isStrike() || isSpare() || isOpen();
    }

    @Override
    public Boolean isStrike() {
        return balls.size() == 1 &&
                balls.get(0).getNumberOfPoints().equals(maximumNumberOfPointsPerBall);
    }
}
