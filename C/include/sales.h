#ifndef __SALES_H
#define __SALES_H
#include "catProd.h"
#include "catClient.h"
typedef struct _sale** Sales;
typedef struct _sale* Sale;
Sale constructSale(char* saleStr); // constroi a Sale recebendo a string da sale
void saleToString (Sale sale,char* buffer); // passa uma sale para string por referencia
Sales initSales(catProd catProd, catClient catClient); // inicia as sales 
int getMonthOfSale(Sale sale); // dá o mes em que foi vendida a sale
Sale cloneSale(Sale sale); // faz um clone de uma sale
int getFilial(Sale sale); // da a filial da venda
Client getClientOfSale(Sale sale); // dá o cliente que efetuou a compra
int getUnits(Sale sale); // dá o numero de unidades vendidas da venda
Product getProductOfSale(Sale sale); // dá o produto que foi vendido
void freeSales(Sales arrayOfSales); // dá free do array das sales
char getSaleType(Sale sale); // dá o tipo de venda N ou P
double getPrice(Sale sale); // dá o preço a que foi vendida a sale
Sale getSale(Sale sale); // dá a sale
#endif
