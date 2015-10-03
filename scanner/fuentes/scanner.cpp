//el analizador lexicográfico de pl0
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>
//#include "scanner.h"
//#include "abap.h"
//#include "lexico.h"
//#include "auxiliares.h"

using namespace std;

int obtch(),getline(char s[],int lim);
int obtParam(int n); //funciones internas a scanner.cpp

int MAXLIN = obtParam(1);
int MAXDIG = obtParam(2);
int MAXIDD = obtParam(3);
char linea[MAXLINEA];     //buffer de líneas 
int ll;                   //contador de caracteres
int offset;               //corrimiento en la lectura de los caracteres del programa fuente
int fin_de_archivo;       //bandera de fin de archivo (obtch)   
int ch;                   //último caracter leído
char lex[MAXID+1];        //último lexeme leído ( +1 para colocar "\0")
long int valor ;          //valor numérico de una lexeme correspondiene a un número

//obtoken: obtiene el siguiente token del programa fuente                   
void obtoken()
{
 char lexid[MAXID+1]; //+1 para colocar el marcador "\0"
 int i,j;
 int ok=0;


 //quitar blancos, caracter de cambio de línea y tabuladores
 while (ch==' ' || ch=='\n' || ch=='\t') ch=obtch() ;

 //si la lexeme comienza con una letra, es identificador o palabra reservada
 if (isalpha(ch)) {
    lexid[0]=ch;
    i=1;
    while ( isalpha( (ch=obtch()) ) ||  isdigit(ch)   ) 
      if (i<MAXID) lexid[i++]=ch;
    lexid[i]='\0';
  
    //¿es identificador o palabra reservada?.buscar en la tabla de palabras reservadas
	//una búsqueda lineal que tendrá que ser sustituída por otro tipo de búsqueda más efectiva. 
	//...en esa nueva búsqueda desaparecerá el "break"
    for (j=0;j<MAXPAL;++j) 
        if (strcmp(lexid,lexpal[j])==0) {
	       ok=1;
	       break;
        }
    int centro,inf=0,sup=MAXPAL-1;
    while(inf<=sup){
      centro=((sup-inf)/2)+inf;
      if(lexpal[centro]==lexid){
            ok=1;
            //printf("el dato se encontro en la posicion: %d", centro);
            //return centro;
      }
      else if(lexid < lexpal[centro]) sup=centro-1;
      else                           inf=centro+1;
   }        

    if (ok==1) 
       token=tokpal[j]; //es palabra reservada
    else
       token=ident; //es identificador
 
    strcpy(lex,lexid); //copiar en lex
 }
 else //si comienza con un dígito...
    if (isdigit(ch)) {
       lexid[0]=ch;
       i=j=1;
       while ( isdigit( (ch=obtch()))) {
	         if (i<MAXDIG) lexid[i++]=ch;
	         j++;
       }
       lexid[i]='\0';
       if (j>MAXDIG)
          error(30); //este número es demasiado grande
       token=numero;
       valor=atol(lexid); //valor numérico de una lexeme correspondiene a un número	        
    }
    else //reconocimiento de símbolos especiales, incluyendo pares de simbolos (aplicamos "lookahead symbol technique")
       if (ch=='<') {
          ch=obtch();
          if (ch=='=') {
             token=mei;
             ch=obtch();
          }
          else
             if (ch=='>') {
                token=nig;
                ch=obtch();
             }
             else
                token=mnr;
       }
       else
          if (ch=='>') {
             ch=obtch();
             if (ch=='=') {
                token=mai;
                ch=obtch();
             }
             else 
                token=myr;
          }
          else 
             if (ch==':') {
                ch=obtch();
                if (ch=='=') {
	               token=asignacion;
	               ch=obtch();
                }
               else
	               token=nulo;
             }
             else {
                token=espec[ch]; //hashing directo en la tabla de tokens de símbolos especiales del lenguaje
                ch=obtch();
             }
}

//obtch: obtiene el siguiente caracter del programa fuente
int obtch()
{ 

 if (fin_de_archivo==1) {
	fclose(fp);//cerrar el programa fuente
    printf("Analisis lexicografico finalizado.\n");
    exit(1); //salir...
 }
  
 if (offset==ll-1) {
    ll=getline(linea,MAXLIN); //trae una línea del programa fuente al buffer de líneas
    if (ll==0) 	   
       fin_de_archivo=1; //se retrasa en un blanco la deteccion de EOF, porque obtoken lleva un caracter adelantado. si no, en 
						 //algunos casos tendríamos problemas, por ejemplo: no se reconoceria el punto final del programa (...end.)

    printf("\n%s",linea);
    offset=-1;
 }

 ++offset;

 if ( (linea[offset]=='\0') || (fin_de_archivo==1) )   
    return(' '); 
 else  
    return(toupper(linea[offset])); //de esto depende si el lenguaje es sensitivo de mayúsculas o no.

}

//getline: lee la siguiente línea del fuente y regresa su tamaño (incluyendo '\n') o 0 si EOF. (por ejemplo: para VAR regresa 4)
//es probablemente la rutina de más bajo nivel del compilador
//tomada de "El Lenguaje de programación C" - Kernighan & Ritchie - pag 28                        
int getline(char s[],int lim)
{
 int c,i;

 for (i=0;i<lim-1 && (c=getc(fp))!=EOF && c!='\n';++i)
     s[i]=c;

 if (c=='\n') {
    s[i]=c;
    ++i;
 }

 s[i]='\0';
 return (i);
}



int obtParam(int n)
{
   char nombre[10]="param.txt", linea2[81];
   string val, namVar;
   string linea;
   FILE *fichero;
   size_t found;

   fichero = fopen( nombre, "r" );
   
   while(fgets(linea2, 81, fichero)!=NULL){
     linea = linea2;
     found = linea.find(":");
     namVar = linea.substr(0,found);
     val = linea.substr(found+1,linea.size());
     if(namVar=="MAXLINEA" && n == 1){
      fclose(fichero);
      return atoi(val.c_str());
     }else if (namVar=="MAXDIGIT" && n == 2){
      fclose(fichero);
      return atoi(val.c_str());
     }
     linea = "";
   }

   return 0;
}
