import javax.swing.*;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.*;

public class RecentColors extends AbstractColorChooserPanel{
    @Override
    public void updateChooser() {
    }

    @Override
    protected void buildChooser() {
        setLayout(new GridLayout(5, 5));
    }

    @Override
    public String getDisplayName() {
        return "Recently Used Colors";
    }

    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }

    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }
}