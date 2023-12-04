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
            while (tokenArray.length > nextToken && tokenArray[nextToken].equals(TokenType.TYPE_SPECIFIER)) {  
                declaration(tokenArray, lexemeArray);
                System.out.println("\n");
                nextToken++;
            }
            
        } catch (Exception e) {
            System.out.println("Error reading file: " + e);
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
}
