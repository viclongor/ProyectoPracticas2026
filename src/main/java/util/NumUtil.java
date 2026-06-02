package util;

public class NumUtil {
    public static double generate(int min, int max){
        return (Math.random()*(max-min))+min;
    }
    public static int generateInt(int min, int max){
        return (int)(Math.random()*(max-min))+min;
    }    public static int roundTo5(double num){
        return (int) Math.round(num / 5.0)*5;
    }
    public static int generateDamage(int armor){
        int dice1 = generateInt(1,6);
        int dice2 = generateInt(1,6);
        int dice3 = generateInt(1,6);
        int dice4 = generateInt(1,6);
        int result = (dice1 + dice2 + dice3 + dice4) - armor;
        if(armor>=result){
            return 0;
        }else{
            return result;
        }
    }
}
