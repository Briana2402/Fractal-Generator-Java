import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.stream.IntStream;

public class MandelbrotSet extends JPanel {
    //Defining constants
    final static int RESOLUTION = 4;
    double MIN_X = -2.5;
    double MAX_X = 1.5;
    double MIN_Y = -1.5;
    double MAX_Y = 1.5;
    
    
    //Defining variables used by the whole class
    int SCREENX = 800;
    int SCREENY = 600;
    double MOVE_X = 0;
    double MOVE_Y = 0;
    double xPixel; //the x coordinate in a graph corresponding to a pixel in screen space
    double yPixel; //the y coordinate in a graph corresponding to a pixel in screen space
    int iterations;
    int AMOUNT_OF_SECTIONS;
    int backgroundColor = colorToHex(Color.DARK_GRAY);
    int backgroundColor2 = colorToHex(Color.RED);
    int[] backgroundColors;
    int foregroundColor = colorToHex(Color.ORANGE);

    int mouseX = 0;
    int mouseY = 0;

    private BufferedImage fractal;

    MandelbrotSet() {
        this.iterations = 100;
        this.AMOUNT_OF_SECTIONS = this.iterations;
        this.backgroundColors = new int[iterations + 2];
        this.backgroundColors[0] = backgroundColor;
        this.backgroundColors[iterations + 1] = backgroundColor2;
        this.xPixel = (MAX_X - MIN_X) / (SCREENX - 1); 
        this.yPixel = (MAX_Y - MIN_Y) / (SCREENY - 1);
        initialise();
        this.addComponentListener(new ResizeListener());
        this.addMouseListener(new MouseManipulation());
        this.addMouseWheelListener(new MouseManipulation());
        this.addMouseMotionListener(new MouseManipulation());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fractal, 0, 0, this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
    }

    public void initialise() {
        determineColorForLevel(backgroundColor2, backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
        fractal = new BufferedImage(SCREENX, SCREENY, BufferedImage.TYPE_INT_RGB);
        drawSet();
    }

    public void drawSet() {
        Thread t1 = new Thread(() -> drawSetUpLeft());
        Thread t2 = new Thread(() -> drawSetUpRight());
        Thread t3 = new Thread(() -> drawSetDownRight());
        Thread t4 = new Thread(() -> drawSetDownLeft());
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public void drawSetUpLeft() {
        //IntStream used for multithreading
        IntStream.range(0, SCREENX / 2).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(0, SCREENY / 2).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    public void drawSetUpRight() 
    {
        //IntStream used for multithreading
        IntStream.range(SCREENX / 2 - 1, SCREENX).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(0, SCREENY / 2).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    public void drawSetDownRight() 
    {
        //IntStream used for multithreading
        IntStream.range(SCREENX / 2 - 1, SCREENX).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(SCREENY / 2 - 1, SCREENY).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    public void drawSetDownLeft() 
    {
        //IntStream used for multithreading
        IntStream.range(0, SCREENX / 2).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(SCREENY / 2 - 1, SCREENY).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    public int determineColor(int x, int y) {
        //we create a new complex number, where the "vertical part" of the screen is the imaginary part
        ComplexNumber number = convertToComplex(x, y);
        //we create the number Z which is used for the mandelbrot set formula
        ComplexNumber z = number; 
        int iteration;
        for (iteration = 0; iteration < this.iterations; ++iteration) {
            if (z.absoluteValue() > 4.0) { //if the complex number is larger than 2, it diverges
                break; //we end the for loop
            }

            z = z.square(); //we square the complex number
            z = z.addition(number); //we add the 'c'
        }

        return (iteration == this.iterations) ? this.foregroundColor : this.backgroundColors[iteration];
    }

    public void determineColorForLevel(int color1, int color2) {
        int red1 = (color1 >> 16) & 0xFF;
        int green1 = (color1 >> 8) & 0xFF;
        int blue1 = (color1) & 0xFF;

        int red2 = (color2 >> 16) & 0xFF;
        int green2 = (color2 >> 8) & 0xFF;
        int blue2 = (color2) & 0xFF;

        for (int i = 1; i <= AMOUNT_OF_SECTIONS; ++i) {
            double p = (double) i / (double) AMOUNT_OF_SECTIONS;
            double finalRed = red1 * p + red2 * (1 - p);
            double finalGreen = green1 * p + green2 * (1 - p);
            double finalBlue = blue1 * p + blue2 * (1 - p);
            int finalColor = ((int) finalRed & 0xFF) << 16 | ((int) finalGreen & 0xFF) << 8 | ((int) finalBlue & 0xFF);
            backgroundColors[i - 1] = finalColor;
        }
    }

    /**
     * Converts the complex number coordinates from screen space into accurate "graphing space"
     * @param x
     * @param y
     * @return
     */
    public ComplexNumber convertToComplex(int x, int y) {
        double real = MIN_X + x * xPixel + MOVE_X;
        double imaginary = MAX_Y - y * yPixel + MOVE_Y;

        return new ComplexNumber(real, imaginary);
    }

    public void zoom(int pixels) {
        this.MAX_X = this.MIN_X + (xPixel * (double) (SCREENX - pixels));
        this.MIN_X += xPixel * (double) pixels;
        this.MAX_Y = this.MIN_Y + (yPixel * (SCREENY - ((double) pixels * SCREENY / SCREENX)));
        this.MIN_Y += yPixel * (double) pixels * SCREENY / SCREENX;
        this.xPixel = (MAX_X - MIN_X) / (SCREENX - 1); 
        this.yPixel = (MAX_Y - MIN_Y) / (SCREENY - 1);

        drawSet();
        this.repaint();
    }

    public void moveFractal(double dx, double dy) {
        this.MOVE_X -= 2 * dx * xPixel;
        this.MOVE_Y += 2 * dy * yPixel;
        drawSet();
        this.repaint();
    }

    private static int colorToHex(Color color) {
        return color.getRGB();
    }

    public void setBackgroundColor(int c, Color newColor) {
        int intNewColor = colorToHex(newColor);
        if (c == 1) {
            this.backgroundColor = intNewColor;
            this.setBackground(newColor);
            determineColorForLevel(backgroundColor2, backgroundColor);
        } else {
            this.backgroundColor2 = intNewColor;
            determineColorForLevel(backgroundColor2, backgroundColor);
        }
        drawSet();
        this.repaint();
    }

    public void setForegroundColor(Color newColor) {
        int intNewColor = colorToHex(newColor);
        this.foregroundColor = intNewColor;
        drawSet();
        this.repaint();
    }

    public void changeIterationNum(int iterations) {
        this.iterations = iterations;
        drawSet();
        this.repaint();
    }

    public void setDimensions(int newWidth, int newHeight) {
        this.SCREENX = newWidth;
        this.SCREENY = newHeight;
        initialise();
    }

    public void setMousePoint(Point newPoint) {
        this.mouseX = (int) newPoint.getX();
        this.mouseY = (int) newPoint.getY();
    }

    public Point getMousePoint() {
        return new Point(this.mouseX, this.mouseY);
    }

    public int getScreenX() {
        return this.SCREENX;
    }

    public int getScreenY() {
        return this.SCREENY;
    }

    public BufferedImage getFractal() {
        return this.fractal;
    }

    public int getIterations() {
        return this.iterations;
    }

    public int getForegroundColor() {
        return this.foregroundColor;
    }

    public int[] getBackgroundColors() {
        return this.backgroundColors;
    }

    public double getMoveX() {
        return this.MOVE_X;
    }

    public double getMoveY() {
        return this.MOVE_Y;
    }

    public double getXPixel() {
        return this.xPixel;
    }

    public double getYPixel() {
        return this.yPixel;
    }
}