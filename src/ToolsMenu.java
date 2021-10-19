import javax.swing.*;

import java.awt.event.*;

class ToolsMenu extends JMenu implements ActionListener {
    //Define constants
    final static String TOGGLE_SIDEBAR = "Toggle Sidebar";
    SidePanel sidePanel;
    JSplitPane mainScreen;
    boolean sidePanelVisibility = true;

    ToolsMenu(SidePanel sidePanel, JSplitPane mainScreen) {
        JMenuItem toggleSideBar = new JMenuItem("Toggle SideBar");

        toggleSideBar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        toggleSideBar.setMnemonic(KeyEvent.VK_S);

        toggleSideBar.addActionListener(this);

        this.add(toggleSideBar);

        this.setText("Tools");
        this.setMnemonic(KeyEvent.VK_T);

        this.sidePanel = sidePanel;
        this.mainScreen = mainScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        sidePanelVisibility = !sidePanelVisibility;
        sidePanel.setVisible(sidePanelVisibility);
        mainScreen.setLeftComponent(sidePanel);
    }
}