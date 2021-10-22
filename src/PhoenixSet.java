/**
 * @author Iliyan Teofilov
 * @ID 1671952. Group2b
 */

import javax.swing.*;

public class PhoenixSet extends MandelbrotSet {
    //Defining constants
    final static double STARTING_C_REAL = 0.5667;
    final static double STARTING_C_IMG = 0;
    final static double STARTING_P_REAL = -0.5;
    final static double STARTING_P_IMG = 0;
    final static int SCREEN_X = 800;
    final static int SCREEN_Y = 600;

    double c_real = STARTING_C_REAL;
    double c_img = STARTING_C_IMG;

    double p_real = STARTING_P_REAL;
    double p_img = STARTING_P_IMG;

    Thread t1;
    Thread t2;
    Thread t3;
    Thread t4;

    /**
     * Phoenix set constructor. Creates a new Phoenix Set.
     * @param iterations number of iterations
     */
    PhoenixSet(int iterations) {
        super(iterations);
    }

    /**
     * Phoenix set constructor. Creates a new Phoenix Set.
     * @param iterations number of iterations
     * @param bgColor background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color 
     * @param mainScreen main screen
     */
    PhoenixSet(int iterations, int bgColor, int bgColor2, int fgColor, JSplitPane mainScreen) {
        super(iterations, bgColor, bgColor2, fgColor, mainScreen);
    }

    /**
     * Phoenix set constructor. Creates a new Phoenix Set.
     * @param iterations number of iterations
     * @param bgColor background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color 
     * @param mainScreen main screen
     * @param c_real constant real value
     * @param c_img constant imaginary value
     */
    PhoenixSet(int iterations, int bgColor, int bgColor2, int fgColor, JSplitPane mainScreen, double c_real, double c_img) {
        this(iterations, bgColor, bgColor2, fgColor, mainScreen);
        this.c_real = c_real;
        this.c_img = c_img;
        drawSet();
    }

    /**
     * Phoenix set constructor. Creates a new Phoenix Set.
     * @param iterations number of iterations
     * @param moveX movement in X
     * @param moveY movement in Y
     * @param minX minimum X value
     * @param maxX maximum X value
     * @param minY minimum Y value
     * @param maxY maximum Y value
     * @param bgColor background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color 
     * @param mainScreen main screen
     */
    PhoenixSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, JSplitPane mainScreen) {
        super(iterations, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen);
    }

    /**
     * Phoenix set constructor. Creates a new Phoenix Set.
     * @param iterations number of iterations
     * @param moveX movement in X
     * @param moveY movement in Y
     * @param minX minimum X value
     * @param maxX maximum X value
     * @param minY minimum Y value
     * @param maxY maximum Y value
     * @param bgColor background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color 
     * @param mainScreen main screen
     * @param c_real constant real value
     * @param c_img constant imaginary value
     * @param p_real multiplying constant real value
     * @param p_img multiplying constant imaginary value
     * @param screenX screen width
     * @param screenY screen height
     */
    PhoenixSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, JSplitPane mainScreen, double c_real, double c_img, double p_real, double p_img, int screenX, int screenY) {
        this(iterations, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen);
        this.c_real = c_real;
        this.c_img = c_img;
        this.p_real = p_real;
        this.p_img = p_img;
        this.screenX = screenX;
        this.screenY = screenY;
        this.xPixel = (maxX - minX) / (screenX - 1); 
        this.yPixel = (maxY - minY) / (screenY - 1);
        initialise();
    }

    /**
     * Phoenix set constructor. Creates a new Phoenix Set.
     * @param iterations number of iterations
     * @param moveX movement in X
     * @param moveY movement in Y
     * @param minX minimum X value
     * @param maxX maximum X value
     * @param minY minimum Y value
     * @param maxY maximum Y value
     * @param bgColor background color
     * @param bgColor2 background color 2
     * @param fgColor foreground color 
     * @param mainScreen main screen
     * @param c_real constant real value
     * @param c_img constant imaginary value
     * @param p_real multiplying constant real value
     * @param p_img multiplying constant imaginary value
     * @param screenX screen width
     * @param screenY screen height
     * @param isInfinite whether we are iterating to infiinity
     */
    PhoenixSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, JSplitPane mainScreen, double c_real, double c_img, double p_real, double p_img, int screenX, int screenY, boolean isInfinite) {
        this(iterations, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen, c_real, c_img, p_real, p_img, screenX, screenY);
        this.infinite = isInfinite;
        
    }

    @Override
    public int determineColor(int x, int y) {
        //we create a new complex number, where the "vertical part" of the screen is the imaginary part
        //we create the number Z which is used for the phoenix set formula. it is the real and imaginary part reversed
        double z_x = maxY - y * yPixel + moveY;
        double z_y = minX + x * xPixel + moveX;        

        double z_x_prev = z_x;
        double z_y_prev = z_y;
        
        int iteration;

        for (iteration = 0; iteration < this.iterations; ++iteration) {
            if (z_x * z_x - z_y * z_y > 4) { //if the complex number is larger than 2, it diverges
                break; //we end the for loop
            }

            //we square the complex number and we add the constants
            double tmp_x = z_x;
            double tmp_y = z_y;
            z_x = z_x * z_x - z_y * z_y + this.c_real + this.p_real * z_x_prev - this.p_img * z_y_prev;
            z_y = 2 * tmp_x * z_y + this.c_img + this.p_real * z_y_prev + this.p_img * z_x_prev;
            z_x_prev = tmp_x;
            z_y_prev = tmp_y;
        }

        return (iteration == this.iterations) ? this.foregroundColor : this.backgroundColors[iteration];
    }

    @Override
    public void incrementIterations() {
        int currentIterations = getIterations();
        currentIterations += ITERATION_INCREMENTATION;
        PhoenixSet newPhoenixSet =
        new PhoenixSet(currentIterations,
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
        this.c_real,
        this.c_img,
        this.p_real,
        this.p_img,
        this.screenX,
        this.screenY,
        true);
        mainScreen.setRightComponent(newPhoenixSet);
    }

    public void setCReal(double c_real) {
        this.c_real = c_real;
    }

    public void setCImg(double c_img) {
        this.c_img = c_img;
    }

    public double getCReal() {
        return this.c_real;
    }

    public double getCImg() {
        return this.c_img;
    }

    public double getPReal() {
        return this.p_real;
    }

    public double getPImg() {
        return this.p_img;
    }
}