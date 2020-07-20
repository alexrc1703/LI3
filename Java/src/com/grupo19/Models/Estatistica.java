package com.grupo19.Models;


import com.grupo19.GereVendasModel;
import com.grupo19.Interfaces.IEstatisticas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Estatistica implements IEstatisticas, Serializable {

    private int numVendasValidas;
    private int numVendasTotal;
    private int numProdutosTotal;
    private int numClientesTotal;
    private int numTotalProdutosComprados;
    private int numClientesNaoCompraram;
    private int numTotalDeComprasValorNulo;
    private double facturacaoTotal;
    private List<double[]> factPerMonth;
    private List<int[]> numberOfSalesPerMonth;
    private List<int[]> diffClientsNumber;

    /**
     * Construtor da estatistica
     */
    public Estatistica() {
        int numFiliais = GereVendasModel.getNumFiliais();
        factPerMonth = new ArrayList<>(numFiliais);
        numberOfSalesPerMonth = new ArrayList<>(numFiliais);
        diffClientsNumber = new ArrayList<>(numFiliais);
    }

    public void updateFactPerMonth(double[] factPerMonth) {
        this.factPerMonth.add(factPerMonth);
    }

    public void updateNumberOfSalesPerMonth(int[] numberOfSalesPerMonth) {
        this.numberOfSalesPerMonth.add(numberOfSalesPerMonth);
    }

    public void updateDiffClientsNumber(int[] diffClientsNumber) {
        this.diffClientsNumber.add(diffClientsNumber);
    }

    /**
     * Get da faturacao por mes
     *
     * @return list das filiais com a faturacao por mes
     */
    public List<double[]> getFactPerMonth() {
        return new ArrayList<>(factPerMonth);
    }

    /**
     * Get Do numero de sales por mes
     *
     * @return list das filiais com o numero de sales por mes
     */
    public List<int[]> getNumberOfSalesPerMonth() {
        return new ArrayList<>(numberOfSalesPerMonth);
    }

    /**
     * Get do numero de clientes diferentes
     *
     * @return list das filiais com o numero de sales por mes
     */
    public List<int[]> getDiffClientsNumber() {
        return new ArrayList<>(diffClientsNumber);
    }

    /**
     * setter para o numero total de produtos
     *
     * @param numProdutosTotal numero de produtos total
     */
    public void setNumProdutosTotal(int numProdutosTotal) {
        this.numProdutosTotal = numProdutosTotal;
    }

    /**
     * setter para o numero total de clientes
     *
     * @param numClientesTotal numero de clientes total
     */
    public void setNumClientesTotal(int numClientesTotal) {
        this.numClientesTotal = numClientesTotal;
    }

    /**
     * setter para o numero total de vendas
     *
     * @param numVendasTotal numero de vendas total
     */
    public void setNumVendasTotal(int numVendasTotal) {
        this.numVendasTotal = numVendasTotal;
    }

    /**
     * setter para o numero total de vendas
     *
     * @param numVendasValidas numero de vendas validas
     */
    public void setNumVendasValidas(int numVendasValidas) {
        this.numVendasValidas = numVendasValidas;
    }

    /**
     * getter para o numero total de vendas invalidas
     *
     * @return total de vendas invalidas
     */
    public int getNumVendasInvalidas() {
        return this.numVendasTotal - this.numVendasValidas;
    }

    /**
     * getter para o numero total de Produtos
     *
     * @return total de produtos
     */
    public int getTotalProdNum() {
        return this.numProdutosTotal;
    }

    /**
     * getter para o numero total de produtos comprados
     *
     * @return total de produtos comprados
     */
    public int getNumTotalProdutosComprados() {
        return numTotalProdutosComprados;
    }


    /**
     * setter para o numero total de produtos comprados
     *
     * @param numTotalProdutosComprados numero total de produtos comprados
     */
    public void setNumTotalProdutosComprados(int numTotalProdutosComprados) {
        this.numTotalProdutosComprados = numTotalProdutosComprados;
    }

    /**
     * getter para o numero total de clientes que nao realizaram compras
     *
     * @return numero total de clientes
     */
    public int getNumClientesNaoCompraram() {
        return numClientesNaoCompraram;
    }

    /**
     * setter para o numero total de clientes que nao realizaram compras
     *
     * @param numClientesNaoCompraram numero de clientes que nunca compraram
     */
    public void setNumClientesNaoCompraram(int numClientesNaoCompraram) {
        this.numClientesNaoCompraram = numClientesNaoCompraram;
    }

    /**
     * getter para o numero total de vendas com valor nulo
     *
     * @return numero total de vendas nulas
     */
    public int getNumTotalDeComprasValorNulo() {
        return numTotalDeComprasValorNulo;
    }

    /**
     * setter para o numero total de vendas com valor nulo
     *
     * @param numTotalDeComprasValorNulo numero total de compras valor nulo
     */
    public void setNumTotalDeComprasValorNulo(int numTotalDeComprasValorNulo) {
        this.numTotalDeComprasValorNulo = numTotalDeComprasValorNulo;
    }

    /**
     * getter para a faturacao total
     *
     * @return faturacao total
     */
    public double getFacturacaoTotal() {
        return facturacaoTotal;
    }

    /**
     * setter para a faturacao total
     *
     * @param facturacaoTotal faturaçao total
     */
    public void setFacturacaoTotal(double facturacaoTotal) {
        this.facturacaoTotal = facturacaoTotal;
    }

    /**
     * getter para os produtos nao comprados
     *
     * @return total de produtos nao comprados
     */
    public int getProdNaoComprados() {
        return this.getTotalProdNum() - this.getNumTotalProdutosComprados();
    }


    /**
     * getter para o numero total de clientes
     *
     * @return total de clientes
     */
    @Override
    public int getTotalClientNum() {
        return this.numClientesTotal;
    }
}
