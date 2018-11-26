package martelc.bowling.domain.frames;

public class Ball {
    private Integer numberOfPoints = 0;

    public Ball(Integer numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public Ball(Ball ball) {
        this.numberOfPoints = ball.getNumberOfPoints();
    }

    public Integer getNumberOfPoints() {
       return numberOfPoints;
    }
}
