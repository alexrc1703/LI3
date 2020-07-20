package com.grupo19.Interfaces;

public interface ISale {
    /**
     * metdodo que devolve o codigo de produto
     *
     * @return String codigo de produto
     */
    String getProduct();


    /**
     * metodo que devolve o codigo de cliente
     *
     * @return String codigo de cliente
     */
    String getClient();


    /**
     * metodo que devolve o codigo do tipo de preco(N ou P)
     *
     * @return String tipo de preço
     */
    String getSaleType();

    /**
     * metodo que determina o mes da venda
     *
     * @return Int de Mes
     */
    int getMonth();


    /**
     * metodo que determina a filial da venda
     *
     * @return Int de Filial
     */
    int getFilial();


    /**
     * metodo que determina o número de unidades por venda
     *
     * @return Int de unidades de venda
     */
    int getUnits();


    /**
     * metodo que determina o preco do produto
     *
     * @return Double preco do produto
     */
    double getPrice();


    /**
     * metodo que determina o valor total de venda(preco*unidades)
     *
     * @return Double  valor de venda
     */
    double totalPrice();


    /**
     * metodo que verifica se uma venda é válida, comparando com os codigos de produto e clientes dos respetivos catálogos
     *
     * @param iCatProd   instância de ICatProd
     * @param iCatClient instância de ICatClient
     * @return boolean
     */
    boolean isValid(ICatProd iCatProd, ICatClient iCatClient);

    /**
     * metodo de clone de uma venda
     *
     * @return um clone de venda
     */
    ISale clone();
}
