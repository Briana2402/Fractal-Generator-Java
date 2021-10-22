import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
                String fileName = JOptionPane.showInputDialog("Enter a file name to save to.\n(no need for .txt extension)");
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
                        if (this.mainScreen.getRightComponent() instanceof JuliaSet) { //if we are dealing with a julia set / phoenix set
                            JuliaSet juliaSet = (JuliaSet) this.mainScreen.getRightComponent();
                            pw.println("Settings for Julia / Phoenix Set.");
                            pw.println(juliaSet.getIterations());
                            pw.println(juliaSet.getMoveX());
                            pw.println(juliaSet.getMoveY());
                            pw.println(juliaSet.getMinX());
                            pw.println(juliaSet.getMaxX());
                            pw.println(juliaSet.getMinY());
                            pw.println(juliaSet.getMaxY());
                            pw.println(juliaSet.getBackgroundColor(0));
                            pw.println(juliaSet.getBackgroundColor(1));
                            pw.println(juliaSet.getForegroundColor());
                            pw.println(juliaSet.getCReal());
                            pw.println(juliaSet.getCImg());
                        }
                        pw.close();
                    } catch (FileNotFoundException exc) {
                        JOptionPane.showMessageDialog(null, "The file " + fileName + " could not be opened for writing.", "ERROR", JOptionPane.ERROR_MESSAGE);
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
                                boolean complete = false;
                                if (sc.hasNextDouble()) {
                                    double angle = sc.nextDouble();
                                    if (sc.hasNextInt()) {
                                        int iterations = sc.nextInt();
                                        if (sc.hasNextInt()) {
                                            int moveX = sc.nextInt();
                                            if (sc.hasNextInt()) {
                                                int moveY = sc.nextInt();
                                                if (sc.hasNextDouble()) {
                                                    double squareSize = sc.nextDouble();
                                                    if (sc.hasNextInt()) {
                                                        int backgroundRGB = sc.nextInt();
                                                        if (sc.hasNextInt()) {
                                                            int squareRGB = sc.nextInt();
                                                            if (sc.hasNextInt()) {
                                                                int triangleRGB = sc.nextInt();
                                                                if (sc.hasNextInt()) {
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
                                                                    complete = true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!complete) {
                                    JOptionPane.showMessageDialog(null,
                                    "Corrupted settings file!",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case "Settings for Mandelbrot Set.":
                                boolean complete2 = false;
                                if (sc.hasNextInt()) {
                                    int iterations = sc.nextInt();
                                    if (sc.hasNextDouble()) {
                                        double moveX = sc.nextDouble();
                                        if (sc.hasNextDouble()) {
                                            double moveY = sc.nextDouble();
                                            if (sc.hasNextDouble()) {
                                                double minX = sc.nextDouble();
                                                if (sc.hasNextDouble()) {
                                                    double maxX = sc.nextDouble();
                                                    if (sc.hasNextDouble()) {
                                                        double minY = sc.nextDouble();
                                                        if (sc.hasNextDouble()) {
                                                            double maxY = sc.nextDouble();
                                                            if (sc.hasNextInt()) {
                                                                int bgColor1 = sc.nextInt();
                                                                if (sc.hasNextInt()) {
                                                                    int bgColor2 = sc.nextInt();
                                                                    if (sc.hasNextInt()) {
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
                                                                        fgColor);
                                                                        mainScreen.setRightComponent(mandelbrotSet);
                                                                        MandelbrotSetSidePanel sidePanel2 =
                                                                        new MandelbrotSetSidePanel(mainScreen,
                                                                        iterations,
                                                                        bgColor1,
                                                                        bgColor2,
                                                                        fgColor);
                                                                        mainScreen.setLeftComponent(sidePanel2);
                                                                        complete2 = true;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!complete2) {
                                    JOptionPane.showMessageDialog(null,
                                    "Corrupted settings file!",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                                }
                                break;

                                case "Settings for Julia / Phoenix Set.":
                                boolean complete3 = false;
                                if (sc.hasNextInt()) {
                                    int _iterations = sc.nextInt();
                                    if (sc.hasNextDouble()) {
                                        double _moveX = sc.nextDouble();
                                        if (sc.hasNextDouble()) {
                                            double _moveY = sc.nextDouble();
                                            if (sc.hasNextDouble()) {
                                                double _minX = sc.nextDouble();
                                                if (sc.hasNextDouble()) {
                                                    double _maxX = sc.nextDouble();
                                                    if (sc.hasNextDouble()) {
                                                        double _minY = sc.nextDouble();
                                                        if (sc.hasNextDouble()) {
                                                            double _maxY = sc.nextDouble();
                                                            if (sc.hasNextInt()) {
                                                                int _bgColor1 = sc.nextInt();
                                                                if (sc.hasNextInt()) {
                                                                    int _bgColor2 = sc.nextInt();
                                                                    if (sc.hasNextInt()) {
                                                                        int _fgColor = sc.nextInt();
                                                                        if (sc.hasNextDouble()) {
                                                                            double c_real = sc.nextDouble();
                                                                            if (sc.hasNextDouble()) {
                                                                                double c_img = sc.nextDouble();
                                                                                JuliaSet juliaSet = 
                                                                                new JuliaSet(_iterations,
                                                                                _moveX,
                                                                                _moveY, 
                                                                                _minX,
                                                                                _maxX,
                                                                                _minY,
                                                                                _maxY,
                                                                                _bgColor1,
                                                                                _bgColor2,
                                                                                _fgColor,
                                                                                c_real,
                                                                                c_img);
                                                                                mainScreen.setRightComponent(juliaSet);
                                                                                JuliaSetSidePanel sidePanel3 =
                                                                                new JuliaSetSidePanel(mainScreen,
                                                                                _iterations,
                                                                                _bgColor1,
                                                                                _bgColor2,
                                                                                _fgColor,
                                                                                c_real,
                                                                                c_img);
                                                                                mainScreen.setLeftComponent(sidePanel3);
                                                                                complete3 = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!complete3) {
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
                    }
                }
                break;
        }
    }
}