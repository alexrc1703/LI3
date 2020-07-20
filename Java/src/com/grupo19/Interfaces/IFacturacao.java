package com.grupo19.Interfaces;


import java.util.List;
import java.util.Map;

public interface IFacturacao {
    /**
     * Método de comparacao
     *
     * @param obj objeto a comparar
     * @return Valor de verdade
     */
    boolean equals(Object obj);

    /**
     * Adiciona uma venda à estutura principal da Facturacao
     *
     * @param sale a adicionar
     */
    void add(ISale sale);

    /**
     * Método de Clone
     *
     * @return cópia de uma Facturacao
     */
    IFacturacao clone();

    /**
     * facturacao total
     *
     * @return Valor total de todas as Sales
     */
    double facturacaoTotal();

    /**
     * Calcula a facturacao do mês pretendido
     *
     * @param month mes
     * @return valor faturado
     */
    double valorTotalFactMensal(int month);

    /**
     * @param codProd string id do produto
     * @return Inteiro com todos os clientes que compraram o produto inserido
     */
    int numberOfClientsWhoBought(String codProd);

    /**
     * Calcula o número total de clientes dos dozes meses que compraram o produto
     *
     * @param codProd string id do produto
     * @return list organizada por mes
     */
    List<Integer> numberOfClientsWhoBoughtPerMonth(String codProd);

    /**
     * Método que determina a facturacao por filial e por mês do produto prod
     *
     * @param prod string id do produto
     * @return List organizada por mes
     */
    List<List<Double>> facturacaoPerProdPerFilialPerMonth(String prod);

    /**
     * Método getter da estruta principal da Facturacao
     *
     * @return List ArrayOfSales
     */
    List<Map<String, IFacturacaoPorProd>> getArrayOfSales();

    /**
     * calcula quantas vezes o produto foi comprado, por quantos clientes diferentes e total facturado por mês
     *
     * @param client string do id do produto
     * @return res list
     */
    List<List<Double>> getNumClientAndFacturacao(String client);

    /**
     * Método que determina a facturacao por filial e por mês do produto prod
     *
     * @param produto string id do produto
     * @param tamanho tamanho dado pelo utilizador
     * @return res list ordenada segundo o critério(Ordem Decrescente)
     */
    List<Map.Entry<String, ITuple<Integer, Double>>> getXClientsWhoMostBoughtProduct(String produto, int tamanho);

}


