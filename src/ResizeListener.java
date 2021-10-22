/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import java.awt.event.*;

public class ResizeListener implements ComponentListener{
    PythagorasTree pythagorasTree;
    MandelbrotSet mandelbrotSet;
    PhoenixSet phoenixSet;

    @Override
    public void componentResized(ComponentEvent e) {
        //Update screen sizes of fractals when they get resized.
        if (e.getComponent() instanceof MandelbrotSet) {
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            int width = mandelbrotSet.getWidth();
            int height = mandelbrotSet.getHeight();
            if (width + height != 0) {
                mandelbrotSet.setDimensions(width, height);
            }
        }
        if (e.getComponent() instanceof PythagorasTree) {
            pythagorasTree = (PythagorasTree) e.getComponent();
            int width = pythagorasTree.getWidth();
            int height = pythagorasTree.getHeight();
            if (width + height != 0) {
                pythagorasTree.updateScreenSize(width, height);
            }
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //Update screen sizes of fractals when are shown.
        if (e.getComponent() instanceof MandelbrotSet) {
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            int width = mandelbrotSet.getWidth();
            int height = mandelbrotSet.getHeight();
            if (width + height != 0) {
                mandelbrotSet.setDimensions(width, height);
            }
        }
        if (e.getComponent() instanceof PhoenixSet) {
            phoenixSet = (PhoenixSet) e.getComponent();
            int width = phoenixSet.getWidth();
            int height = phoenixSet.getHeight();
            if (width + height != 0) {
                phoenixSet.setDimensions(width, height);
            }
        }
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
