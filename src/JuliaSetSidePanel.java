import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

public class JuliaSetSidePanel extends SidePanel{
    JuliaSet fractalPanel;
    JTextField iterations;
    JSplitPane mainScreen;
    ColorPicker bg1ColorPicker;
    ColorPicker bg2ColorPicker;
    ColorPicker fgColorPicker;
    JTextField c_real;
    JTextField c_img;

    JuliaSetSidePanel(JSplitPane mainScreen, int iterationsNum, int bgColor1, int bgColor2, int fgColor, double c_real_val, double c_img_val) {
        super();

        this.mainScreen = mainScreen;
        updateFractalPanel();

        JButton pickBg1 = new JButton("Pick");
        JButton pickBg2 = new JButton("Pick");
        JButton pickFg = new JButton("Pick");
        JLabel labelIterationNum = new JLabel("Number of Iterations");
        JLabel labelCReal = new JLabel("C Real Value");
        JLabel labelCImg = new JLabel("C Imaginary Value");
        labelIterationNum.setForeground(Color.WHITE);
        labelCReal.setForeground(Color.WHITE);
        labelCImg.setForeground(Color.WHITE);

        iterations = new JTextField();
        iterations.setText(String.valueOf(iterationsNum));
        iterations.setMaximumSize(new Dimension(50, 20));

        c_real = new JTextField();
        c_real.setText(String.valueOf(c_real_val));
        c_real.setMaximumSize(new Dimension(50, 20));

        c_img = new JTextField();
        c_img.setText(String.valueOf(c_img_val));
        c_img.setMaximumSize(new Dimension(50, 20));

        pickBg1.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickBg2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickFg.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCReal.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));
        labelCReal.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));
        labelCImg.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));

        bg1ColorPicker = new ColorPicker(0, new Color(bgColor1), pickBg1, fractalPanel);
        bg2ColorPicker = new ColorPicker(1, new Color(bgColor2), pickBg2, fractalPanel);
        fgColorPicker = new ColorPicker(2, new Color(fgColor), pickFg, fractalPanel);

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
                        JuliaSet juliaSet = (JuliaSet) mainScreen.getRightComponent();
                        int bgColor1 = juliaSet.getBackgroundColor(0);
                        int bgColor2 = juliaSet.getBackgroundColor(1);
                        int fgColor = juliaSet.getForegroundColor();
                        double c_real = juliaSet.getCReal();
                        double c_img = juliaSet.getCImg();
                        mainScreen.setRightComponent(new JuliaSet(iterationsNumber, bgColor1, bgColor2, fgColor, c_real, c_img));
                        updateFractalPanel();
                        updateColorPickers();
                    }
                } catch (Exception exc) {
                    if (iterations.getText().length() > 0) {
                        JOptionPane.showMessageDialog(null,
                        "Please input a numerical value.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                iterations.setText(null);
                            }
                        });
                    }
                }
            }
        });

        c_real.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double newCReal = Double.parseDouble(c_real.getText());
                    JuliaSet juliaSet = (JuliaSet) mainScreen.getRightComponent();
                    int iterationsNumber = juliaSet.getIterations();
                    int bgColor1 = juliaSet.getBackgroundColor(0);
                    int bgColor2 = juliaSet.getBackgroundColor(1);
                    int fgColor = juliaSet.getForegroundColor();
                    double moveX = juliaSet.getMoveX();
                    double moveY = juliaSet.getMoveY();
                    double minX = juliaSet.getMinX();
                    double maxX = juliaSet.getMaxX();
                    double minY = juliaSet.getMinY();
                    double maxY = juliaSet.getMaxY();
                    double c_img_value = juliaSet.getCImg();
                    JuliaSet newJuliaSet = new JuliaSet(iterationsNumber, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, newCReal, c_img_value);
                    mainScreen.setRightComponent(newJuliaSet);
                    updateFractalPanel();
                    updateColorPickers();
                } catch (Exception exc) {
                    if (iterations.getText().length() > 0) {
                        JOptionPane.showMessageDialog(null,
                        "Please input a numerical value.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                c_real.setText(null);
                            }
                        });
                    }
                }
            }
        });

        c_img.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double newCImg = Double.parseDouble(c_img.getText());
                    JuliaSet juliaSet = (JuliaSet) mainScreen.getRightComponent();
                    int iterationsNumber = juliaSet.getIterations();
                    int bgColor1 = juliaSet.getBackgroundColor(0);
                    int bgColor2 = juliaSet.getBackgroundColor(1);
                    int fgColor = juliaSet.getForegroundColor();
                    double moveX = juliaSet.getMoveX();
                    double moveY = juliaSet.getMoveY();
                    double minX = juliaSet.getMinX();
                    double maxX = juliaSet.getMaxX();
                    double minY = juliaSet.getMinY();
                    double maxY = juliaSet.getMaxY();
                    double c_real_value = juliaSet.getCReal();
                    JuliaSet newJuliaSet = new JuliaSet(iterationsNumber, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, c_real_value, newCImg);
                    mainScreen.setRightComponent(newJuliaSet);
                    updateFractalPanel();
                    updateColorPickers();
                } catch (Exception exc) {
                    if (iterations.getText().length() > 0) {
                        JOptionPane.showMessageDialog(null,
                        "Please input a numerical value.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                c_real.setText(null);
                            }
                        });
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
        this.add(labelCReal);
        this.add(c_real);
        this.add(labelCImg);
        this.add(c_img);
    }

    private void updateFractalPanel() {
        this.fractalPanel = (JuliaSet) mainScreen.getRightComponent();
    }

    private void updateColorPickers() {
        JuliaSet juliaSet = (JuliaSet) mainScreen.getRightComponent();
        bg1ColorPicker.updateFractalPanel(juliaSet);
        bg2ColorPicker.updateFractalPanel(juliaSet);
        fgColorPicker.updateFractalPanel(juliaSet);
    }
}