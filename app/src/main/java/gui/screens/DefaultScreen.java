package gui.screens;

import gui.MineSweeper;

import javax.swing.*;

/**Blank screen to display when nothing else is needed.
 *
 */
public class DefaultScreen extends JPanel implements Screen {

    public static final DefaultScreen INSTANCE = new DefaultScreen();

    private DefaultScreen() {
        super();
        setBackground(MineSweeper.BACKGROUND_COLOR);
    }

}
