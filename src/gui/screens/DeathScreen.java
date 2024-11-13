package gui.screens;

import gui.MineSweeper;

import javax.swing.*;
import java.awt.*;

public class DeathScreen extends JPanel implements Screen {

    public static final DeathScreen INSTANCE = new DeathScreen();

    private DeathScreen() {
        super();
        drawComponent();
    }

    //adds components to the death screen
    private void drawComponent() {

        //set layout
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setBackground(MineSweeper.BACKGROUND_COLOR);

        //add text
        Label deathMsg = new Label("You died");
        deathMsg.setForeground(MineSweeper.TEXT_COLOR);
        deathMsg.setFont(new Font("arial", Font.PLAIN, 100));
        add(deathMsg);



    }
}
