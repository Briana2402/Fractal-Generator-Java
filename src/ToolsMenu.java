/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;

import java.awt.event.*;

class ToolsMenu extends JMenu implements ActionListener {
    //Define constants
    final static String TOGGLE_SIDEBAR = "Toggle Sidebar";

    //Define variables used by the whole class
    SidePanel sidePanel;
    JSplitPane mainScreen;
    boolean sidePanelVisibility = true;

    /**
     * Tools Menu constructor. Creates a new Tools Menu object.
     * @param mainScreen the main screen where the fractals are
     */
    ToolsMenu(JSplitPane mainScreen) {
        JMenuItem toggleSideBar = new JMenuItem("Toggle SideBar");

        toggleSideBar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        toggleSideBar.setMnemonic(KeyEvent.VK_S);

        toggleSideBar.addActionListener(this);

        this.add(toggleSideBar);

        this.setText("Tools");
        this.setMnemonic(KeyEvent.VK_T);

        this.sidePanel = (SidePanel) mainScreen.getLeftComponent();
        this.mainScreen = mainScreen;
    }

    //listen for when we use the menu
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (mainScreen.getLeftComponent() != null) {
            this.sidePanel = (SidePanel) mainScreen.getLeftComponent();
        }
        sidePanelVisibility = !sidePanelVisibility;
        sidePanel.setVisible(sidePanelVisibility);
        mainScreen.setLeftComponent(sidePanel);
    }
}