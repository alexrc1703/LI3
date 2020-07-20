#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "client.h"
#include "constants.h"
struct _client{
    char* clientStr;
    int codClient;
    int compras[3];
};


static int valClient = 0;

Clients loadClientsAndVerify();

Clients initClients() {
    return loadClientsAndVerify();
}

char* getClientStr(Client client) {
    return strdup(client->clientStr);
}

int getClientNum (Client client) {
    return client->codClient;
}

Client constructClient(char* clientStr) {
    char numstr[5]; 
    strncpy(numstr,clientStr+1,4);
    numstr[4] = '\0';
    int num = atoi(numstr);
    Client tmp = (Client) malloc(sizeof(struct _client));
    tmp->clientStr = strdup(clientStr);
    tmp->codClient = num;
    for(int i=0;i<NUM_FILIAIS; i++) tmp->compras[i] = 0;
    return tmp;
}


Client cloneClient(Client client){
    Client tmp = constructClient(getClientStr(client));
    for(int i=0;i<NUM_FILIAIS;i++){
        tmp->compras[i]=client->compras[i];
    }
    return tmp;
}


int validate_Client(Client client) {
    if(isupper(client->clientStr[0]) && client->codClient <= 5000 && client->codClient >= 1000) return 1;
    return 0;
} 

// procura se um codigo de cliente das vendas é valido
int isClientInData(Client arrClient[], char *clientToValidate) {
    Client tmp = constructClient(clientToValidate);
    if(validate_Client(tmp) == 1){
        for(int i = 0; arrClient[i]; i++) {
            if(strcmp(arrClient[i]->clientStr,clientToValidate) == 0) return 1;
        }
    }
    return 0;
}

int numClientComecadoPor(Client clientArr[], char initLetter) {
    int res = 0;
    for(int i=0;clientArr[i] != NULL; i++) {
        if(clientArr[i]->clientStr[0] == initLetter) res++;
    } 
    return res;
}



Clients loadClientsAndVerify() {
    Clients arrClient = malloc(TAM_LENGHT_CLT*(sizeof(Client)));
    char str[TAM_STR_CLT] = "";
    FILE* fp= fopen(CLIENTPATH,"r");
    if( fp == NULL) return 0;
    while(fgets(str,TAM_STR_CLT,fp)) {
        Client tmp = constructClient(strtok(str,"\n\r"));
        if(validate_Client(tmp) == 1) { 
            arrClient[valClient] = tmp;
            valClient++;
        }
    }
    fclose(fp); 
    return arrClient;
}

char getClientLetter(Client client){
    return(client->clientStr[0]);
}

int getClientIndexFromLetter(Client client) {
    return getClientLetter(client) - 'A';
}

 
void freeClients(Clients arrayOfClient){
    for(int i=0; arrayOfClient[i] != NULL; i++) {
        free(arrayOfClient[i]);
    } 
    free(arrayOfClient);
}


void setComprasFromFilial(Client client , int filial) {
    client->compras[filial]++;
}

int getComprasFromFilial(Client client , int filial) {
    return client->compras[filial];
}

int hasClientBought(Client client) {
    int flag = 0;
    for(int i = 0; i<NUM_FILIAIS && flag == 0; i++) {
        if(getComprasFromFilial(client,i) > 0) flag = 1;
    }
    return flag;
}

int clientBoughtInAll(Client client) {
    int flag = 1;
    for(int i = 0; i<NUM_FILIAIS && flag == 0; i++) {
        if(getComprasFromFilial(client,i) == 0) flag = 0;
    }
    return flag;
}
