#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "product.h"
#include "client.h"
#include "sgv.h"
#include "catProd.h"
#include "catClient.h"
#include "sales.h"
#include "constants.h"

struct _sale {
    Product prod; 
    double price; 
    int units; 
    char saleType; 
    Client client; 
    int monthOfSale; 
    int filial; 
};


static int valSales = 0;


// controi uma sale em que pega num codigo de uma sale e reparte-o nas suas componentes
Sale constructSale(char* saleStr) {
    Sale sale = (Sale) malloc(sizeof(struct _sale));
    char strToTokenize[TAM_STR_SALES];
    strcpy(strToTokenize,saleStr);
    char* token;
    char* saleFields[TAM_SALE_FIELDS];
    token = strtok(strToTokenize," ");
    for(int i = 0 ; token != NULL ; i++) {
        saleFields[i] = strdup(token);
        token = strtok(NULL," ");
    }   
    sale->prod = constructProd(saleFields[0]);
    sale->price = atof(saleFields[1]);
    sale->units = atoi(saleFields[2]);
    sale->saleType = saleFields[3][0];
    sale->client = constructClient(saleFields[4]);
    sale->monthOfSale = atoi(saleFields[5]);
    sale->filial = atoi(saleFields[6]);
    return sale;
    
}


// passa uma sale para string 
void saleToString (Sale sale, char* buffer) {
    snprintf(buffer,TAM_STR_SALES,"%s %.2f %d %c %s %d %d\n", getProdStr(sale->prod), sale->price, sale->units, sale->saleType,getClientStr(sale->client),sale->monthOfSale,sale->filial);
}

Sale cloneSale(Sale sale) {
    Sale tmp = (Sale) malloc(sizeof(struct _sale));
    tmp->prod = cloneProduct(sale->prod);
    tmp->price = sale->price;
    tmp->units = sale->units;
    tmp->saleType = sale->saleType;
    tmp->client = cloneClient(sale->client);
    tmp->monthOfSale = sale->monthOfSale;
    tmp->filial = sale->filial;
    return tmp;
}

// verfica se uma venda é valida recebendo a venda em questão e todos os clientes e todos os produtos
int validate_Sale(Sale sale, catProd catProd ,catClient catClient) {
    if((sale->price) > 999.99 && (sale ->price) < 0.00) return 0;
    if((sale->units) > 200 && (sale->units) < 0) return 0;
    if((sale->monthOfSale) > 12 && (sale->monthOfSale) < 1) return 0;
    if((sale->filial) > 3 && (sale->filial) < 1) return 0;
    if(((sale->saleType) == 'N' && (sale->saleType) == 'P')) return 0;
    if(clientExists(catClient,sale->client) == 0) return 0;
    if(prodExists(catProd,sale->prod) == 0) return 0;
    return 1;
}  





double getPrice(Sale sale ){
    return sale->price;
}

int getUnits(Sale sale) {
    return sale->units;
}

int getFilial(Sale sale) {
    return sale->filial;
}

Client getClientOfSale(Sale sale) {
    return sale->client;
}

int getMonthOfSale(Sale sale) {
    return sale->monthOfSale;
}
Product getProductOfSale(Sale sale) {
    return sale->prod;
}

char getSaleType(Sale sale) {
    return sale->saleType;
}


void saveSalesToFile(char *filePath,Sales saleArr) {
    FILE* fp = fopen(filePath,"w");
    char buffer[TAM_STR_SALES];
    for(int i = 0 ; saleArr[i] != NULL ; i++) {
        saleToString(saleArr[i],buffer);
        fputs(buffer,fp);
    }
    fclose(fp);
}

Sales initSales(catProd catProd, catClient catClient) {
    Sales arrSale = (Sales) malloc(TAM_LENGHT_SALES * (sizeof(Sale)));
    int flag = 1;
    char str[TAM_STR_SALES];
    FILE* fp;
    fp = fopen(VENDASVALPATH,"r");
    if( fp == NULL){
        fp = fopen(VENDASPATH,"r");
        flag = 0;
    }  

    while(fgets(str,TAM_STR_SALES,fp)) {
        Sale tmp = constructSale(strtok(str,"\n\r"));
        if(flag == 0 && validate_Sale(tmp,catProd,catClient) == 0) continue; 
        arrSale[valSales] = constructSale(strtok(str,"\n\r"));
        markProductThatBought(catProd,getProductOfSale(arrSale[valSales]),getFilial(arrSale[valSales])-1,getUnits(tmp));
        markClientThatBought(catClient,getClientOfSale(arrSale[valSales]),getFilial(arrSale[valSales])-1);
        valSales++;
    }
    fclose(fp);
    if(flag == 0) saveSalesToFile(VENDASVALPATH,arrSale); 
    return arrSale;
    
}

Sale getSale(Sale sale) {
    return sale;
}

void freeSales(Sales arrayOfSales){
    for(int i=0; arrayOfSales[i] != NULL; i++) {
        free(arrayOfSales[i]);
    } 
    free(arrayOfSales);
}
