#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "product.h"
#include "constants.h"

struct _product {
    char* strProd;
    int codProd;
    int bought[3];
};

int validate_Product(Product prod);
Products loadProductsAndVerify();

static int valProd = 0;

Products initProducts() {
    return loadProductsAndVerify();
}

Product constructProd(char *prodStr) {
    Product tmp = (Product) malloc(sizeof(struct _product));
    char numstr[5]; 
    strncpy(numstr,prodStr+2,4);
    numstr[4] = '\0';
    int num = atoi(numstr);
    tmp->strProd = strdup(prodStr);
    tmp->codProd = num;
    for(int i=0;i<NUM_FILIAIS; i++) tmp->bought[i] = 0;
    return tmp;
}

void freeProducts(Products arrayOfProd){
    for(int i=0; arrayOfProd[i] != NULL; i++) {
        free(arrayOfProd[i]);
    } 
    free(arrayOfProd);
}


Products loadProductsAndVerify() {
    Products arrVal = (Products) malloc(TAM_LENGHT_SALES*(sizeof(struct _product)));
    char str[TAM_STR_PROD];
    FILE* fp= fopen(PRODPATH,"r");
    if( fp == NULL) exit(1);
    while(fgets(str,TAM_STR_PROD,fp)) {
        Product tmp = constructProd(strtok(str,"\n\r"));
        if(validate_Product(tmp) == 1) {
            arrVal[valProd] = tmp;
            valProd++;
        } 
    }
    fclose(fp); 
    return arrVal;
}




// verifica se um produto é valido
int validate_Product(Product prod) {
    if(isupper(prod->strProd[0]) && isupper(prod->strProd[1]) && prod->codProd <= 9999 && prod->codProd >= 1000) return 1;
    return 0;
} 

void setProductBoughtFromFilial(Product prod , int filial,int qt) {
    prod->bought[filial] += qt;
}

int getProductBoughtFromFilial(Product prod , int filial) {
    return prod->bought[filial];
}

int getAllUnitsSell(Product prod) {
    int sum = 0;
    for(int i =0; i<NUM_FILIAIS; i++) sum += getProductBoughtFromFilial(prod,i);
    return sum;
}

int hasProductEverBeenBought(Product prod) {
    int flag = 0;
    for(int i = 0; i<NUM_FILIAIS && flag == 0; i++) {
        if(getProductBoughtFromFilial(prod,i) > 0) flag = 1;
    }
    return flag;
}

Product cloneProduct(Product product) {
    Product tmp = constructProd(getProdStr(product));
    for(int i=0;i<NUM_FILIAIS;i++){
        tmp->bought[i]=product->bought[i];
    }
    return tmp;
}


char* getProdStr(Product prod) {
    return strdup(prod->strProd);
}


char getNProdLetter(Product prod,int n) {
    return (prod->strProd[n]);
}

int getIndexFromProdLetter(Product prod, int n) {
    return getNProdLetter(prod,n) - 'A';
}

int getProdNum(Product prod) {
    return prod->codProd;
}
