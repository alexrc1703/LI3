#ifndef __CATPROD_H
#define __CATPROD_H
#include "product.h"
#include "gmodule.h"

//apontador para a estrutura
typedef struct _catProd* catProd;

// inicia o povoamento do catalogo de produtos
catProd initCatProd(Products prod);

//verifica se o produto ja se encontra na arvore
int prodExists(catProd catProd, Product prod);

//marca produto comprado numa determinada filial
void markProductThatBought(catProd catProd, Product tmp, int filial,int qt);

//devolve array com os produtos nunca comprados
GArray* prodsThatNeverBought(catProd catProd);

//array de produtos que começa, com a letra letter
GArray* listOfProductsThatStartWithLetter(catProd catProd , char letter);

//array dos produtos mais vendidos
GArray* prodsMostSell(catProd catProd, int n);
#endif
