#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <gmodule.h>
#include "product.h"
#include "catProd.h"
#include "constants.h"

struct _catProd {
    GTree* tree[26][26];
};


gint compareProd(gconstpointer a, gconstpointer b);
int getIndexFromProdLetter(Product prod, int n);
void fillData(catProd catProd, Products prod);
gpointer getPointerFromInt(int x);


void initGTree(catProd catProd){
    for(int i = 0; i<26; i++){
        for(int j = 0; j<26; j++) {
            catProd->tree[i][j] = g_tree_new((GCompareFunc) compareProd);
        }
    }
}

catProd initCatProd(Products prod) {
    catProd catProd = malloc(sizeof(struct _catProd));
    initGTree(catProd);
    fillData(catProd,prod);
    return catProd;
} 


void fillData(catProd catProd, Products prod) {
    for(int i =0; prod[i]; i++) {
        int fstLetter = getIndexFromProdLetter(prod[i],0);
        int sndLetter = getIndexFromProdLetter(prod[i],1);
        GTree* tmp = catProd->tree[fstLetter][sndLetter];
        gpointer key = cloneProduct(prod[i]);
        gpointer value = getPointerFromInt(getProdNum(prod[i]));
        g_tree_insert(tmp,value,key);
    } 
} 

gpointer getPointerFromInt(int x) {
    int* tmp = (int*) malloc(sizeof(int));
    *tmp = x;
    gpointer tp = tmp;
    return tp;
}


int prodExists(catProd catProd, Product prod) {
    int fstLetter = getIndexFromProdLetter(prod,0);
    int sndLetter = getIndexFromProdLetter(prod,1);
    gpointer key = getPointerFromInt(getProdNum(prod));
    gpointer tmp = g_tree_lookup(catProd->tree[fstLetter][sndLetter],key);
    return tmp != NULL ? 1:0; 
}

gint compareProd(gconstpointer a, gconstpointer b) {
    return *((gint*)a) - *((gint*)b);
}


void markProductThatBought(catProd catProd, Product tmp, int filial,int qt) {
    int fstLetter = getIndexFromProdLetter(tmp,0);
    int sndLetter = getIndexFromProdLetter(tmp,1);
    Product prod = g_tree_lookup(catProd->tree[fstLetter][sndLetter],getPointerFromInt(getProdNum(tmp)));
    setProductBoughtFromFilial(prod,filial,qt);
}

gboolean traverseProdsWhoNeverBought (gpointer key, gpointer value , gpointer data) {
    (void)(key); //PARA SILENCIAR O WARNING DE NAO ESTAR A SER USADO
    if(hasProductEverBeenBought(value) == 0) g_array_append_val(data,value);
    return FALSE;
}

gboolean addAllToGArray (gpointer key, gpointer value , gpointer data) {
    (void)(key); //PARA SILENCIAR O WARNING DE NAO ESTAR A SER USADO
    g_array_append_val(data,value);
    return FALSE;
}

GArray* prodsThatNeverBought(catProd catProd) {
    GArray* prods = g_array_new(FALSE,FALSE,sizeof(Product));
    for(int i = 0; i<26; i++) {
        for(int j = 0; j<26; j++)
        g_tree_foreach(catProd->tree[i][j],(GTraverseFunc) traverseProdsWhoNeverBought , prods);
    }
    return prods;
}

GArray* listOfProductsThatStartWithLetter(catProd catProd , char letter) {
    GArray* prods = g_array_new(FALSE,FALSE,sizeof(Product));
    for(int i=0; i<26; i++) {
        g_tree_foreach(catProd->tree[(letter - 'A')][i],(GTraverseFunc) addAllToGArray , prods);
    }
    return prods;
}

gint compareUnitsCatProd (gpointer a, gpointer b) {
    Product tmpa = *((Product*) a);
    Product tmpb = *((Product*) b);
    gint tmp =(gint) (getAllUnitsSell(tmpb))-(getAllUnitsSell(tmpa));
    return tmp;
}

gboolean traverse_toFindTheMostSell (gpointer key, gpointer value , gpointer data) {
    (void)(key); //PARA SILENCIAR O WARNING DE NAO ESTAR A SER USADO
    gpointer val = cloneProduct(value);
    if(getAllUnitsSell(*((Product*)value))>0) g_array_append_val(data,val);
    return FALSE;
}

GArray* prodsMostSell(catProd catProd, int n) {
    GArray* arr = g_array_new(FALSE,FALSE,sizeof(Product));
    for(int i = 0; i<26; i++) {
        for(int j = 0; j<26; j++) {
            g_tree_foreach(catProd->tree[i][j],(GTraverseFunc) addAllToGArray , arr);
            g_array_sort(arr,(GCompareFunc) compareUnitsCatProd);
            g_array_remove_range(arr,n,arr->len-n);
        }
    }
    return arr;
}
