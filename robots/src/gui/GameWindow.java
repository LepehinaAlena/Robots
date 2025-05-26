package gui;

import model.RobotModel;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements LocalizableWindow {
    private final GameVisualizer m_visualizer;

    public GameWindow(RobotModel robotModel) {
        super(LocalizationManager.getString("gameWindow.title"), true, true, true, true);
        m_visualizer = new GameVisualizer(robotModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void updateLocalization() {
        setTitle(LocalizationManager.getString("gameWindow.title"));
    }
}
