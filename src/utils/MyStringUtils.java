package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyStringUtils {
    public static String QuotedStr(String input) {
        String ret = "'"+input.replace("'","''")+"'";

        return ret;
    }

    public static String dateToStrDDMMYYYY(Date dt) {
        String ret = "";

        SimpleDateFormat formatDateJava = new SimpleDateFormat("dd/MM/yyyy");
        ret = formatDateJava.format(dt);

        return ret;
    }





    public static void main(String[] args){
        System.out.println(QuotedStr("a'01"));
    }
}
