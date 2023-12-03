public class Parser {
    public static int nextToken = 0;
    public static void main(String[] args) {
        // Creating an array in main
        String[] inputArray = { "IDENTIFIER", "LEFT_SQUARE_BRACKET", "IDENTIFIER", "RIGHT_SQUARE_BRACKET"};

        // Calling the declaration function
        declaration(inputArray);

    }

    // Declaration function
    public static void declaration(String[] inputArray) {

        System.out.println("<declaration>");

        // Calling type_specifier function
        type_specifier(inputArray);

        // Calling declarator_list function
        declarator_list(inputArray);
    }

    // Type_specifier function
    public static void type_specifier(String[] inputArray) {
        // Checking if the first entry of the array is equal to 'TYPE_SPECIFIER'
        if (inputArray.length > nextToken && inputArray[nextToken].equals("TYPE_SPECIFIER")) {
            System.out.println("<type_specifier>");
            nextToken += 1;

        } else {
            System.out.println("ERROR: Next token expected TYPE_SPECIFIER");
            System.exit(0);
        }
    }

    // Declarator_list function
    public static void declarator_list(String[] inputArray) {
        
        System.out.println("<declarator_list>");
        declarator(inputArray);
        while (inputArray.length > nextToken && inputArray[nextToken].equals("COMMA")) {
            declarator(inputArray);
            nextToken += 1;
        }
    
    }
    public static void declarator(String[] inputArray) {
        
        System.out.println("<declarator>");
        identifier(inputArray);
        if(inputArray.length > nextToken && inputArray[nextToken].equals("LEFT_SQUARE_BRACKET")){
            array_size(inputArray);
        }
    
    }

    public static void array_size(String[] inputArray){
         System.out.println("<array_size>");
         nextToken++;
         if(inputArray.length > nextToken && inputArray[nextToken].equals("IDENTIFIER") && inputArray[nextToken+1].equals("RIGHT_SQUARE_BRACKET")){
            identifier(inputArray);
            if(inputArray.length <= nextToken || !inputArray[nextToken].equals("RIGHT_SQUARE_BRACKET")){
                System.out.println("ERROR: Next token expected is RIGHT_SQUARE_BRACKET");
                System.exit(0);
            }
         }
         else if(inputArray.length > nextToken && inputArray[nextToken].equals("NUM") && inputArray[nextToken+1].equals("RIGHT_SQUARE_BRACKET")){
            digit(inputArray);
            nextToken++;
            if(inputArray.length <= nextToken || !inputArray[nextToken].equals("RIGHT_SQUARE_BRACKET")){
                System.out.println("ERROR: Next token expected is RIGHT_SQUARE_BRACKET");
                System.exit(0);
            }
         }
        else{
             System.out.println("ERROR: Next token expected is IDENTIFIER or NUM");
             System.exit(0);
         }
    }
     
    public static void digit(String[] inputArray){
        System.out.println("<digit>");
    }
    
    public static void identifier(String[] inputArray){
         if (inputArray.length > nextToken && inputArray[nextToken].equals("IDENTIFIER")) {
            System.out.println("<identifier>");
            nextToken += 1;

        } else {
            System.out.println("ERROR: Next token expected is IDENTIFIER");
            System.exit(0);
        }
    }
}
