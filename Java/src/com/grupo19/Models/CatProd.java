package com.grupo19.Models;

import com.grupo19.Interfaces.ICatProd;
import com.grupo19.Interfaces.IProduct;
import com.grupo19.Interfaces.ITuple;
import com.grupo19.Tuple;

import java.io.Serializable;
import java.util.*;


public class CatProd implements ICatProd, Serializable {
    private Map<String, IProduct> mapOfProds;

    /**
     * contrutor vazio do CatProd
     */
    public CatProd() {
        mapOfProds = new HashMap<>();
    }

    /**
     * adiciona um produto ao catalogo de produtos
     *
     * @param product produto a inserir
     */
    public void add(IProduct product) {
        mapOfProds.put(product.getCodigo(), product.clone());
    }


    /**
     * verifica se um dado produto existe no catalogo de produtos
     *
     * @param product string de produto
     * @return boolean
     */
    public boolean contains(String product) {
        return mapOfProds.containsKey(product);
    }


    /**
     * atualiza um produto que foi comprado numa dada filial numa certa quantidade
     *
     * @param product string de produto
     * @param filial  inteiro de filial
     * @param qnt     inteiro quantidade a inserir
     */
    public void updateProductBought(String product, int filial, int qnt) {
        IProduct prod = mapOfProds.get(product);
        prod.updateProductBought(filial - 1, qnt);
    }

    /**
     * dá a lista de produtos que nunca foram comprados
     *
     * @return neverBought
     */
    public List<IProduct> productsNeverBought() {
        List<IProduct> neverBought = new ArrayList<>();
        for (Map.Entry<String, IProduct> entry : this.mapOfProds.entrySet()) {
            IProduct prod = entry.getValue();
            if (!prod.isProductEverBought()) neverBought.add(prod);
        }
        return neverBought;
    }


    /**
     * dá a lista de n tamanho com os produtos mais vendidos
     *
     * @param n numero de produtos
     * @return lista dos produtos
     */
    public List<String> productsMostSell(int n) {
        TreeSet<ITuple<String, Integer>> tupleList = new TreeSet<>(new Comparator<ITuple<String, Integer>>() {
            @Override
            public int compare(ITuple<String, Integer> o1, ITuple<String, Integer> o2) {
                return o1.getSecondElem().compareTo(o2.getSecondElem());
            }
        });
        List<String> res = new ArrayList<>(n);
        for (Map.Entry<String, IProduct> entry : this.mapOfProds.entrySet()) {
            IProduct tmpProd = entry.getValue();
            tupleList.add(new Tuple<>(tmpProd.getCodigo(), tmpProd.totalOfUnitsBought()));
        }
        for (int i = 0; i < n; i++) {
            ITuple<String, Integer> tmp = tupleList.pollLast();
            if (tmp != null) res.add(tmp.getFirstElem());
        }
        return res;
    }


}
