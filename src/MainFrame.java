/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    final static int WIDTH = 800;
    final static int HEIGHT = 600;

    //Defining colors
    public final static Color sidePanelBackground = new Color (28, 35, 65);
    public final static Color menuBackground = new Color (19, 22, 35);

    /**
     * Creates the main screen frame that we see.
     */
    MainFrame() {
        ImageIcon frameIcon = new ImageIcon("img/logo.png");
        PythagorasTree fractalPanel = new PythagorasTree(PythagorasTree.STARTING_BG_COLOR,
                                    PythagorasTree.STARTING_SQUARE_COLOR,
                                    PythagorasTree.STARTING_TRIANGLE_COLOR,
                                    PythagorasTree.STARTING_LINE_COLOR);
        PythagorasTreeSidePanel sidePanel = new PythagorasTreeSidePanel(fractalPanel,
                                    PythagorasTree.STARTING_ITERATIONS,
                                    PythagorasTree.STARTING_ANGLE,
                                    PythagorasTree.STARTING_BG_COLOR,
                                    PythagorasTree.STARTING_SQUARE_COLOR,
                                    PythagorasTree.STARTING_TRIANGLE_COLOR,
                                    PythagorasTree.STARTING_LINE_COLOR);
        JSplitPane mainScreen = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JMenuBar menuBar = new JMenuBar();
        JMenu files = new FilesMenu(mainScreen);
        JMenu tools = new ToolsMenu(mainScreen);
        JMenu fractals = new FractalsMenu(mainScreen);
        
        menuBar.add(files);
        menuBar.add(fractals);
        menuBar.add(tools);

        mainScreen.add(sidePanel);
        mainScreen.add(fractalPanel);
        this.add(mainScreen);
        this.setJMenuBar(menuBar);

        this.setTitle("Fractal Generator");
        this.setIconImage(frameIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
