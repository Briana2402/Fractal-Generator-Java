/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

public class MandelbrotSetSidePanel extends SidePanel {
    //Defining variables accessed by the whole class
    MandelbrotSet fractalPanel;
    JTextField iterations;
    JSplitPane mainScreen;
    ColorPicker bg1ColorPicker;
    ColorPicker bg2ColorPicker;
    ColorPicker fgColorPicker;

    /**
     * Mandelbrot set side panel constructor. Creates new Mandelbrot Set side panel.
     * @param mainScreen main screen where the panel is
     * @param iterationsNum number of iterations
     * @param bgColor1 background color 1
     * @param bgColor2 background color 2
     * @param fgColor foreground color
     */
    MandelbrotSetSidePanel(JSplitPane mainScreen, int iterationsNum, int bgColor1, int bgColor2, int fgColor) {
        super();

        this.mainScreen = mainScreen;
        updateFractalPanel();

        JButton pickBg1 = new JButton("Pick");
        JButton pickBg2 = new JButton("Pick");
        JButton pickFg = new JButton("Pick");
        JLabel labelIterationNum = new JLabel("Number of Iterations");
        labelIterationNum.setForeground(Color.WHITE);

        iterations = new JTextField();
        iterations.setText(String.valueOf(iterationsNum));
        iterations.setMaximumSize(new Dimension(50, 20));
        
        pickBg1.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickBg2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickFg.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));

        bg1ColorPicker = new ColorPicker(0, new Color(bgColor1), pickBg1, fractalPanel);
        bg2ColorPicker = new ColorPicker(1, new Color(bgColor2), pickBg2, fractalPanel);
        fgColorPicker = new ColorPicker(2, new Color(fgColor), pickFg, fractalPanel);

        //listen for when the iterations are changed. Create a new Mandelbrot Set object with that
        //number of iterations and place it at the right of the main screen.
        iterations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer iterationsNumber = Integer.parseInt(iterations.getText());
                    if (iterationsNumber < 0) {
                        JOptionPane.showMessageDialog(null,
                        "Please enter a number of iterations larger than or equal to 0.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                    } else {
                        MandelbrotSet mandelbrotSet = (MandelbrotSet) mainScreen.getRightComponent();
                        int bgColor1 = mandelbrotSet.getBackgroundColor(0);
                        int bgColor2 = mandelbrotSet.getBackgroundColor(1);
                        int fgColor = mandelbrotSet.getForegroundColor();
                        mainScreen.setRightComponent(new MandelbrotSet(iterationsNumber, bgColor1, bgColor2, fgColor, mainScreen));
                        updateFractalPanel();
                        updateColorPickers();
                    }
                } catch (Exception exc) {
                    if (iterations.getText().length() > 0 && !iterations.getText().equals("0")) {
                        JOptionPane.showMessageDialog(null,
                        "Please input a numerical value.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                iterations.setText(null);
                            }
                        });
                    } if (iterations.getText().equals("0")) {
                        MandelbrotSet mandelbrotSet = (MandelbrotSet) mainScreen.getRightComponent();
                        int bgColor1 = mandelbrotSet.getBackgroundColor(0);
                        int bgColor2 = mandelbrotSet.getBackgroundColor(1);
                        int fgColor = mandelbrotSet.getForegroundColor();
                        mainScreen.setRightComponent(new MandelbrotSet(0, bgColor1, bgColor2, fgColor, mainScreen));
                        updateFractalPanel();
                        updateColorPickers();
                    }
                }
            }
        });

        this.add(bg1ColorPicker);
        this.add(pickBg1);
        this.add(bg2ColorPicker);
        this.add(pickBg2);
        this.add(fgColorPicker);
        this.add(pickFg);
        this.add(labelIterationNum);
        this.add(iterations);
    }

    private void updateFractalPanel() {
        this.fractalPanel = (MandelbrotSet) mainScreen.getRightComponent();
    }

    /**
     * Updates the color pickers so that they change the color of the fractal currently displayed
     */
    private void updateColorPickers() {
        MandelbrotSet mandelbrotSet = (MandelbrotSet) mainScreen.getRightComponent();
        bg1ColorPicker.updateFractalPanel(mandelbrotSet);
        bg2ColorPicker.updateFractalPanel(mandelbrotSet);
        fgColorPicker.updateFractalPanel(mandelbrotSet);
    }
}
