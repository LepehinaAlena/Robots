package gui;

import java.util.Map;

public interface StatefulWindow {
    String getWindowId();
    Map<String, String> getWindowState();
    void setWindowState(Map<String, String> state);
    boolean isWindowVisible();
    void setWindowVisible(boolean visible);
}