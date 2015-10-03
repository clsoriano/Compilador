#include "parametros.h"

//lexemes de las palabras reservadas
extern char *lexpal[MAXPAL];

//lista de tokens de ABAP
enum simbolo {nulo,ident,numero,mas,menos,por,barra,oddtok,igl,nig,mnr,mei,myr,mai,parena,parenc,coma,puntoycoma,
	          punto,asignacion,reporttok,writetok,selecttok,fromtok,wheretok,classtok,withtok,intotok,replacetok,userpromtok}; //definido aquí en el encabezado

extern enum simbolo token;

//tabla de tokens de palabras reservadas
extern enum simbolo tokpal [MAXPAL]; 

//tabla de tokens correspondientes a operadores y símbolos especiales
extern enum simbolo espec[255] ;

