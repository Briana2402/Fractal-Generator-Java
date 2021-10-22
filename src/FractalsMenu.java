/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;

import java.awt.event.*;

public class FractalsMenu extends JMenu implements ActionListener{
    //Define constants
    final static String PYTHAGORAS_TREE = "Render Pythagoras Tree";
    final static String MANDELBROT_SET = "Render Mandelbrot Set";
    final static String PHOENIX_SET = "Render Phoenix Set";

    //Define varaibles used by the whole class
    JSplitPane mainScreen;

    /**
     * Fractals menu constructor. Creates new fractals menu.
     * @param mainScreen the main screen where the fractal is placed
     */
    FractalsMenu(JSplitPane mainScreen) {
        JMenuItem pythagorasTree = new JMenuItem("Pythagoras Tree");
        JMenuItem mandelbrotSet = new JMenuItem("Mandelbrot Set");
        JMenuItem phoenixSet = new JMenuItem("Phoenix Set");

        this.mainScreen = mainScreen;

        pythagorasTree.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        pythagorasTree.setMnemonic(KeyEvent.VK_T);
        pythagorasTree.setActionCommand(PYTHAGORAS_TREE);
        mandelbrotSet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        mandelbrotSet.setMnemonic(KeyEvent.VK_M);
        mandelbrotSet.setActionCommand(MANDELBROT_SET);
        phoenixSet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        phoenixSet.setMnemonic(KeyEvent.VK_P);
        phoenixSet.setActionCommand(PHOENIX_SET);

        pythagorasTree.addActionListener(this);
        mandelbrotSet.addActionListener(this);
        phoenixSet.addActionListener(this);

        this.add(pythagorasTree);
        this.add(mandelbrotSet);
        this.add(phoenixSet);

        this.setText("Fractals");
        this.setMnemonic(KeyEvent.VK_R);
    }

    //create fractals accordingly
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case PYTHAGORAS_TREE:
                PythagorasTree pythagorasTree = new PythagorasTree(PythagorasTree.STARTING_BG_COLOR,
                                                PythagorasTree.STARTING_SQUARE_COLOR,
                                                PythagorasTree.STARTING_TRIANGLE_COLOR,
                                                PythagorasTree.STARTING_LINE_COLOR);
                PythagorasTreeSidePanel sidePanel = new PythagorasTreeSidePanel(pythagorasTree,
                                                PythagorasTree.STARTING_ITERATIONS,
                                                PythagorasTree.STARTING_ANGLE,
                                                PythagorasTree.STARTING_BG_COLOR,
                                                PythagorasTree.STARTING_SQUARE_COLOR,
                                                PythagorasTree.STARTING_TRIANGLE_COLOR,
                                                PythagorasTree.STARTING_LINE_COLOR);
                mainScreen.setLeftComponent(sidePanel);
                mainScreen.setRightComponent(pythagorasTree);
                break;
            case MANDELBROT_SET:
                MandelbrotSet mandelbrotSet = new MandelbrotSet(MandelbrotSet.STARTING_ITERATIONS);
                mainScreen.setRightComponent(mandelbrotSet);
                MandelbrotSetSidePanel sidePanel2 = new MandelbrotSetSidePanel(this.mainScreen,
                                                MandelbrotSet.STARTING_ITERATIONS,
                                                MandelbrotSet.STARTING_BG_COLOR,
                                                MandelbrotSet.STARTING_BG2_COLOR,
                                                MandelbrotSet.STARTING_FG_COLOR);
                mainScreen.setLeftComponent(sidePanel2);
                break;
            case PHOENIX_SET:
                PhoenixSet phoenixSet = new PhoenixSet(PhoenixSet.STARTING_ITERATIONS);
                mainScreen.setRightComponent(phoenixSet);
                PhoenixSetSidePanel sidePanel3 = new PhoenixSetSidePanel(this.mainScreen,
                                                PhoenixSet.STARTING_ITERATIONS,
                                                PhoenixSet.STARTING_BG_COLOR,
                                                PhoenixSet.STARTING_BG2_COLOR,
                                                PhoenixSet.STARTING_FG_COLOR,
                                                PhoenixSet.STARTING_C_REAL,
                                                PhoenixSet.STARTING_C_IMG,
                                                PhoenixSet.STARTING_P_REAL,
                                                PhoenixSet.STARTING_P_IMG);
                mainScreen.setLeftComponent(sidePanel3);
                break;
        }
    }
}