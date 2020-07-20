package com.grupo19;

import com.grupo19.Interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador
 */
public class GereVendasController implements IGereVendasController {
    private IGereVendasModel model;
    private IGereVendasView view;

    /**
     * define o modelo do controler
     *
     * @param model model
     */
    public void setModel(IGereVendasModel model) {
        this.model = model;
    }

    /**
     * define o view do controler
     *
     * @param view view
     */
    public void setView(IGereVendasView view) {
        this.view = view;

    }

    /**
     * query 1
     */
    private void query1() {
        Crono.start();
        List<IProduct> tmp = model.productsNoOneBoughtModel();
        view.setTimeQueue(Crono.stop());
        view.addToStringBrowser(tmp.stream().map(IProduct::getCodigo).sorted(String::compareTo).collect(Collectors.toList()));
        view.setRow(5);
        view.setCol(10);
        view.updateMenu(Menu.STRINGBROWSER);
        view.updateView();
    }


    /**
     * query 2
     */
    private void query2() {
        int month = GereVendasView.getMonthFromInput() - 1;
        Crono.start();
        StringBuilder sb = new StringBuilder();
        sb.append("Para o mês ").append(month + 1).append(" :").append("\n");
        int totalSum = 0;
        for (int i = 0; i < GereVendasModel.getNumFiliais(); i++) {
            ITuple<Integer, Integer> tmp = model.totalNumbOfSalesInMonthAndClientsBought(month, i);
            if (tmp == null) continue;
            totalSum += tmp.getFirstElem();
            sb.append("Na Filial ").append(i + 1).append(" foram feitas ").append(tmp.getFirstElem()).append(" vendas por ").append(tmp.getSecondElem()).append(" clientes diferentes").append("\n");
        }
        sb.append("Dá um total de ").append(totalSum).append(" vendas neste mês\n");
        view.setTimeQueue(Crono.stop());
        view.showLine(sb.toString());


    }


    /**
     * query 3
     */
    private void query3() {
        String client = GereVendasView.getUserInputString("Insira um código de cliente");
        Crono.start();
        List<ITuple<Integer, Integer>> tmp = model.totalPurchasesOfAClientPerYear(client);
        if (tmp == null) {
            view.setTimeQueue(Crono.stop());
            view.showLine("Cliente não existe");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            ITuple<Integer, Integer> tmpTuple = null;
            if (tmp.size() > i) tmpTuple = tmp.get(i);
            if (tmpTuple == null) continue;
            sb.append("Mês ")
                    .append(i + 1)
                    .append(" :")
                    .append("   Fez ").append(tmpTuple.getFirstElem())
                    .append(" compras de ")
                    .append(tmpTuple.getSecondElem())
                    .append(" produtos diferentes e gastou ")
                    .append(model.totalFaturadoPClientPMonth(client, i)).append("\n");
        }
        view.setTimeQueue(Crono.stop());
        view.showLine(sb.toString());
    }

    /**
     * query 4
     */
    private void query4() {
        String l = GereVendasView.getUserInputString("Insira o código do Produto:");
        Crono.start();
        List<List<Double>> lista = model.getNumClientAndFacturacao(l);
        view.setTimeQueue(Crono.stop());
        StringBuilder sb = new StringBuilder();
        if (lista.isEmpty()) {
            view.setTimeQueue(Crono.stop());
            view.showLine("Não existe esse produto!");
            return;
        }
        for (int i = 0; i < 12; i++) {
            if (lista.get(i).isEmpty()) {
                sb.append("Mês ").append(i + 1).append(": O Produto ").append(l).append(" não foi comprado neste mês").append("\n");
                continue;
            }
            sb.append("Mês ")
                    .append(i + 1)
                    .append(": O Produto ")
                    .append(l).append(" foi comprado ")
                    .append(Math.round(lista.get(i).get(0))).append(" vez(es),por ")
                    .append(Math.round(lista.get(i).get(1))).append(" clientes diferentes")
                    .append(" e facturou :").append(lista.get(i).get(2))
                    .append("\n");

        }

        view.showLine(sb.toString());
    }


    /**
     * query 5
     */
    private void query5() {
        String l = GereVendasView.getUserInputString("Insira o código de Cliente:");
        Crono.start();
        List<ITuple<String, Integer>> tmp = model.getListOfProductsBoughtOfClient(l);
        view.setTimeQueue(Crono.stop());
        List<String> stringList = new ArrayList<>(20);
        for (ITuple tuple : tmp) {
            stringList.add(tuple.getFirstElem() + " " + String.format("%03d", (int) tuple.getSecondElem()));
        }
        view.addToStringBrowser(stringList);
        view.setRow(5);
        view.setCol(10);
        view.updateMenu(Menu.STRINGBROWSER);
        view.updateView();
    }

    /**
     * query 6
     */
    private void query6() {
        int n = GereVendasView.getUserInputInt("Insira o  número de vezes: ");
        Crono.start();
        List<ITuple<String, Integer>> tmp = model.productsMostSellAndNumberOfClients(n);
        view.setTimeQueue(Crono.stop());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            ITuple<String, Integer> tuple = tmp.get(i);
            sb.append(i + 1).append("º - Produto ").append(tuple.getFirstElem()).append(" foi comprado por ").append(tuple.getSecondElem()).append(" clientes\n");
        }
        view.showLine(sb.toString());
    }


    /**
     * query 7
     */
    private void query7() {
        Crono.start();
        List<List<String>> lista = model.getListOfClientsWhoMostBought();
        view.setTimeQueue(Crono.stop());
        StringBuilder sb = new StringBuilder(256);
        for (int i = 0; i < GereVendasModel.getNumFiliais(); i++) {
            sb.append("Filial")
                    .append(i + 1)
                    .append(" :")
                    .append(lista.get(i))
                    .append("\n");
        }
        view.showLine(sb.toString());

    }


    /**
     * query 8
     */
    private void query8() {
        int x = GereVendasView.getUserInputInt("Insira o número de clientes pretendido:");
        Crono.start();
        StringBuilder sb = new StringBuilder();
        List<ITuple<String, Integer>> lista = model.getClientsWhoBoughtMostOften(x);
        view.setTimeQueue(Crono.stop());
        for (int i = 0; i < x; i++) {
            sb.append("Cliente ")
                    .append(lista.get(i).getFirstElem())
                    .append(" comprou:")
                    .append(lista.get(i).getSecondElem()).append(" vez(es)")
                    .append("\n");

        }
        view.showLine(sb.toString());
    }


    /**
     * query 9
     */
    private void query9() {
        String l = GereVendasView.getUserInputString("Insira o código de Produto:");
        int tamanho = GereVendasView.getUserInputInt("Insira o número de clientes que deseja ver:");
        Crono.start();
        List<Map.Entry<String, ITuple<Integer, Double>>> lista = model.getXClientsWhoMostBoughtProduct(l, tamanho);
        view.setTimeQueue(Crono.stop());
        StringBuilder sb = new StringBuilder();
        if (lista.isEmpty()) sb.append("Não existe esse produto");
        for (int i = 0; i < lista.size(); i++) {
            sb.append("O clinte ").append(lista.get(i).getKey()).append(" comprou o produto ")
                    .append(lista.get(i).getValue().getFirstElem())
                    .append(" vez(es) e gastou um total de ")
                    .append(lista.get(i).getValue().getSecondElem())
                    .append(" \n");
        }
        view.showLine(sb.toString());

    }


    /**
     * query 10
     */
    private void query10() {
        String prod = GereVendasView.getUserInputString("Insira o código de Produto:");
        List<String> stringList = new ArrayList<>();
        stringList.add("Filiais");
        for (int j = 1; j <= 12; j++) {
            if (j > 9) {
                stringList.add("Mês " + j);
                continue;
            }
            stringList.add("Mês  " + j);
        }
        Crono.start();
        List<List<Double>> tmp = model.facturacaoPerProdPerFilialPerMonth(prod);
        view.setTimeQueue(Crono.stop());
        for (int i = 0; i < GereVendasModel.getNumFiliais(); i++) {
            stringList.add("Filial " + (i + 1));
            for (int j = 0; j < 12; j++) {
                List<Double> doubleList = tmp.get(j);
                if (doubleList.isEmpty()) {
                    stringList.add(String.format("%010.2f", ((Double) 0.0)));
                    continue;
                }
                stringList.add(String.format("%010.2f", doubleList.get(i)));
            }
        }
        view.addToStringBrowser(stringList);
        view.setCol(GereVendasModel.getNumFiliais() + 1);
        view.setRow(13);
        view.updateMenu(Menu.STRINGBROWSER);
        view.updateView();
    }


    /**
     * Metodo que reage ao imput dado pelo utilizador
     */
    private void reactToInput(int choice) {
        switch (choice) {
            case 1:
                query1();
                break;
            case 2:
                query2();
                break;
            case 3:
                query3();
                break;
            case 4:
                query4();
                break;
            case 5:
                query5();
                break;
            case 6:
                query6();
                break;
            case 7:
                query7();
                break;
            case 8:
                query8();
                break;
            case 9:
                query9();
                break;
            case 10:
                query10();
                break;
            case 11:
                Crono.start();
                model.saveState("data.tmp");
                view.setTimeQueue(Crono.stop());
                view.showLine("Dados Guardados");
                break;
            default:
                break;

        }
    }


    /**
     * metodo que inicia o controller
     */
    public void init() {
        model.updateStaticInfo();
        view.setTimeQueue(model.getTimeOfLoadData());
        view.showInfoView(model.getFichVendas(), model.getEstatatistica());
        do {
            if (view.getCurrentMenu() == Menu.MAINMENU) view.setTimeQueue(model.getTimeOfLoadData());
            view.updateView();
            reactToInput(view.getChoice());
        }
        while (!view.exit());
    }


}
