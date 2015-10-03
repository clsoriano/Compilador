
//se define e inicializa la tabla de lexemes correspondientes a las palabras reservadas
char *lexpal[MAXPAL]={"REPORT","WRITE", "SELECT", "FROM", "WHERE", "CLASS", "WITH", "INTO", "REPLACE", "USERPROMPT"};

//el token
enum simbolo token;

//se define e inicializa la tabla de tokens de palabras reservadas
enum simbolo tokpal [MAXPAL]={reporttok,writetok,selecttok,fromtok,wheretok,classtok,withtok,intotok,replacetok,userpromtok};

//tabla de tokens correspondientes a operadores y símbolos especiales
enum simbolo espec[255] ;

