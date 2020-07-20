#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "timer.h"
#include "sgv.h"
#include "queries.h"
#include "constants.h"
#include "stringsBrowser.h"

void navegaStrings(char** arr,int tamanho,int linhas, int colunas);
void header(Timer timer) {
    printf("\n");
    printf("             PROGRAMA DE GESTAO DE VENDAS REALIZADO POR JOSE SANTOS , ALEXANDRE COSTA e PEDRO QUEIROS                           Tempo Decorrido: %.2f segundos\n\n", calculateTime(timer));
}
SGV nop(SGV sgv){
    return sgv;
}


void query1(SGV sgv) {
    char letter;
    int p;
    printf("Insira a letra:\n");
    p = scanf(" %c",&letter);
    while(letter < 'A' || letter > 'Z') {
    printf("Insira uma letra compreendida entre A e Z \n");
    p = scanf(" %c",&letter);
    }
    Timer timer = initTimer();
    int tamanho = 0;
    char** arr;
    arr = getProducsThatStartWithLetter(sgv,letter,&tamanho);
    if(arr != NULL) {
        navegaStrings(arr,tamanho,10,15);
    } else {
        printf("Não existe nenhum produto com esta letra %c\n",letter);
    }
    p++;
    finishTimer(timer);
    header(timer);
    free(arr);
    free(timer); 
}


void query2(SGV sgv) {
    int mes = 0 ;
    char str[10];
    char** arr;
    int p;
    Timer timer = initTimer();
    printf("Insira o codigo do Produto e o mes(EX: AB1094 9)\n");
    p = scanf(" %s %d", str, &mes);
    while(mes <0 && mes >12) {
    p = scanf(" %s %d", str, &mes);
    }
    Product tmp = constructProd(str);
    printf("0 - Ver de todas as filiais\n");
    printf("(1-%d) - Ver uma filial em especifico\n",NUM_FILIAIS);
    int choice = 0;
    do {
        p = scanf(" %d",&choice);
    }while(choice > NUM_FILIAIS);
    arr = totalPerMonthPerProd(sgv,tmp,mes-1,choice); 
    finishTimer(timer);
    header(timer); 
    if(arr != NULL){
        navegaStrings(arr,2,2,1);
    } else {
        printf("Ninguem comprou o Produto %s na Filial %d\n",getProdStr(tmp), choice);
    }
    p++;
    free(timer);
    free(arr);
}

void query3(SGV sgv) {
    char** arr;
    int tamanho;
    Timer timer = initTimer();
    arr = getProductsThatNeverBoughtString(sgv,&tamanho);
    finishTimer(timer);    
    header(timer); 
    if(arr != NULL) {
    navegaStrings(arr,tamanho,10,15);
    }
    free(timer);
    free(arr);
}

void query4(SGV sgv) {
    char** arr;
    int tamanho;
    Timer timer = initTimer();
    arr = getClientsThatBoughtInAllFilialsString(sgv,&tamanho);
    finishTimer(timer);
    header(timer); 
    navegaStrings(arr,tamanho,10,15);
    free(timer);
    free(arr);
}

void query5(SGV sgv) {
    Timer timer = initTimer();
    int res = getClientsThatNeverBoughtNum(sgv);
    int res1 = getProductsThatNeverBoughtNum(sgv);
    finishTimer(timer);
    header(timer); 
    printf("N Clientes que nunca compraram: %d , N Produtos que nunca foram comprados: %d\n",res,res1);
    free(timer);
}

void query6(SGV sgv) {
    char bufClient[TAM_STR_CLT];
    char** arr;
    int tamanho=0;
    int p;
    int filial;
    printf("Escreva o Codigo de Cliente seguido da filial (EX: A1983 2)\n");
    p = scanf(" %s %d",bufClient,&filial);
    while(filial > NUM_FILIAIS && filial < 0) {
        printf("Insira um valor para a filial entre 1 e %d\n",NUM_FILIAIS);
        p = scanf(" %d",&filial);
    }
    Client tmpClient = constructClient(bufClient);
    Timer timer = initTimer();
    arr = getProductsBoughtFromClientPerMonth(sgv,&tamanho,filial,tmpClient);
    finishTimer(timer);
    header(timer); 
    if(arr != NULL) {
        navegaStrings(arr,tamanho,12,1);
    } else {
        printf("O Cliente %s não comprou nesta filial\n",getClientStr(tmpClient));
    }
    p++;
    free(timer);
    free(arr);
}

void query7(SGV sgv) {
    Timer timer = initTimer();
    int i,j,p;
    int totalVendas= 0;;
    printf("Insira o intervalo do mês (EX: 9 12)\n");
    p = scanf(" %d %d",&i , &j);
    double res = vendasBetweenMonths(sgv, i, j,&totalVendas);
    finishTimer(timer);
    header(timer); 
    printf("Resultado: Vendas: %d , Faturado:%.2f \n",totalVendas,res);
    free(timer);
    p++;
}

void query8(SGV sgv) {
    char** arr;
    int tamanho=0,filial=0,p;
    char buffer[TAM_STR_PROD];
    printf("Insira o Codigo do Produto seguido com a Filial (EX: AB1049 3)\n");
   
    p = scanf(" %s %d",buffer,&filial);
    while (filial > NUM_FILIAIS){
        printf("Essa filial não existe insira um número entre 1 e %d\n", NUM_FILIAIS);
        p = scanf(" %s %d",buffer,&filial);
    }
    Product tmpProd = constructProd(buffer);
    Timer timer = initTimer();
    arr = clientesCompraramProd(sgv,tmpProd,filial,&tamanho);
    finishTimer(timer);
    header(timer); 
    if(arr != NULL && tamanho != 0){
        navegaStrings(arr,tamanho,2,5);
    } else {
        printf("Esse Produto %s , nunca foi comprado nesta filial %d\n",getProdStr(tmpProd),filial);
    }
    p++;
    free(timer);
    free(arr);
}

void query9(SGV sgv) {
    char** arr;
    int tamanho=0,month=0,p;
    char buffer[TAM_STR_CLT];
    printf("Insira o Codigo do Cliente seguido com o mes (EX: A1049 3)\n");
   
    p = scanf(" %s %d",buffer,&month);
    while (month >= 12 && month <= 1){
        printf("Esse mes não existe insira um número entre 1 e 12\n");
        p = scanf(" %s %d",buffer,&month);
    }
    Client tmpClient = constructClient(buffer);
    Timer timer = initTimer();
    arr = getCodProductsBoughFromClientPerMonth(sgv,&tamanho,tmpClient,month);
    finishTimer(timer);
    header(timer); 
    if(arr != NULL && tamanho != 0){
        navegaStrings(arr,tamanho,10,1);
    } else {
        printf("Este Cliente %s , nunca comprou neste mes %d\n",getClientStr(tmpClient),month);
        p = scanf(" %d", &month);
    }
    p++;
    free(timer);
    free(arr);   
}


void query10(SGV sgv) {
    int n,p;
    printf("Quantos produtos quer ver?\n");
    p = scanf(" %d",&n);
    Timer timer = initTimer();
    char** arr = getNMostSellProducts(sgv,n);
    finishTimer(timer);
    header(timer); 
    if(arr != NULL) {
      navegaStrings(arr,n,10,1);
    }
    p++;
    free(timer);
}


void query11(SGV sgv) {
    char** arr;
    int tamanho=3,p=0;
    char buffer[TAM_STR_CLT];
    printf("Insira o Codigo do Cliente (EX: A1049):\n");
   
    p = scanf(" %s",buffer);

    Client tmpClient = constructClient(buffer);
    Timer timer = initTimer();
    arr = get3MostBoughtProductsPerClient(sgv,tmpClient);
    finishTimer(timer);
    header(timer); 
    if(arr != NULL && tamanho != 0){
        navegaStrings(arr,tamanho,10,1);
    } else {
        printf("Este Cliente %s , nunca comprou neste ano\n",getClientStr(tmpClient));
        p = scanf(" %d", &tamanho);
    }
    p++;
    free(timer);
    free(arr);       
}

void menu() {
    printf("1  - Produtos começados por uma determinada Letra\n");
    printf("2  - Dado um mês e um código de produto,determinar e apresentar o número total de vendas e o total facturado com esse produto em tal mês\n");
    printf("3  - Lista ordenada dos produtos que ninguém comprou \n");
    printf("4  - Lista ordenada dos clientes que compraram em todas as filiais \n");
    printf("5  - Determinar o número de clientes registados que não realizaram compras bem como o número de produtos que ninguém comprou. \n");
    printf("6  - Total de produtos comprados por um cliente(mês a mês) \n");
    printf("7  - Determinar o total de vendas e o total facturado num intervalo de meses \n");
    printf("8  - Determinar os códigos dos clientes que compraram um dado produto de uma dada filial\n");
    printf("9 - Determinar a lista de códigos de produtos que um dado cliente mais comprou por quantidade(ordem decrescente)\n");
    printf("10 - Produtos mais vendidos em todo o ano, indicando o nº de clientes e de unidades vendidas, filial a filial\n");
    printf("11 - Determinar quais os códigos de 3 produtos em que um dado cliente mais gastou dinheiro durante o ano\n");
}


void listenForInput(SGV sgv) {
    int menu = 0,p;
    p = scanf("%d",&menu);
    switch(menu) {
        case 0:
            exit(0);
            break;
        case 1:
            query1(sgv);
            break;
        case 2:
            query2(sgv);
            break;
        case 3:
            query3(sgv);
            break;
        case 4:
            query4(sgv);
            break;
        case 5:
            query5(sgv);
            break;
        case 6:
            query6(sgv);
            break;
        case 7:
            query7(sgv);
            break;
        case 8:
            query8(sgv);
            break;
        case 9:
            query9(sgv);
            break;
        case 10:
            query10(sgv);
            break;
        case 11:
            query11(sgv);
            break;          
    }
    p++;
}


// inicia o programa em geral
void init() {
    printf("A Ler Dados:");
    fflush(stdout);
    Timer timer = initTimer();
    SGV sgv = initSGV();
    finishTimer(timer);
    printf("  Dados Lidos\n");
    
    while(1){
        header(timer);
        menu();
        listenForInput(sgv);
    };
}

//TODO DAR FREE AO NAVEGADOR DE STRINGS
void navegaStrings(char** arr,int tamanho,int linhas, int colunas) {
    int numero = 300,p=0;
    if(tamanho > 100)
    {
        colunas = 15;
    }
    StringsBrowser browser;
    browser = initbrowser(linhas,colunas);
    SetBrowserInfo(browser,arr,tamanho);
    printf("\n");
    imprimePagina(browser);
    while(numero != 9) {
        printf("Total de resultados da query:%d\n", tamanho);
        printf("\n");
        printf("Insira 0 para imprimir a pagina anterior\n");
        printf("Insira 1 para imprimir a proxima pagina\n");
        printf("Insira 9 para terminar \n");
        printf("\n");
        p = scanf(" %d",&numero);
        if(numero == 0){
            printf("\n");
            PaginaAnterior(browser);
        }
        else if(numero == 1){
            printf("\n");
            ProximaPagina(browser);
        }
        
    }
    p++;
    freeBrowser(browser);
}


int main() {
    init();
    return 0;
}
