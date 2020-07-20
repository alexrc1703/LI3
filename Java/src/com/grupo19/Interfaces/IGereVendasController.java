package com.grupo19.Interfaces;

public interface IGereVendasController {

    /**
     * define o modelo do controler
     *
     * @param model model
     */
    void setModel(IGereVendasModel model);


    /**
     * define o view do controler
     *
     * @param view view
     */
    void setView(IGereVendasView view);


    /**
     * metodo que inicia o controller
     */
    void init();
}
