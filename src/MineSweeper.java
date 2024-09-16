import gui.GameComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**A class that creates and instantiates the gui for the game Minesweeper.
 *
 */
public class MineSweeper  {


    private static GameComponent game;
    /** Creates the surrounding gui to store the minesweeper game in.
     *
     */
    public static void buildGui() {
        JFrame container =  new JFrame();
        BorderLayout mainLayout = new BorderLayout();
        container.setLayout(mainLayout);

        //add buttons and text input at the top of the GUI.
        JComponent topBar = new JComponent() {
        };
        topBar.setLayout(new FlowLayout());
        topBar.add(new Label("Height"));
        TextField heightInput = new TextField("20");
        topBar.add(heightInput);
        topBar.add(new Label("Width"));
        TextField widthInput = new TextField("20");
        topBar.add(widthInput);
        topBar.add(new Label("Density"));
        TextField densityInput = new TextField("0.3");
        topBar.add(densityInput);

        //list of all text inputs for parameters for the game
        ArrayList<TextField> gameInputs = new ArrayList<>();
        gameInputs.add(heightInput);
        gameInputs.add(widthInput);
        gameInputs.add(densityInput);

        //button to generate a new game
        JButton generator = getGeneratorButton(gameInputs, container);
        topBar.add(generator);

        container.add(topBar, BorderLayout.NORTH);





        //set window data and make it visible
        container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        container.setSize(700, 500);
        container.setName("Minesweeper");
        container.setTitle("Minesweeper");
        container.setVisible(true);
    }

    private static JButton getGeneratorButton(ArrayList<TextField> inputs, JFrame container) {
        JButton generator = new JButton("Generate new Game");
        generator.addActionListener(e -> {
//                generateNewGame(heightInput.getText(), widthInput.getText());
            try {
                if (game != null) {
                    container.remove(game);
                }
                int height = Integer.parseInt(inputs.get(0).getText());
                int width = Integer.parseInt(inputs.get(1).getText());
                float density = Float.parseFloat(inputs.get(2).getText());
                game = new GameComponent(height, width, density);
                container.add(game, BorderLayout.CENTER);
                addSideBars(container);
                container.repaint();
                container.paintComponents(container.getGraphics());
            } catch (NumberFormatException ignored){

            }
        });
        return generator;
    }
    //add sideBars to the window to center the game
    private static void addSideBars(JFrame container) {
        JComponent sideBar = new JComponent() {
        };
        sideBar.setBackground(Color.WHITE);
        Dimension gameSize = game.getPreferredSize();
        sideBar.setSize((container.getWidth()-gameSize.width)/2, container.getHeight());
        container.add(sideBar, BorderLayout.WEST);
        container.add(sideBar, BorderLayout.EAST);
    }


    public static void main(String[] args) {
        Runnable createGui = MineSweeper::buildGui;
        SwingUtilities.invokeLater(createGui);
    }

}
