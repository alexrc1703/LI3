package com.grupo19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class testeVendas {
    /**
     * metodo main
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        List<String> lines;
        Crono.start();
        lines = lerLinhasWithBuff("Vendas_1M.txt");
        out.println(Crono.stop());
        out.println(lines.size());
        out.println("SEGUNDO TESTE:");
        Crono.start();
        lerAllLines("Vendas_1M.txt");
        out.println(Crono.stop());
    }

    /**
     * le linha por linha de ficheiro para uma list de strings
     *
     * @param fichtxt ficheiro de texto
     * @return lista das linhas lidas do ficheiro
     */
    public static List<String> lerLinhasWithBuff(String fichtxt) {
        List<String> linhas = new ArrayList<>();
        BufferedReader inFile;
        String linha;
        try {
            inFile = new BufferedReader((new FileReader(fichtxt)));
            while ((linha = inFile.readLine()) != null) linhas.add(linha);
        } catch (IOException exc) {
            out.println(exc);

        }
        return linhas;
    }

    /**
     * le o ficheiro de uma vez
     *
     * @param fichtxt ficheiro de texto
     * @return lista das linhas lidas do ficheiro
     */
    public static List<String> lerAllLines(String fichtxt) {
        List<String> linhas = new ArrayList<>();
        try {
            linhas = Files.readAllLines(Paths.get(fichtxt));
        } catch (IOException exc) {
            out.println(exc);
        }
        return linhas;
    }
}


