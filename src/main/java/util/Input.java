package util;

import java.util.Scanner;

public class Input {
    public static int getInt(){
        Scanner sc = new Scanner(System.in);
        int num=Integer.MAX_VALUE;
        try{
            num = Integer.parseInt(sc.nextLine());
            return num;
        }catch (Exception e){
        }
        return num;

    }
}
