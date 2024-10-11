package gui;

import gui.screens.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**A class that creates and instantiates the gui for the game Minesweeper.
 *
 */
public class MineSweeper  {

    //the game instance to display
    private static GameScreen gameScreen;

    private static Screen currentScreen;

    //the JFrame that contains the entire gui
    private static final JFrame CONTAINER = new JFrame();

    private static final GameGenerateObserver GEN_OBSERVER = new GameGenerateObserver();

    //colors
    public static final Color BACKGROUND_COLOR = new Color(27, 27, 37);

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
        Dimension gameSize = gameScreen.getPreferredSize();
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
                if (currentScreen != null) {
                    CONTAINER.remove((JComponent)currentScreen);
                }
                int height = Integer.parseInt(params.get(0));
                int width = Integer.parseInt(params.get(1));
                float density = Float.parseFloat(params.get(2));
                gameScreen = new GameScreen(height, width, density);
                CONTAINER.add(gameScreen, BorderLayout.CENTER);
                //add sidebars to center game
                addSideBars();
                currentScreen = gameScreen;
                CONTAINER.repaint();
                CONTAINER.paintComponents(CONTAINER.getGraphics());
            } catch (NumberFormatException ignored){

            }
        }

    }

    /**observer to notify gui when game state has changed
    game/game over/win
     **/
    public static class GameStateObserver {

        /**Notifies the GUI that the game state has changed.
         * Results in the GUI changing the screen shown
         * State 0 is running game normally, 1 : game over, 2 : win
         * @param state the new state of the game
         */
        public void updateState(int state) {
            CONTAINER.remove((JComponent)currentScreen);
            switch (state) {
                case 1:
                    CONTAINER.add(DeathScreen.INSTANCE, BorderLayout.CENTER);
                    currentScreen = DeathScreen.INSTANCE;
                    break;
                case 2:
                    CONTAINER.add(WinScreen.INSTANCE, BorderLayout.CENTER);
                    currentScreen = WinScreen.INSTANCE;
                    break;
                default:
                    break;
            }
            CONTAINER.repaint();
            CONTAINER.paintComponents(CONTAINER.getGraphics());
        }

    }


    public static void main(String[] args) {
        Runnable createGui = MineSweeper::buildGui;
        SwingUtilities.invokeLater(createGui);
    }

}
