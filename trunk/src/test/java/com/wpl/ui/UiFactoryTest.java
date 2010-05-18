package com.wpl.ui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Assert;
import org.junit.Test;

import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiLocation;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.UiType;

@UiLayout(NullLayoutManager.class)
@UiSize(height = 480, width = 640)
public class UiFactoryTest extends JPanel {

    private static final long serialVersionUID = 1L;

    @UiText("Hello World!")
    @UiLocation(x = 0, y = 100)
    @UiSize(height = 100, width = 400)
    @UiFont(name = "Tahoma", style = Font.BOLD, size = 36)
    private JLabel mLabel;

    @UiText("My Button")
    @UiLocation(x = 100, y = 200)
    @UiSize(height = 20, width = 100)
    @UiType(CustomButton.class)
    private JButton mButton;

    @UiText("Default TEXT VALUE")
    @UiLocation(x = 200, y = 300)
    @UiSize(height = 20, width = 100)
    private JTextField mTextField;

    @SuppressWarnings("unused")
    @UiInit
    private void init() {

        Assert.assertNotNull(mLabel);
        Assert.assertNotNull(mButton);
        Assert.assertNotNull(mTextField);
    }

    @Test
    public void myPanelShow() throws InterruptedException {

        UiFactory factory = new UiFactory();

        JFrame frame = new JFrame();
        frame.getContentPane().add(factory.createPanel(UiFactoryTest.class));
        frame.setSize(1024, 768);
        frame.setLocation(0, 0);
        frame.setVisible(true);
        Thread.sleep(60000);
    }
}
