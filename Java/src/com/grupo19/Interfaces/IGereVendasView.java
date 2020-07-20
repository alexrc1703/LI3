package com.grupo19.Interfaces;

import com.grupo19.Menu;

import java.util.List;

public interface IGereVendasView {

    /**
     * permite percorrer os dados consultados
     */
    void navigate();

    /**
     * altera o modo de visualizacao do menu
     */
    void updateView();

    /**
     * altera o menu
     *
     * @param menu menu
     */
    void updateMenu(Menu menu);

    /**
     * Define quantas linhas o navegador de strings vai ter
     *
     * @param row numero de linhas
     */
    void setRow(int row);

    /**
     * Define quantas colunas o navegador de strings vai ter
     *
     * @param col numero de colunas
     */
    void setCol(int col);

    /**
     * Adiciona uma lista de strings ao navegador de strings
     *
     * @param stringList lista com as strings a adicionar ao stirngsBrowser
     */
    void addToStringBrowser(List<String> stringList);

    /**
     * getter da opcao do menu
     *
     * @return int ( escolha do utilizador)
     */
    int getChoice();

    /**
     * método para fechar o menu
     *
     * @return bool
     */
    boolean exit();

    /**
     * getter do menu
     *
     * @return status do Menu
     */
    Menu getCurrentMenu();

    /**
     * imprime a line no ecra
     *
     * @param line linha a imprimir
     */
    void showLine(String line);


    /**
     * setter para o timeQueue
     *
     * @param timeQueue tempo Da query
     */
    void setTimeQueue(double timeQueue);


    /**
     * converte os dados para string para serem visualizados
     *
     * @param fichName    nome do ficheiro
     * @param estatistica classe wrapper das estatisticas
     */
    void showInfoView(String fichName, IEstatisticas estatistica);

}
