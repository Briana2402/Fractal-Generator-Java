public class ComplexNumber {
    double real;
    double imaginary;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }   

    ComplexNumber addition(ComplexNumber number) {
        double real = number.getReal();
        double imaginary = number.getImaginary();

        return new ComplexNumber(this.real + real, this.imaginary + imaginary);
    }

    ComplexNumber multiplication(ComplexNumber number) {
        double real = number.getReal();
        double imaginary = number.getImaginary();

        double a = this.real * real;
        double b = this.real * imaginary;
        double c = this.imaginary * real;
        double d = this.imaginary * imaginary;

        return new ComplexNumber(a - d, b + c);
    }

    double absoluteValue() {
        double real = this.getReal();
        double imaginary = this.getImaginary();

        return Math.hypot(real, imaginary);
    }

    double getReal() {
        return this.real;
    }

    double getImaginary() {
        return this.imaginary;
    }
}