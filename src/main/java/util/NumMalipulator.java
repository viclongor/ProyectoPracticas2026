package util;

public class NumMalipulator {
    public static double generate(int min, int max){
        return (Math.random()*max)+min;
    }
    public static int generateInt(int min, int max){
        return (int)(Math.random()*max)+min;
    }
    public static int roundTo5(double num){
        return (int) Math.round(num / 5.0)*5;
    }
}
