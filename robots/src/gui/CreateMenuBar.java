package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class CreateMenuBar {

    public static JMenuBar createMenuBar(MainApplicationFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu(frame));
        menuBar.add(createLookAndFeelMenu(frame));
        menuBar.add(createTestMenu(frame));
        menuBar.add(createLanguageMenu(frame));
        return menuBar;
    }

    private static JMenu createLanguageMenu(MainApplicationFrame frame) {
        JMenu languageMenu = new JMenu(LocalizationManager.getString("languageMenu.title"));

        JMenuItem russianItem = new JMenuItem(LocalizationManager.getString("language.russian"));
        russianItem.addActionListener(event -> {
            LocalizationManager.setLocale(new Locale("ru"));
            frame.updateLocalization();
        });

        JMenuItem englishItem = new JMenuItem(LocalizationManager.getString("language.english"));
        englishItem.addActionListener(event -> {
            LocalizationManager.setLocale(new Locale("en"));
            frame.updateLocalization();
        });
        JMenuItem translitItem = new JMenuItem(LocalizationManager.getString("language.translit"));
        translitItem.addActionListener(event -> {
            LocalizationManager.setLocale(new Locale("ru", "", "translit"));
            frame.updateLocalization();
        });

        languageMenu.add(russianItem);
        languageMenu.add(englishItem);
        languageMenu.add(translitItem);

        return languageMenu;
    }


    private static JMenu createFileMenu(MainApplicationFrame frame) {
        JMenu fileMenu = new JMenu(LocalizationManager.getString("fileMenu"));
        fileMenu.setMnemonic(KeyEvent.VK_V);
        fileMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getString("fileMenu.description"));

        JMenuItem exitMenuItem = new JMenuItem(LocalizationManager.getString("exitMenuItem"), KeyEvent.VK_S);
        exitMenuItem.addActionListener((event) -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private static JMenu createLookAndFeelMenu(MainApplicationFrame frame) {
        JMenu lookAndFeelMenu = new JMenu(LocalizationManager.getString("lookAndFeelMenu"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getString("lookAndFeelMenu.description"));

        JMenuItem systemLookAndFeel = new JMenuItem(LocalizationManager.getString("systemLookAndFeel"), KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem(LocalizationManager.getString("crossplatformLookAndFeel"), KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            frame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private static JMenu createTestMenu(MainApplicationFrame frame) {
        JMenu testMenu = new JMenu(LocalizationManager.getString("testMenu"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(LocalizationManager.getString("testMenu.description"));

        JMenuItem addLogMessageItem = new JMenuItem(LocalizationManager.getString("addLogMessageItem"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(LocalizationManager.getString("addLogMessageItem"));
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }
}
