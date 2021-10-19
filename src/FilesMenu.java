import javax.swing.*;
import java.awt.event.*;

public class FilesMenu extends JMenu implements ActionListener{
    //Define constants
    final static String EXIT_PROGRAM = "Exit Program";

    FilesMenu() {
        JMenuItem exit = new JMenuItem("Exit");

        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setActionCommand(EXIT_PROGRAM);

        exit.addActionListener(this);

        this.add(exit);

        this.setText("Files");
        this.setMnemonic(KeyEvent.VK_F);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        switch (e.getActionCommand()) {
            case EXIT_PROGRAM: System.exit(0); break;
        }
    }
}