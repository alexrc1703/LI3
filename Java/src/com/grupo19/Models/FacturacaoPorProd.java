package com.grupo19.Models;

import com.grupo19.GereVendasModel;
import com.grupo19.Interfaces.IFacturacaoPorProd;
import com.grupo19.Interfaces.ISale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FacturacaoPorProd implements IFacturacaoPorProd, Serializable {

    /**
     * list de sales de um produto
     */
    private List<ISale> salesList;

    /**
     * construtor por omissao
     */
    public FacturacaoPorProd() {
        this.salesList = new ArrayList<>();
    }

    /**
     * construtor de cópia
     */
    private FacturacaoPorProd(IFacturacaoPorProd fact) {

        this.salesList = fact.getSalesList();
    }

    /**
     * Devolve o total das sales do prod (qnt*preco)
     *
     * @return total
     */
    public double totalSaleProd() {
        double total = 0;
        for (ISale s : salesList) {
            total += s.getUnits() * s.getPrice();
        }
        return total;
    }

    /**
     * getter da list de sales
     *
     * @return list de sales
     */

    public List<ISale> getSalesList() {
        return new ArrayList<>(this.salesList);
    }


    /**
     * construtor de clone
     *
     * @return clone da IFacturacao
     */
    public IFacturacaoPorProd clone() {
        return new FacturacaoPorProd(this);
    }

    /**
     * adiciona uma ISale a list
     *
     * @param s sale a inserir
     */
    public void addSale(ISale s) {

        salesList.add(s.clone());
    }


    /**
     * metodo equals
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        IFacturacaoPorProd fact = (FacturacaoPorProd) obj;
        for (ISale s : fact.getSalesList()) {
            if (!salesList.contains(s)) return false;
        }
        return true;
    }


    /**
     * Numero de diferentes clientes que realizaram compras
     *
     * @return double res
     */
    public double getDifClientsWhoBought() {
        HashSet<String> lista = new HashSet<>();
        double res;
        for (ISale a : this.salesList) {
            lista.add(a.getClient());
        }
        res = (double) lista.size();
        return res;
    }


    /**
     * Calcula a facturacao por filial
     *
     * @return list por filial
     */
    public List<Double> factPerFilial() {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < GereVendasModel.getNumFiliais(); i++) {
            res.add(0.0);
        }
        for (ISale s : this.salesList) {
            res.add(s.getFilial() - 1, s.totalPrice());
        }
        return res;
    }


    /**
     * @return retorna o número de diferentes clientes que compraram neste mês
     */
    public int getDiffClients() {
        Set<String> set = new HashSet<>();
        for (ISale s : this.salesList) {
            set.add(s.getClient());
        }
        return set.size();
    }
}