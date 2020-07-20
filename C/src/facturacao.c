#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "product.h"
#include "client.h"
#include "sales.h"
#include "glib.h"
#include "gmodule.h"
#include "product.h"
#include "facturacao.h"

struct _month{
    GTree* treeProd[26][26];
};

struct _facturacao{
    Month* arrMonths;
};

struct _infoVenda {
    int nVenda;
    double faturado;
};

struct _prodPorClient{
    GArray* arrOfClientP;
    GArray* arrOfClientN;
};
typedef struct _facturacao* Facturacao;
typedef struct _month* Month;
typedef struct _prodPorClient* prodPClient;
typedef struct _infoVenda* infoVenda;

GArray* getArrOfClientP(prodPClient prodPClient){
    return prodPClient->arrOfClientP;
}

GArray* getArrOfClientN(prodPClient prodPClient){
    return prodPClient->arrOfClientN;
}


gint compareSales(gconstpointer a, gconstpointer b) {
    return *((gint*)a) - *((gint*)b);
}



void initStructFact(Month* months){
    for(int k = 0; k<12;k++){
        months[k] = (Month) malloc(sizeof(struct _month));
        for(int i = 0; i<26; i++){
            for(int j = 0; j<26; j++) {
                (months[k])->treeProd[i][j] = g_tree_new((GCompareFunc) compareSales);
            }
        } 
    }
    
}

gpointer getPointerFromIntFact(int x) {
    int* tmp = (int*) malloc(sizeof(int));
    *tmp = x;
    gpointer tp = tmp;
    return tp;
}

/*
vai buscar as letras do prod da cada sale para povoar a arvore
passar a arvore para a struct faturacao 
inserir parte numerica do prod na arvore 
caso produto nao exista criar array, caso contrario adicionar sale ao array
*/
void fillDataSale(Facturacao facturacao, Sales arrSale){
    for(int i =0; arrSale[i]; i++) {
        Product prod=getProductOfSale(arrSale[i]);
        int fstLetter = getIndexFromProdLetter(prod,0);
        int sndLetter = getIndexFromProdLetter(prod,1);
        GTree* tmp = facturacao->arrMonths[getMonthOfSale(arrSale[i])-1]->treeProd[fstLetter][sndLetter];
        gpointer key = getPointerFromIntFact(getProdNum(prod));
        gpointer tmptree = g_tree_lookup(tmp,key);
        GArray* arrProd = tmptree;
        if(arrProd == NULL) {
            arrProd = g_array_sized_new(FALSE, FALSE, sizeof(Sale),8);
            gpointer value = arrProd;
            g_tree_insert(tmp,key,value);
        }
        if(arrProd != NULL){
            gpointer tmpSale = cloneSale(arrSale[i]);            
            g_array_append_val(arrProd,tmpSale);
        }
    }
}
 
Facturacao initFactura(Sales arrSale){
    Facturacao facturacao = (Facturacao) malloc(sizeof(struct _facturacao));
    facturacao->arrMonths = calloc(12,sizeof(Month));
    initStructFact(facturacao->arrMonths);
    fillDataSale(facturacao,arrSale);
    return facturacao;
}
 
int totalUnitsSaleGArray(GArray* arr) {
    int sum = 0;
    for(int i=0; i<((int)arr->len);i++) {
        sum += getUnits(g_array_index(arr,Sale,i));
    }
    return sum;
}


//numero de unidades por sale vendidas
int totalSalesPerProd(Facturacao facturacao, Product prod) {
    int total = 0;
    int fstLetter = getIndexFromProdLetter(prod,0);
    int sndLetter = getIndexFromProdLetter(prod,1);
 for(int i=0; i<12; i++) {
        Month tmp = facturacao->arrMonths[i];
        GTree* tmpTree = tmp->treeProd[fstLetter][sndLetter];
        gpointer tmpGP = g_tree_lookup(tmpTree,getPointerFromIntFact(getProdNum(prod)));
        if(tmpGP != NULL) {
            GArray* tmpArr = (GArray*) tmpGP;
            total += totalUnitsSaleGArray(tmpArr);
        } 
    } 
    return total;
}


GArray* totalSalesPerProdPerMonth(Facturacao facturacao, Product prod , int month) {
    int fstLetter = getIndexFromProdLetter(prod,0);
    int sndLetter = getIndexFromProdLetter(prod,1);
    return g_tree_lookup(facturacao->arrMonths[month]->treeProd[fstLetter][sndLetter],getPointerFromIntFact(getProdNum(prod)));
}


/* Dado um intervalo fechado de meses, por exemplo [1..3], determinar o total de
vendas (nº de registos de venda) registadas nesse intervalo e o total facturado; */

gboolean traverseTotalSales (gpointer key, gpointer value , gpointer data) {
    (void)(key); //PARA SILENCIAR O WARNING DE NAO ESTAR A SER USADO
    GArray* tmp = value;
    infoVenda tmpinfo = data;
    
    for(int i =0; i<(int)tmp->len; i++) {
        (tmpinfo->nVenda) += getUnits(g_array_index(tmp,Sale,i));
        (tmpinfo->faturado) += (getUnits(g_array_index(tmp,Sale,i)) * getPrice(g_array_index(tmp,Sale,i)));
    }
    return FALSE;
}


double vendasEmIntervalo(Facturacao facturacao,int begin, int end, int *nVendas){
    double faturado = 0;
    int qt = 0;
    infoVenda infoVenda = malloc(sizeof(struct _infoVenda));
    for(int w=begin-1; w<=end-1;w++){
        for(int i = 0; i<26; i++) {
            for(int j = 0; j<26; j++){
                infoVenda->faturado = 0;
                infoVenda->nVenda = 0;
                g_tree_foreach(facturacao->arrMonths[w]->treeProd[i][j],(GTraverseFunc)traverseTotalSales , infoVenda);
                qt += infoVenda->nVenda;
                faturado += infoVenda->faturado;
            }
        }
    }
    *nVendas = qt;
    return faturado;
}




/* Dado um código de produto e uma filial, determinar os códigos (e número total)
dos clientes que o compraram, distinguindo entre compra N e compra P; 

 */


prodPClient clientsWhoBoughtThisProd(Product prod, int filial, Facturacao facturacao){
    prodPClient prodPClient = malloc(sizeof(struct _prodPorClient));
    prodPClient->arrOfClientP = g_array_new(FALSE, FALSE, sizeof(Client));
    prodPClient->arrOfClientN = g_array_new(FALSE, FALSE, sizeof(Client));
    int fstLetter = getIndexFromProdLetter(prod,0);
    int sndLetter = getIndexFromProdLetter(prod,1);
    for(int i=0;i<12;i++){
        GArray* tmp = g_tree_lookup(facturacao->arrMonths[i]->treeProd[fstLetter][sndLetter],getPointerFromIntFact(getProdNum(prod)));
        if(tmp!=NULL){
            for(int j=0;j<(int)(tmp->len);j++){
                if(getFilial(g_array_index(tmp,Sale,j))==filial){
                    gpointer tmpClient = cloneClient(getClientOfSale(g_array_index(tmp,Sale,j)));
                    if(getSaleType(g_array_index(tmp,Sale,j)) =='N'){
                        g_array_append_val(prodPClient->arrOfClientN,tmpClient);
                    } else {
                        g_array_append_val(prodPClient->arrOfClientP,tmpClient);
                    }   
                }
            }
        }
    }
  
return prodPClient;
}

int getNumBuyersForAProd(Facturacao facturacao, Product prod) {
    int numBuyers = 0;
    int fstLetter = getIndexFromProdLetter(prod,0);
    int sndLetter = getIndexFromProdLetter(prod,1);
    for(int i=0; i<12; i++) {
        GArray* tmp = g_tree_lookup(facturacao->arrMonths[i]->treeProd[fstLetter][sndLetter],getPointerFromIntFact(getProdNum(prod)));
        if(tmp!= NULL)
        numBuyers += tmp->len;
    }
    return numBuyers;
}


