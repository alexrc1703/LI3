#ifndef __FATURACAO_H
#define __FATURACAO_H
#include "sales.h"

//apontador para a struct
typedef struct _facturacao* Facturacao;

//apontador para a struct
typedef struct _month* Month;

//apontador para a struct
typedef struct _prodPorClient* prodPClient;

//faturacao de um determinado produto
int totalSalesPerProd(Facturacao facturacao, Product prod);

//inica o povamento
Facturacao initFactura(Sales arrSale);

//devolve o array das vendas de um prod num determinado mes
GArray* totalSalesPerProdPerMonth(Facturacao facturacao, Product prod , int month);

//valor faturado dentro de os meses inseridos
double vendasEmIntervalo( Facturacao facturacao,int begin, int end, int *nVendas);

//devolve array dos clientes com precos em promo
GArray* getArrOfClientP(prodPClient prodPClient);

//devolve array dos clientes com precos normais
GArray* getArrOfClientN(prodPClient prodPClient);

//clientes que compraram determinado produto
prodPClient clientsWhoBoughtThisProd(Product prod, int filial, Facturacao facturacao);

//clientes que compraram o prod
int getNumBuyersForAProd(Facturacao facturacao, Product prod);
#endif
