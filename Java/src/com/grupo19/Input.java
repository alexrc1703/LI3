package com.grupo19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Input {

    /**
     * le strings a partir do input
     *
     * @return String
     */
    public static String lerString() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        String txt = "";
        while (!ok) {
            try {
                txt = input.nextLine();
                ok = true;
            } catch (InputMismatchException e) {
                out.println("Texto Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return txt;
    }

    /**
     * le inteiros a partir do input
     *
     * @return o inteiro que lê
     */
    public static int lerInt() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        int i = 0;
        while (!ok) {
            try {
                i = input.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                out.println("Inteiro Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return i;
    }

    /**
     * le doubles a partir do input
     *
     * @return o double que lê
     */
    public static double lerDouble() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        double d = 0.0;
        while (!ok) {
            try {
                d = input.nextDouble();
                ok = true;
            } catch (InputMismatchException e) {
                out.println("Valor real Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return d;
    }

    /**
     * le floats a partir do input
     *
     * @return o float que lê
     */
    public static float lerFloat() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        float f = 0.0f;
        while (!ok) {
            try {
                f = input.nextFloat();
                ok = true;
            } catch (InputMismatchException e) {
                out.println("Valor real Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return f;
    }

    /**
     * le boolean do input
     *
     * @return o boolean que lê
     */
    public static boolean lerBoolean() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        boolean b = false;
        while (!ok) {
            try {
                b = input.nextBoolean();
                ok = true;
            } catch (InputMismatchException e) {
                out.println("Booleano Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return b;
    }

    /**
     * le um short do input
     *
     * @return o short que lê
     */
    public static short lerShort() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        short s = 0;
        while (!ok) {
            try {
                s = input.nextShort();
                ok = true;
            } catch (InputMismatchException e) {
                out.println("Short Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return s;
    }

    /**
     * le a partin de um ficheiro para uma lista de strings
     *
     * @param fichtxt nome do ficheiro
     * @return lista com as linhas todas do ficheiro
     */
    public static List<String> lerLinhasWithBuff(String fichtxt) {
        List<String> linhas = new ArrayList<>(20000);
        BufferedReader inFile = null;
        String linha;
        try {
            inFile = new BufferedReader((new FileReader(fichtxt)));
            while ((linha = inFile.readLine()) != null) linhas.add(linha);
        } catch (IOException exc) {
            out.println(exc);
        } finally {
            try {
                if (inFile != null) inFile.close();
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
        return linhas;
    }
}
