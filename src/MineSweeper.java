import gui.GameComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**A class that creates and instantiates the gui for the game Minesweeper.
 *
 */
public class MineSweeper  {



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
        TextField heightInput = new TextField();
        topBar.add(heightInput);
        topBar.add(new Label("Width"));
        TextField widthInput = new TextField();
        topBar.add(widthInput);
        topBar.add(new Label("Density"));
        topBar.add(new TextField());
        JButton generator = getGeneratorButton(heightInput, widthInput, container);
        topBar.add(generator);

        container.add(topBar, BorderLayout.NORTH);

        //set window data and make it visible
        container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        container.setSize(600, 400);
        container.setName("Minesweeper");
        container.setVisible(true);
    }

    private static JButton getGeneratorButton(TextField heightInput, TextField widthInput, JFrame container) {
        JButton generator = new JButton("Generate new Game");
        generator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                generateNewGame(heightInput.getText(), widthInput.getText());
                try {
                    int height = Integer.parseInt(heightInput.getText());
                    int width = Integer.parseInt(widthInput.getText());
                    container.add(new GameComponent(height, width, 0.5), BorderLayout.CENTER);
                    container.repaint();
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
