package br.com.example.wallpark.utils;

public class ValidInteger {
    public boolean isValidInteger(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
