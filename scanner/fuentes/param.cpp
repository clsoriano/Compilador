#include <stdio.h>
#include <string.h>
#include <iostream>
#include <cstdlib>
#include <vector>

using namespace std;

int obtParam(int n);

int obtParam(int n)
{
   char nombre[10]="param.txt", linea2[81];
   string val, namVar;
   string linea;
   FILE *fichero;
   size_t found;

   fichero = fopen( nombre, "r" );
   printf( "Fichero: %s -> ", nombre );
   if( fichero )
      printf( "existe (ABIERTO)\n" );
   else
   {
      printf( "Error (NO ABIERTO)\n" );
      return 1;
   }

   
   while(fgets(linea2, 81, fichero)!=NULL){
     linea = linea2;
     printf("%s", linea2);
     found = linea.find(";");
     namVar = linea.substr(0,found);
     val = linea.substr(found+1,linea.size());
     printf("%s\n",namVar.c_str());
     if(namVar=="MAXLINEA" && n == 1){
      printf("%d\n",atoi(val.c_str()));
      fclose(fichero);
      return atoi(val.c_str());
     }
     linea = "";
   }

   if( !fclose(fichero) )
      printf( "\nFichero cerrado\n" );
   else
   {
      printf( "\nError: fichero NO CERRADO\n" );
      return 1;
   }

   return 0;
}
