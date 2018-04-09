package com.superlamer.Numbers;

public class Numbers {

    private int number;

    public Numbers(int number) {
        this.number = number;
    }

    public boolean isSquare() {
        double squareRoot = Math.sqrt(number);

        if (squareRoot == Math.floor(squareRoot))
            return true;
        else
            return false;
    }

    public boolean isTriangular(int num) {

        if (!isNumberValid(num)) return false;

        int sum = 0;

        for (int n = 1; n <= num; n++) {
            sum += n;
            if (sum == num) return true;
        }

        return false;
    }


    private boolean isNumberValid(int num) {
        if (num < 0) return false;
        switch (num % 10) {
            case 2:
            case 3:
            case 7:
            case 8:
                return false;
        }

        return true;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
