/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import java.awt.event.*;

import javax.swing.event.MouseInputListener;

public class MouseManipulation implements MouseWheelListener, MouseInputListener {
    int NOTCH_ZOOM_VALUE = 32;

    MandelbrotSet mandelbrotSet;
    PythagorasTree pythagorasTree;
    PhoenixSet phoenixSet;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int movement;
        movement = e.getWheelRotation();
        if (movement < 0) { //if the mouse wheel is moved upwards we zoom in
            if (e.getComponent() instanceof MandelbrotSet) { //for mandelbrot and phoenix set
                mandelbrotSet = (MandelbrotSet) e.getComponent();
                mandelbrotSet.zoom(NOTCH_ZOOM_VALUE);
            }
            if (e.getComponent() instanceof PythagorasTree) { //for pythagoras tree
                pythagorasTree = (PythagorasTree) e.getComponent();
                pythagorasTree.zoom(NOTCH_ZOOM_VALUE);
            }
        }
        if (movement > 0) { //if the mouse wheel is moved downwards we zoom out
            if (e.getComponent() instanceof MandelbrotSet) { //for mandelbrot and phoenix set
                mandelbrotSet = (MandelbrotSet) e.getComponent();
                mandelbrotSet.zoom(-NOTCH_ZOOM_VALUE);
            }
            if (e.getComponent() instanceof PythagorasTree) { //for pythagoras tree
                pythagorasTree = (PythagorasTree) e.getComponent();
                pythagorasTree.zoom(-NOTCH_ZOOM_VALUE);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //for moving around the fractal
        if (e.getComponent() instanceof MandelbrotSet) {
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            mandelbrotSet.setMousePoint(e.getPoint());
        }
        if (e.getComponent() instanceof PythagorasTree) {
            pythagorasTree = (PythagorasTree) e.getComponent();
            pythagorasTree.setMousePoint(e.getPoint());
        }
        if (e.getComponent() instanceof PhoenixSet) {
            phoenixSet = (PhoenixSet) e.getComponent();
            phoenixSet.setMousePoint(e.getPoint());
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
        //for moving around the fractal
        //negative dx: dragging left
        //positive dx: dragging right
        //negative dy: dragging up
        //positive dy: dragging down
        if (e.getComponent() instanceof MandelbrotSet) { //for mandelbrot and phoenix set
            mandelbrotSet = (MandelbrotSet) e.getComponent();
            double dx = e.getX() - mandelbrotSet.getMousePoint().getX();
            double dy = e.getY() - mandelbrotSet.getMousePoint().getY();
            mandelbrotSet.setMousePoint(e.getPoint());
            mandelbrotSet.moveFractal(dx, dy);
        }
        if (e.getComponent() instanceof PythagorasTree) { //for pythagoras tree
            pythagorasTree = (PythagorasTree) e.getComponent();
            double dx = e.getX() - pythagorasTree.getMousePoint().getX();
            double dy = e.getY() - pythagorasTree.getMousePoint().getY();
            pythagorasTree.setMousePoint(e.getPoint());
            pythagorasTree.moveFractal(dx, dy);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
