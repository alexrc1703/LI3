#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <gmodule.h>
#include "product.h"
#include "client.h"
#include "sales.h"
#include "sgv.h"
#include "catProd.h"
#include "catClient.h"
#include "facturacao.h"
#include "filial.h"
#include "constants.h"

struct _sgv {
    catProd catProd;
    catClient catClient;
    Facturacao fact;
    Filiais filiais;
};


SGV initSGV() {
    SGV sgv = (SGV) malloc(sizeof(struct _sgv));
    Clients arrayOfClient = initClients();
    Products arrayOfProd = initProducts();
    sgv->catProd = initCatProd(arrayOfProd);
    sgv->catClient = initCatClient(arrayOfClient);
    freeProducts(arrayOfProd);
    freeClients(arrayOfClient);
    Sales arrayOfSales = initSales(getCatProd(sgv),getCatClient(sgv));
    sgv->filiais = initFiliais(arrayOfSales);
    sgv->fact = initFactura(arrayOfSales);
    freeSales(arrayOfSales);
    return sgv; 
}

Filial getFilialSGV(SGV sgv, int filial) {
    return sgv->filiais[filial-1];
}

catClient getCatClient(SGV sgv) {
    return sgv->catClient;
}

catProd getCatProd(SGV sgv){
    return sgv->catProd;
}

Facturacao getFacturacao(SGV sgv) {
    return sgv->fact;
}
Filiais getFiliaisSGV(SGV sgv) {
    return sgv->filiais;
}
