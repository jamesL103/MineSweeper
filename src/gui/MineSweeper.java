package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**A class that creates and instantiates the gui for the game Minesweeper.
 *
 */
public class MineSweeper  {

    //the game instance to display
    private static GameComponent game;

    //the JFrame that contains the entire gui
    private static final JFrame CONTAINER = new JFrame();

    private static final GameGenerateObserver GEN_OBSERVER = new GameGenerateObserver();

    private static int state;
    /** Creates the surrounding gui to store the minesweeper game in.
     *
     */
    private static void buildGui() {
        //set window data and make it visible
        CONTAINER.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CONTAINER.setSize(700, 500);
        CONTAINER.setName("Minesweeper");
        CONTAINER.setTitle("Minesweeper");
        CONTAINER.setVisible(true);

        BorderLayout mainLayout = new BorderLayout();
        CONTAINER.setLayout(mainLayout);

        //add top bar for game generation
        CONTAINER.add(new GameControlBar(GEN_OBSERVER), BorderLayout.NORTH);


    }

    //add sideBars to the window to center the game in the frame
    private static void addSideBars() {
        JComponent sideBar = new JComponent() {
        };
        sideBar.setBackground(Color.WHITE);
        Dimension gameSize = game.getPreferredSize();
        sideBar.setSize((CONTAINER.getWidth()-gameSize.width)/2, CONTAINER.getHeight());
        CONTAINER.add(sideBar, BorderLayout.WEST);
        CONTAINER.add(sideBar, BorderLayout.EAST);
    }

    //observer to notify the application when to generate a new minesweeper game
    public static class GameGenerateObserver {

        /**Notifies the GUI to replace the current gui.MineSweeper game instance with a newly generated one
         * according to the specified parameters.
         *
         * @param params the game generation parameters.
         */
        public void notifyGenerate(ArrayList<String> params) {
            try {
                if (game != null) {
                    CONTAINER.remove(game);
                }
                int height = Integer.parseInt(params.get(0));
                int width = Integer.parseInt(params.get(1));
                float density = Float.parseFloat(params.get(2));
                game = new GameComponent(height, width, density);
                CONTAINER.add(game, BorderLayout.CENTER);
                //add sidebars to center game
                addSideBars();
                CONTAINER.repaint();
                CONTAINER.paintComponents(CONTAINER.getGraphics());
            } catch (NumberFormatException ignored){

            }
        }

    }


    public static void main(String[] args) {
        Runnable createGui = MineSweeper::buildGui;
        SwingUtilities.invokeLater(createGui);
    }

}
