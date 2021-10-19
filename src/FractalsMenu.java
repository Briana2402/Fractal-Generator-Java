import javax.swing.*;
import java.awt.event.*;

public class FractalsMenu extends JMenu implements ActionListener{
    //Define constants
    final static String PYTHAGORAS_TREE = "Render Pythagoras Tree";
    final static String MANDELBROT_SET = "Render Mandelbrot Set";
    final static String PHOENIX_SET = "Render Phoenix Set";

    //Define varaibles used by the whole class
    JSplitPane mainScreen;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case PYTHAGORAS_TREE:
                PythagorasTree pythagorasTree = new PythagorasTree();
                PythagorasTreeSidePanel sidePanel = new PythagorasTreeSidePanel(pythagorasTree);
                mainScreen.setLeftComponent(sidePanel);
                mainScreen.setRightComponent(pythagorasTree);
                break;
            case MANDELBROT_SET:
                MandelbrotSet mandelbrotSet = new MandelbrotSet();
                MandelbrotSetSidePanel sidePanel2 = new MandelbrotSetSidePanel(mandelbrotSet);
                mainScreen.setLeftComponent(sidePanel2);
                mainScreen.setRightComponent(mandelbrotSet);
                break;
        }
    }
}