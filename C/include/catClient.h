#ifndef __CATCLIENT_H
#define __CATCLIENT_H
#include "client.h"
#include <gmodule.h>

//apontador para a struct 
typedef struct _catClient* catClient;

//função responsável por iniciar toda a estrutura do catálogo de clientes
catClient initCatClient(Clients clients);

//verifica se um dado cliente ja se encontra na estrutura
int clientExists(catClient catClient, Client client);

//funçao que devolve o array de clientes que nunca fizeram compras
GArray* clientsThatNeverBought(catClient catClient);

//marca o client que comprou na filial
void markClientThatBought(catClient catClient, Client tmp, int filial);

//devolve array com os clientes que fizeram compras em todas as filiais
GArray* clientsThatBoughtInAllFilials(catClient catClient);
#endif
