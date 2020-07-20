#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "stringsBrowser.h"

struct _stringsbrowser
{
    char** bString;
    int linha;
    int colunas;
    int cursor;
    int pagina;
    int tamanho;
};

void allocaBrowser(StringsBrowser browser,char* arr[],int tamanho)
{
    int i;
    browser->bString = calloc(tamanho,sizeof(char*));
    for(i = 0; i< tamanho; i++)
    {
        browser->bString[i] = strdup(arr[i]);
    }
    browser->tamanho = tamanho;


}


// dá free no array do browser
void freeBrowser(StringsBrowser browser)
{
    int tamanho = browser->tamanho;
    int i;
    for(i=0;i < tamanho;i++)
    {
        free(browser->bString[i]);
    }
    free(browser);
}

// cria o array do browser dado um certo array
// verificar se o browser está ocupado e se estiver dar free dele e dps alocar direito
void SetBrowserInfo(StringsBrowser browser,char* arr[],int tamanho)
{
    if(tamanho == 0){return;}
    if(browser->tamanho == 0)
    {
        allocaBrowser(browser,arr,tamanho);
    }
    else
    {
        freeBrowser(browser);
        allocaBrowser(browser,arr,tamanho);
    }
    
}

//navega o array e imprime uma pagina dos seus elementos
void navegador(StringsBrowser browser)
{
    
    int c,l,colunas;
    int count = (browser->pagina) * (browser->colunas) * (browser->linha);
    int tmp = count;
    colunas = browser->colunas - 1;
    for(l = 0;l < (browser->linha) ; l++)
    {   
        // printf na linha indicada
        int fds = 1;
        int col = 0;
        if((tmp < browser->tamanho) && (browser->bString[count] != NULL)){
        printf("%s     ",(char*)browser->bString[count]);
        count++;
        tmp++;
        }
        else
        {
            printf("\n");
            return;
        }
        
        if(browser->tamanho > browser->linha)
        {
            for(c = 0; c < colunas; c++)
            {
                //printf da coluna indicada
                fds = (c+1)*(browser->linha);
                col = count + fds - 1;
                if((col < browser->tamanho) && (browser->bString[col] != NULL)){
                printf("%s     ",(char*)browser->bString[col]);
                tmp++;
                }
            }
        }
        printf("\n");
    }
    printf("\n");
}


//inicia o browser 
StringsBrowser initbrowser(int x,int y)
{
    StringsBrowser browser = (StringsBrowser) malloc(sizeof(struct _stringsbrowser));
    browser->linha = x;
    browser->colunas = y;
    browser->cursor = 0;
    browser->pagina = 0;
    browser->tamanho = 0;
    return browser;
    
}

// imprime a pagina que estás correntemente
void imprimePagina(StringsBrowser browser)
{
    navegador(browser);
}


// calcula o numero maximo de paginas que o array pode ter com o numero de colunas corrente
int maxpag(StringsBrowser browser)
{
    int max,percentagem;
    max = (browser->tamanho) / ((browser->linha) * ((browser->colunas)));
    percentagem = (browser->tamanho) % ((browser->linha) * (browser->colunas));
    if(percentagem == 0){max--;}
    return max;
}

// imprime a pagina seguinte se for possivel
void ProximaPagina(StringsBrowser browser){
    // pode fazer smp prox pagina msm que ja nao tenha nada e dps da merda por isso vereficar se pode ir para a prox
    int max = maxpag(browser);
    if(browser->pagina < max){
    browser->pagina++;
    navegador(browser);
    }
    else{
        printf("Ja estas na ultima pagina\n");
        navegador(browser);
    }
} 

// imprime a pagina anterior se for possivel
void PaginaAnterior(StringsBrowser browser){
    if(browser->pagina == 0)
    {
        printf("Ja estas na primeira pagina\n");
        navegador(browser);
    }
    else
    {
        browser->pagina--;
        browser->cursor = (browser->pagina) * (browser->colunas) * (browser->linha);
        navegador(browser);
    }
}
void setLinhas(StringsBrowser browser,int x)
{
    browser->linha = x;
}

void setColunas(StringsBrowser browser,int y)
{
    browser->colunas = y;
}


