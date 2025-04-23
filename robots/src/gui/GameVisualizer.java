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
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class GameVisualizer extends JPanel implements Observer {
    private final Timer m_timer = new Timer("events generator", true);
    private final RobotModel robotModel;
    private RobotModel.RobotState currentRobotState;
    private RobotModel.TargetState currentTargetState;

    public GameVisualizer(RobotModel robotModel) {
        this.robotModel = robotModel;
        robotModel.addObserver(this);
        robotModel.initModel();

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 10);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
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

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof RobotModel.RobotState) {
            currentRobotState = (RobotModel.RobotState) arg;
        } else if (arg instanceof RobotModel.TargetState) {
            currentTargetState = (RobotModel.TargetState) arg;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (currentRobotState == null || currentTargetState == null) {
            return;
        }

        drawRobot(g2d,
                round(currentRobotState.x),
                round(currentRobotState.y),
                currentRobotState.direction);
        drawTarget(g2d,
                currentTargetState.x,
                currentTargetState.y);
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
}