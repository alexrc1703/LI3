package com.grupo19;

/**
 * Menu
 */
public enum Menu {
    MAINMENU(new String[]{"Lista ordenada alfabeticamente com os códigos dos produtos nunca comprados e o seu respectivo total",
            "Dado um mês , determinar o número total global de vendas realizadas e o número total de clientes distintos que as fizeram.",
            "Dado um código de cliente, determinar, para cada mês, quantas compras fez, quantos produtos distintos comprou e quanto gastou no total.",
            "Dado o código de um produto existente, quantas vezes foi comprado, por quantos clientes diferentes e o total facturado.",
            "Dado o código de um cliente determinar os produtos que mais comprou",
            "Determinar X produtos mais vendidos em todo o ano ",
            "Determinar a lista dos três maiores compradores em termos de dinheiro facturado, por filial",
            "Determinar os códigos dos X clientes que compraram mais produtos diferentes",
            "Dado o código de um produto que deve existir, determinar o conjunto dos X clientes que mais o compraram  ",
            "Determinar mês a mês, e para cada mês filial a filial, a facturação total do produto dado",
            "Guardar estado do programa em ficheiro objeto(Nome por defeito:data.tmp)"}),
    STRINGBROWSER;

    private String[] menuOptions;

    /**
     * Construtor do menu
     */
    Menu() {

    }

    /**
     * Construtor do menu parametrizado para receber as opções do MainMenu
     * construtor parametrizado
     *
     * @param menuOptions opçao do menu
     */
    Menu(String[] menuOptions) {
        this.menuOptions = menuOptions;
    }

    /**
     * Getter das opções do menu
     * metodo getter da String de opcoes do menu
     *
     * @return lista de strings do menu
     */
    public String[] getMenuOptions() {
        return menuOptions;
    }
}
