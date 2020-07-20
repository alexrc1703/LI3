#ifndef __PRODUCT_H
#define __PRODUCT_H
typedef struct _product** Products;
typedef struct _product* Product;
Product* initProducts();
char* getProdStr(Product prod);
Product constructProd(char *prodStr);
void freeProducts(Products arrayOfProd);
int getProdNum(Product prod);
Product cloneProduct(Product product);
int getIndexFromProdLetter(Product prod, int n);
void setProductBoughtFromFilial(Product prod , int filial,int qt);
int getProductBoughtFromFilial(Product prod , int filial);
int hasProductEverBeenBought(Product prod);
int getAllUnitsSell(Product prod);
#endif
