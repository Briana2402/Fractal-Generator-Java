import java.awt.event.*;

public class ResizeListener implements ComponentListener{
    MandelbrotSet mandelbrotSet;

    @Override
    public void componentResized(ComponentEvent e) {
        
        if (e.getComponent() instanceof MandelbrotSet) {
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            int width = mandelbrotSet.getWidth();
            int height = mandelbrotSet.getHeight();
            if (width + height != 0) {
                mandelbrotSet.setDimensions(mandelbrotSet.getWidth(), mandelbrotSet.getHeight());
            }
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
