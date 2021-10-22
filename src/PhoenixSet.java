import javax.swing.*;

public class PhoenixSet extends MandelbrotSet {
    //Defining constants
    final static double STARTING_C_REAL = -0.7;
    final static double STARTING_C_IMG = 0.27015;

    double c_real = STARTING_C_REAL;
    double c_imaginary = STARTING_C_IMG;


    Thread t1;
    Thread t2;
    Thread t3;
    Thread t4;

    PhoenixSet(int iterations) {
        super(iterations);
    }

    PhoenixSet(int iterations, int bgColor, int bgColor2, int fgColor, JSplitPane mainScreen) {
        super(iterations, bgColor, bgColor2, fgColor, mainScreen);
    }

    PhoenixSet(int iterations, int bgColor, int bgColor2, int fgColor, JSplitPane mainScreen, double c_real, double c_img) {
        this(iterations, bgColor, bgColor2, fgColor, mainScreen);
        this.c_real = c_real;
        this.c_imaginary = c_img;
        drawSet();
    }

    PhoenixSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, JSplitPane mainScreen) {
        super(iterations, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen);
    }

    PhoenixSet(int iterations, double moveX, double moveY, double minX, double maxX, double minY, double maxY, int bgColor1, int bgColor2, int fgColor, JSplitPane mainScreen, double c_real, double c_img) {
        this(iterations, moveX, moveY, minX, maxX, minY, maxY, bgColor1, bgColor2, fgColor, mainScreen);
        this.c_real = c_real;
        this.c_imaginary = c_img;
        drawSet();
    }

    @Override
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

    public void setCReal(double c_real) {
        this.c_real = c_real;
    }

    public void setCImg(double c_img) {
        this.c_imaginary = c_img;
    }

    public double getCReal() {
        return this.c_real;
    }

    public double getCImg() {
        return this.c_imaginary;
    }
}