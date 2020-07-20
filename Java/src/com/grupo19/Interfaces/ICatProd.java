package com.grupo19.Interfaces;

import java.util.List;

public interface ICatProd {

    /**
     * adiciona um produto ao catalogo de produtos
     *
     * @param product produto a inserir
     */
    void add(IProduct product);


    /**
     * dá a lista de n tamanho com os produtos mais vendidos
     *
     * @param n numero de produtos a consultar
     * @return lista de produtos
     */
    List<String> productsMostSell(int n);


    /**
     * verifica se um dado produto existe no catalogo de produtos
     *
     * @param codProd string de produto
     * @return boolean
     */
    boolean contains(String codProd);


    /**
     * atualiza um produto que foi comprado numa dada filial numa certa quantidade
     *
     * @param codProd string de produto
     * @param filial  inteiro de filial
     * @param qnt     inteiro quantidade a inserir
     */
    void updateProductBought(String codProd, int filial, int qnt);


    /**
     * dá a lista de produtos que nunca foram comprados
     *
     * @return neverBought
     */
    List<IProduct> productsNeverBought();
}
