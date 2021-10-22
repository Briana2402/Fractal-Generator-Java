/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Path2D;

public class PythagorasTree extends JPanel{
    //Define constants
    final static int STARTING_X = 200;
    final static int STARTING_Y = 300;
    final static int SCREENX = 800;
    final static int SCREENY = 600;
    final static int STARTING_ITERATIONS = 7;
    final static int STARTING_ANGLE = 45;
    final static Color STARTING_BG_COLOR = Color.LIGHT_GRAY;
    final static Color STARTING_SQUARE_COLOR = new Color (39, 110, 245);
    final static Color STARTING_TRIANGLE_COLOR = new Color (200, 4, 21);
    final static Color STARTING_LINE_COLOR = Color.BLACK;
    
    //Define variables used by the whole class
    Color backgroundColor;
    Color foregroundColor;
    Color triangleColor;
    Color lineColor;
    double angleRad;
    int iterations;
    double size;
    Image lines;
    Image foreground;
    int width;
    int height;
    int moveX;
    int moveY;
    int mouseX = 0;
    int mouseY = 0;

    int screenX;
    int screenY;

    boolean infinite;

    Path2D tree = new Path2D.Double();

    /**
     * Pythagoras tree constructor.
     * @param backgroundColor screen background color.
     * @param foregroundColor foreground color.
     * @param triangleColor triangle color.
     * @param lineColor line color.
     */
    PythagorasTree(Color backgroundColor, Color foregroundColor, Color triangleColor, Color lineColor) {
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.triangleColor = triangleColor;
        this.lineColor = lineColor;
        this.addComponentListener(new ResizeListener());
        this.screenX = SCREENX;
        this.screenY = SCREENY;

        initialise();
    }

    /**
     * Pythagoras tree constructor
     * @param angleRad angle of pythagoras tree in radians
     * @param iterations number of iterations
     * @param moveX x movement of the tree
     * @param moveY y movement of the tree
     * @param squareSize size of the initial square
     * @param bg background color
     * @param sq square color
     * @param tg triangle color
     * @param ln line color
     */
    PythagorasTree(double angleRad, int iterations, int moveX, int moveY, double squareSize, Color bg, Color sq, Color tg, Color ln) {
        this(bg, sq, tg, ln);
        this.angleRad = angleRad;
        this.infinite = (iterations == 0);
        this.iterations = (this.infinite) ? 20 : iterations;
        this.moveX = moveX;
        this.moveY = moveY;
        this.size = squareSize;

        this.repaint();
    }

    /**
     * Initialise all variables and add all listeners.
     */
    private void initialise() {
        this.angleRad = Math.toRadians(45);
        this.iterations = 7;
        this.size = 75;
        this.moveX = 0;
        this.moveY = 0;

        this.setBackground(backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
        this.addMouseMotionListener(new MouseManipulation());
        this.addMouseListener(new MouseManipulation());
        this.addMouseWheelListener(new MouseManipulation());
    }

    /**
     * Create a square with the following points.
     * @param x1 bottom left X
     * @param y1 bottom left Y
     * @param x2 bottom right X
     * @param y2 bottom right Y
     * @param x3 top right X
     * @param y3 top right Y
     * @param x4 top left X
     * @param y4 top left Y
     * @return Path2D square object
     */
    private Path2D createSquare(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        Path2D currentSquare = new Path2D.Double();
        currentSquare.moveTo(x1, y1);   //places the bottom left corner of the square at the desired coordinates  
        currentSquare.lineTo(x2, y2);   //draws a line to the bottom right corner of the square
        currentSquare.lineTo(x3, y3);   //draws a line to the top right corner of the square
        currentSquare.lineTo(x4, y4);   //draws a line to the top left corner of the square
        currentSquare.closePath();      //finishes the square by turning it into a closed shape

        return currentSquare;
    }

    /**
     * Create a triangle with the following points.
     * @param x1 point 1 X
     * @param y1 point 1 Y
     * @param x2 point 2 X
     * @param y2 point 2 Y
     * @param x3 point 3 X
     * @param y3 point 3 Y
     * @return Path2D triangle object
     */
    private Path2D createTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        Path2D currentTriangle = new Path2D.Double();
        currentTriangle.moveTo(x1, y1);
        currentTriangle.lineTo(x2, y2);
        currentTriangle.lineTo(x3, y3);
        currentTriangle.closePath();

        return currentTriangle;
    }

    /**
     * Paints the pythagoras tree.
     * @param g Graphics2D used to paint tree
     */
    private void paintPythagorasTree(Graphics2D g) {
        g.setColor(foregroundColor);
        g.fill(this.tree);
        g.setColor(lineColor);
        g.draw(this.tree);
    }

    private void paintTriangle(Path2D triangle, Graphics2D g) {
        g.setColor(triangleColor);      //fills the triangle in the triangle color
        g.fill(triangle);
    }

    /**
     * Creates all squares and triangles for pythagoras tree. Fills triangles as well.
     * @param g Graphics2D used to paint triangles
     * @param iteration iteration number
     * @param currentSize square size
     * @param currentAngle square angle relative to screen
     * @param x bottom left X coordinate of square
     * @param y bottom left Y coordinate of square
     */
    private void createPythagorasTree(Graphics2D g, int iteration, double currentSize, double currentAngle, double x, double y) {
        if (iteration == this.iterations || currentSize < 1) {
            return;
        }

        if (currentAngle >= 2 * Math.PI) {
            currentAngle -= Math.PI;
        }
        if (currentAngle <= -2 * Math.PI) {
            currentAngle += Math.PI;
        }

        //Create a new square
        double x2 = x + currentSize * Math.cos(currentAngle);
        double y2 = y - currentSize * Math.sin(currentAngle);
        //Check for out of field of view. If we cannot see it, don't render it.
        if (((x < x2 && ((x < 0 - currentSize) || x2 > screenX + currentSize))
        || (x2 < x && ((x2 < 0 - currentSize) || (x > screenX + currentSize)))
        || (y < y2 && ((y < 0 - currentSize) || (y2 > screenY + currentSize)))
        || (y2 < y && ((y2 < 0 - currentSize) || (y > screenY + currentSize)))) && iteration > 4) {
            return;
        }
        currentAngle += this.angleRad;
        double x3 = x2 + y2 - y;
        double y3 = y2 + x - x2;
        double x4 = x + y2 - y;
        double y4 = y + x - x2;
        double tmp = currentSize * Math.cos(this.angleRad);
        double x5 = x4 + tmp * Math.cos(currentAngle);
        double y5 = y4 - tmp * Math.sin(currentAngle);
        Path2D currentSquare = createSquare(x, y, x2, y2, x3, y3, x4, y4);
        //Add it to the tree
        tree.append(currentSquare, false);
        //Create a new triangle
        if (iteration < this.iterations - 1) {
            Path2D currentTriangle = createTriangle(x4, y4, x5, y5, x3, y3);
            paintTriangle(currentTriangle, g);
        }   

        createPythagorasTree(g, iteration + 1, currentSize * Math.cos(this.angleRad), currentAngle, x4, y4); //left side
        createPythagorasTree(g, iteration + 1, currentSize * Math.sin(this.angleRad), currentAngle - (Math.PI / 2), x5, y5); //right side
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //createPythagorasTree(g2d, 0, STARTING_X + moveX, STARTING_Y + size + moveY, STARTING_X + size + moveX, STARTING_Y + size + moveY, 0);
        createPythagorasTree(g2d, 0, size, 0, STARTING_X + moveX, STARTING_Y + moveY);
        paintPythagorasTree(g2d);
    }

    /**
     * Moves the fractal a certain amount of pixels
     * @param dx X pixel movement
     * @param dy Y pixel movement
     */
    public void moveFractal(double dx, double dy) {
        this.moveX += dx;
        this.moveY += dy;
        this.tree = new Path2D.Double();
        this.repaint();
    }

    /**
     * Zooms into the fractal a certain amount of pixels
     * @param pixels pixels it zooms in
     */
    public void zoom(int pixels) {
        this.size += pixels;
        this.moveX += pixels;
        this.moveY += pixels;
        if (this.size < 0) {
            this.size = 0;
        }
        if (this.infinite) {
            this.iterations++;
        }
        this.tree = new Path2D.Double();
        this.repaint();
    }

    void setBackgroundColor(Color newBgColor) {
        this.backgroundColor = newBgColor;
        this.setBackground(backgroundColor);
        this.tree = new Path2D.Double();
        this.repaint();
        System.out.println("Background Changed");
    }

    void setForegroundColor(Color newFgColor) {
        this.foregroundColor = newFgColor;
        this.tree = new Path2D.Double();
        this.repaint();
        System.out.println("Foreground Changed");
    }

    void setTriangleColor(Color newTgColor) {
        this.triangleColor = newTgColor;
        this.tree = new Path2D.Double();
        this.repaint();
        System.out.println("Triangle Changed");
    }

    void setLineColor(Color newLnColor) {
        this.lineColor = newLnColor;
        this.tree = new Path2D.Double();
        this.repaint();
        System.out.println("Line Color Changed");
    }

    void changeAngle(int newAngle) {
        this.angleRad = Math.toRadians(newAngle);
        this.tree = new Path2D.Double();
        this.repaint();
        System.out.println("New Angle: " + newAngle);
    }

    void changeIterationNum(int iterations) {
        this.infinite = (iterations == 0);
        this.iterations = (this.infinite) ? 20 : iterations;
        this.tree = new Path2D.Double();
        this.repaint();
        System.out.println("Iterations: " + this.iterations);
    }

    public void updateScreenSize(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public void setMousePoint(Point newPoint) {
        this.mouseX = (int) newPoint.getX();
        this.mouseY = (int) newPoint.getY();
    }

    public Point getMousePoint() {
        return new Point(mouseX, mouseY);
    }

    public double getAngle() {
        return this.angleRad;
    }

    public int getIterations() {
        return this.iterations;
    }

    public int getMoveX() {
        return this.moveX;
    }

    public int getMoveY() {
        return this.moveY;
    }

    public double getSquareSize() {
        return this.size;
    }

    public int getBackgroundColor() {
        return this.backgroundColor.getRGB();
    }

    public int getSquareColor() {
        return this.foregroundColor.getRGB();
    }

    public int getTriangleColor() {
        return this.triangleColor.getRGB();
    }

    public int getLineColor() {
        return this.lineColor.getRGB();
    }
}