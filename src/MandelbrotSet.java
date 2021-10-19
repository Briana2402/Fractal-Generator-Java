import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotSet extends JPanel {
    //Defining constants
    final static double STARTING_X = -3;
    final static double STARTING_Y = 2;
    final static double DOMAIN = 6;
    final static double RANGE = 4;
    final static int AMOUNT_OF_SECTIONS = 20;
    final static int RESOLUTION = 3;
    
    //Defining variables used by the whole class
    int SCREENX = 800;
    int SCREENY = 600;
    double xPixel; //the x coordinate in a graph corresponding to a pixel in screen space
    double yPixel; //the y coordinate in a graph corresponding to a pixel in screen space
    int iterations;
    int backgroundColor = colorToHex(Color.DARK_GRAY);
    int backgroundColor2 = colorToHex(Color.RED);
    int[] backgroundColors = new int[AMOUNT_OF_SECTIONS];
    int foregroundColor = colorToHex(Color.ORANGE);

    BufferedImage fractal;

    MandelbrotSet() {
        this.iterations = 100;
        initialise();
        this.addComponentListener(new ResizeListener());
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
        xPixel = DOMAIN / (SCREENX * RESOLUTION - 1); 
        yPixel = RANGE / (SCREENY * RESOLUTION - 1);
        determineColorForLevel(backgroundColor2, backgroundColor);
        this.setPreferredSize(new Dimension(SCREENX, SCREENY));
        this.setSize(new Dimension(SCREENX, SCREENY));
        fractal = new BufferedImage(SCREENX, SCREENY, BufferedImage.TYPE_INT_RGB);
        drawSet();
    }

    public void drawSet() {
        for (int x = 0; x < SCREENX; ++x) { //horizontal pixels
            for (int y = 0; y < SCREENY; ++y) { //vertical pixels
                int pixelColor = determineColor(x, y);
                fractal.setRGB(x, y, pixelColor);
            }
        }

        this.revalidate();
        this.repaint();
    }

    public int determineColor(int x, int y) {
        //we create a new complex number, where the "vertical part" of the screen is the imaginary part
        ComplexNumber number = convertToComplex(x, y);
        //we create the number Z which is used for the mandelbrot set formula
        ComplexNumber z = number; 
        int iteration;
        for (iteration = 0; iteration < this.iterations; ++iteration) {
            if (z.absoluteValue() > 2.0) { //if the complex number is larger than 2, it diverges
                break; //we end the for loop
            }

            z = z.multiplication(z); //we square the complex number
            z = z.addition(number); //we add the 'c'
        }

        if (iteration == this.iterations) { //if it converged, its part of the set so we paint it the body color
            return foregroundColor;
        } else {
            if (iteration == 0) {
                return backgroundColor;
            } else if (iteration >= 1 && iteration <= AMOUNT_OF_SECTIONS) {
                return backgroundColors[iteration - 1];
            } else {
                return backgroundColor2;
            }
        }
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
        double real = STARTING_X + x * xPixel;
        double imaginary = STARTING_Y - y * yPixel;

        return new ComplexNumber(real, imaginary);
    }

    public int colorToHex(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        String hex = String.format("0x%02X%02X%02X", red, green, blue);
        //System.out.println(hex);
        int hexColor = Integer.decode(hex);
        return hexColor;
    }

    public void setBackgroundColor(int c, Color newColor) {
        int intNewColor = colorToHex(newColor);
        if (c == 1) {
            this.backgroundColor = intNewColor;
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
}