Resolver erro no Lexer.java:
 1var1 deveria ser reconhecido como nome de uma variavel e deveria dá erro, pois uma variavel nao pode iniciar com número. 

Saída do código:

Processed input: int 1var1;
Tokens: [(TYPE_SPECIFIER, int), (NUM, 1), (IDENTIFIER, var1)]

Saída esperada:

ERROR: 1var1, variavel não pode ser iniciada com caractere númerico.

Estado atual do Lexer.java:
Resolvendo erro a anáise léxica da declaração de variaveis estará completa

