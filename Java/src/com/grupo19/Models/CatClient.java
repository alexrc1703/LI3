package com.grupo19.Models;

import com.grupo19.Interfaces.ICatClient;
import com.grupo19.Interfaces.IClient;
import com.grupo19.Interfaces.ITuple;
import com.grupo19.Tuple;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class CatClient implements ICatClient, Serializable {
    private Map<String, IClient> mapOfClients;

    /**
     * construtor vazio do CatClient
     */
    public CatClient() {
        mapOfClients = new HashMap<>();
    }

    /**
     * adiciona um cliente ao catalogo de clientes
     *
     * @param client cliente a inserir
     */
    public void add(IClient client) {
        mapOfClients.put(client.getCodigo(), client);
    }

    /**
     * verifica se um cliente existe no catalogo de clientes
     *
     * @param codClient string codigo de cliente
     * @return boolean
     */
    public boolean contains(String codClient) {
        return mapOfClients.containsKey(codClient);
    }


    /**
     * atualiza o cliente que comprou um dado produto numa dada filial
     *
     * @param client  string de cliente
     * @param filial  inteiro de filial
     * @param product string de produto
     */
    public void updateClientBought(String client, int filial, String product) {
        IClient clt = mapOfClients.get(client);
        clt.updateClientBought(filial - 1, product);
    }

    /**
     * dá uma lista com todos os clientes que nunca compraram
     *
     * @return tmp
     */
    public List<IClient> clientsNeverBought() {
        List<IClient> tmp = new ArrayList<>();
        for (Map.Entry entry : mapOfClients.entrySet()) {
            IClient tmpClient = (IClient) entry.getValue();
            if (!tmpClient.hasClientEverBought()) tmp.add(tmpClient.clone());
        }
        return tmp;
    }


    /**
     * Lista de clientes que não compraram em todas as filiais
     *
     * @return lista de clientes que não compraram em todas as filiais
     */

    public List<IClient> listOfClientsThatDBoughtInAllFilials() {
        return this.mapOfClients.values().stream().filter(c -> !c.hasClientEverBought()).collect(Collectors.toList());
    }

    /**
     * Lista de clientes que compraram em todas as filiais
     *
     * @return lista de clientes que compraram em todas as filiais
     */
    public List<IClient> listOfClientsThatBoughtInAllFilials() {
        return this.mapOfClients.values().stream().filter(IClient::hasClientEverBought).collect(Collectors.toList());
    }


    /**
     * Lista de X(numero dado) clientes que mais compraram
     *
     * @param n numero de clientes a devolver
     * @return res lista resultante
     */
    public List<ITuple<String, Integer>> listOfClientsWhoBoughtMost(int n) {
        TreeSet<ITuple<String, Integer>> set = new TreeSet<>(new Comparator<ITuple<String, Integer>>() {
            @Override
            public int compare(ITuple<String, Integer> o1, ITuple<String, Integer> o2) {
                if (o1.getSecondElem().equals(o2.getSecondElem()))
                    return o2.getFirstElem().compareTo(o1.getFirstElem());
                return o1.getSecondElem().compareTo(o2.getSecondElem());
            }
        });
        for (Map.Entry<String, IClient> entry : this.mapOfClients.entrySet()) {
            set.add(new Tuple<>(entry.getKey(), entry.getValue().NumDiffProductsBought()));
        }
        List<ITuple<String, Integer>> res = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            ITuple<String, Integer> tuple = set.pollLast();
            if (tuple != null) res.add(tuple);
        }
        return res;
    }

}
