package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GameControlBar extends JComponent {

    //observer to alert container GUI
    private MineSweeper.GameGenerateObserver observer;

    //text input for game height
    private final TextField HEIGHT_INPUT = new TextField("20");
    //input for game width
    private final TextField WIDTH_INPUT = new TextField("20");
    //input for mine density
    private final TextField DENSITY_INPUT = new TextField("0.3");

    //create new game settings bar
    //observer will alert container application that game needs to be regenerated
    public GameControlBar(MineSweeper.GameGenerateObserver observer) {
        this.observer = observer;
        addTextInput();

        //button to generate game
        JButton generator = getGeneratorButton();
        add(generator);
    }

    //private default constructor
    private GameControlBar() {

    }

    /**Creates the text fields and labels for input of game settings
     *
     */
    private void addTextInput() {
        setLayout(new FlowLayout());
        add(new Label("Height"));
        add(HEIGHT_INPUT);
        add(new Label("Width"));
        add(WIDTH_INPUT);
        add(new Label("Density"));
        add(DENSITY_INPUT);
    }

    /**Instantiates the button to generate the game.
     *
     * @return an instance of the generator button
     */
    private JButton getGeneratorButton() {
        JButton generator = new JButton("Generate new Game");
        generator.addActionListener(_ -> {
            ArrayList<String> params = new ArrayList<>();
            params.add(HEIGHT_INPUT.getText());
            params.add(WIDTH_INPUT.getText());
            params.add(DENSITY_INPUT.getText());
            observer.notifyGenerate(params);
        });
        return generator;
    }

}
