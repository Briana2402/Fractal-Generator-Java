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

    ComplexNumber square() {
        double a = this.real * this.real;
        double b = this.imaginary * this.imaginary;
        double c = this.real * this.imaginary;

        return new ComplexNumber(a - b, 2 * c);
    }

    double absoluteValue() {
        return this.real * this.real + this.imaginary * this.imaginary;
    }

    double getReal() {
        return this.real;
    }

    double getImaginary() {
        return this.imaginary;
    }
}