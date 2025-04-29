package model;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class RobotModel extends Observable {

    public volatile double m_robotPositionX;
    public volatile double m_robotPositionY;
    public volatile double m_robotDirection;

    public volatile int m_targetPositionX;
    public volatile int m_targetPositionY;

    private final Timer modelUpdateTimer = new Timer("Model update timer", true);

    public static final double maxVelocity = 0.1;
    public static final double maxAngularVelocity = 0.001;

    public RobotModel() {
        m_robotPositionX = 100;
        m_robotPositionY = 100;
        m_robotDirection = 0;

        m_targetPositionX = 150;
        m_targetPositionY = 100;

        modelUpdateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    public void initModel() {
        setChanged();
        notifyObservers(new RobotState(m_robotPositionX, m_robotPositionY, m_robotDirection,
                m_targetPositionX, m_targetPositionY));
    }

    public void setTargetPosition(int x, int y) {
        m_targetPositionX = x;
        m_targetPositionY = y;
        setChanged();
        notifyObservers(new RobotState(m_robotPositionX, m_robotPositionY, m_robotDirection,
                m_targetPositionX, m_targetPositionY));
    }

    private void updateRobotPosition(double x, double y, double direction) {
        m_robotPositionX = x;
        m_robotPositionY = y;
        m_robotDirection = direction;
        setChanged();
        notifyObservers(new RobotState(m_robotPositionX, m_robotPositionY, m_robotDirection,
                m_targetPositionX, m_targetPositionY));
    }

    public void onModelUpdateEvent() {
        double distance = RobotModel.distance(
                m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);

        if (distance < 0.5) {
            return;
        }
        double angleToTarget = RobotModel.angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        angleToTarget -= m_robotDirection;
        angleToTarget = angleToTarget * 180 / Math.PI;
        if (Math.abs(angleToTarget) > 180) {
            angleToTarget -= 360;
        }

        double angularVelocity = 0;
        if (angleToTarget > 0) {
            angularVelocity = RobotModel.maxAngularVelocity;
        }
        if (angleToTarget < 0) {
            angularVelocity = -RobotModel.maxAngularVelocity;
        }
        if (Math.abs(angleToTarget) > 90) {
            angularVelocity = RobotModel.maxAngularVelocity;
        }

        moveRobot(RobotModel.maxVelocity, angularVelocity, 10);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX)) {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }

        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }

        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        updateRobotPosition(newX, newY, newDirection);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI) angle -= 2 * Math.PI;
        return angle;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public static class RobotState {
        public final double x, y, direction, tx, ty;

        public RobotState(double x, double y, double direction, double tx, double ty) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.tx = tx;
            this.ty = ty;
        }
    }
}