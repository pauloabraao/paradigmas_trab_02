import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public static List<Token> lexer(String inputString) {
        List<Token> tokens = new ArrayList<>();
        int index = 0;
        int length = inputString.length();

        while (index < length) {
            char currentChar = inputString.charAt(index);

            // Ignore whitespace and newline characters
            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            if (Character.isLetter(currentChar)) {
                // Start of a variable (word)
                StringBuilder word = new StringBuilder();
                while (index < length && (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
                    word.append(currentChar);
                    index++;
                    if (index < length) {
                        currentChar = inputString.charAt(index);
                    }
                }

                String wordString = word.toString().toLowerCase(); // Convert to lowercase for case-insensitivity
                if (isTypeSpecifier(wordString)) {
                    tokens.add(new Token(TokenType.TYPE_SPECIFIER, wordString));
                } else {
                    tokens.add(new Token(TokenType.IDENTIFIER, wordString));
                }
            } else if (Character.isDigit(currentChar)) {
                // Start of a number
                StringBuilder num = new StringBuilder();
                while (index < length && Character.isDigit(currentChar)) {
                    num.append(currentChar);
                    index++;
                    if (index < length) {
                        currentChar = inputString.charAt(index);
                    }
                }
                tokens.add(new Token(TokenType.NUM, num.toString()));
            } else {
                // Handle other characters
                switch (currentChar) {
                    case ',':
                        tokens.add(new Token(TokenType.COMMA, Character.toString(currentChar)));
                        index++;
                        break;
                    case '[':
                        tokens.add(new Token(TokenType.LEFT_SQUARE_BRACKET, Character.toString(currentChar)));
                        index++;
                        break;
                    case ']':
                        tokens.add(new Token(TokenType.RIGHT_SQUARE_BRACKET, Character.toString(currentChar)));
                        index++;
                        break;
                     case ';':
                        tokens.add(new Token(TokenType.SEMICOLON, Character.toString(currentChar)));
                        index++;
                        break;
                    default:
                        // Ignore other characters for simplicity
                        index++;
                        break;
                }
            }
        }

        return tokens;
    }

    private static boolean isTypeSpecifier(String word) {
        // Check if the word is a type specifier
        return word.equals("int") || word.equals("char") || word.equals("float") ||
               word.equals("double") || word.equals("short") || word.equals("long") ||
               word.equals("signed") || word.equals("unsigned");
    }

    public static void main(String[] args) {
        Path caminho = Paths.get("file.txt");
        try {
        byte[] texto = Files.readAllBytes(caminho);
        String leitura = new String(texto);
        String processedInput = processInput(leitura);
        System.out.println("Processed input: " + processedInput);
        List<Token> tokens = lexer(processedInput);
        System.out.println("Tokens: " + tokens);
        }catch(Exception e) {
        	System.out.println("Erro de Leitura!");
        } 
    }

    private static String processInput(String input) {
        // Remove comments from the input
        input = input.replaceAll("\\\\.*", "");

        // Find the expression in the input (ignoring comments)
        StringBuilder expression = new StringBuilder();
        String[] lines = input.split("\n");
        for (String line : lines) {
            expression.append(line);
        }

        // Remove extra whitespace
        return expression.toString().trim();
    }
}

class Token {
    TokenType type;
    String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + type + ", " + value + ")";
    }
}

enum TokenType {
    TYPE_SPECIFIER, IDENTIFIER, NUM, COMMA, SEMICOLON, LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET, UNKNOWN
}