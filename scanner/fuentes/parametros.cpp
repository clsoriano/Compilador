int obtParam(int n){
   char nombre[10]="param.txt", linea2[81];
   string val, namVar;
   string lineaParam;
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
     lineaParam = linea2;
     printf("%s", linea2);
     found = lineaParam.find(":");
     namVar = lineaParam.substr(0,found);
     val = lineaParam.substr(found+1,lineaParam.size());
     printf("%s\n",namVar.c_str());
     if(namVar=="MAXLINEA" && n==1){
      printf("%d\n",atoi(val.c_str()));
      fclose(fichero);
      return atoi(val.c_str());
     }else if(namVar=="MAXLINEA" && n==1){{
     }
     lineaParam = "";
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
