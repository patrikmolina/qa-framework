package io.github.patrikmolina;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidadorEdadTest {
    @Test
    void edadNegativa(){
        boolean resultado = ValidadorEdad.esValida(-1);
        assertFalse(resultado);
    }

    @Test
    void edadLimiteMenor(){
        boolean resultado = ValidadorEdad.esValida(17);
        assertFalse(resultado);
    }

    @Test
    void edadLimiteayor(){
        boolean resultado = ValidadorEdad.esValida(66);
        assertFalse(resultado);
    }

    @Test
    void edadMenor(){
        boolean resultado = ValidadorEdad.esValida(18);
        assertTrue(resultado);
    }

    @Test
    void edadMayor(){
        boolean resultado = ValidadorEdad.esValida(65);
        assertTrue(resultado);
    }
}
