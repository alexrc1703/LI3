package com.grupo19.Interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IFilial {

    /**
     * metodo que adiciona uma sala a filial
     *
     * @param sale sale a inserir
     */
    void add(ISale sale);

    /**
     * Método de clone da filial
     *
     * @return clone de filial
     */
    IFilial clone();


    /**
     * metodo que da o total faturado por um cliente num dado mes
     *
     * @param client String de cliente
     * @param month  Int do mes
     * @return sum
     */
    double totalFaturadoPerClientPerMonth(String client, int month);

    /**
     * (query 3)metodo que determina os produtos distintos comprados por um cliente num dado mes
     * e o numero de compras
     *
     * @param cliente codigo de cliente
     * @param mes     mes
     * @return Tuple
     */
    ITuple<Integer, Set<String>> numOfDifferentProductsOfClientAndNumOfSales(String cliente, int mes);

    /**
     * (query 2) metodo que diz quantas vendas ouve num mes e quantos clintes distintos compraram
     *
     * @param x numero obtido pelo input do utilizador
     * @return Tuple
     */
    ITuple<Integer, Integer> totalNumbOfSalesInMonthAndClientsBought(int x);


    /**
     * metodo que faz o get da filial completamente clonado
     *
     * @return tmp
     */
    Map<String, List<List<ISale>>> getFilialData();

    /**
     * (query 5) lista de codigos dos produtos que comporu por ordem decrescente de quantidade
     * e para quantos iguais por ordem alfabetica
     *
     * @param client codigo de cliente
     * @return mapa
     */
    Map<String, Integer> getListOfProductsBoughtOfClient(String client);


    /**
     * (query 7)determinar a lista de tres maiores compradores em termos de dinheiro faturado
     *
     * @return list com o codigo dos clientes que mais compraram
     */
    List<String> getListOfClientsWhoMostBought();


    /**
     * facturacao de um mes
     *
     * @param mes mes
     * @return facturacao
     */
    double FaturacaoPorMes(int mes);

    /**
     * numero de sales por mes
     *
     * @return arr
     */
    int[] numberOfSalesPerMonth();

    /**
     * array com os diferentes clientes que compraram mes a mes
     *
     * @return lista
     */
    int[] DiferentClientsWhoBought();


}
