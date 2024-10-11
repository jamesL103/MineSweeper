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

    //font
    private final Font DEFAULT_FONT = new Font("Ebrima", Font.PLAIN, 12);

    //colors
    private final Color GENERATE_BUTTON_COLOR = new Color(93, 172, 211);

    //create new game settings bar
    //observer will alert container application that game needs to be regenerated
    public GameControlBar(MineSweeper.GameGenerateObserver observer) {
        super();
        this.observer = observer;
        addTextInput();

        //button to generate game
        JButton generator = getGeneratorButton();
        add(generator);
    }

    //private default constructor
    private GameControlBar() {

    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(MineSweeper.BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**Creates the text fields and labels for input of game settings
     *
     */
    private void addTextInput() {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        Label temp = new Label("Height");
        temp.setFont(DEFAULT_FONT);
        add(temp);
        add(HEIGHT_INPUT);
        temp = new Label("Width");
        temp.setFont(DEFAULT_FONT);
        add(temp);
        add(WIDTH_INPUT);

        temp = new Label("Density");
        temp.setFont(DEFAULT_FONT);
        add(temp);
        add(DENSITY_INPUT);
    }

    /**Instantiates the button to generate the game.
     *
     * @return an instance of the generator button
     */
    private JButton getGeneratorButton() {
        JButton generator = new JButton("Generate new Game");
        generator.addActionListener(e -> {
            ArrayList<String> params = new ArrayList<>();
            params.add(HEIGHT_INPUT.getText());
            params.add(WIDTH_INPUT.getText());
            params.add(DENSITY_INPUT.getText());
            observer.notifyGenerate(params);
        });
        generator.setBackground(GENERATE_BUTTON_COLOR);
        generator.setForeground(new Color(53, 53, 53));
        return generator;
    }

}
