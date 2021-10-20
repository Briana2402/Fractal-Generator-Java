import java.awt.event.*;

import javax.swing.event.MouseInputListener;

public class MouseManipulation implements MouseWheelListener, MouseInputListener {
    int NOTCH_ZOOM_VALUE = 8;

    MandelbrotSet mandelbrotSet;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int movement;
        movement = e.getWheelRotation();
        if (movement < 0) { //if the mouse wheel is moved upwards
            if (e.getComponent() instanceof MandelbrotSet) {
                mandelbrotSet = (MandelbrotSet) e.getComponent();
                mandelbrotSet.zoom(NOTCH_ZOOM_VALUE);
            }
        }
        if (movement > 0) { //if the mouse wheel is moved downwards
            if (e.getComponent() instanceof MandelbrotSet) {
                mandelbrotSet = (MandelbrotSet) e.getComponent();
                mandelbrotSet.zoom(-NOTCH_ZOOM_VALUE);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent() instanceof MandelbrotSet) {
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            mandelbrotSet.setMousePoint(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //negative dx: dragging left
        //positive dx: dragging right
        //negative dy: dragging up
        //positive dy: dragging down
        if (e.getComponent() instanceof MandelbrotSet) {
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            double dx = e.getX() - mandelbrotSet.getMousePoint().getX();
            double dy = e.getY() - mandelbrotSet.getMousePoint().getY();
            mandelbrotSet.setMousePoint(e.getPoint());
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            mandelbrotSet.moveFractal(dx, dy);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
