package gui.screens;

import javax.swing.*;
import java.awt.*;

public class WinScreen extends JComponent implements Screen {

    public static final WinScreen INSTANCE = new WinScreen();


    private WinScreen() {
        drawComponent();
    }

    public void drawComponent() {
        //set layout
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //add text
        Label deathMsg = new Label("you one");
        deathMsg.setFont(new Font("arial", Font.PLAIN, 100));
        add(deathMsg);
    }

}
