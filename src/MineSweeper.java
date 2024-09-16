import gui.GameComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
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
        container.setSize(600, 400);
        container.setName("Minesweeper");
        container.setVisible(true);
    }

    private static JButton getGeneratorButton(ArrayList<TextField> inputs, JFrame container) {
        JButton generator = new JButton("Generate new Game");
        generator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    container.repaint();
                    container.paintComponents(container.getGraphics());
//                    System.out.println("height input text: " + heightInput.getText());
                } catch (NumberFormatException ignored){

                }
            }
        });
        return generator;
    }


    public static void main(String[] args) {
        Runnable createGui = MineSweeper::buildGui;
        SwingUtilities.invokeLater(createGui);

    }

}
