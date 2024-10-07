package gui.screens;

import javax.swing.*;
import java.awt.*;

public class DeathScreen extends JComponent implements Screen {

    public static final DeathScreen INSTANCE = new DeathScreen();

    private DeathScreen() {
        drawComponent();
    }

    //adds components to the death screen
    private void drawComponent() {

        //set layout
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //add text
        Label deathMsg = new Label("fuck");
        deathMsg.setFont(new Font("arial", Font.PLAIN, 100));
        add(deathMsg);



    }
}
