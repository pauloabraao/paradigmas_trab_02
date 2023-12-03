public class Parser {
    public static int nextToken = 0;
    public static void main(String[] args) {

        String[] tokenArray = {"TYPE_SPECIFIER", "IDENTIFIER", "SEMICOLON","TYPE_SPECIFIER", "IDENTIFIER", "SEMICOLON"};
        String[] lexemeArray = {"int", "var1", ";", "int", "var2", ";"};

        while (tokenArray.length > nextToken && tokenArray[nextToken].equals("TYPE_SPECIFIER")) {  
            declaration(tokenArray, lexemeArray);
            System.out.println("\n");
            nextToken++;
        }

 
       
    }

    public static void declaration(String[] tokenArray, String[] lexemeArray) {

        System.out.println("Enter <declaration>");
        type_specifier(tokenArray, lexemeArray);
        declarator_list(tokenArray, lexemeArray);
        System.out.println("Exit <declaration>");

    }

    public static void type_specifier(String[] tokenArray, String[] lexemeArray) {
        
        if (tokenArray.length > nextToken && tokenArray[nextToken].equals("TYPE_SPECIFIER")) {
            System.out.println("Enter <type_specifier>");
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            System.out.println("Exit <type_specifier>");
            nextToken += 1;

        } else {
            System.out.println("ERROR: Next token expected TYPE_SPECIFIER");
            System.exit(0);
        }
    }

    public static void declarator_list(String[] tokenArray, String[] lexemeArray) {
        
        System.out.println("Enter <declarator_list>");
        declarator(tokenArray, lexemeArray);
        while (tokenArray.length > nextToken && tokenArray[nextToken].equals("COMMA")) {
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;
            declarator(tokenArray, lexemeArray);
            
        }
        System.out.println("Exit <declarator_list>");
    
    }
    public static void declarator(String[] tokenArray, String[] lexemeArray) {
        
        System.out.println("Enter <declarator>");
        identifier(tokenArray, lexemeArray);
        if(tokenArray.length > nextToken && tokenArray[nextToken].equals("LEFT_SQUARE_BRACKET")){
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            array_size(tokenArray, lexemeArray);
            nextToken++;
        }

        if(tokenArray.length > nextToken && !tokenArray[nextToken].equals("SEMICOLON")){
            System.out.println("ERROR: Next token expected is SEMICOLON");
            System.exit(0);
        }else{
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
        }
       
        
  
        System.out.println("Exit <declarator>"); 
    }

    public static void array_size(String[] tokenArray, String[] lexemeArray){
         System.out.println("Enter <array_size>");
         nextToken++;
         if(tokenArray.length > nextToken && tokenArray[nextToken].equals("IDENTIFIER")){
            identifier(tokenArray, lexemeArray);
            if(tokenArray.length <= nextToken || !tokenArray[nextToken].equals("RIGHT_SQUARE_BRACKET")){
                System.out.println("ERROR: Next token expected is RIGHT_SQUARE_BRACKET");
                System.exit(0);
            }
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
         }
         else if(tokenArray.length > nextToken && tokenArray[nextToken].equals("NUM")){
            digit(tokenArray, lexemeArray);
            if(tokenArray.length <= nextToken || !tokenArray[nextToken].equals("RIGHT_SQUARE_BRACKET")){
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
     
    public static void digit(String[] tokenArray, String[] lexemeArray){
        
        if (tokenArray.length > nextToken && tokenArray[nextToken].equals("NUM")) {
            System.out.println("Enter <digit>");
            System.out.println("Next token is " + tokenArray[nextToken] + "\nNext lexeme is " + lexemeArray[nextToken]);
            nextToken += 1;

        } else {
            System.out.println("ERROR: Next token expected is NUM");
            System.exit(0);
        }
        System.out.println("Exit <digit>");
    }
    
    public static void identifier(String[] tokenArray, String[] lexemeArray){
        if (tokenArray.length > nextToken && tokenArray[nextToken].equals("IDENTIFIER")) {
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
