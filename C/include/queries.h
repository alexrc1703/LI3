#ifndef __QUERIES_H
#define __QUERIES_H
int getClientsThatNeverBoughtNum(SGV sgv);// querie que ve o numero de clientes que nunca compraram
int getProductsThatNeverBoughtNum(SGV sgv); // querie que ve o numero de produtos que nunca foram comprados
char** getProductsThatNeverBoughtString(SGV sgv,int *tamanho); // dá uma array com os produtos que nunca foram comprados
char** getProducsThatStartWithLetter(SGV sgv, char letter, int *tamanho); // dá um array com os produtos começados por uma certa letra
char** totalPerMonthPerProd(SGV sgv, Product prod ,int month,int flag); // dá um array com os produtos de um certo mes
double  vendasBetweenMonths(SGV sgv,int ini,int fim, int *totalVendas); // dá as vendas num dado intervalo de tempo
char** clientesCompraramProd(SGV sgv, Product prod, int filial, int* tamanho); // clientes que compraram um certo produto numa dada filial
char** getClientsThatBoughtInAllFilialsString(SGV sgv,int* tamanho); // dá um array com os clientes que nunca compraram em qualqquer da filiais
char** getProductsBoughtFromClientPerMonth(SGV sgv, int *tamanho,int filial,Client client); // dá um array com os produtos comprados por um cliente num dado mes
char** getCodProductsBoughFromClientPerMonth(SGV sgv, int *tamanho, Client client, int month);// dá o codigo de produtos comprados por o cliente num dado mes
char** get3MostBoughtProductsPerClient(SGV sgv,Client client); // dá os 3 produtos mais comprados por um dado cliente
char** getNMostSellProducts(SGV sgv,int n); // dá os n produtos mais vendidos
#endif
