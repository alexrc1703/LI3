package com.grupo19.Interfaces;

import java.util.List;

public interface IFacturacaoPorProd {

    /**
     * getter da list de sales
     *
     * @return list de sales
     */
    List<ISale> getSalesList();

    /**
     * adiciona uma ISale a list
     *
     * @param s sale a inserir
     */
    void addSale(ISale s);

    /**
     * construtor de clone
     *
     * @return clone da IFacturacao
     */
    IFacturacaoPorProd clone();


    /**
     * Devolve o total das sales do prod (qnt*preco)
     *
     * @return total
     */
    double totalSaleProd();


    /**
     * Numero de diferentes clientes que realizaram compras
     *
     * @return double res
     */
    double getDifClientsWhoBought();

    /**
     * Calcula a facturacao por filial
     *
     * @return list por filial
     */
    List<Double> factPerFilial();

    /**
     * @return retorna o número de clientes diferentes neste mês
     */
    int getDiffClients();
}