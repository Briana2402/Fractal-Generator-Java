/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    //Defining constants
    final static Color backgroundColor = new Color (0x123456);
    final static int SCREENX = 200;
    final static int SCREENY = 600;
    final static int STARTING_ITERATIONS = 100;

    /**
     * Side Panel constructor. Creates a new side panel object.
     */
    SidePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.setBackground(backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
        this.setVisible(true);
    }
}