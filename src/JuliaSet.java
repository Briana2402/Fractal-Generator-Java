import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class JuliaSet extends JPanel {
    //TODO make the phoenix set extend the mandelbrot set

    //Defining constants
    final static int STARTING_ITERATIONS = 100;
    final static int STARTING_BG_COLOR = colorToHex(Color.DARK_GRAY);
    final static int STARTING_BG2_COLOR = colorToHex(Color.RED);
    final static int STARTING_FG_COLOR = colorToHex(Color.ORANGE);
    final static double STARTING_C_REAL = -0.7;
    final static double STARTING_C_IMG = 0.27015;

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
    int backgroundColor = STARTING_BG_COLOR;
    int backgroundColor2 = STARTING_BG2_COLOR;
    int[] backgroundColors;
    int foregroundColor = STARTING_FG_COLOR;

    int mouseX = 0;
    int mouseY = 0;

    boolean infinite;

    double c_real = STARTING_C_REAL;
    double c_imaginary = STARTING_C_IMG;
    int exponent;

    private BufferedImage fractal;

    Thread t1;
    Thread t2;
    Thread t3;
    Thread t4;

    JuliaSet(int iterations) {
        this.infinite = (iterations == 0);
        this.iterations = (this.infinite) ? 100 : iterations;
        this.AMOUNT_OF_SECTIONS = this.iterations;
        this.backgroundColors = new int[this.AMOUNT_OF_SECTIONS + 1];
        this.backgroundColors[0] = backgroundColor;
        this.xPixel = (MAX_X - MIN_X) / (SCREENX - 1); 
        this.yPixel = (MAX_Y - MIN_Y) / (SCREENY - 1);
        this.addComponentListener(new ResizeListener());
        this.addMouseListener(new MouseManipulation());
        this.addMouseWheelListener(new MouseManipulation());
        this.addMouseMotionListener(new MouseManipulation());
        initialise();
    }

    JuliaSet(int iterations, int bgColor, int bgColor2, int fgColor) {
        this(iterations);
        this.backgroundColor = bgColor;
        this.backgroundColor2 = bgColor2;
        this.foregroundColor = fgColor;
        determineColorForLevel(backgroundColor2, backgroundColor);
        drawSet();
    }

    JuliaSet(int iterations, int bgColor, int bgColor2, int fgColor, double c_real, double c_img) {
        this(iterations, bgColor, bgColor2, fgColor);
        this.c_real = c_real;
        this.c_imaginary = c_img;
        drawSet();
    }

    JuliaSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor) {
        this(iterations, bgColor1, bgColor2, fgColor);
        this.MOVE_X = moveX;
        this.MOVE_Y = moveY;
        this.MIN_X = minX;
        this.MAX_X = maxX;
        this.MIN_Y = minY;
        this.MAX_Y = maxY;
        this.xPixel = (MAX_X - MIN_X) / (SCREENX - 1); 
        this.yPixel = (MAX_Y - MIN_Y) / (SCREENY - 1);
        drawSet();
    }

    JuliaSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, double c_real, double c_img) {
        this(iterations, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor);
        this.c_real = c_real;
        this.c_imaginary = c_img;
        drawSet();
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

    private void initialise() {
        determineColorForLevel(backgroundColor2, backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
        fractal = new BufferedImage(SCREENX, SCREENY, BufferedImage.TYPE_INT_RGB);
        drawSet();
    }

    private void drawSet() {
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

    private void drawSetUpLeft() {
        //IntStream used for multithreading
        IntStream.range(0, SCREENX / 2).parallel().forEach(x -> { //horizontal pixels
            IntStream.range(0, SCREENY / 2).parallel().forEach(y -> { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            });
        });

        this.repaint();
    }

    private void drawSetUpRight() 
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

    private void drawSetDownRight() 
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

    private void drawSetDownLeft() 
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
        //we create the number Z which is used for the phoenix / julia set formula
        double z_x = MIN_X + x * xPixel + MOVE_X;
        double z_y = MAX_Y - y * yPixel + MOVE_Y;
        
        int iteration;
        for (iteration = 0; iteration < this.iterations; ++iteration) {
            if (z_x * z_x - z_y * z_y > 4) { //if the complex number is larger than 2, it diverges
                break; //we end the for loop
            }

            //we square the complex number and we add the constant
            double tmp = z_x;
            z_x = z_x * z_x - z_y * z_y + this.c_real;
            z_y = 2 * tmp * z_y + this.c_imaginary;
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
            double finalRed = p * (red1 - red2) + red2;
            double finalGreen = p * (green1 - green2) + green2;
            double finalBlue = p * (blue1 - blue2) + blue2;
            int finalColor = ((int) finalRed & 0xFF) << 16 | ((int) finalGreen & 0xFF) << 8 | ((int) finalBlue & 0xFF);
            backgroundColors[i] = finalColor;
        }
        backgroundColors[0] = backgroundColor;
    }

    public void zoom(int pixels) {
        this.MAX_X = this.MIN_X + (xPixel * (double) (SCREENX - pixels));
        this.MIN_X += xPixel * (double) pixels;
        this.MAX_Y = this.MIN_Y + (yPixel * (SCREENY - ((double) pixels * SCREENY / SCREENX)));
        this.MIN_Y += yPixel * (double) pixels * SCREENY / SCREENX;
        this.xPixel = (MAX_X - MIN_X) / (SCREENX - 1); 
        this.yPixel = (MAX_Y - MIN_Y) / (SCREENY - 1);
        if (infinite) {
            this.iterations++;
        }
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

    public void setDimensions(int newWidth, int newHeight) {
        int dx = newWidth - this.SCREENX;
        int dy = newHeight - this.SCREENY;
        this.MIN_X = this.MIN_X - (xPixel * dx);
        this.MAX_Y = this.MAX_Y + (yPixel * dy);
        this.SCREENX = newWidth;
        this.SCREENY = newHeight;
        this.xPixel = (MAX_X - MIN_X) / (SCREENX - 1); 
        this.yPixel = (MAX_Y - MIN_Y) / (SCREENY - 1);
        initialise();
    }

    public void setMousePoint(Point newPoint) {
        this.mouseX = (int) newPoint.getX();
        this.mouseY = (int) newPoint.getY();
    }

    public void changeIterationNum(int iterations) {
        this.iterations = iterations;
        drawSet();
        this.repaint();
    }

    public void setCReal(double c_real) {
        this.c_real = c_real;
    }

    public void setCImg(double c_img) {
        this.c_imaginary = c_img;
    }

    public Point getMousePoint() {
        return new Point(this.mouseX, this.mouseY);
    }

    public int getBackgroundColor(int bg) {
        return (bg == 0) ? this.backgroundColor : this.backgroundColor2;
    }

    public int getForegroundColor() {
        return this.foregroundColor;
    }

    public int getIterations() {
        return this.iterations;
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

    public double getMinX() {
        return this.MIN_X;
    }

    public double getMaxX() {
        return this.MAX_X;
    }

    public double getMinY() {
        return this.MIN_Y;
    }

    public double getMaxY() {
        return this.MAX_Y;
    }

    public double getCReal() {
        return this.c_real;
    }

    public double getCImg() {
        return this.c_imaginary;
    }
}