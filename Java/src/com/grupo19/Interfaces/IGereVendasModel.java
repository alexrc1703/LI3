package com.grupo19.Interfaces;


import java.util.List;
import java.util.Map;


public interface IGereVendasModel {
    /**
     * dá o catalogo de produtos
     *
     * @return IcatProd
     */
    ICatProd getCatProd();


    /**
     * dá as filias
     *
     * @return filiais
     */
    IFilial[] getFiliais();


    /**
     * dá a facturacao
     *
     * @return facturacao
     */
    IFacturacao getFacturacao();

    /**
     * Metodo para dar resposta a query 2
     *
     * @param x      numero de clientes
     * @param filial numero da filial em questao
     * @return tuple de inteiro inteiro
     */
    ITuple<Integer, Integer> totalNumbOfSalesInMonthAndClientsBought(int x, int filial);

    /**
     * dá o nome do ficheiro de vendas
     *
     * @return string
     */
    String getFichVendas();

    /**
     * define o nome do ficheiro de vendas
     *
     * @param fichVendas ficheiro de vendas
     */
    void setFichVendas(String fichVendas);

    /**
     * dá a informacao estatica
     *
     * @return estatisticas
     */
    IEstatisticas getEstatatistica();

    /**
     * dá o tempo que demorou a carregar os dados
     *
     * @return double
     */
    double getTimeOfLoadData();

    /**
     * define o tempo que demorou a carregar os dados
     *
     * @param time tempo
     */
    void setTimeOfLoadData(double time);

    /**
     * dá o catalogo de clientes
     *
     * @return ICatClient
     */
    ICatClient getCatClient();


    /**
     * lista de produtos que ninguem comprou
     *
     * @return lista
     */
    List<IProduct> productsNoOneBoughtModel();


    List<IClient> listOfClientsThatBoughtInAllFilials();

    List<IClient> listOfClientsThatDBoughtInAllFilials();


    /**
     * dá a lista de produtos comprados por um cliente
     *
     * @param a codigo de cliente
     * @return lista
     */
    List<ITuple<String, Integer>> getListOfProductsBoughtOfClient(String a);


    /**
     * dá a lista de produtos comprados por um cliente
     *
     * @param a codigo de cliente
     * @return lista
     */
    List<ITuple<Integer, Integer>> totalPurchasesOfAClientPerYear(String a);


    /**
     * totol faturado por um cliente num dado month
     *
     * @param a   codigo de cliente
     * @param mes mes
     * @return double
     */
    double totalFaturadoPClientPMonth(String a, int mes);


    /**
     * dá a lista de cliente que mais compraram
     *
     * @return lista
     */
    List<List<String>> getListOfClientsWhoMostBought();


    /**
     * dá os clientes que compraram mais
     *
     * @param x x clientes
     * @return lista
     */
    List<ITuple<String, Integer>> getClientsWhoBoughtMostOften(int x);


    /**
     * dá o numero de clientes e a facturaçao
     *
     * @param client cod cliente
     * @return lista
     */
    List<List<Double>> getNumClientAndFacturacao(String client);


    /**
     * produtos que mais venderam e o numero dos clientes distintos que o compraram
     *
     * @param n n clientes
     * @return lista
     */
    List<ITuple<String, Integer>> productsMostSellAndNumberOfClients(int n);


    /**
     * facturaçao por produto por filial e por mes
     *
     * @param prod cod produto
     * @return lista
     */
    List<List<Double>> facturacaoPerProdPerFilialPerMonth(String prod);


    /**
     * salva o estado do programa na app
     *
     * @param fichObject nome do ficheiro
     */
    void saveState(String fichObject);


    /**
     * da os X clientes que mais compraram um dado produto
     *
     * @param produto codigo de produto
     * @param tamanho tamanho definido pelo utilizador
     * @return lista
     */
    List<Map.Entry<String, ITuple<Integer, Double>>> getXClientsWhoMostBoughtProduct(String produto, int tamanho);


    /**
     * atualiza a info estatica
     */
    void updateStaticInfo();

}
