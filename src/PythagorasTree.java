import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

//TODO Maybe if u do super.drawline it could have worked

//TODO instead of making the alpha value 50% make it paint the squares first and then the lines

//TODO stop going between hex angle and radians constantly

public class PythagorasTree extends JPanel{
    //Define constants
    final int STARTING_X = 200;
    final int STARTING_Y = 300;
    final int SCREENX = 800;
    final int SCREENY = 600;
    
    //Define variables used by the whole class
    Color backgroundColor;
    Color foregroundColor;
    Color triangleColor;
    Color lineColor;
    int angle;
    int iterations;
    double size;
    Image lines;
    Image foreground;
    int width;
    int height;

    PythagorasTree() {
        this.backgroundColor = Color.LIGHT_GRAY;
        this.foregroundColor = new Color (39, 110, 245, 127);
        this.triangleColor = new Color (200, 4, 21, 127);
        this.lineColor = Color.BLACK;

        this.angle = 45;
        this.iterations = 10;
        this.size = 75;

        this.setBackground(backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
    }

    PythagorasTree(Color backgroundColor, Color foregroundColor, Color triangleColor, Color lineColor) {
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.triangleColor = triangleColor;

        this.lineColor = lineColor;

        this.angle = 30;
        this.iterations = 7;
        this.size = 75;

        this.setBackground(backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
    }

    private Path2D createSquare(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        Path2D currentSquare = new Path2D.Double();
        currentSquare.moveTo(x1, y1);   //places the bottom left corner of the square at the desired coordinates  
        currentSquare.lineTo(x2, y2);   //draws a line to the bottom right corner of the square
        currentSquare.lineTo(x4, y4);   //draws a line to the top right corner of the square
        currentSquare.lineTo(x3, y3);   //draws a line to the top left corner of the square
        currentSquare.closePath();      //finishes the square by turning it into a closed shape

        return currentSquare;
    }

    private Path2D createTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        Path2D currentTriangle = new Path2D.Double();
        currentTriangle.moveTo(x1, y1);
        currentTriangle.lineTo(x2, y2);
        currentTriangle.lineTo(x3, y3);
        currentTriangle.closePath();

        return currentTriangle;
    }

    /*private void drawLines(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        BufferedImage lines = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        Graphics gN = lines.getGraphics();
        Graphics2D g = (Graphics2D) gN;

        g.setComposite(AlphaComposite.Clear); //make the background of the buffered image clear
        g.fillRect(0, 0, 500, 500);

        g.setComposite(AlphaComposite.Src);

        Path2D currentSquare = createSquare(x1, y1, x2, y2, x3, y3, x4, y4);

        g.setColor(lineColor);          //draws the line around the perimeter of the square
        g.draw(currentSquare);

        //g.fillOval((int) x5 - 3, (int) y5 - 3, 6, 6);

        this.lines = lines;
    }*/

    /**
     * To see the principle used to render the pythagoras tree, check PythagorasTreePrinciple.jpg under the img folder.
     */
    private void drawPythagorasTree(Graphics2D g, int iteration, double x1, double y1, double x2, double y2, double currentAngle) {
        if (iteration == this.iterations) {
            return;
        }

        double xDif = x2 - x1;
        double yDif = y1 - y2;
        double angleRad = Math.toRadians(angle);
        double currentAngleRad = Math.toRadians(currentAngle);
        double currentSize = Math.sqrt(xDif * xDif + yDif * yDif);
        double dist = currentSize * Math.cos(angleRad);
        
        double xDist = dist * Math.cos(currentAngleRad + angleRad);
        double yDist = dist * Math.sin(currentAngleRad + angleRad);

        //top left point
        double x3 = x1 - yDif;
        double y3 = y1 - xDif;

        //top right point
        double x4 = x2 - yDif;
        double y4 = y2 - xDif;

        double x5 = x3 + xDist;
        double y5 = y3 - yDist;

        //drawLines(x1, y1, x2, y2, x3, y3, x4, y4);

        Path2D currentSquare = createSquare(x1, y1, x2, y2, x3, y3, x4, y4);
        g.setColor(foregroundColor);    //draws the square in the foreground color
        g.fill(currentSquare);
        g.setColor(lineColor);          //draws the line around the perimeter of the square
        g.draw(currentSquare);

        Path2D currentTriangle = createTriangle(x3, y3, x5, y5, x4, y4);
        g.setColor(triangleColor);
        g.fill(currentTriangle);
        g.setColor(lineColor);
        g.draw(currentTriangle);

        //draws the next squares
        drawPythagorasTree(g, iteration + 1, x3, y3, x5, y5, currentAngle + angle);
        drawPythagorasTree(g, iteration + 1, x5, y5, x4, y4, currentAngle - 90 + angle);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        /*Image fractal = drawPythagorasTree(g2d, 0, STARTING_X, STARTING_Y, STARTING_X + size, STARTING_Y);
        g2d.drawImage(fractal, 0, 0, this);*/
        //g2d.drawImage(this.lines, 0, 0, this);
        drawPythagorasTree(g2d, 0, STARTING_X, STARTING_Y + size, STARTING_X + size, STARTING_Y + size, 0);
    }

    void setBackgroundColor(Color newBgColor) {
        this.backgroundColor = newBgColor;
        this.setBackground(backgroundColor);
        this.repaint();
        System.out.println("Background Changed");
    }

    void setForegroundColor(Color newFgColor) {
        int red = newFgColor.getRed();
        int green = newFgColor.getGreen();
        int blue = newFgColor.getBlue();
        Color newColor = new Color (red, green, blue, 127);
        this.foregroundColor = newColor;
        this.repaint();
        System.out.println("Foreground Changed");
    }

    void setTriangleColor(Color newTgColor) {
        int red = newTgColor.getRed();
        int green = newTgColor.getGreen();
        int blue = newTgColor.getBlue();
        Color newColor = new Color (red, green, blue, 127);
        this.triangleColor = newColor;
        this.repaint();
        System.out.println("Triangle Changed");
    }

    void setLineColor(Color newLnColor) {
        this.lineColor = newLnColor;
        this.repaint();
        System.out.println("Line Color Changed");
    }

    void changeAngle(int newAngle) {
        this.angle = newAngle;
        this.repaint();
        System.out.println("New Angle: " + newAngle);
    }

    void changeIterationNum(int iterations) {
        this.iterations = iterations;
        this.repaint();
        System.out.println("Iterations: " + iterations);
    }
}