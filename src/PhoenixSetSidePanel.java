/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

public class PhoenixSetSidePanel extends SidePanel{
    PhoenixSet fractalPanel;
    JTextField iterations;
    JSplitPane mainScreen;
    ColorPicker bg1ColorPicker;
    ColorPicker bg2ColorPicker;
    ColorPicker fgColorPicker;
    JTextField c_real;
    JTextField c_img;
    JTextField p_real;
    JTextField p_img;

    /**
     * Phoenix Set side panel constructor. Creates a new Phoenix Set side panel.
     * @param mainScreen the main screen
     * @param iterationsNum the number of iterations
     * @param bgColor1 background color 1
     * @param bgColor2 background color 2
     * @param fgColor foreground color
     * @param c_real_val constant real value
     * @param c_img_val constant imaginary value
     * @param p_real_val multiplying constant real value
     * @param p_img_val multiplying constant imaginary value
     */
    PhoenixSetSidePanel(JSplitPane mainScreen, int iterationsNum, int bgColor1, int bgColor2, int fgColor, double c_real_val, double c_img_val, double p_real_val, double p_img_val) {
        super();

        this.mainScreen = mainScreen;
        updateFractalPanel();

        JButton pickBg1 = new JButton("Pick");
        JButton pickBg2 = new JButton("Pick");
        JButton pickFg = new JButton("Pick");
        JLabel labelIterationNum = new JLabel("Number of Iterations");
        JLabel labelCReal = new JLabel("C Real Value");
        JLabel labelCImg = new JLabel("C Imaginary Value");
        JLabel labelPReal = new JLabel("P Real Value");
        JLabel labelPImg = new JLabel("P Imaginary Value");
        labelIterationNum.setForeground(Color.WHITE);
        labelCReal.setForeground(Color.WHITE);
        labelCImg.setForeground(Color.WHITE);
        labelPReal.setForeground(Color.WHITE);
        labelPImg.setForeground(Color.WHITE);

        iterations = new JTextField();
        iterations.setText(String.valueOf(iterationsNum));
        iterations.setMaximumSize(new Dimension(50, 20));

        c_real = new JTextField();
        c_real.setText(String.valueOf(c_real_val));
        c_real.setMaximumSize(new Dimension(50, 20));

        c_img = new JTextField();
        c_img.setText(String.valueOf(c_img_val));
        c_img.setMaximumSize(new Dimension(50, 20));

        p_real = new JTextField();
        p_real.setText(String.valueOf(p_real_val));
        p_real.setMaximumSize(new Dimension(50, 20));

        p_img = new JTextField();
        p_img.setText(String.valueOf(p_img_val));
        p_img.setMaximumSize(new Dimension(50, 20));

        pickBg1.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickBg2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickFg.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCReal.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPReal.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));
        labelCReal.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));
        labelCImg.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        labelPReal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        labelPImg.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        bg1ColorPicker = new ColorPicker(0, new Color(bgColor1), pickBg1, fractalPanel);
        bg2ColorPicker = new ColorPicker(1, new Color(bgColor2), pickBg2, fractalPanel);
        fgColorPicker = new ColorPicker(2, new Color(fgColor), pickFg, fractalPanel);

        //update phoenix set according to the number of iterations we have input.
        //whenever we change the iterations, we create a new updated phoenix set object and place it on the screen
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
                        PhoenixSet phoenixSet = (PhoenixSet) mainScreen.getRightComponent();
                        int bgColor1 = phoenixSet.getBackgroundColor(0);
                        int bgColor2 = phoenixSet.getBackgroundColor(1);
                        int fgColor = phoenixSet.getForegroundColor();
                        double c_real = phoenixSet.getCReal();
                        double c_img = phoenixSet.getCImg();
                        mainScreen.setRightComponent(new PhoenixSet(iterationsNumber, bgColor1, bgColor2, fgColor, mainScreen, c_real, c_img));
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

        //update phoenix set real constant value
        c_real.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double newCReal = Double.parseDouble(c_real.getText());
                    PhoenixSet phoenixSet = (PhoenixSet) mainScreen.getRightComponent();
                    int iterationsNumber = phoenixSet.getIterations();
                    int bgColor1 = phoenixSet.getBackgroundColor(0);
                    int bgColor2 = phoenixSet.getBackgroundColor(1);
                    int fgColor = phoenixSet.getForegroundColor();
                    double moveX = phoenixSet.getMoveX();
                    double moveY = phoenixSet.getMoveY();
                    double minX = phoenixSet.getMinX();
                    double maxX = phoenixSet.getMaxX();
                    double minY = phoenixSet.getMinY();
                    double maxY = phoenixSet.getMaxY();
                    double c_img_value = phoenixSet.getCImg();
                    double p_real_value = phoenixSet.getPReal();
                    double p_img_value = phoenixSet.getPImg();
                    int screenX = phoenixSet.getScreenX();
                    int screenY = phoenixSet.getScreenY();
                    PhoenixSet newJuliaSet = new PhoenixSet(iterationsNumber, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen, newCReal, c_img_value, p_real_value, p_img_value, screenX, screenY);
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

        //update phoenix set imaginary constant value
        c_img.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double newCImg = Double.parseDouble(c_img.getText());
                    PhoenixSet phoenixSet = (PhoenixSet) mainScreen.getRightComponent();
                    int iterationsNumber = phoenixSet.getIterations();
                    int bgColor1 = phoenixSet.getBackgroundColor(0);
                    int bgColor2 = phoenixSet.getBackgroundColor(1);
                    int fgColor = phoenixSet.getForegroundColor();
                    double moveX = phoenixSet.getMoveX();
                    double moveY = phoenixSet.getMoveY();
                    double minX = phoenixSet.getMinX();
                    double maxX = phoenixSet.getMaxX();
                    double minY = phoenixSet.getMinY();
                    double maxY = phoenixSet.getMaxY();
                    double c_real_value = phoenixSet.getCReal();
                    double p_real_value = phoenixSet.getPReal();
                    double p_img_value = phoenixSet.getPImg();
                    int screenX = phoenixSet.getScreenX();
                    int screenY = phoenixSet.getScreenY();
                    PhoenixSet newJuliaSet = new PhoenixSet(iterationsNumber, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen, c_real_value, newCImg, p_real_value, p_img_value, screenX, screenY);
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

        //update phoenix set real multiplying constant value
        p_real.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double newPReal = Double.parseDouble(p_real.getText());
                    PhoenixSet phoenixSet = (PhoenixSet) mainScreen.getRightComponent();
                    int iterationsNumber = phoenixSet.getIterations();
                    int bgColor1 = phoenixSet.getBackgroundColor(0);
                    int bgColor2 = phoenixSet.getBackgroundColor(1);
                    int fgColor = phoenixSet.getForegroundColor();
                    double moveX = phoenixSet.getMoveX();
                    double moveY = phoenixSet.getMoveY();
                    double minX = phoenixSet.getMinX();
                    double maxX = phoenixSet.getMaxX();
                    double minY = phoenixSet.getMinY();
                    double maxY = phoenixSet.getMaxY();
                    double c_real_value = phoenixSet.getCReal();
                    double c_img_value = phoenixSet.getCImg();
                    double p_img_value = phoenixSet.getPImg();
                    int screenX = phoenixSet.getScreenX();
                    int screenY = phoenixSet.getScreenY();
                    PhoenixSet newJuliaSet = new PhoenixSet(iterationsNumber, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen, c_real_value, c_img_value, newPReal, p_img_value, screenX, screenY);
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

        //update phoenix set imaginary multiplying constant value
        p_img.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double newPImg = Double.parseDouble(p_img.getText());
                    PhoenixSet phoenixSet = (PhoenixSet) mainScreen.getRightComponent();
                    int iterationsNumber = phoenixSet.getIterations();
                    int bgColor1 = phoenixSet.getBackgroundColor(0);
                    int bgColor2 = phoenixSet.getBackgroundColor(1);
                    int fgColor = phoenixSet.getForegroundColor();
                    double moveX = phoenixSet.getMoveX();
                    double moveY = phoenixSet.getMoveY();
                    double minX = phoenixSet.getMinX();
                    double maxX = phoenixSet.getMaxX();
                    double minY = phoenixSet.getMinY();
                    double maxY = phoenixSet.getMaxY();
                    double c_real_value = phoenixSet.getCReal();
                    double c_img_value = phoenixSet.getCReal();
                    double p_real_value = phoenixSet.getPReal();
                    int screenX = phoenixSet.getScreenX();
                    int screenY = phoenixSet.getScreenY();
                    PhoenixSet newJuliaSet = new PhoenixSet(iterationsNumber, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen, c_real_value, c_img_value, p_real_value, newPImg, screenX, screenY);
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
        this.add(labelPReal);
        this.add(p_real);
        this.add(labelPImg);
        this.add(p_img);
    }

    private void updateFractalPanel() {
        this.fractalPanel = (PhoenixSet) mainScreen.getRightComponent();
    }

    //update the color pickers whenever the fractal shown on screen is changed or updated
    private void updateColorPickers() {
        PhoenixSet phoenixSet = (PhoenixSet) mainScreen.getRightComponent();
        bg1ColorPicker.updateFractalPanel(phoenixSet);
        bg2ColorPicker.updateFractalPanel(phoenixSet);
        fgColorPicker.updateFractalPanel(phoenixSet);
    }
}