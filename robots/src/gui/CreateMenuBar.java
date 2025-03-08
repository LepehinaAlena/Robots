package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class CreateMenuBar {

    public static JMenuBar createMenuBar(MainApplicationFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu(frame));
        menuBar.add(createLookAndFeelMenu(frame));
        menuBar.add(createTestMenu(frame));
        return menuBar;
    }

    private static JMenu createFileMenu(MainApplicationFrame frame) {
        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setMnemonic(KeyEvent.VK_V);
        fileMenu.getAccessibleContext().setAccessibleDescription("Управление состоянием работы приложения");

        JMenuItem exitMenuItem = new JMenuItem("Выход", KeyEvent.VK_S);
        exitMenuItem.addActionListener((event) -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private static JMenu createLookAndFeelMenu(MainApplicationFrame frame) {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения приложения");

        JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private static JMenu createTestMenu(MainApplicationFrame frame) {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");

        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }
}
