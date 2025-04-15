package gui;

import model.RobotModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import java.util.HashMap;
import java.util.Map;

import log.Logger;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final Map<JInternalFrame, WindowStateAdapter> windowAdapters = new HashMap<>();
    private final WindowStateManager windowStateManager = new WindowStateManager();
    private final RobotModel robotModel = new RobotModel();

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        createAndAddWindows();

        setJMenuBar(CreateMenuBar.createMenuBar(this));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                addConfirmExit();
            }
        });
    }

    private void createAndAddWindows() {
        createLogWindow();
        createGameWindow();
        createCoordinatesWindow();
    }

    private void createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        logWindow.setBounds(10, 10, 200, 500);
        logWindow.setVisible(true);


        Map<String, String> defaultState = new HashMap<>();
        defaultState.put("x", "10");
        defaultState.put("y", "10");
        defaultState.put("width", "500");
        defaultState.put("height", "800");
        defaultState.put("iconified", "false");
        defaultState.put("visible", "true");
        Logger.debug("Протокол работает");
        addWindow(logWindow, "logWindow", defaultState);
    }

    private void createGameWindow() {
        GameWindow gameWindow = new GameWindow(robotModel);

        gameWindow.setBounds(600, 150, 500, 400);
        gameWindow.setVisible(true);


        Map<String, String> defaultState = new HashMap<>();
        defaultState.put("x", "100");
        defaultState.put("y", "100");
        defaultState.put("width", "400");
        defaultState.put("height", "400");
        defaultState.put("iconified", "false");
        defaultState.put("visible", "true");
        addWindow(gameWindow, "gameWindow", defaultState);
    }

    private void createCoordinatesWindow() {
        RobotCoordinatesWindow coordinatesWindow = new RobotCoordinatesWindow(robotModel);

        coordinatesWindow.setBounds(300, 10, 300, 100);
        coordinatesWindow.setVisible(true);

        Map<String, String> defaultState = new HashMap<>();
        defaultState.put("x", "300");
        defaultState.put("y", "10");
        defaultState.put("width", "300");
        defaultState.put("height", "100");
        defaultState.put("iconified", "false");
        defaultState.put("visible", "true");
        addWindow(coordinatesWindow, "coordinatesWindow", defaultState);
    }

    private void addWindow(JInternalFrame frame, String windowId, Map<String, String> defaultState) {

        desktopPane.add(frame);
        WindowStateAdapter adapter = new WindowStateAdapter(frame, windowId, defaultState);
        windowAdapters.put(frame, adapter);
        windowStateManager.loadWindowState(adapter);
        frame.setVisible(true);
    }

    private void saveAllWindowStates() {
        windowAdapters.values().forEach(windowStateManager::saveWindowState);
    }

    private void addConfirmExit() {
        Object[] options = {"Да", "Нет"};
        int response = JOptionPane.showOptionDialog(
                null,
                "Вы подтверждаете выход?",
                "Подтверждение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (response == JOptionPane.YES_OPTION) {
            saveAllWindowStates();
            System.exit(0);
        }
    }

    public void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}