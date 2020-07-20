#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <gmodule.h>
#include "client.h"
#include "catClient.h"
#include "constants.h"

struct _catClient {
    GTree* tree[26]; 
};

gint compareClient(gconstpointer a, gconstpointer b);
void fillDataClient(catClient catClient, Clients client);
gpointer getPointerFromIntClient(int x);

void initGTreeClient(catClient catClient){
    for(int i = 0; i<26; i++){
            catClient->tree[i] = g_tree_new((GCompareFunc) compareClient);
    }
}

catClient initCatClient(Clients arrClient) {
    catClient catClient = malloc(sizeof(struct _catClient));
    initGTreeClient(catClient);
    fillDataClient(catClient,arrClient);
    return catClient;
} 


void fillDataClient(catClient catClient, Clients client) {
    for(int i =0; client[i] != NULL; i++) {
        
        int fstLetter = getClientIndexFromLetter(client[i]);
        GTree* tmp = catClient->tree[fstLetter];
        if(tmp != NULL) {
            gpointer value = cloneClient(client[i]);
            gpointer key = getPointerFromIntClient(getClientNum(client[i]));
            g_tree_insert(tmp,key,value);
        } 
    }
} 

gpointer getPointerFromIntClient(int x) {
    int* tmp = (int*) malloc(sizeof(int));
    *tmp = x;
    gpointer tp = tmp;
    return tp;
}

int clientExists(catClient catClient, Client client) {
    int fstLetter = getClientIndexFromLetter(client);
    gpointer key = getPointerFromIntClient(getClientNum(client));
    gpointer tmp = g_tree_lookup(catClient->tree[fstLetter],key);
    return tmp != NULL ? 1:0; 
}


/** COMPARA OS INTEIROS DOS CLIENTES E NAO OS CLIENTES */
gint compareClient(gconstpointer a, gconstpointer b) {
    return *((gint*)a) - *((gint*)b);
}


void markClientThatBought(catClient catClient, Client tmp, int filial) {
    Client client = g_tree_lookup(catClient->tree[getClientIndexFromLetter(tmp)],getPointerFromIntClient(getClientNum(tmp)));
    setComprasFromFilial(client,filial);
}


gboolean traverseClientsWhoNeverBought (gpointer key, gpointer value , gpointer data) {
    (void)(key); //PARA SILENCIAR O WARNING DE NAO ESTAR A SER USADO
    if(hasClientBought(value) == 0) g_array_append_val(data,value);
    return FALSE;
}

GArray* clientsThatNeverBought(catClient catClient) {
    GArray* clients = g_array_new(FALSE,FALSE,sizeof(Client));
    for(int i = 0; i<26; i++) {
        g_tree_foreach(catClient->tree[i],(GTraverseFunc) traverseClientsWhoNeverBought , clients);
    }
    return clients;
}

gboolean traverseClientsWhoBoughtInAll (gpointer key, gpointer value , gpointer data) {
    (void)(key); //PARA SILENCIAR O WARNING DE NAO ESTAR A SER USADO
    if(clientBoughtInAll(value) == 1) g_array_append_val(data,value);
    return FALSE;
}

GArray* clientsThatBoughtInAllFilials(catClient catClient) {
    GArray* clients = g_array_new(FALSE,FALSE,sizeof(Client));
    for(int i = 0; i<26; i++) {
        g_tree_foreach(catClient->tree[i],(GTraverseFunc) traverseClientsWhoBoughtInAll , clients);
    }
    return clients;
}





