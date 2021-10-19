import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MandelbrotSetSidePanel extends SidePanel {
    //Defining variables accessed by the whole class
    MandelbrotSet fractalPanel;
    JTextField iterations;
    MandelbrotSetSidePanel(MandelbrotSet fractalPanel) {
        super();

        this.fractalPanel = fractalPanel;

        JButton pickBg1 = new JButton("Pick");
        JButton pickBg2 = new JButton("Pick");
        JButton pickFg = new JButton("Pick");
        JLabel labelIterationNum = new JLabel("Number of Iterations");
        labelIterationNum.setForeground(Color.WHITE);

        iterations = new JTextField();
        iterations.setText("100");

        iterations.setMaximumSize(new Dimension(50, 20));
        
        pickBg1.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickBg2.setAlignmentX(Component.CENTER_ALIGNMENT);
        pickFg.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelIterationNum.setBorder(BorderFactory.createEmptyBorder(50, 20, 10, 20));

        ColorPicker bg1ColorPicker = new ColorPicker(0, Color.DARK_GRAY, pickBg1, fractalPanel);
        ColorPicker bg2ColorPicker = new ColorPicker(1, Color.RED, pickBg2, fractalPanel);
        ColorPicker fgColorPicker = new ColorPicker(2, Color.ORANGE, pickFg, fractalPanel);

        iterations.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeIterationNum();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                changeIterationNum();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                changeIterationNum();
            }

            public void changeIterationNum() {
                if (iterations.getText().length() > 0) {
                    try {
                        Integer iterationsNumber = Integer.parseInt(iterations.getText());
                        if (iterationsNumber < 0) {
                            JOptionPane.showMessageDialog(null,
                            "Please enter a number of iterations larger than or equal to 0.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (iterationsNumber == 0) {
                                fractalPanel.changeIterationNum(20);
                            } else {
                                fractalPanel.changeIterationNum(iterationsNumber);
                            }
                        }
                    } catch (Exception e) {
                        if (iterations.getText().length() > 0) {
                            JOptionPane.showMessageDialog(null,
                            "Please input a numerical value.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                            iterations.setText(null);
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    iterations.setText(null);
                                }
                            });
                        }
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
}
