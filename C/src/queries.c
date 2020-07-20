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

//QUERY 4
char** getClientsThatBoughtInAllFilialsString(SGV sgv,int* tamanho) {
    GArray* clients = clientsThatBoughtInAllFilials(getCatClient(sgv));
    *tamanho = clients->len;
    char **arrStrings = malloc((*tamanho)* (sizeof(char*)));
    for(int i = 0; i<(*tamanho); i++) {
        arrStrings[i] = getClientStr(g_array_index(clients,Client,i));
    }
    g_array_free(clients,TRUE);
    return arrStrings;
}

//QUERY 5
int getClientsThatNeverBoughtNum(SGV sgv) {
    GArray* clients = clientsThatNeverBought(getCatClient(sgv));
    int tmp = clients->len;
    g_array_free(clients,TRUE);
    return tmp;
}
// QUERY 5
int getProductsThatNeverBoughtNum(SGV sgv) {
    GArray* products = prodsThatNeverBought(getCatProd(sgv));
    int tmp = products->len;
    g_array_free(products,TRUE);
    return tmp;
}

//QUERY 6

int  getQuantidadeGArraySale(GArray* arr) {
    int units = 0;
    for(int i=0; i< (int) arr->len; i++) units += getUnits(g_array_index(arr,Sale,i));
    return units;
}

//QUERY 6

char** getProductsBoughtFromClientPerMonth(SGV sgv, int *tamanho,int filial,Client client) {
    GArray** tmpArr = getGArrayOfSalesPerYearPerClient(getFilialSGV(sgv,filial),client);
    *tamanho = 12;
    if(tmpArr == NULL) return NULL;
    char **arrStrings = calloc(*tamanho,sizeof(char*));
    for(int i=0; i<12; i++) {
        GArray* MonthArr = tmpArr[i];
        int tmpunits = getQuantidadeGArraySale(MonthArr);
        char buffer[50];
        snprintf(buffer,50,"Mes: %d , Unidades: %d",(i+1),tmpunits);
        arrStrings[i] = strdup(buffer);
        g_array_free(MonthArr,TRUE);

    }
    free(tmpArr);
    return arrStrings;
    
}

//QUERY 10

char** getNMostSellProducts(SGV sgv,int n) {
    GArray* arr = prodsMostSell(getCatProd(sgv),n);
    char **arrStrings = calloc(n,sizeof(char*));
    for(int i = 0;i<(int)arr->len;i++) {
        int numBuyers = getNumBuyersForAProd(getFacturacao(sgv),g_array_index(arr,Product,i));
        int filial1 = getProductBoughtFromFilial(g_array_index(arr,Product,i),0);
        int filial2 = getProductBoughtFromFilial(g_array_index(arr,Product,i),1);
        int filial3 = getProductBoughtFromFilial(g_array_index(arr,Product,i),2);
        char buffer[150];
        snprintf(buffer,150,"Produto: %s , N de Compradores: %d, N de Vendas Filial 1 : %d, N de Vendas Filial 2 : %d, N de Vendas Filial 3 : %d ",getProdStr(g_array_index(arr,Product,i)),numBuyers, filial1,filial2,filial3 );
        arrStrings[i] = strdup(buffer); 
    }
    
    return arrStrings;
}

//QUERY 11

char** get3MostBoughtProductsPerClient(SGV sgv,Client client) {
    GArray* arr = top3ProductsPerClient(getFiliaisSGV(sgv),client);
    if(arr == NULL) return NULL;
    char **arrStrings = calloc(arr->len,sizeof(char*));
    for(int i=0; i<(int)arr->len;i++) {
        char buffer[50];
        snprintf(buffer,50,"Produto: %s , Facturacao: %.2f",getProdStr(getSaleTupleProd(g_array_index(arr,saleTuple,i))),getSaleTupleFact(g_array_index(arr,saleTuple,i)));
        arrStrings[i] = strdup(buffer);
    }
    return arrStrings;
}


//QUERY 9

gint compareUnitsSoldBySale (gpointer a, gpointer b) {
    return getUnits(*(Sale*)b)-getUnits(*(Sale*)a);
}

//QUERY 9

char** getCodProductsBoughFromClientPerMonth(SGV sgv, int *tamanho, Client client, int month){
    GArray* arrTmp = getArrayOfSalesPerMonthPerClientPerFiliais(getFiliaisSGV(sgv),client,month);
    g_array_sort(arrTmp,(GCompareFunc)compareUnitsSoldBySale);
    *tamanho = arrTmp->len;
    char **arrStrings = malloc((*tamanho)* (sizeof(char*)));
    for(int i = 0; i<(*tamanho); i++) {
        char buffer[50];
        snprintf(buffer,50,"Produto: %s , Unidades: %d",getProdStr(getProductOfSale(g_array_index(arrTmp,Sale,i))),getUnits(g_array_index(arrTmp,Sale,i)));
        arrStrings[i] = strdup(buffer);
    } 
    g_array_free(arrTmp,TRUE); 
    return arrStrings;
}



// QUERY 3
char** getProductsThatNeverBoughtString(SGV sgv,int *tamanho) {
    GArray* products = prodsThatNeverBought(getCatProd(sgv));
     *tamanho = products->len;
    char **arrStrings = malloc((*tamanho)* (sizeof(char*)));
    for(int i = 0; i<(*tamanho); i++) {
        arrStrings[i] = getProdStr(g_array_index(products,Product,i));
    }
    g_array_free(products,TRUE);
    return arrStrings;
}

// QUERY 1
char** getProducsThatStartWithLetter(SGV sgv, char letter, int *tamanho) {
    GArray* arr = listOfProductsThatStartWithLetter(getCatProd(sgv),letter);
    *tamanho = arr->len;
    char **arrStrings = malloc((*tamanho)* (sizeof(char*)));
    for(int i = 0; i<(*tamanho); i++) {
        arrStrings[i] = getProdStr(g_array_index(arr,Product,i));
    }
    g_array_free(arr,TRUE);
    return arrStrings;
}

//QUERY 2
char** totalPerMonthPerProd(SGV sgv, Product prod ,int month,int flag) {
    double priceP = 0 , priceN = 0;
    int qntP = 0 , qntN = 0;
    GArray* tmp = totalSalesPerProdPerMonth(getFacturacao(sgv),prod,month);
    if(tmp == NULL) return NULL;
    char **arrStrings = NULL;
    
    for(int i = 0; i<((int) tmp->len); i++) {
        char buffer[50];
        if(flag != 0 && getFilial(g_array_index(tmp,Sale,i)) != flag ) continue;
        if(getSaleType(g_array_index(tmp,Sale,i)) == 'N' ) {
            qntN += getUnits(g_array_index(tmp,Sale,i));
            priceN += getUnits(g_array_index(tmp,Sale,i)) * getPrice(g_array_index(tmp,Sale,i));
            
        } else {
            qntP += getUnits(g_array_index(tmp,Sale,i));
            priceP += getUnits(g_array_index(tmp,Sale,i)) * getPrice(g_array_index(tmp,Sale,i));
            
        }
        arrStrings = malloc((2)* (sizeof(char*)));
        snprintf(buffer,50,"Venda N: Unidades: %d , Facturacao: %.2f",qntN,priceN);
        arrStrings[0] = strdup(buffer);
        snprintf(buffer,50,"Venda P: Unidades: %d , Facturacao: %.2f",qntP,priceP);
        arrStrings[1] = strdup(buffer);
    }
    g_array_free(tmp,TRUE);
    return arrStrings;
} 

// QUERY 7

double  vendasBetweenMonths(SGV sgv,int ini,int fim, int *totalVendas){
    int qtVendas = 0;
    double res = vendasEmIntervalo(getFacturacao(sgv),ini,fim,&qtVendas);
    *totalVendas = qtVendas;
    return res;
}

// QUERY 8

char** clientesCompraramProd(SGV sgv, Product prod, int filial, int* tamanho){
    prodPClient tmpstruct = clientsWhoBoughtThisProd(prod,filial,getFacturacao(sgv));
    GArray* arrOfClientN = getArrOfClientN(tmpstruct);
    GArray* arrOfClientP = getArrOfClientP(tmpstruct);
    *tamanho = arrOfClientN->len + arrOfClientP->len;
    char **arrStrings = calloc(*tamanho,sizeof(char*));
    int i;
    for(i = 0 ; i<(int) arrOfClientN->len;i++) {
        char buffer[15];
        snprintf(buffer,15,"%s N",getClientStr(g_array_index(arrOfClientN,Client,i)));
        arrStrings[i] = strdup(buffer);
    }
    for(int j = 0 ; j< (int) arrOfClientP->len;j++) {
        char buffer[15];
        snprintf(buffer,15,"%s P",getClientStr(g_array_index(arrOfClientP,Client,j)));
        arrStrings[j+i] = strdup(buffer);
    } 
    g_array_free(arrOfClientN,TRUE);
    g_array_free(arrOfClientP,TRUE);
    return arrStrings;
}
