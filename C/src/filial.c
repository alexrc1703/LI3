#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "filial.h"
#include "gmodule.h"
#include "glib.h"
#include "sales.h"
#include "client.h"
#include "product.h"
#include "constants.h"

struct _mainStruct{
    GArray** arr;
};

typedef struct _mainStruct* mainStruct;

struct _filial {
    GTree** tree;
};

struct _saleTuple {
    double fact;
    int units;
    Product prod;
};



gint compareFilial(gconstpointer a, gconstpointer b);
gpointer getPointerFromIntFilial(int x);
void addData(Filial filial, Sale sale);

void initTree(Filial filial) {
    filial->tree = (GTree**) calloc(26,sizeof(GTree*));
    for(int i=0; i<26; i++)
        filial->tree[i] = g_tree_new((GCompareFunc) compareFilial);
}

void fillDataFiliais(Filiais filiais, Sales arrSale) {
    for(int i = 0; arrSale[i]; i++) {
        addData(filiais[getFilial(arrSale[i])-1],arrSale[i]);
    }
}

void initMonthArray(mainStruct tmp) {
    tmp->arr = (GArray**) calloc(12,sizeof(GArray*));
    for(int i=0; i<12; i++)
        tmp->arr[i] = g_array_sized_new(FALSE,FALSE,sizeof(Sale),12);
}

Product getSaleTupleProd(saleTuple saleTuple) {
    return cloneProduct(saleTuple->prod);
}

double getSaleTupleFact(saleTuple saleTuple) {
    return saleTuple->fact;
}
int getSaleTupleUnits(saleTuple saleTuple) {
    return saleTuple->units;
}

/** OBTER A ARVORE RELATIVA AO CLIENTE 
 *  OBTER A KEY PARA DEPOIS PROCURAR NA ARVORE A PARTE REFERENTE AO CLIENTE EM QUESTAO
 *  OBTER O ARRAY DE APONTADORES QUE ESTA NA ARVORE
 *  SE NAO ESTIVER CRIAR UM 
 *  ADICIONAR A SALE AO ARRAY DE APONTADORES(QUE APONTA PARA UMA SALE)
*/

void addData(Filial filial, Sale sale) {
    Client tmpClient = getClientOfSale(sale);
    int indexLetter = getClientIndexFromLetter(tmpClient);
    int monthSale = getMonthOfSale(sale) - 1;
    GTree* tmpTree = filial->tree[indexLetter];
    gpointer key = getPointerFromIntFilial(getClientNum(tmpClient));
    gpointer tmpGP = g_tree_lookup(tmpTree,key);
    mainStruct tmpMS = (mainStruct) tmpGP;
    GArray* tmpArr;
    if(tmpMS == NULL) {  //INICIAR GARRAY
         tmpMS = (mainStruct) malloc(sizeof(struct _mainStruct));
         initMonthArray(tmpMS);
         g_tree_insert(tmpTree,key,tmpMS);
    }
    tmpArr = tmpMS->arr[monthSale];
    gpointer tmpVal = cloneSale(sale);
    g_array_append_val(tmpArr,tmpVal);
}


Filial initFilial() {
    Filial filial = (Filial) malloc(sizeof(struct _filial));
    initTree(filial);
    return filial;
    
}

Filiais initFiliais(Sales arrSale) {
    Filiais filiais = calloc(NUM_FILIAIS,sizeof(Filial));
    for(int i=0 ; i<NUM_FILIAIS; i++) {
         filiais[i] = initFilial();  
    }
    fillDataFiliais(filiais,arrSale); 
    return filiais;
}



gint compareFilial(gconstpointer a, gconstpointer b) {
    return *((gint*)a) - *((gint*)b);
}


gpointer getPointerFromIntFilial(int x) {
    int* tmp = (int*) malloc(sizeof(int));
    *tmp = x;
    gpointer tp = tmp;
    return tp;
}

/**
 * (ARR + TREE ) CODCLIENTE -> (ARRAY)MES -> (ARRAY)sVENDAS
  */


int getNumberOfSalesPerClientPerMonth(Filial filial, Client client,int month) {
    int indexLetter = getClientIndexFromLetter(client);
    GTree* tmp = filial->tree[indexLetter];
    mainStruct tmpList = NULL;
    gpointer tmpGP = g_tree_lookup(tmp,getPointerFromIntFilial(getClientNum(client)));
    if(tmpGP != NULL) {
        tmpList = (mainStruct)tmpGP;
        return tmpList->arr[month-1]->len;
    }
    return 0;
}

int getNumberOfSalesPerClientPerYearPerFilial(Filial filial, Client client) {
    int total = 0;
    for(int i=1;i<=12;i++) {
       total += getNumberOfSalesPerClientPerMonth(filial,client,i);
    }
    return total;
}

int getNumberOfSalesPerClientPerYear(Filiais filiais, Client client) {
    int total = 0;
    for(int i=0;filiais[i] ;i++) {
       total += getNumberOfSalesPerClientPerYearPerFilial(filiais[i],client);
    }
    return total;
}

GArray** getGArrayOfSalesPerYearPerClient(Filial filial,Client client) {
    int indexLetter = getClientIndexFromLetter(client);
    GTree* tmp = filial->tree[indexLetter];
    mainStruct tmpList = NULL;
    gpointer tmpGP = g_tree_lookup(tmp,getPointerFromIntFilial(getClientNum(client)));
    if(tmpGP != NULL) {
        tmpList = (mainStruct)tmpGP;
        return tmpList->arr;
    } 
    return NULL;

}

GArray* getArrayOfSalesPerMonthPerClientPerFilial(Filial filial,Client client, int month) {
    int indexLetter = getClientIndexFromLetter(client);
    GTree* tmp = filial->tree[indexLetter];
    mainStruct tmpList = NULL;
    gpointer tmpGP = g_tree_lookup(tmp,getPointerFromIntFilial(getClientNum(client)));
    if(tmpGP != NULL) {
        tmpList = (mainStruct)tmpGP;
        return tmpList->arr[month-1];
    }
    return NULL;

}

GArray* getArrayOfSalesPerMonthPerClientPerFiliais(Filiais filiais,Client client,int month) {
    GArray* arr = g_array_new(FALSE,FALSE,sizeof(Sale));
    for(int i =0; i<NUM_FILIAIS; i++) {
        GArray* tmp = getArrayOfSalesPerMonthPerClientPerFilial(filiais[i],client,month);
        if(tmp != NULL) {
            for(int j=0;j<(int)tmp->len;j++) {
                g_array_append_val(arr,g_array_index(tmp,Sale,j));
            }
        }
        
    }
    return arr;
}



gint compareFact (gpointer a, gpointer b) {
    double atmp =  ((*(saleTuple*)a)->fact);
    double btmp = ((*(saleTuple*) b)->fact);
    gint tmp =(gint) btmp-atmp;
    return tmp;
}


GArray* top3ProductsPerClientPerFilial(Filial filial, Client client) {
    double lessFact = 0.0;
    int indexLetter = getClientIndexFromLetter(client);
    GArray* tmp = g_array_new(FALSE,FALSE,sizeof(saleTuple));
    for(int i=0; i<12; i++) {
        mainStruct mS = g_tree_lookup(filial->tree[indexLetter],getPointerFromIntFilial(getClientNum(client)));
        if(mS != NULL) {
            GArray* tmpMonth = mS->arr[i] ;
            for(int j = 0; j<(int)tmpMonth->len; j++) {
                double tmpFact = (double) (getUnits(g_array_index(tmpMonth,Sale,j))*getPrice(g_array_index(tmpMonth,Sale,j)));
                if(tmp->len < 3) {
                    saleTuple sT = malloc(sizeof(struct _saleTuple));
                    sT->fact = tmpFact;
                    sT->units = getUnits(g_array_index(tmpMonth,Sale,j));
                    sT->prod = cloneProduct(getProductOfSale(g_array_index(tmpMonth,Sale,j)));
                    g_array_append_val(tmp,sT);
                    g_array_sort(tmp,(GCompareFunc) compareFact);
                    lessFact = getSaleTupleFact(g_array_index(tmp,saleTuple,j));
                } else if(lessFact <  tmpFact)  {
                    saleTuple sT = malloc(sizeof(struct _saleTuple));
                    sT->fact = tmpFact;
                    sT->units = getUnits(g_array_index(tmpMonth,Sale,j));
                    sT->prod = cloneProduct(getProductOfSale(g_array_index(tmpMonth,Sale,j)));
                    g_array_append_val(tmp,sT);
                    g_array_sort(tmp,(GCompareFunc) compareFact);
                    lessFact = getSaleTupleFact(g_array_index(tmp,saleTuple,3));
                    g_array_remove_range(tmp,3,tmp->len-3);
                    
                }
            }

        }  else return NULL;
    }
    return tmp;
    
}

GArray* top3ProductsPerClient(Filiais filiais, Client client) {
    GArray* arr = g_array_new(FALSE,FALSE,sizeof(saleTuple));
    for(int i = 0; i<NUM_FILIAIS;i++) {
        GArray* tmp = top3ProductsPerClientPerFilial(filiais[i],client);
        if(tmp != NULL) {
            for(int j=0; j<(int)tmp->len;j++) {
                g_array_append_val(arr,(g_array_index(tmp,saleTuple,j)));
            }
        } else return NULL;
        
    }
    g_array_sort(arr,(GCompareFunc) compareFact);
    if(arr->len > 3) g_array_remove_range(arr,3,6);
    return arr;
}


