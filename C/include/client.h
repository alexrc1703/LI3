#ifndef __CLIENT_H
#define __CLIENT_H

//apontador para criar listas de estruturas do tipo Client
typedef struct _client** Clients;

//apontador para a struct  
typedef struct _client* Client;

//verifica se um codigo de um cliente é valido
int validate_Client(Client client);

//devolve o campo string de um cliente
char* getClientStr(Client client);

//elimina todos os elementos de um array de clientes
void freeClients(Clients arrayOfClient);

//devolve o campo da parte numerica de um codigo de cliente
int getClientNum(Client client);

//cria um cliente 
Client constructClient(char* clientStr);

//devolve um inteiro que serve para indexação do cliente
int getClientIndexFromLetter(Client client);

//funcao que clona clientes para garantir encapsulamento
Client cloneClient(Client client);

//inicia o povoamento da struct 
Clients initClients();

//adiciona ao array a indentificacao da filial 
void setComprasFromFilial(Client client , int filial);

//devolve o numero de compras feitas numa determinada filial por um  determinado cliente
int getComprasFromFilial(Client client , int filial);

//verifica se o cliente fez compras em alguma filial
int hasClientBought(Client client);

//verifica se o cliente fez compras em todas as filiais
int clientBoughtInAll(Client client);

#endif
