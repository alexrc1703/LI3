package com.grupo19.Models;

import com.grupo19.Interfaces.IFilial;
import com.grupo19.Interfaces.ISale;
import com.grupo19.Interfaces.ITuple;
import com.grupo19.Tuple;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Filial implements IFilial, Serializable {

    private Map<String, List<List<ISale>>> filialData;


    /**
     * construtor vazio da filial
     */
    public Filial() {

        this.filialData = new HashMap<>();

    }

    /**
     * construtor parametrizado da filial
     *
     * @param a filial
     */
    public Filial(IFilial a) {
        this.filialData = a.getFilialData();
    }


    /**
     * faz um clone da filial
     *
     * @return IFilial
     */
    public IFilial clone() {
        return new Filial(this);
    }

    /**
     * metodo que faz o get da filial completamente clonado
     *
     * @return tmp
     */
    public Map<String, List<List<ISale>>> getFilialData() {
        Map<String, List<List<ISale>>> tmp = new HashMap<>();
        for (Map.Entry<String, List<List<ISale>>> mapa : this.filialData.entrySet()) {
            tmp.put(mapa.getKey(), cloneOfLists(mapa.getValue()));
        }
        return tmp;
    }


    /**
     * metodo para clonar a lista de listas de ISales
     *
     * @param init list a clonar
     * @return nova lista
     */
    private List<List<ISale>> cloneOfLists(List<List<ISale>> init) {
        List<List<ISale>> res = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            res.add(i, init.get(i).stream().map(ISale::clone).collect(Collectors.toList()));
        }
        return res;
    }

    /**
     * metodo que adiciona uma sala a filial
     *
     * @param a venda a adicionar
     */
    public void add(ISale a) {
        if (!this.filialData.containsKey(a.getClient())) {
            List<List<ISale>> lista = new ArrayList<>(12);
            for (int i = 0; i < 12; i++) {
                List<ISale> s = new ArrayList<>(4);
                lista.add(s);
            }
            this.filialData.put(a.getClient(), lista);
        }
        List<List<ISale>> monthArr = this.filialData.get(a.getClient());
        List<ISale> saleArr = monthArr.get(a.getMonth() - 1);
        saleArr.add(a.clone());

    }


    /**
     * total de faturacao
     *
     * @return double
     */
    public double FacturacaoTotal() {
        double facturacao = 0;
        for (Map.Entry<String, List<List<ISale>>> mapa : this.filialData.entrySet()) {
            for (int i = 0; i < 12; i++) {
                for (ISale sale : mapa.getValue().get(i)) {
                    facturacao += sale.totalPrice();
                }
            }
        }
        return facturacao;
    }


    /**
     * facturacao de um mes
     *
     * @param mes mes
     * @return facturacao
     */
    public double FaturacaoPorMes(int mes) {
        double facturacao = 0;
        for (Map.Entry<String, List<List<ISale>>> mapa : this.filialData.entrySet()) {
            for (ISale sale : mapa.getValue().get(mes)) {
                facturacao += sale.totalPrice();
            }
        }
        return facturacao;
    }


    /**
     * Número de distintos clientes que compraram em cada mês
     *
     * @return lista de inteiros correspondente aos 12 meses
     */
    public int[] DiferentClientsWhoBought() {
        List<Set<String>> lista = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            lista.add(new HashSet<>());
        }
        for (Map.Entry<String, List<List<ISale>>> mapa : this.filialData.entrySet()) {
            for (int i = 0; i < 12; i++) {
                for (ISale sale : mapa.getValue().get(i)) {
                    lista.get(i).add(sale.getClient());
                }
            }
        }
        int[] tmp = new int[12];
        for (int i = 0; i < 12; i++) {
            tmp[i] = lista.get(i).size();
        }
        return tmp;
    }

    /**
     * numero de sales por mes
     *
     * @return arr
     */
    public int[] numberOfSalesPerMonth() {
        int[] arr = new int[12];

        for (Map.Entry<String, List<List<ISale>>> mapa : this.filialData.entrySet()) {
            for (int i = 0; i < 12; i++) {
                arr[i] += mapa.getValue().get(i).size();
            }
        }
        return arr;
    }


    /**
     * (query 2) metodo que diz quantas vendas ouve num mes e quantos clintes distintos compraram
     *
     * @param x numero pedido pelo utilizador
     * @return Tuple
     */
    public ITuple<Integer, Integer> totalNumbOfSalesInMonthAndClientsBought(int x) {
        if (x > 11 || x < 0) {
            return null;
        }
        int res = 0;
        Set<String> client = new HashSet<>();
        for (Map.Entry<String, List<List<ISale>>> lista : this.filialData.entrySet()) {
            List<ISale> tmp = lista.getValue().get(x);
            res += tmp.size();
            for (ISale sale : tmp) {
                client.add(sale.getClient());

            }
        }
        return new Tuple<>(res, client.size());
    }


    /**
     * metodo que da o total faturado por um cliente num dado mes
     *
     * @param client cod cliente
     * @param month  mes
     * @return sum
     */
    public double totalFaturadoPerClientPerMonth(String client, int month) {
        double sum = 0.0;
        List<ISale> tmp = this.filialData.get(client).get(month);
        for (ISale sale : tmp) {
            sum = sale.totalPrice();
        }
        return sum;
    }


    /**
     * (query 3)metodo que determina os produtos distintos comprados por um cliente num dado mes
     * e o numero de compras
     *
     * @param cliente cod cliente
     * @param mes     mes
     * @return Tuple
     */
    public ITuple<Integer, Set<String>> numOfDifferentProductsOfClientAndNumOfSales(String cliente, int mes) {
        if (mes > 11 || mes < 0) {
            return null;
        }
        List<ISale> tmp = this.filialData.get(cliente).get(mes);
        Set<String> rep = new HashSet<>();
        for (ISale sale : tmp) {
            rep.add(sale.getProduct());
        }
        return new Tuple<>(tmp.size(), rep);
    }


    /**
     * (query 5) lista de codigos dos produtos que comporu por ordem decrescente de quantidade
     * e para quantos iguais por ordem alfabetica
     *
     * @param client cod cliente
     * @return mapa
     */
    public Map<String, Integer> getListOfProductsBoughtOfClient(String client) {
        Map<String, Integer> mapa = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            for (ISale sale : this.filialData.get(client).get(i)) {
                if (!mapa.containsKey(sale.getProduct())) {
                    mapa.put(sale.getProduct(), sale.getUnits());
                } else {
                    int tmp = mapa.get(sale.getProduct());
                    tmp += sale.getUnits();
                    mapa.put(sale.getProduct(), tmp);
                }
            }
        }

        return mapa;
    }


    /**
     * (query 7)determinar a lista de tres maiores compradores em termos de dinheiro faturado
     *
     * @return Lista com as strings correspondentes ao codigo do cliente
     */
    public List<String> getListOfClientsWhoMostBought() {
        TreeSet<ITuple<String, Double>> tmp = new TreeSet<>(((o1, o2) -> o2.getSecondElem().compareTo(o1.getSecondElem())));
        for (Map.Entry<String, List<List<ISale>>> lista : this.filialData.entrySet()) {
            double fact = 0.0;
            for (int i = 0; i < 12; i++) {
                for (ISale sale : lista.getValue().get(i)) {
                    fact += sale.totalPrice();
                }
            }
            tmp.add(new Tuple<>(lista.getKey(), fact));
        }
        List<String> res = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            ITuple<String, Double> tuple = tmp.pollFirst();
            if (tuple != null) res.add(tuple.getFirstElem());
        }
        return res;
    }


}
