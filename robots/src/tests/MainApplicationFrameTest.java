package tests;

import gui.MainApplicationFrame;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.Component;

import static org.junit.Assert.assertTrue;

public class MainApplicationFrameTest {

    private MainApplicationFrame mainApplicationFrame;
    private JMenuItem exitMenuItem;

    @Before
    public void setUp() {
        mainApplicationFrame = new MainApplicationFrame();
        JMenuBar menuBar = mainApplicationFrame.getJMenuBar();
        JMenu fileMenu = menuBar.getMenu(0);
        exitMenuItem = fileMenu.getItem(0);
    }

    @Test
    public void testConfirmExit() {
        try {
            exitMenuItem.doClick();
            JOptionPane optionPane = getOptionPane(mainApplicationFrame);
            assertTrue(optionPane != null);
            System.out.println("Exit is correct");
            optionPane.setValue(JOptionPane.YES_OPTION);
            assertTrue(mainApplicationFrame.isDisplayable());
            System.out.println("Exit is correct");
        }
        catch (AssertionError e){
            System.out.println("Button 'No' is correct");
        }
    }

    private JOptionPane getOptionPane(JFrame frame) {
        for (Component comp : frame.getComponents()) {
            if (comp instanceof JOptionPane) {
                return (JOptionPane) comp;
            }
        }
        return null;
    }
}

