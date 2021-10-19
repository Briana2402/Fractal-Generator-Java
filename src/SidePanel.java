import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    //Defining constants
    final static Color backgroundColor = new Color (0x123456);
    final static int SCREENX = 200;
    final static int SCREENY = 600;

    SidePanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.setBackground(backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
        this.setVisible(true);
    }
}