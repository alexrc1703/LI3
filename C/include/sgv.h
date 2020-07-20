#ifndef __SGV_H
#define __SGV_H
#include "catClient.h"
#include "catProd.h"
#include "facturacao.h"
#include "filial.h"
typedef struct _sgv* SGV;
SGV initSGV(); // inicia o sgv
catClient getCatClient(SGV sgv); // dá o catalogo de clientes do sgv
catProd getCatProd(SGV sgv); // dá o catalogo de produtos do sgv
Facturacao getFacturacao(SGV sgv); // dá a faturação do sgv
Filial getFilialSGV(SGV sgv, int filial); // dá a filial do sgv
Filiais getFiliaisSGV(SGV sgv);// dá as filiais do sgv
#endif


