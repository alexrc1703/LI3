package com.grupo19.Interfaces;

public interface IClient {
    /**
     * dá o codigo do cliente
     *
     * @return string string de cliente
     */
    String getCodigo();


    /**
     * verifica se um codigo de produto é valido
     *
     * @return boolean
     */
    boolean isValid();


    /**
     * cria um clone de um cliente
     *
     * @return cliente
     */
    IClient clone();


    /**
     * retorna o numero de filiais em que o cliente comprou
     *
     * @return inteiro
     */
    int NumDiffProductsBought();


    /**
     * adiciona um cliente que comprou
     *
     * @param filial  inteiro de filial
     * @param product string de produto
     */
    void updateClientBought(int filial, String product);


    /**
     * verifica se um cliente alguma vez comprou
     *
     * @return boolean
     */
    boolean hasClientEverBought();
}
