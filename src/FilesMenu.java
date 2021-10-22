import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;

import java.awt.event.*;
import java.awt.Color;

public class FilesMenu extends JMenu implements ActionListener{
    //Define constants
    final static String EXIT_PROGRAM = "Exit Program";
    final static String SAVE_SETTINGS = "Save Settings";
    final static String LOAD_SETTINGS = "Load Settings";

    //Define variables used by the whole class
    JSplitPane mainScreen;

    FilesMenu(JSplitPane mainScreen) {
        this.mainScreen = mainScreen;

        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem exit = new JMenuItem("Exit");

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.setMnemonic(KeyEvent.VK_S);
        save.setActionCommand(SAVE_SETTINGS);

        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.setMnemonic(KeyEvent.VK_L);
        load.setActionCommand(LOAD_SETTINGS);

        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setActionCommand(EXIT_PROGRAM);

        save.addActionListener(this);
        load.addActionListener(this);
        exit.addActionListener(this);

        this.add(save);
        this.add(load);
        this.add(exit);

        this.setText("Files");
        this.setMnemonic(KeyEvent.VK_F);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        switch (e.getActionCommand()) {
            case EXIT_PROGRAM: System.exit(0); break;
            case SAVE_SETTINGS:
                String fileName = JOptionPane.showInputDialog("Enter a file name to save to.\n(no need for extension)");
                fileName += ".txt";
                if (!fileName.equals("null.txt")) {
                    File cfg = new File("saved" + File.separator + fileName);
                    try {
                        PrintWriter pw = new PrintWriter(cfg);
                        if (this.mainScreen.getRightComponent() instanceof PythagorasTree) { //if we are dealing with a pythagoras tree
                            PythagorasTree pythagorasTree = (PythagorasTree) this.mainScreen.getRightComponent();
                            pw.println("Settings for Pythagoras Tree.");
                            pw.println(pythagorasTree.getAngle()); //angle in radians
                            pw.println(pythagorasTree.getIterations()); //iterations
                            pw.println(pythagorasTree.getMoveX()); //movement in X
                            pw.println(pythagorasTree.getMoveY()); //movement in Y
                            pw.println(pythagorasTree.getSquareSize()); //size of square
                            pw.println(pythagorasTree.getBackgroundColor()); //rgb background color
                            pw.println(pythagorasTree.getSquareColor()); //rgb square color
                            pw.println(pythagorasTree.getTriangleColor()); //rgb triangle color
                            pw.println(pythagorasTree.getLineColor()); //rgb line color
                        }
                        if (this.mainScreen.getRightComponent() instanceof MandelbrotSet) { //if we are dealing with a mandelbrot set
                            MandelbrotSet mandelbrotSet = (MandelbrotSet) this.mainScreen.getRightComponent();
                            pw.println("Settings for Mandelbrot Set.");
                            pw.println(mandelbrotSet.getIterations());
                            pw.println(mandelbrotSet.getMoveX());
                            pw.println(mandelbrotSet.getMoveY());
                            pw.println(mandelbrotSet.getMinX());
                            pw.println(mandelbrotSet.getMaxX());
                            pw.println(mandelbrotSet.getMinY());
                            pw.println(mandelbrotSet.getMaxY());
                            pw.println(mandelbrotSet.getBackgroundColor(0));
                            pw.println(mandelbrotSet.getBackgroundColor(1));
                            pw.println(mandelbrotSet.getForegroundColor());
                        }
                        if (this.mainScreen.getRightComponent() instanceof PhoenixSet) { //if we are dealing with a julia set / phoenix set
                            PhoenixSet phoenixSet = (PhoenixSet) this.mainScreen.getRightComponent();
                            pw.println("Settings for Julia / Phoenix Set.");
                            pw.println(phoenixSet.getIterations());
                            pw.println(phoenixSet.getMoveX());
                            pw.println(phoenixSet.getMoveY());
                            pw.println(phoenixSet.getMinX());
                            pw.println(phoenixSet.getMaxX());
                            pw.println(phoenixSet.getMinY());
                            pw.println(phoenixSet.getMaxY());
                            pw.println(phoenixSet.getBackgroundColor(0));
                            pw.println(phoenixSet.getBackgroundColor(1));
                            pw.println(phoenixSet.getForegroundColor());
                            pw.println(phoenixSet.getCReal());
                            pw.println(phoenixSet.getCImg());
                        }
                        pw.close();                       
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(null, "The file " + fileName + " could not be opened for writing.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        exc.printStackTrace();
                    }
                }
                break;
            case LOAD_SETTINGS:
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("saved"));
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT Files", "txt");
                fileChooser.setFileFilter(txtFilter);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Scanner sc = new Scanner(selectedFile);
                        String firstLine = "nothing";
                        if (sc.hasNextLine()) {
                            firstLine = sc.nextLine();
                        }
                        switch (firstLine) {
                            case "Settings for Pythagoras Tree.":
                                try {
                                    double angle = sc.nextDouble();
                                    int iterations = sc.nextInt();
                                    int moveX = sc.nextInt();
                                    int moveY = sc.nextInt();
                                    double squareSize = sc.nextDouble();
                                    int backgroundRGB = sc.nextInt();
                                    int squareRGB = sc.nextInt();
                                    int triangleRGB = sc.nextInt();
                                    int lineRGB = sc.nextInt();
                                    PythagorasTree newTree = new PythagorasTree(angle,
                                    iterations,
                                    moveX,
                                    moveY,
                                    squareSize,
                                    new Color(backgroundRGB),
                                    new Color(squareRGB),
                                    new Color(triangleRGB),
                                    new Color(lineRGB));
                                    PythagorasTreeSidePanel newPanel =
                                    new PythagorasTreeSidePanel(newTree,
                                    iterations,
                                    Math.toDegrees(angle),
                                    new Color(backgroundRGB),
                                    new Color(squareRGB),
                                    new Color(triangleRGB),
                                    new Color(lineRGB));
                                    mainScreen.setRightComponent(newTree);
                                    mainScreen.setLeftComponent(newPanel);
                                } catch (InputMismatchException exception) {
                                    JOptionPane.showMessageDialog(null,
                                    "Corrupted settings file!",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Settings for Mandelbrot Set.":
                                try {
                                    int iterations = sc.nextInt();
                                    double moveX = sc.nextDouble();
                                    double moveY = sc.nextDouble();
                                    double minX = sc.nextDouble();
                                    double maxX = sc.nextDouble();
                                    double minY = sc.nextDouble();
                                    double maxY = sc.nextDouble();
                                    int bgColor1 = sc.nextInt();
                                    int bgColor2 = sc.nextInt();
                                    int fgColor = sc.nextInt();
                                    MandelbrotSet mandelbrotSet = 
                                    new MandelbrotSet(iterations,
                                    moveX,
                                    moveY, 
                                    minX,
                                    maxX,
                                    minY,
                                    maxY,
                                    bgColor1,
                                    bgColor2,
                                    fgColor,
                                    this.mainScreen);
                                    mainScreen.setRightComponent(mandelbrotSet);
                                    MandelbrotSetSidePanel sidePanel2 =
                                    new MandelbrotSetSidePanel(mainScreen,
                                    iterations,
                                    bgColor1,
                                    bgColor2,
                                    fgColor);
                                    mainScreen.setLeftComponent(sidePanel2);
                                } catch (InputMismatchException exception) {
                                    JOptionPane.showMessageDialog(null,
                                    "Corrupted settings file!",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                                    exception.printStackTrace();
                                }
                                break;

                                case "Settings for Julia / Phoenix Set.":
                                try {
                                    int _iterations = sc.nextInt();
                                    double _moveX = sc.nextDouble();
                                    double _moveY = sc.nextDouble();
                                    double _minX = sc.nextDouble();
                                    double _maxX = sc.nextDouble();
                                    double _minY = sc.nextDouble();
                                    double _maxY = sc.nextDouble();
                                    int _bgColor1 = sc.nextInt();
                                    int _bgColor2 = sc.nextInt();
                                    int _fgColor = sc.nextInt();
                                    double c_real = sc.nextDouble();
                                    double c_img = sc.nextDouble();
                                    PhoenixSet phoenixSet = 
                                    new PhoenixSet(_iterations,
                                    _moveX,
                                    _moveY, 
                                    _minX,
                                    _maxX,
                                    _minY,
                                    _maxY,
                                    _bgColor1,
                                    _bgColor2,
                                    _fgColor,
                                    this.mainScreen,
                                    c_real,
                                    c_img);
                                    mainScreen.setRightComponent(phoenixSet);
                                    PhoenixSetSidePanel sidePanel3 =
                                    new PhoenixSetSidePanel(mainScreen,
                                    _iterations,
                                    _bgColor1,
                                    _bgColor2,
                                    _fgColor,
                                    c_real,
                                    c_img);
                                    mainScreen.setLeftComponent(sidePanel3);
                                } catch (InputMismatchException exception) {
                                    JOptionPane.showMessageDialog(null,
                                    "Corrupted settings file!",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                                }
                                break;

                            default: 
                                System.out.println(firstLine);
                                JOptionPane.showMessageDialog(null,
                                "This is not a valid settings file!",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                                break;
                        }
                        sc.close();
                    } catch (FileNotFoundException exc) {
                        JOptionPane.showMessageDialog(null,
                        "The file " + selectedFile.getName() + " could not be found.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                        exc.printStackTrace();
                    }
                }
                break;
        }
    }
}