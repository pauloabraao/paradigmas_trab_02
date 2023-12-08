import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class Parser {
    public static int nextToken = 0;
    public static void main(String[] args) {

        TokenType[] tokenTypes;
        TokenType[] tokenArray;
        String[] lexemeArray;
        
        try {
            Path filePath = Paths.get("file.txt");
            byte[] fileBytes = Files.readAllBytes(filePath);
            String fileContent = new String(fileBytes);
            String processedInput = Lexer.processInput(fileContent);
            System.out.println("Processed input: " + processedInput);

            // Get an array of TokenType values using the Lexer class
            tokenTypes = Lexer.getTokenTypes(processedInput);
            System.out.println("Token Types: " + java.util.Arrays.toString(tokenTypes));
            tokenArray =  tokenTypes;
            lexemeArray = Lexer.getTokenValue(processedInput);

           
            isMainDeclaration(tokenArray, lexemeArray);
            isVarDeclaration(tokenArray, lexemeArray);
            isExpression(tokenArray, lexemeArray);
            isIfStatement(tokenArray, lexemeArray);
            isAssigment(tokenArray, lexemeArray);
            
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
        }
  
    }

    public static void isExpression(TokenType[] tokenArray, String[] lexemeArray) {
        while (tokenArray.length > nextToken && 
        (tokenArray[nextToken].equals(TokenType.NUM) || tokenArray[nextToken].equals(TokenType.IDENTIFIER)) && 
        (tokenArray[nextToken + 1].equals(TokenType.SUM_OP) || 
        tokenArray[nextToken + 1].equals(TokenType.SUB_OP))) {  
            expression(tokenArray, lexemeArray);
            System.out.println("\n");
            nextToken++;
        }
    }

    public static void isMainDeclaration(TokenType[] tokenArray, String[] lexemeArray) {

        while (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.TYPE_SPECIFIER) && tokenArray[nextToken + 1].equals(TokenType.MAIN)) {  
            program(tokenArray, lexemeArray);
            System.out.println("\n");
            nextToken++;
        }
    }

    public static void isIfStatement(TokenType[] tokenArray, String[] lexemeArray) {

        while (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.IF_STATEMENT)) {  
            if_statement(tokenArray, lexemeArray);
            System.out.println("\n");
            nextToken++;
        }
    }

    public static void isVarDeclaration(TokenType[] tokenArray, String[] lexemeArray) {

        while (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.TYPE_SPECIFIER) && !tokenArray[nextToken + 1].equals(TokenType.MAIN)) {  
            declaration(tokenArray, lexemeArray);
            System.out.println("\n");
            nextToken++;
        }
    }

    public static void isAssigment(TokenType[] tokenArray, String[] lexemeArray) {

            while (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.IDENTIFIER)) {  
                assignment_statement(tokenArray, lexemeArray);
                System.out.println("\n");
                nextToken++;
        }
    }

    public static void declaration(TokenType[] tokenArray, String[] lexemeArray) {

        System.out.println("Enter <declaration>");
        type_specifier(tokenArray, lexemeArray);
        declarator_list(tokenArray, lexemeArray);
        System.out.println("Exit <declaration>");

    }

    public static void type_specifier(TokenType[] tokenArray, String[] lexemeArray) {
        
        if (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.TYPE_SPECIFIER)) {
            System.out.println("Enter <type_specifier>");
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            System.out.println("Exit <type_specifier>");
            nextToken += 1;

        } else {
            System.out.println("ERROR: Next token expected TYPE_SPECIFIER");
            System.exit(0);
        }
    }

    public static void declarator_list(TokenType[] tokenArray, String[] lexemeArray) {
        
        System.out.println("Enter <declarator_list>");
        declarator(tokenArray, lexemeArray);
        while (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.COMMA)) {
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
            declarator(tokenArray, lexemeArray);
            
        }
        System.out.println("Exit <declarator_list>");
    
    }
    public static void declarator(TokenType[] tokenArray, String[] lexemeArray) {
        
        System.out.println("Enter <declarator>");
        identifier(tokenArray, lexemeArray);
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.LEFT_SQUARE_BRACKET)){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            array_size(tokenArray, lexemeArray);
            nextToken++;
        }

        if(tokenArray.length > nextToken && !tokenArray[nextToken].equals(TokenType.SEMICOLON)){
            System.out.println("ERROR: Next token expected is SEMICOLON");
            System.exit(0);
        }else{
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
        }

        System.out.println("Exit <declarator>"); 
    }

    public static void array_size(TokenType[] tokenArray, String[] lexemeArray){
         System.out.println("Enter <array_size>");
         nextToken++;
         if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.IDENTIFIER)){
            identifier(tokenArray, lexemeArray);
            if(tokenArray.length <= nextToken || !tokenArray[nextToken].equals(TokenType.RIGHT_SQUARE_BRACKET)){
                System.out.println("ERROR: Next token expected is RIGHT_SQUARE_BRACKET");
                System.exit(0);
            }
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
         }
         else if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.NUM)){
            digit(tokenArray, lexemeArray);
            if(tokenArray.length <= nextToken || !tokenArray[nextToken].equals(TokenType.RIGHT_SQUARE_BRACKET)){
                System.out.println("ERROR: Next token expected is RIGHT_SQUARE_BRACKET");
                System.exit(0);
            }
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
         }
        else{
             System.out.println("ERROR: Next token expected is IDENTIFIER or NUM");
             System.exit(0);
        }
        System.out.println("Exit <array_size>");
    }
     
    public static void digit(TokenType[] tokenArray, String[] lexemeArray){
        
        if (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.NUM)) {
            System.out.println("Enter <digit>");
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;

        } else {
            System.out.println("ERROR: Next token expected is NUM");
            System.exit(0);
        }
        System.out.println("Exit <digit>");
    }
    
    public static void identifier(TokenType[] tokenArray, String[] lexemeArray){
        if (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.IDENTIFIER)) {
            System.out.println("Enter <identifier>");
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;

        } else {
            System.out.println("ERROR: Next token expected is IDENTIFIER");
            System.exit(0);
        }
        System.out.println("Exit <identifier>");
    }

    // Main fuction declaration
    public static void program(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <program>");
        declaration_main(tokenArray, lexemeArray);
        System.out.println("Exit <program>");

    }
    public static void declaration_main(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <declaration_main>");
        function_declaration(tokenArray, lexemeArray);
        System.out.println("Exit <declaration_main>");  
    }
    public static void function_declaration(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <function_declaration>");
        type_specifier(tokenArray, lexemeArray);
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.MAIN)){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
        }else{
            System.out.println("ERROR: Next token expected is MAIN");
            System.exit(0);
        }
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.LEFT_PARENTHESES)){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
        }else{
            System.out.println("ERROR: Next token expected is LEFT_PARENTHESES");
            System.exit(0);
        }
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.RIGHT_PARENTHESES)){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
        }else{
            System.out.println("ERROR: Next token expected is RIGHT_PARENTHESES");
            System.exit(0);
        }
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.LEFT_BRACKET)){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
        }else{
            System.out.println("ERROR: Next token expected is LEFT_BRACKET");
            System.exit(0);
        }
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.RIGHT_BRACKET)){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
        }else{
            System.out.println("ERROR: Next token expected is RIGHT_BRACKET");
            System.exit(0);
        }
        System.out.println("Exit <function_declaration>");
    }

    // arithmetic operations
    public static void expression(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <expression>");
        term(tokenArray, lexemeArray);
        while(tokenArray.length > nextToken && (tokenArray[nextToken].equals(TokenType.SUM_OP) ||  tokenArray[nextToken].equals(TokenType.SUB_OP))){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
            term(tokenArray, lexemeArray);
        }
        System.out.println("Exit <expression>");

    }
    public static void term(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <term>");

        factor(tokenArray, lexemeArray);
        while(tokenArray.length > nextToken && (tokenArray[nextToken].equals(TokenType.MULT_OP) ||  tokenArray[nextToken].equals(TokenType.DIV_OP))){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
            factor(tokenArray, lexemeArray);
        }
        System.out.println("Exit <term>");
    }
    public static void factor(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <factor>");
        
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.IDENTIFIER)){
            identifier(tokenArray, lexemeArray);
         }
         else if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.NUM)){
            digit(tokenArray, lexemeArray);
         }
        else{
             System.out.println("ERROR: Next token expected is IDENTIFIER or NUM");
             System.exit(0);
        }

        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.LEFT_PARENTHESES)){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
            expression(tokenArray, lexemeArray);
            if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.RIGHT_PARENTHESES)){
                System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
                nextToken += 1;
            }else{
                System.out.println("ERROR: Next token expected is RIGHT_PARENTHESES");
                System.exit(0);
            }
        }
        System.out.println("Exit <factor>");     
    }

    //logical expression
    public static void logical_expression(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <logical_expression>");
        logical_term(tokenArray, lexemeArray);
        while(tokenArray.length > nextToken && (tokenArray[nextToken].equals(TokenType.AND_OP))){
            logical_term(tokenArray, lexemeArray);
        }
        System.out.println("Exit <logical_expression>");
    }
    public static void logical_term(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <logical_term>");
        comparison_expression(tokenArray, lexemeArray);
        while(tokenArray.length > nextToken && (tokenArray[nextToken].equals(TokenType.OR_OP))){
            comparison_expression(tokenArray, lexemeArray);
        }
        System.out.println("Exit <logical_term>");
    }
    public static void comparison_expression(TokenType[] tokenArray, String[] lexemeArray){
        System.out.println("Enter <comparison_expression>");
        expression(tokenArray, lexemeArray);
        while(tokenArray.length > nextToken && (
        tokenArray[nextToken].equals(TokenType.LESS_THAN) ||  
        tokenArray[nextToken].equals(TokenType.GREATER_THAN) ||
        tokenArray[nextToken].equals(TokenType.EQUALS) ||
        tokenArray[nextToken].equals(TokenType.NOT_EQUALS) ||
        tokenArray[nextToken].equals(TokenType.GREATER_THAN_OR_EQUALS) ||
        tokenArray[nextToken].equals(TokenType.LESS_THAN_OR_EQUALS))){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
            expression(tokenArray, lexemeArray);
        }
        System.out.println("Exit <comparison_expression>");
    }

    public static void if_statement(TokenType[] tokenArray, String[] lexemeArray){
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.IF_STATEMENT)){
            System.out.println("Enter <if_statement>");
            nextToken += 1;
            if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.LEFT_PARENTHESES)){
                System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
                nextToken += 1;
                logical_expression(tokenArray, lexemeArray);
                if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.RIGHT_PARENTHESES)){
                    System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
                    nextToken += 1;
                }else{
                    System.out.println("ERROR: Next token expected is RIGHT_PARENTHESES");
                    System.exit(0);
                }
            }else{
                System.out.println("ERROR: Next token expected is LEFT_PARENTHESES");
                System.exit(0);
            }

            if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.ELSE_STATEMENT)){
                System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
                nextToken += 1;
            }

            System.out.println("Exit <if_statement>");
        }else{
            System.out.println("ERROR: Next token expected is IF_STATEMENT");
            System.exit(0);
        }
    }

    //variable assignment

    public static void assignment_statement(TokenType[] tokenArray, String[] lexemeArray){  
        System.out.println("Enter <assignment_statement>");
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.TYPE_SPECIFIER)){
            declaration(tokenArray, lexemeArray);
            if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.ASSIGN_OP)){
                System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
                nextToken += 1;
            }else{
               System.out.println("ERROR: Next token expected is IF_STATEMENT");
               System.exit(0); 
            }
            expression(tokenArray, lexemeArray);
        }else if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.IDENTIFIER)) {
            identifier(tokenArray, lexemeArray);
            if(tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.ASSIGN_OP)){
                System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
                nextToken += 1;
            }else{
               System.out.println("ERROR: Next token expected is IF_STATEMENT");
               System.exit(0); 
            }
            expression(tokenArray, lexemeArray);
        }
        System.out.println("Exit <assignment_statement>");

    }      
}
