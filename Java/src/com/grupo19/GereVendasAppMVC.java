package com.grupo19;

import com.grupo19.Interfaces.IGereVendasController;
import com.grupo19.Interfaces.IGereVendasModel;
import com.grupo19.Interfaces.IGereVendasView;

import static java.lang.System.out;

public class GereVendasAppMVC {

    /**
     * metodo main que inicia a aplicaçao
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        IGereVendasModel model = GereVendasModel.recoverState("data.tmp");
        if (model == null) {
            model = GereVendasModel.loadData();
            if (model == null) {
                out.println("ERRO INICIALIZACAO");
                System.exit(-1);
            }
        }
        IGereVendasController controller = new GereVendasController();
        IGereVendasView view = new GereVendasView();
        controller.setModel(model);
        controller.setView(view);
        controller.init();
        System.exit(0);
    }
}
