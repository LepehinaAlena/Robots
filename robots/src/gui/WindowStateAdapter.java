package gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class WindowStateAdapter implements StatefulWindow {
    private final JInternalFrame frame;
    private final String windowId;
    private final Map<String, String> defaultState;

    public WindowStateAdapter(JInternalFrame frame, String windowId,
                              Map<String, String> defaultState) {
        this.frame = frame;
        this.windowId = windowId;
        this.defaultState = defaultState;
    }

    @Override public String getWindowId() { return windowId; }

    @Override
    public Map<String, String> getWindowState() {
        Map<String, String> state = new HashMap<>();
        state.put("x", Integer.toString(frame.getX()));
        state.put("y", Integer.toString(frame.getY()));
        state.put("width", Integer.toString(frame.getWidth()));
        state.put("height", Integer.toString(frame.getHeight()));
        state.put("iconified", Boolean.toString(frame.isIcon()));
        state.put("visible", Boolean.toString(frame.isVisible()));
        return state;
    }

    @Override
    public void setWindowState(Map<String, String> state) {
        try {
            int x = Integer.parseInt(state.getOrDefault("x", defaultState.get("x")));
            int y = Integer.parseInt(state.getOrDefault("y", defaultState.get("y")));
            int width = Integer.parseInt(state.getOrDefault("width", defaultState.get("width")));
            int height = Integer.parseInt(state.getOrDefault("height", defaultState.get("height")));
            boolean iconified = Boolean.parseBoolean(state.getOrDefault("iconified", defaultState.get("iconified")));
            boolean visible = Boolean.parseBoolean(state.getOrDefault("visible", defaultState.get("visible")));

            frame.setBounds(x, y, width, height);
            frame.setIcon(iconified);
            frame.setVisible(visible);
        } catch (Exception e) {
            System.err.println("Error restoring window state: " + e.getMessage());
        }
    }

    @Override public boolean isWindowVisible() { return frame.isVisible(); }
}