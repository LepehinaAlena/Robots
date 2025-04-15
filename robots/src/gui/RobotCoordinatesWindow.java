package gui;

import model.RobotModel;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class RobotCoordinatesWindow extends JInternalFrame {
    private final JLabel coordinatesLabel;
    private final RobotModel robotModel;
    private final Timer updateTimer;

    public RobotCoordinatesWindow(RobotModel robotModel) {
        super("Координаты робота", true, true, true, true);
        this.robotModel = robotModel;

        coordinatesLabel = new JLabel();
        coordinatesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        updateCoordinates();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinatesLabel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().add(panel);
        setSize(250, 100);

        updateTimer = new Timer("Coordinates updater", true);
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateCoordinates();
            }
        }, 0, 50);
    }

    private void updateCoordinates() {
        SwingUtilities.invokeLater(() -> {
            coordinatesLabel.setText(String.format(
                    "<html>X: %.2f<br>Y: %.2f<br>Angle: %.2f°</html>",
                    robotModel.getRobotPositionX(),
                    robotModel.getRobotPositionY(),
                    Math.toDegrees(robotModel.getRobotDirection())));
        });
    }

    @Override
    public void dispose() {
        updateTimer.cancel();
        super.dispose();
    }
}