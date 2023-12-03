### Correção de erro no Lexer.java

#### Problema
O arquivo `Lexer.java` está apresentando um problema em que a entrada `1var1` está sendo reconhecida como o nome de uma variável, embora deveria resultar em um erro, uma vez que uma variável não pode iniciar com um número.

#### Saída Atual do Código
```
Processed input: int 1var1;
Tokens: [(TYPE_SPECIFIER, int), (NUM, 1), (IDENTIFIER, var1)]
```

#### Saída Esperada
```
ERROR: 1var1, variável não pode ser iniciada com caractere numérico.
```
### Estado Atual do Lexer.java

O arquivo `Lexer.java` está em processo de correção do erro na análise léxica da declaração de variáveis. Após a resolução desse problema, a análise léxica estará completa para essa rotina.