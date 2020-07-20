#ifndef __STRINGBROWSER_H
#define __STRINGBROWSER_H
typedef struct _stringsbrowser* StringsBrowser;

StringsBrowser initbrowser(int l,int c); // inicia o browser com o numero de colunas e linhas desejado
void SetBrowserInfo(StringsBrowser browser,char* arr[],int tamanho); // insere a informação do array no browser
void imprimePagina(StringsBrowser browser); // imprime a pagina que está neste momento
void ProximaPagina(StringsBrowser browser); // imprime a proxima pagina
void PaginaAnterior(StringsBrowser browser); // imprime a pagina anterior
void freeBrowser(StringsBrowser browser);
#endif
