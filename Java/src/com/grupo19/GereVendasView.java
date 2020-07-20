package com.grupo19;

import com.grupo19.Interfaces.IEstatisticas;
import com.grupo19.Interfaces.IGereVendasView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Classe da View
 */
public class GereVendasView implements IGereVendasView {
    private Menu menu;
    private int choice;
    private List<String> stringBrowser;
    private int cursor;
    private int page;
    private int row;
    private int col;
    private double timeQueue;

    /**
     * Construtor da class GereVendasView
     */
    public GereVendasView() {
        updateMenu(Menu.MAINMENU);
        this.cursor = 0;
        this.page = 0;
        this.col = 10;
        this.row = 5;
        stringBrowser = new ArrayList<>();
    }

    /**
     * Lê o input(String) do user
     *
     * @param s mensagem para o user
     * @return input(string) user
     */
    public static String getUserInputString(String s) {
        out.print(s + " ");
        return Input.lerString();
    }

    /**
     * Lê o input(int) do user
     *
     * @param s mensagem para o user
     * @return input(int) do user
     */
    public static int getUserInputInt(String s) {
        out.print(s + " ");
        return Input.lerInt();
    }

    /**
     * Get de um mês valido (entre 1 e 12)
     *
     * @return um mes valido
     */
    public static int getMonthFromInput() {
        int month = getUserInputInt("Insira o mês:");
        while (month < 1 || month > 12) month = getUserInputInt("Insira um mês válido:");
        return month;
    }

    /**
     * Define quantas linhas o navegador de strings vai ter
     *
     * @param row numero linha
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Define quantas colunas o navegador de strings vai ter
     *
     * @param col numero colunas
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Adiciona uma lista de strings ao navegador de strings
     *
     * @param stringsList lista de strings a colocar no navegador
     */
    public void addToStringBrowser(List<String> stringsList) {
        if (stringsList.isEmpty()) return;
        stringBrowser.clear();
        stringBrowser.addAll(stringsList);
    }

    /**
     * converte os dados para string para serem visualizados
     *
     * @param fichName    nome do ficheiro
     * @param estatistica estatistica
     */
    @Override
    public void showInfoView(String fichName, IEstatisticas estatistica) {
        StringBuilder sb = new StringBuilder("Ficheiro lido: ");
        sb.append(fichName).append("\n");
        sb.append("Número total de registos de venda errados: ").append(estatistica.getNumVendasInvalidas()).append("\n");
        sb.append("Número total de produtos: ").append(estatistica.getTotalProdNum()).append("\n");
        sb.append("Número total de produtos comprados: ").append(estatistica.getNumTotalProdutosComprados()).append("\n");
        sb.append("Número total de produtos não comprados: ").append(estatistica.getProdNaoComprados()).append("\n");
        sb.append("Número total de clientes: ").append(estatistica.getTotalClientNum()).append("\n");
        sb.append("Número total de clientes que nada compraram: ").append(estatistica.getNumClientesNaoCompraram()).append("\n");
        sb.append("Número de commpras de valor total igual a 0.0: ").append(estatistica.getNumTotalDeComprasValorNulo()).append("\n");
        DecimalFormat df = new DecimalFormat("###.##");
        sb.append("Faturação total: ").append(df.format(estatistica.getFacturacaoTotal())).append("\n");
        sb.append("Prima 1 para mais informações\n");
        sb.append("Prima 2 para continuar\n");
        header();
        out.println(sb.toString());
        int choice = Input.lerInt();
        switch (choice) {
            case 1:
                showDetailedInfo(estatistica);
                break;
            default:
                break;
        }

    }

    /**
     * Mostra mais informação detalhada
     *
     * @param estatistica estatistica
     */
    private void showDetailedInfo(IEstatisticas estatistica) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (double[] arr : estatistica.getFactPerMonth()) {
            sb.append("Filial ").append(count + 1).append("\n");
            for (int i = 0; i < 12; i++) {
                sb.append("Mes ").append(i + 1).append(": facturou ").append(arr[i]).append("\n");
            }
            sb.append("\n");
            count++;
        }
        count = 0;
        for (int[] arr : estatistica.getNumberOfSalesPerMonth()) {
            sb.append("Filial ").append(count + 1).append("\n");
            for (int i = 0; i < 12; i++) {
                sb.append("Mes ").append(i + 1).append(": houve ").append(arr[i]).append(" vendas ").append("\n");
            }
            sb.append("\n");
            count++;
        }
        count = 0;
        for (int[] arr : estatistica.getDiffClientsNumber()) {
            sb.append("Filial ").append(count + 1).append("\n");
            for (int i = 0; i < 12; i++) {
                sb.append("Mes ").append(i + 1).append(": compraram ").append(arr[i]).append(" clientes diferentes ").append("\n");
            }
            sb.append("\n");
            count++;
        }
        this.showLine(sb.toString());
    }

    /**
     * Imprime sempre a informação que quisermos antes do menu
     */
    private void header() {
        StringBuilder sb = new StringBuilder("Programa de Gestao de Vendas Realizado por Jose Santos, Pedro Queiros e Alexandre Costa             Tempo Decorrido:");
        DecimalFormat df = new DecimalFormat("###.##");
        sb.append(df.format(this.timeQueue)).append(" segundos\n");
        out.println(sb.toString());
    }


    /**
     * Update menu
     *
     * @param menu menu para onde vai mudar
     */
    public void updateMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * altera o modo do menu
     */
    public void updateView() {
        header();
        switch (this.menu) {
            case STRINGBROWSER:
                navigate();
                break;
            default:
                showMenuAsList(menu);
                choice = Input.lerInt();
                if (choice >= menu.getMenuOptions().length) {
                    this.menu = Menu.MAINMENU;
                }
                break;
        }

    }


    /**
     * imprime o menu como uma lista
     *
     * @param menu menu a imprimir
     */
    private void showMenuAsList(Menu menu) {
        String[] menuOptions = menu.getMenuOptions();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String s : menuOptions) {
            i++;
            sb.append(i).append(" - ").append(s).append("\n");
        }
        out.print(sb.toString());
    }

    /**
     * setter para o timeQueue
     *
     * @param timeQueue tempo de espera da query
     */
    public void setTimeQueue(double timeQueue) {
        this.timeQueue = timeQueue;
    }

    /**
     * getter da opcao do menu
     *
     * @return int
     */
    public int getChoice() {
        return this.choice;
    }


    /**
     * imprime a line no ecra
     *
     * @param line linha a imprimir
     */
    public void showLine(String line) {
        header();
        System.out.println(line);
        Input.lerString();
    }


    /**
     * permite percorrer os dados consultados
     */
    public void navigate() {
        if (this.stringBrowser.isEmpty()) {
            out.println("Não existem resultados");
            Input.lerString();
            updateMenu(Menu.MAINMENU);
            return;
        }
        while (this.getCurrentMenu() == Menu.STRINGBROWSER) {
            this.printStringsBrowser();
            switch (Input.lerInt()) {
                case 1:
                    if (page > 0) page--;
                    break;
                case 2:
                    if (stringBrowser.size() > (cursor) + (row * col)) page++;
                    break;
                default:
                    stringBrowser.clear();
                    this.menu = Menu.MAINMENU;
                    break;
            }
        }

    }

    /**
     * Define as dimensoes para a visualizacao das consultas
     */
    private void printStringsBrowser() {
        cursor = page * (row * col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int index = ((j * row) + i + cursor);
                if (stringBrowser.size() <= index) break;
                String tmp = stringBrowser.get(index);
                out.print(tmp);
                out.print("       ");
            }
            out.print("\n");
        }
        out.println("Total de resultados da query: " + stringBrowser.size());
        out.println("\nPrima 0 para sair");
        out.println("Prima 1 para a página anterior");
        out.println("Prima 2 para a próxima página");
    }


    /**
     * devolve o menu
     *
     * @return Menu
     */
    public Menu getCurrentMenu() {
        return this.menu;
    }


    /**
     * método para fechar o menu
     *
     * @return boolean
     */
    public boolean exit() {
        if (menu != Menu.MAINMENU) return false;
        return choice == 0 || choice > menu.getMenuOptions().length;
    }
}
