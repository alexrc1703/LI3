package com.grupo19.Models;

import com.grupo19.GereVendasModel;
import com.grupo19.Interfaces.IClient;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements IClient, Serializable {
    private String codigo;
    private Set<String> productBought;
    private boolean[] boughtOnFilial;


    /**
     * construtor vazio de um cliente
     */
    public Client() {
        this.codigo = "";
        productBought = new HashSet<>(70);
        boughtOnFilial = new boolean[GereVendasModel.getNumFiliais()];
    }

    /**
     * construtor parametrizado de um cliente
     *
     * @param s string de cliente
     */
    public Client(String s) {
        this.codigo = s;
        productBought = new HashSet<>(70);
        boughtOnFilial = new boolean[GereVendasModel.getNumFiliais()];
    }

    /**
     * construtor copia de um cliente
     *
     * @param a cliente a inserir
     */
    public Client(Client a) {
        this.codigo = a.getCodigo();
        productBought = new HashSet<>(70);
        productBought.addAll(a.productBought);
        boughtOnFilial = new boolean[GereVendasModel.getNumFiliais()];
        System.arraycopy(a.boughtOnFilial, 0, this.boughtOnFilial, 0, a.boughtOnFilial.length);
    }

    /**
     * adiciona um cliente que comprou
     *
     * @param filial  inteiro filial
     * @param product string de produto
     */
    public void updateClientBought(int filial, String product) {
        this.productBought.add(product);
        boughtOnFilial[filial] = true;
    }

    /**
     * verifica se um cliente alguma vez comprou
     *
     * @return boolean
     */
    public boolean hasClientEverBought() {
        boolean tmp = true;
        for (boolean a : this.boughtOnFilial) {
            if (!tmp) break;
            tmp = a;
        }
        return tmp;
    }

    /**
     * dá o codigo do cliente
     *
     * @return string
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * verifica se um codigo de produto é valido
     *
     * @return boolean
     */
    public boolean isValid() {
        Pattern pattern = Pattern.compile("^[A-Z][1-9][0-9]{3}$");
        Matcher matcher = pattern.matcher(this.getCodigo());
        return matcher.matches();
    }

    /**
     * cria um clone de um cliente
     *
     * @return cliente
     */
    public Client clone() {
        return new Client(this);
    }

    /**
     * retorna o hashCode de um cliente
     *
     * @return inteiro
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.boughtOnFilial, this.codigo});
    }

    /**
     * verifica se dois clientes sao iguais
     *
     * @param o objeto a comparar
     * @return boolean
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Client p = (Client) o;
        return (this.codigo.equals(p.getCodigo()) && Arrays.equals(p.boughtOnFilial, this.boughtOnFilial));

    }

    /**
     * retorna o numero de filiais em que o cliente comprou
     *
     * @return numero de filiais
     */
    public int NumDiffProductsBought() {
        return this.productBought.size();
    }


}
