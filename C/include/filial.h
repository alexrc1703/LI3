#ifndef __FILIAL_H
#define __FILIAL_H
#include "sales.h"

//apontador para a struct
typedef struct _filial** Filiais;

//apontador para a struct
typedef struct _filial* Filial;

//apontador para a struct
typedef struct _saleTuple* saleTuple;

//devolve tuplo de prods
Product getSaleTupleProd(saleTuple saleTuple);
//delvole tuplo de sales
double getSaleTupleFact(saleTuple saleTuple);

int getSaleTupleUnits(saleTuple saleTuple);
//inicia a struct
Filiais initFiliais(Sales arrSale);
//sales por cliente por mes
int getNumberOfSalesPerClientPerMonth(Filial filial, Client client,int month);

//sales por cliente por ano
int getNumberOfSalesPerClientPerYear(Filiais filiais, Client client);

//array sales
GArray** getGArrayOfSalesPerYearPerClient(Filial filial,Client client);
//array de sales mensal
GArray* getArrayOfSalesPerMonthPerClientPerFiliais(Filiais filiais,Client client,int month);

//array top3 products
GArray* top3ProductsPerClient(Filiais filiais, Client client);
#endif
