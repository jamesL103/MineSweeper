package gui.screens;

import gui.MineSweeper;

import javax.swing.*;
import java.awt.*;

public class WinScreen extends JPanel implements Screen {

    public static final WinScreen INSTANCE = new WinScreen();


    private WinScreen() {
        super();
        drawComponent();
    }

    public void drawComponent() {
        //set layout
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setBackground(MineSweeper.BACKGROUND_COLOR);
        setForeground(MineSweeper.TEXT_COLOR);

        //add text
        Label deathMsg = new Label("you one");
        deathMsg.setFont(new Font("arial", Font.PLAIN, 100));
        add(deathMsg);
    }

}
