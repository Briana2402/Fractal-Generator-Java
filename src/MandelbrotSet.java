/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.stream.IntStream;

public class MandelbrotSet extends JPanel {
    //Defining constants
    final static int STARTING_ITERATIONS = 100;
    final static int STARTING_INF_ITERATIONS = 800;
    final static int STARTING_BG_COLOR = colorToHex(Color.DARK_GRAY);
    final static int STARTING_BG2_COLOR = colorToHex(Color.RED);
    final static int STARTING_FG_COLOR = colorToHex(Color.ORANGE);
    final static int ITERATION_INCREMENTATION = 1;
    final static double STARTING_MIN_X = -2.5;
    final static double STARTING_MAX_X =  1.5;
    final static double STARTING_MIN_Y = -1.5;
    final static double STARTING_MAX_Y = 1.5;
    
    //Defining variables used by the whole class
    int screenX = 800;
    int screenY = 600;
    double moveX = 0;
    double moveY = 0;
    double minX = STARTING_MIN_X;
    double maxX = STARTING_MAX_X;
    double minY = STARTING_MIN_Y;
    double maxY = STARTING_MAX_Y;
    double xPixel; //the x coordinate in a graph corresponding to a pixel in screen space
    double yPixel; //the y coordinate in a graph corresponding to a pixel in screen space
    int iterations;
    int amountOfSections;
    int backgroundColor = STARTING_BG_COLOR;
    int backgroundColor2 = STARTING_BG2_COLOR;
    int foregroundColor = STARTING_FG_COLOR;
    int[] backgroundColors;

    int mouseX = 0;
    int mouseY = 0;

    boolean infinite;

    BufferedImage fractal;

    JSplitPane mainScreen;

    Thread t1;
    Thread t2;
    Thread t3;
    Thread t4;

    /**
     * Mandelbrot set constructor. Creates new Mandelbrot set.
     * @param iterations number of iterations
     */
    MandelbrotSet(int iterations) {
        this.infinite = (iterations == 0);
        this.iterations = (this.infinite) ? STARTING_INF_ITERATIONS : iterations;
        this.amountOfSections = this.iterations;
        this.backgroundColors = new int[this.amountOfSections + 1];
        this.backgroundColors[0] = backgroundColor;
        this.xPixel = (maxX - minX) / (screenX - 1); 
        this.yPixel = (maxY - minY) / (screenY - 1);
        this.addComponentListener(new ResizeListener());
        this.addMouseListener(new MouseManipulation());
        this.addMouseWheelListener(new MouseManipulation());
        this.addMouseMotionListener(new MouseManipulation());
        initialise();
    }

    /**
     * Mandelbrot set constructor. Creates new Mandelbrot set.
     * @param iterations number of iterations
     * @param bgColor background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color
     * @param mainScreen main screen
     */
    MandelbrotSet(int iterations, int bgColor, int bgColor2, int fgColor, JSplitPane mainScreen) {
        this(iterations);
        this.backgroundColor = bgColor;
        this.backgroundColor2 = bgColor2;
        this.foregroundColor = fgColor;
        this.mainScreen = mainScreen;
        determineColorForLevel(backgroundColor2, backgroundColor);
        drawSet();
    }

    /**
     * Mandelbrot set constructor. Creates new Mandelbrot set.
     * @param iterations number of iterations
     * @param moveX movement in X
     * @param moveY movement in Y
     * @param minX minimum X
     * @param maxX maximum X
     * @param minY minimum Y
     * @param maxY maximum Y
     * @param bgColor1 background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color 
     * @param mainScreen main screen
     */
    MandelbrotSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, JSplitPane mainScreen) {
        this(iterations, bgColor1, bgColor2, fgColor, mainScreen);
        this.moveX = moveX;
        this.moveY = moveY;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.xPixel = (maxX - minX) / (screenX - 1); 
        this.yPixel = (maxY - minY) / (screenY - 1);
        drawSet();
    }

    /**
     * Mandelbrot set constructor. Creates new Mandelbrot set.
     * @param iterations number of iterations
     * @param moveX movement
     * @param moveX movement in X
     * @param moveY movement in Y
     * @param minX minimum X
     * @param maxX maximum X
     * @param minY minimum Y
     * @param maxY maximum Y
     * @param bgColor1 background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color 
     * @param mainScreen main screen
     * @param screenX screen width
     * @param screenY screen height
     * @param isInfinite whether we are iterating infinitely
     */
    MandelbrotSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, JSplitPane mainScreen, int screenX, int screenY, boolean isInfinite) {
        this(iterations, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen);
        this.infinite = isInfinite;
        this.screenX = screenX;
        this.screenY = screenY;
        this.xPixel = (maxX - minX) / (screenX - 1); 
        this.yPixel = (maxY - minY) / (screenY - 1);
        initialise();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fractal, 0, 0, this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.setPreferredSize(new Dimension(screenX, screenY));
    }

    void initialise() {
        determineColorForLevel(backgroundColor2, backgroundColor);
        this.setPreferredSize(new Dimension(screenX, screenY));
        this.setSize(new Dimension(screenX, screenY));
        fractal = new BufferedImage(screenX, screenY, BufferedImage.TYPE_INT_RGB);
        drawSet();
    }

    /**
     * Draws the set. Divides the load in 4 threads.
     */
    public void drawSet() {
        try {
            t1 = new Thread(() -> drawSetUpLeft());
            t2 = new Thread(() -> drawSetUpRight());
            t3 = new Thread(() -> drawSetDownRight());
            t4 = new Thread(() -> drawSetDownLeft());
            t1.start();
            t2.start();
            t3.start();
            t4.start();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception with " + this.iterations + " iterations.");
        }
    }

    /**
     * Draws the top left quarter of the screen
     */
    private void drawSetUpLeft() {
        //IntStream used for multithreading
        IntStream.range(0, screenX / 2).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(0, screenY / 2).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    /**
     * Draws the top right quarter of the screen
     */
    private void drawSetUpRight() 
    {
        //IntStream used for multithreading
        IntStream.range(screenX / 2 - 1, screenX).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(0, screenY / 2).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    /**
     * Draws the bottom right quarter of the screen
     */
    private void drawSetDownRight() 
    {
        //IntStream used for multithreading
        IntStream.range(screenX / 2 - 1, screenX).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(screenY / 2 - 1, screenY).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    /**
     * Draws the bottom left quarter of the screen
     */
    private void drawSetDownLeft() 
    {
        //IntStream used for multithreading
        IntStream.range(0, screenX / 2).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(screenY / 2 - 1, screenY).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    /**
     * Determines the color of the point
     * @param x x coordinate of point
     * @param y y coordinate of point
     * @return the rgb color value
     */
    public int determineColor(int x, int y) {
        //we create a new complex number, where the "vertical part" of the screen is the imaginary part
        double z_x = minX + x * xPixel + moveX;
        double z_y = maxY - y * yPixel + moveY;
        //we create the number Z which is used for the mandelbrot set formula
        double c_x = z_x;
        double c_y = z_y;

        int iteration;
        for (iteration = 0; iteration < this.iterations; ++iteration) {
            if (z_x * z_x + z_y * z_y > 4) { //if the complex number is larger than 2, it diverges
                break; //we end the for loop
            }

            //we square the complex number and we add the constant
            double tmp = z_x;
            z_x = z_x * z_x - z_y * z_y + c_x;
            z_y = 2 * tmp * z_y + c_y;
        }

        return (iteration == this.iterations) ? this.foregroundColor : this.backgroundColors[iteration];
    }

    /**
     * Creates the color gradient between 2 colors.
     * @param color1 first color
     * @param color2 second color
     */
    public void determineColorForLevel(int color1, int color2) {
        int red1 = (color1 >> 16) & 0xFF;
        int green1 = (color1 >> 8) & 0xFF;
        int blue1 = (color1) & 0xFF;

        int red2 = (color2 >> 16) & 0xFF;
        int green2 = (color2 >> 8) & 0xFF;
        int blue2 = (color2) & 0xFF;

        for (int i = 1; i <= amountOfSections; ++i) {
            double p = (double) i / (double) amountOfSections;
            double finalRed = p * (red1 - red2) + red2;
            double finalGreen = p * (green1 - green2) + green2;
            double finalBlue = p * (blue1 - blue2) + blue2;
            int finalColor = ((int) finalRed & 0xFF) << 16 | ((int) finalGreen & 0xFF) << 8 | ((int) finalBlue & 0xFF);
            backgroundColors[i] = finalColor;
        }
        backgroundColors[0] = backgroundColor;
    }

    public void zoom(int pixels) {
        this.maxX = this.minX + (xPixel * (double) (screenX - pixels));
        this.minX += xPixel * (double) pixels;
        this.maxY = this.minY + (yPixel * (screenY - ((double) pixels * screenY / screenX)));
        this.minY += yPixel * (double) pixels * screenY / screenX;
        this.xPixel = (maxX - minX) / (screenX - 1); 
        this.yPixel = (maxY - minY) / (screenY - 1);
        if (infinite && pixels > 0) {
            incrementIterations();
        }
        drawSet();
        this.repaint();
    }

    public void moveFractal(double dx, double dy) {
        this.moveX -= 2 * dx * xPixel;
        this.moveY += 2 * dy * yPixel;
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

    public void setDimensions(int newWidth, int newHeight) {
        int dx = newWidth - this.screenX;
        int dy = newHeight - this.screenY;
        this.minX = this.minX - (xPixel * dx);
        this.maxY = this.maxY + (yPixel * dy);
        this.screenX = newWidth;
        this.screenY = newHeight;
        this.xPixel = (maxX - minX) / (screenX - 1); 
        this.yPixel = (maxY - minY) / (screenY - 1);
        initialise();
    }

    public void setMousePoint(Point newPoint) {
        this.mouseX = (int) newPoint.getX();
        this.mouseY = (int) newPoint.getY();
    }

    public void incrementIterations() {
        int currentIterations = getIterations();
        currentIterations += ITERATION_INCREMENTATION;
        MandelbrotSet newMandelbrotSet =
        new MandelbrotSet(currentIterations,
        this.moveX,
        this.moveY,
        this.minX,
        this.maxX,
        this.minY, 
        this.maxY,
        this.backgroundColor,
        this.backgroundColor2,
        this.foregroundColor,
        this.mainScreen,
        this.screenX,
        this.screenY,
        true);
        mainScreen.setRightComponent(newMandelbrotSet);
    }

    public Point getMousePoint() {
        return new Point(this.mouseX, this.mouseY);
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
    }

    public BufferedImage getFractal() {
        return this.fractal;
    }

    public int getIterations() {
        return this.iterations;
    }

    public int getBackgroundColor(int bg) {
        return (bg == 0) ? this.backgroundColor : this.backgroundColor2;
    }

    public int getForegroundColor() {
        return this.foregroundColor;
    }

    public int[] getBackgroundColors() {
        return this.backgroundColors;
    }

    public double getMoveX() {
        return this.moveX;
    }

    public double getMoveY() {
        return this.moveY;
    }

    public double getXPixel() {
        return this.xPixel;
    }

    public double getYPixel() {
        return this.yPixel;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMaxY() {
        return this.maxY;
    }
}