package gui;

import model.RobotModel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class GameVisualizer extends JPanel {
    private final Timer m_timer = initTimer();
    private final RobotModel robotModel;

    public GameVisualizer(RobotModel robotModel) {
        this.robotModel = robotModel;

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void setTargetPosition(Point p) {
        robotModel.setTargetPosition(p.x, p.y);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void onModelUpdateEvent() {
        double distance = RobotModel.distance(
                robotModel.getTargetPositionX(), robotModel.getTargetPositionY(),
                robotModel.getRobotPositionX(), robotModel.getRobotPositionY());

        if (distance < 0.5) {
            return;
        }
        double angleToTarget = RobotModel.angleTo(robotModel.m_robotPositionX, robotModel.m_robotPositionY, robotModel.m_targetPositionX, robotModel.m_targetPositionY);
        angleToTarget -= robotModel.m_robotDirection;
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

        robotModel.moveRobot(RobotModel.maxVelocity, angularVelocity, 10);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d,
                round(robotModel.getRobotPositionX()),
                round(robotModel.getRobotPositionY()),
                robotModel.getRobotDirection());
        drawTarget(g2d,
                robotModel.getTargetPositionX(),
                robotModel.getTargetPositionY());
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, x, y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, x + 10, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x + 10, y, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }
}