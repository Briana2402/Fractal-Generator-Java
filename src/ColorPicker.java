/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class ColorPicker extends JPanel implements ActionListener {
    //Define variables used by the whole class
    Color backgroundColor = new Color (0x123456);
    JTextArea colorPreview = new JTextArea();
    Color color;
    PythagorasTree pythagorasPanel;
    MandelbrotSet mandelbrotPanel;
    PhoenixSet phoenixPanel;
    int currentFractalType = 0; //0: Pythagoras tree. 1: Mandelbrot set. 2: Phoenix set.
    int pickerType;
    JLabel colorLabel;

    /**
     * Color picker constructor. Creates a new color picker with the following properties.
     * @param pickerType color picker type.0: backgrund color1. 1: bacgkrund clor 2. 2: foreground color. 3: line color
     * @param color default color selected
     * @param pickButton button that opens up color picker
     */
    ColorPicker(int pickerType, Color color, JButton pickButton) {
        colorLabel = new JLabel();
        switch (pickerType) {
            case 0: //Background Color 1
                colorLabel.setText("Background Color 1");
                break;
            case 1: //Background Color 2
                colorLabel.setText("Background Color 2");
                break;
            case 2: //Foreground Color
                colorLabel.setText("Foreground Color");
                break;
            case 3: //Line Color
                colorLabel.setText("Line Color");
                break;
        }
        this.color = color;
        this.pickerType = pickerType;
        colorLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        colorLabel.setHorizontalAlignment(JLabel.CENTER);
        colorLabel.setForeground(Color.WHITE);
        pickButton.addActionListener(this);
        colorPreview.setBorder(new EmptyBorder(10, 10, 10, 10));
        colorPreview.setText("          ");  
        colorPreview.setBackground(color);
        colorPreview.setEditable(false);
        colorPreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorPreview.setFocusable(false);

        this.add(colorLabel);
        this.add(colorPreview);
        this.add(pickButton);

        this.setBackground(backgroundColor);
    }

    /**
     * Color picker constructor. Creates a new color picker with the following properties.
     * @param pickerType color picker type.0: backgrund color1. 1: bacgkrund clor 2. 2: foreground color. 3: line color
     * @param color default color selected
     * @param pickButton button that opens up color picker
     * @param fractalPanel fractal panel that the color picker controls
     */
    ColorPicker(int pickerType, Color color, JButton pickButton, PythagorasTree fractalPanel) {
        this(pickerType, color, pickButton);
        this.pythagorasPanel = fractalPanel;
        this.currentFractalType = 0;
    }

    /**
     * Color picker constructor. Creates a new color picker with the following properties.
     * @param pickerType color picker type.0: backgrund color1. 1: bacgkrund clor 2. 2: foreground color. 3: line color
     * @param color default color selected
     * @param pickButton button that opens up color picker
     * @param fractalPanel fractal panel that the color picker controls
     */
    ColorPicker(int pickerType, Color color, JButton pickButton, MandelbrotSet fractalPanel) {
        this(pickerType, color, pickButton);
        this.mandelbrotPanel = fractalPanel;
        this.currentFractalType = 1;
    }

    /**
     * Color picker constructor. Creates a new color picker with the following properties.
     * @param pickerType color picker type.0: backgrund color1. 1: bacgkrund clor 2. 2: foreground color. 3: line color
     * @param color default color selected
     * @param pickButton button that opens up color picker
     * @param fractalPanel fractal panel that the color picker controls
     */
    ColorPicker(int pickerType, Color color, JButton pickButton, PhoenixSet fractalPanel) {
        this(pickerType, color, pickButton);
        this.phoenixPanel = fractalPanel;
        this.currentFractalType = 2;
    }

    //check for the button press
    @Override
    public void actionPerformed(ActionEvent e) {
        this.color = JColorChooser.showDialog(this, "Pick Color", this.color);
        if (this.color != null) {
            colorPreview.setBackground(color);
            switch (this.pickerType) {
                case 0: //Background Color 1
                    switch (this.currentFractalType) {
                        case 0: //Pythagoras Tree
                            pythagorasPanel.setBackgroundColor(this.color);
                            break;
                        case 1: //Mandelbrot Set
                            mandelbrotPanel.setBackgroundColor(1, this.color);
                            break;
                        case 2: //Phoenix Set
                            phoenixPanel.setBackgroundColor(1, this.color);
                            break;
                        default: break;
                    }
                    break;
                case 1: //Background Color 2
                    switch (this.currentFractalType) {
                        case 0: //for the Pythagoras Tree, we will use bg color 2 as the triangle color
                            pythagorasPanel.setTriangleColor(this.color);
                            break;
                        case 1: //Mandelbrot Set
                            mandelbrotPanel.setBackgroundColor(2, this.color);
                            break;
                        case 2: //Phoenix Set
                            phoenixPanel.setBackgroundColor(2, this.color);
                            break;
                        default: break;
                    }
                    break;
                case 2: //Foreground Color
                    switch (this.currentFractalType) {
                        case 0: //Pythagoras Tree
                            pythagorasPanel.setForegroundColor(this.color);
                            break;
                        case 1: //Mandelbrot Set
                            mandelbrotPanel.setForegroundColor(this.color);
                            break;
                        case 2: //Phoenix Set
                            phoenixPanel.setForegroundColor(this.color);
                            break;
                        default: break;
                    }
                    break;
                case 3: //Line Color
                    pythagorasPanel.setLineColor(this.color);
                    break;
                default: break;
            }
        }
    }

    //used so that there isn't a huge space in between the pick button and the color preview
    @Override
    public Dimension getMaximumSize() { 
        super.getMaximumSize();
        Dimension dim = super.getMaximumSize();
        dim.height = 50;
        return dim;
    }

    void setLabelText(String newText) {
        this.colorLabel.setText(newText);
        this.repaint();
    }

    void updateFractalPanel(MandelbrotSet mandelbrotSet) {
        this.mandelbrotPanel = mandelbrotSet;
    }

    void updateFractalPanel(PhoenixSet phoenixSet) {
        this.phoenixPanel = phoenixSet;
    }
}
