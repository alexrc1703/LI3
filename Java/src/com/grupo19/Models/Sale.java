package com.grupo19.Models;

import com.grupo19.GereVendasModel;
import com.grupo19.Interfaces.ICatClient;
import com.grupo19.Interfaces.ICatProd;
import com.grupo19.Interfaces.ISale;

import java.io.Serializable;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Sale implements ISale, Serializable {

    private String prod, client, saleType;
    private int month, filial, units;
    private double price;


    /**
     * construtor parametrizado
     *
     * @param _codProd   String de codigo de produto
     * @param _price     Double preco
     * @param _units     Int número de unidades
     * @param _saleType  String identificadora do tipo de preco
     * @param _codClient String codigo de cliente
     * @param _month     Int mes da venda
     * @param _filial    Int identificador de filial
     */
    public Sale(String _codProd, double _price, int _units, String _saleType, String _codClient, int _month, int _filial) {
        prod = _codProd;
        price = _price;
        units = _units;
        saleType = _saleType;
        client = _codClient;
        month = _month;
        filial = _filial;
    }


    /**
     * construtor de cópia
     *
     * @param s instancia de uma venda
     */
    public Sale(ISale s) {
        this.prod = s.getProduct();
        this.client = s.getClient();
        this.filial = s.getFilial();
        this.month = s.getMonth();
        this.price = s.getPrice();
        this.units = s.getUnits();
        this.saleType = s.getSaleType();
    }

    /**
     * método que produz uma Venda dada a String da linha do ficheiro vendas
     *
     * @param line String do ficheiro de vendas
     * @return instância de venda nova
     */
    public static ISale readLineToSale(String line) {
        String codProd, codClient, saleType;
        int month, filial, units;
        double price;
        String[] campos = line.split(" ");
        codProd = campos[0];
        codClient = campos[4];
        saleType = campos[3];
        try {
            price = Double.parseDouble(campos[1]);
            units = Integer.parseInt(campos[2]);
            month = Integer.parseInt(campos[5]);
            filial = Integer.parseInt(campos[6]);
        } catch (InputMismatchException exc) {
            return null;
        }
        return new Sale(codProd, price, units, saleType, codClient, month, filial);
    }

    /**
     * metdodo que devolve o codigo de produto
     *
     * @return String codigo de produto
     */
    public String getProduct() {
        return prod;
    }

    /**
     * metodo que devolve o codigo de cliente
     *
     * @return String codigo de cliente
     */
    public String getClient() {
        return client;
    }

    /**
     * metodo que verifica se uma venda é válida, comparando com os codigos de produto e clientes dos respetivos catálogos
     *
     * @param iCatProd   instância de ICatProd
     * @param iCatClient instância de ICatClient
     * @return boolean
     */
    public boolean isValid(ICatProd iCatProd, ICatClient iCatClient) {
        return (iCatProd.contains(getProduct()) &&
                iCatClient.contains(getClient()) &&
                getMonth() > 0 && getMonth() < 13 &&
                getUnits() > 0 && getUnits() < 1000 &&
                getFilial() > 0 && getFilial() < (GereVendasModel.getNumFiliais() + 1) &&
                (getSaleType().equals("N") || getSaleType().equals("P")));

    }

    /**
     * metodo que devolve o codigo do tipo de preco(N ou P)
     *
     * @return String tipo de preço
     */
    public String getSaleType() {
        return saleType;
    }

    public int getMonth() {
        return month;
    }

    public int getFilial() {
        return filial;
    }

    public int getUnits() {
        return units;
    }

    /**
     * metodo que determina o preco do produto
     *
     * @return Double preco do produto
     */
    public double getPrice() {
        return price;
    }

    /**
     * metodo que determina o valor total de venda(preco*unidades)
     *
     * @return Double  valor de venda
     */
    @Override
    public double totalPrice() {
        return this.price * this.units;
    }


    /**
     * método de Hash para Vendas
     *
     * @return novo codigo de Hash
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.client, this.filial, this.month, this.price, this.prod, this.saleType, this.units});
    }


    /**
     * método equals
     *
     * @param o object
     * @return valor de verdade
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Sale)) return false;
        Sale sale = (Sale) o;
        return (sale.getProduct().equals(this.getProduct()) &&
                sale.getClient().equals(this.getClient()) &&
                sale.getPrice() == this.getPrice() &&
                sale.getFilial() == this.getFilial() &&
                sale.getMonth() == this.getFilial() &&
                sale.getSaleType().equals(this.getSaleType()) &&
                sale.getUnits() == this.getUnits());
    }


    /**
     * método de clone de uma venda
     *
     * @return um clone de venda
     */
    public ISale clone() {
        return new Sale(this);
    }
}


