package com.grupo19;


/**
 * @author asus
 * <p>
 * Crono = mede um tempo entre start() e stop()
 * O tempo e medido em nanosegundos e convertido para
 * um double que representa os segs na sua parte inteira.
 * @author FMM
 * @version (a version number or a date)
 */
/**
 * Crono = mede um tempo entre start() e stop()
 * O tempo e medido em nanosegundos e convertido para
 *  um double que representa os segs na sua parte inteira.
 *
 * @author FMM
 * @version (a version number or a date)
 */

import static java.lang.System.nanoTime;

public class Crono {

    private static long inicio = 0L;
    private static long fim = 0L;

    /**
     * construtor vazio do crono
     */
    public static void start() {
        fim = 0L;
        inicio = nanoTime();
    }

    /**
     * metodo que para o contador do tempo
     * @return double
     */
    public static double stop() {
        fim = nanoTime();
        long elapsedTime = fim - inicio;
        // segundos
        return elapsedTime / 1.0E09;
    }


    /**
     * da retorno do tempo que passou
     * @return string
     */
    public static String print() {
        return "" + stop();
    }
}