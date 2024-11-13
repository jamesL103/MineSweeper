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
    //layout and constraints
    private static final GridBagLayout LAYOUT  = new GridBagLayout();
    private static final GridBagConstraints GBC = new GridBagConstraints();
    private static final GridBagConstraints SCREEN_GBC = new GridBagConstraints();

    private static final GameGenerateObserver GEN_OBSERVER = new GameGenerateObserver();

    //colors
    public static final Color BACKGROUND_COLOR = new Color(27, 27, 37);
    public static final Color TEXT_COLOR = new Color(241, 241, 241);

    /** Creates the surrounding gui to store the minesweeper game in.
     *
     */
    private static void buildGui() {
        //set window data and make it visible
        CONTAINER.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CONTAINER.setSize(700, 500);
        CONTAINER.setName("Minesweeper");
        CONTAINER.setTitle("Minesweeper");
        CONTAINER.getContentPane().setBackground(BACKGROUND_COLOR);
        CONTAINER.setVisible(true);

        GBC.weightx = 1.0;
        GBC.weighty = 1.0;

        CONTAINER.setLayout(LAYOUT);
        setScreenConstraints();

        //add top bar for game generation
        addControlBar();

        currentScreen = DefaultScreen.INSTANCE;
        CONTAINER.add(DefaultScreen.INSTANCE, SCREEN_GBC);


    }

    //sets the GridBagConstraints for the displayed screens
    private static void setScreenConstraints() {
        SCREEN_GBC.weighty = 1.0;
        SCREEN_GBC.weightx = 1.0;
        SCREEN_GBC.gridx = 0;
        SCREEN_GBC.gridy = 1;
        SCREEN_GBC.gridwidth = GridBagConstraints.REMAINDER;
        SCREEN_GBC.gridheight = 1;
        SCREEN_GBC.fill = GridBagConstraints.BOTH;
    }


    //adds the game control bar to the top
    private static void addControlBar() {
        GBC.gridx = 0;
        GBC.gridy = 0;
        GBC.weighty = 0.1;
        GBC.gridheight = 1;
        GBC.gridwidth = GridBagConstraints.REMAINDER;
        GBC.fill = GridBagConstraints.HORIZONTAL;
        CONTAINER.add(new GameControlBar(GEN_OBSERVER), GBC);
    }

    //add sideBars to the window to center the game in the frame
    private static void addSideBars() {
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.WHITE);
        Dimension gameSize = gameScreen.getPreferredSize();
        sideBar.setSize((CONTAINER.getWidth()-gameSize.width)/2, CONTAINER.getHeight());



        CONTAINER.add(sideBar, BorderLayout.WEST);
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

                CONTAINER.add(gameScreen, SCREEN_GBC);
                //add sidebars to center game
//                addSideBars();
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
                    CONTAINER.add(DeathScreen.INSTANCE, SCREEN_GBC);
                    currentScreen = DeathScreen.INSTANCE;
                    break;
                case 2:
                    CONTAINER.add(WinScreen.INSTANCE, SCREEN_GBC);
                    currentScreen = WinScreen.INSTANCE;
                    break;
                default:
                    CONTAINER.add(DefaultScreen.INSTANCE, SCREEN_GBC);
                    currentScreen = DefaultScreen.INSTANCE;
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
