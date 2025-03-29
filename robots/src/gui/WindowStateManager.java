package gui;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class WindowStateManager {
    private static final String STATE_FILE = System.getProperty("user.home") + "/.window_states.properties";
    private final Properties states = new Properties();

    public WindowStateManager() {
        loadStatesFromFile();
    }

    public void saveWindowState(StatefulWindow window) {
        if (window.isWindowVisible()) {
            Map<String, String> state = window.getWindowState();
            state.forEach((key, value) ->
                    states.setProperty(window.getWindowId() + "." + key, value));
            saveStatesToFile();
        }
    }

    public void loadWindowState(StatefulWindow window) {
        Map<String, String> state = new HashMap<>();
        String prefix = window.getWindowId() + ".";

        states.stringPropertyNames().stream()
                .filter(key -> key.startsWith(prefix))
                .forEach(key ->
                        state.put(key.substring(prefix.length()), states.getProperty(key)));

        if (!state.isEmpty()) {
            window.setWindowState(state);
        }
    }

    private void loadStatesFromFile() {
        Path path = Paths.get(STATE_FILE);
        if (Files.exists(path)) {
            try (InputStream input = Files.newInputStream(path)) {
                states.load(input);
            } catch (IOException e) {
                System.err.println("Error loading window states: " + e.getMessage());
            }
        }
    }

    private void saveStatesToFile() {
        try (OutputStream output = Files.newOutputStream(Paths.get(STATE_FILE))) {
            states.store(output, "Window states");
        } catch (IOException e) {
            System.err.println("Error saving window states: " + e.getMessage());
        }
    }
}