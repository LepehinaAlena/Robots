package gui;

import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Properties;
import javax.swing.JInternalFrame;

public class WindowStateManager {
    private static final String CONFIG_FILE = "window_state.properties";
    private static final String PATH = System.getProperty("user.home");


    public void saveWindowState(JInternalFrame frame, String windowId) {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(PATH + "\\" + CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
        }

        properties.setProperty(windowId + ".x", Integer.toString(frame.getX()));
        properties.setProperty(windowId + ".y", Integer.toString(frame.getY()));
        properties.setProperty(windowId + ".width", Integer.toString(frame.getWidth()));
        properties.setProperty(windowId + ".height", Integer.toString(frame.getHeight()));
        properties.setProperty(windowId + ".icon", Boolean.toString(frame.isIcon()));


        try (OutputStream output = new FileOutputStream(PATH + "\\" + CONFIG_FILE)) {
            properties.store(output, "Window State");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWindowState(JInternalFrame frame, String windowId) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(PATH + "\\" + CONFIG_FILE)) {
            properties.load(input);

            if (properties.containsKey(windowId + ".x")) {
                int x = Integer.parseInt(properties.getProperty(windowId + ".x"));
                int y = Integer.parseInt(properties.getProperty(windowId + ".y"));
                int width = Integer.parseInt(properties.getProperty(windowId + ".width"));
                int height = Integer.parseInt(properties.getProperty(windowId + ".height"));

                frame.setLocation(x, y);
                frame.setSize(width, height);

                boolean isIcon = Boolean.parseBoolean(properties.getProperty(windowId + ".icon", "false"));
                if (isIcon) {
                    try {
                        frame.setIcon(true);
                    } catch (PropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
                frame.setLocation(x, y);
                frame.setSize(width, height);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}