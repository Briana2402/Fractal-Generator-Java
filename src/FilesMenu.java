/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

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

    /**
     * Files Menu constructor. Creates a new File Menu.
     * @param mainScreen main screen that the files menu controls.
     */
    FilesMenu(JSplitPane mainScreen) {
        this.mainScreen = mainScreen;

        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem exit = new JMenuItem("Exit");

        //initialise all menu items with accelerators and mnemonics
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

    //check for when it is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        //act according to which button is pressed
        switch (e.getActionCommand()) {
            case EXIT_PROGRAM: System.exit(0); break; //exit program
            case SAVE_SETTINGS: //save current settings to file
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
                        if (this.mainScreen.getRightComponent() instanceof MandelbrotSet && !(this.mainScreen.getRightComponent() instanceof PhoenixSet)) { //if we are dealing with a mandelbrot set
                            MandelbrotSet mandelbrotSet = (MandelbrotSet) this.mainScreen.getRightComponent();
                            pw.println("Settings for Mandelbrot Set.");
                            pw.println(mandelbrotSet.getIterations()); //number of iterations
                            pw.println(mandelbrotSet.getMoveX()); //movement in X
                            pw.println(mandelbrotSet.getMoveY()); //movement in Y
                            pw.println(mandelbrotSet.getMinX()); //minimum X value read in the graph
                            pw.println(mandelbrotSet.getMaxX()); //maximum X value read in the graph
                            pw.println(mandelbrotSet.getMinY()); //minimum Y value read in the graph
                            pw.println(mandelbrotSet.getMaxY()); //maximum Y value read in the graph
                            pw.println(mandelbrotSet.getBackgroundColor(0)); //background color 1
                            pw.println(mandelbrotSet.getBackgroundColor(1)); //background color 2
                            pw.println(mandelbrotSet.getForegroundColor()); //foreground color
                        }
                        if (this.mainScreen.getRightComponent() instanceof PhoenixSet) { //if we are dealing with a julia set / phoenix set
                            PhoenixSet phoenixSet = (PhoenixSet) this.mainScreen.getRightComponent();
                            pw.println("Settings for Phoenix Set.");
                            pw.println(phoenixSet.getIterations()); //number of iterations
                            pw.println(phoenixSet.getMoveX()); //movement in X
                            pw.println(phoenixSet.getMoveY()); //movement in Y
                            pw.println(phoenixSet.getMinX()); //minimum X value read in the graph
                            pw.println(phoenixSet.getMaxX()); //maximum X value read in the graph
                            pw.println(phoenixSet.getMinY()); //minimum Y value read in the graph
                            pw.println(phoenixSet.getMaxY()); //maximum Y value read in the graph
                            pw.println(phoenixSet.getBackgroundColor(0)); //background color 1
                            pw.println(phoenixSet.getBackgroundColor(1)); //background color 2
                            pw.println(phoenixSet.getForegroundColor()); //foreground color
                            pw.println(phoenixSet.getCReal()); //constant real value
                            pw.println(phoenixSet.getCImg()); //constant imaginary value
                            pw.println(phoenixSet.getPReal()); //multiplying constant real value
                            pw.println(phoenixSet.getPImg()); //multiplying constant imaginary value
                        }
                        pw.close();                       
                    } catch (Exception exc) {
                        //something went wrong with creating the file
                        JOptionPane.showMessageDialog(null, "The file " + fileName + " could not be opened for writing.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        exc.printStackTrace();
                    }
                }
                break;
            case LOAD_SETTINGS: //load settings from file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("saved"));
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TXT Files", "txt");//only see txt file
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
                        switch (firstLine) { //see which settings file we are reading
                            case "Settings for Pythagoras Tree.": //create pythagoras tree with settings in file
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
                            case "Settings for Mandelbrot Set.": //create mandelbrot set with settings in file
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

                                case "Settings for Phoenix Set.": //read settings for phoenix set from file
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
                                    double p_real = sc.nextDouble();
                                    double p_img = sc.nextDouble();
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
                                    c_img,
                                    p_real,
                                    p_img,
                                    PhoenixSet.SCREEN_X,
                                    PhoenixSet.SCREEN_Y);
                                    mainScreen.setRightComponent(phoenixSet);
                                    PhoenixSetSidePanel sidePanel3 =
                                    new PhoenixSetSidePanel(mainScreen,
                                    _iterations,
                                    _bgColor1,
                                    _bgColor2,
                                    _fgColor,
                                    c_real,
                                    c_img,
                                    p_real,
                                    p_img);
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
                        //the settings file is incorrect
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