package com.elenakliuchka.repairagency.util;

import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ValidationUtils {

    private ValidationUtils() {
    }
    
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static boolean isValidPhone(String phone) {

        String patternString = "\\+380\\s?[\\(]{0,1}[0-9]{2}[\\)]{0,1}\\s?\\d{3}[-]{0,1}\\d{2}[-]{0,1}\\d{2}";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 6 || password.length() > 15) {
            return false;
        }
        return true;
    }
    public static boolean isValidName(String name) {
        if (name ==null || name.length() > 45) {
            return false;
        }
        return true;
    }
    
    public static String validateNameAndPassword(String name, String password) {        
        if(name==null || name.isEmpty()) {
           return  "Please fill all fields";
        }
        if(password==null ||password.isEmpty()) {
           return  "Please fill all fields";
        }
        if(!ValidationUtils.isValidPassword(password)) {           
            return "Wrong password";
        }        
        if(!ValidationUtils.isValidName(name)) {       
            return "Name is invalid";
        }
        return "";
    }
    
    public static String validateEmail(String email) {
        if(email==null ||email.isEmpty()) {          
            return "Please fill all fields";
        }
        
        if(!ValidationUtils.isValidEmailAddress(email)) {      
            return "Wrong email";
        }
        return "";
    }
    
    public static String validatePhone(String phone) {        
        if(phone==null ||phone.isEmpty()) {            
            return  "Please fill all fields";
        }
        if(!ValidationUtils.isValidPhone(phone)) {          
            return "Wrong phone.";
        }
        return "";
    }
}
