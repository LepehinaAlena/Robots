package gui;

import model.RobotModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class RobotCoordinatesWindow extends JInternalFrame implements Observer, LocalizableWindow {
    private final JLabel coordinatesLabel;
    private final RobotModel robotModel;
    private RobotModel.RobotState currentRobotState;
    private final Timer updateTimer;

    public RobotCoordinatesWindow(RobotModel robotModel) {
        super(LocalizationManager.getString("coordinatesWindow.title"), true, true, true, true);
        this.robotModel = robotModel;
        robotModel.addObserver(this);

        coordinatesLabel = new JLabel();
        coordinatesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        updateCoordinates();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinatesLabel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().add(panel);

        updateTimer = new Timer("Coordinates updater", true);
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateCoordinates();
            }
        }, 0, 50);
    }

    @Override
    public void update(Observable o, Object arg) {
        currentRobotState = (RobotModel.RobotState) arg;
    }

    private void updateCoordinates() {
        if (currentRobotState == null) return;

        SwingUtilities.invokeLater(() -> {
            coordinatesLabel.setText(String.format(
                    "<html>X: %.2f<br>Y: %.2f<br>Angle: %.2f°</html>",
                    currentRobotState.x,
                    currentRobotState.y,
                    Math.toDegrees(currentRobotState.direction)));
        });
    }

    @Override
    public void dispose() {
        robotModel.deleteObserver(this);
        super.dispose();
    }

    @Override
    public void updateLocalization() {
        setTitle(LocalizationManager.getString("coordinatesWindow.title"));
    }
}