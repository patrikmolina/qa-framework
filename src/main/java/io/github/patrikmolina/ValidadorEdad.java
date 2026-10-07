package io.github.patrikmolina;


public class ValidadorEdad {


        public static boolean esValida(int edad){
            if (edad >= 18 && edad <=65) {
                return true;
            }else {return false;}
        }
}
