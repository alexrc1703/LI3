package com.grupo19.Models;

import com.grupo19.GereVendasModel;
import com.grupo19.Interfaces.IProduct;

import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product implements IProduct, Serializable {
    private String codigo;
    private int[] boughtPerFilial;
    private boolean[] boughtOnFilial;

    public Product() {
        this.codigo = "";
        boughtPerFilial = new int[GereVendasModel.getNumFiliais()];
        boughtOnFilial = new boolean[GereVendasModel.getNumFiliais()];
    }


    /**
     * construtor de um produto
     *
     * @param a string de codigo
     */
    public Product(String a) {
        this.codigo = a;
        boughtPerFilial = new int[GereVendasModel.getNumFiliais()];
        boughtOnFilial = new boolean[GereVendasModel.getNumFiliais()];
    }

    /**
     * construtor de cópia
     *
     * @param x instância de produto
     */
    public Product(Product x) {
        this.codigo = x.getCodigo();
        boughtPerFilial = new int[x.boughtPerFilial.length];
        boughtOnFilial = new boolean[x.boughtOnFilial.length];
        System.arraycopy(x.boughtPerFilial, 0, this.boughtPerFilial, 0, x.boughtPerFilial.length);
        System.arraycopy(x.boughtOnFilial, 0, this.boughtOnFilial, 0, x.boughtOnFilial.length);
    }

    /**
     * verifica se o produto foi vendido em alguma filial
     *
     * @return valor de verdade
     */
    public boolean isProductEverBought() {
        boolean tmp = false;
        for (boolean a : this.boughtOnFilial) {
            if (tmp) break;
            tmp = a;
        }
        return tmp;
    }


    /**
     * determina o codigo de produto
     *
     * @return String de produto
     */
    public String getCodigo() {
        return this.codigo;
    }


    /**
     * verifica a validade de um produto
     *
     * @return valor de verdade
     */
    public boolean isValid() {
        Pattern pattern = Pattern.compile("^[A-Z]{2}[1-9][0-9]{3}$");
        Matcher matcher = pattern.matcher(this.getCodigo());
        return matcher.matches();
    }


    /**
     * atualiza so campos d de filial e quantidade vendida
     *
     * @param filial identifica a filial
     * @param qnt    identifica a quantidade
     */
    public void updateProductBought(int filial, int qnt) {
        boughtOnFilial[filial] = true;
        boughtPerFilial[filial] += qnt;
    }


    /**
     * metodo de clone
     *
     * @return clone de produto
     */
    public Product clone() {
        return new Product(this);
    }


    /**
     * método de Hash para Vendas
     *
     * @return novo codigo de Hash
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.boughtOnFilial, this.codigo, this.boughtPerFilial});
    }


    /**
     * método de equals
     *
     * @param o object
     * @return valor de verdade
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Product p = (Product) o;
        return (this.codigo.equals(p.getCodigo()) && Arrays.equals(p.boughtOnFilial, this.boughtOnFilial) && Arrays.equals(p.boughtPerFilial, this.boughtPerFilial));
    }


    /**
     * Calcula o numero de unidades vendidas
     *
     * @return int numero de unidades vendidas
     */
    public int totalOfUnitsBought() {
        return Arrays.stream(this.boughtPerFilial).sum();
    }


}
