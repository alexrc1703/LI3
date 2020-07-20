package com.grupo19.Interfaces;

public interface IProduct {

    /**
     * determina o codigo de produto
     *
     * @return String de produto
     */
    String getCodigo();

    /**
     * verifica a validade de um produto
     *
     * @return valor de verdade
     */
    boolean isValid();

    /**
     * metodo de clone
     *
     * @return clone de produto
     */
    IProduct clone();


    /**
     * atualiza so campos d de filial e quantidade vendida
     *
     * @param filial identifica a filial
     * @param qnt    identifica a quantidade
     */
    void updateProductBought(int filial, int qnt);


    /**
     * verifica se o produto foi vendido em alguma filial
     *
     * @return valor de verdade
     */
    boolean isProductEverBought();


    /**
     * calcula as unidades vendidas
     *
     * @return int do valor total
     */
    int totalOfUnitsBought();
}
