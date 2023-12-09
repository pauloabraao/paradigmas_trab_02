import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {

    public static List<Token> lexer(String inputString) {
        List<Token> tokens = new ArrayList<>();
        int index = 0;
        int length = inputString.length();

        while (index < length) {
            char currentChar = inputString.charAt(index);

            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            if (Character.isLetter(currentChar)) {
                StringBuilder word = new StringBuilder();
                while (index < length && (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
                    word.append(currentChar);
                    index++;
                    if (index < length) {
                        currentChar = inputString.charAt(index);
                    }
                }

                String wordString = word.toString().toLowerCase();
                if (isMain(wordString)) {
                    tokens.add(new Token(TokenType.MAIN, wordString));
                }
                else if (isWhileStatement(wordString)){
                    tokens.add(new Token(TokenType.WHILE, wordString));
                }
                else if (isIfStatement(wordString)){
                    tokens.add(new Token(TokenType.IF_STATEMENT, wordString));
                }
                else if (isElseStatement(wordString)){
                    tokens.add(new Token(TokenType.ELSE_STATEMENT, wordString));
                }
                else if (isTypeSpecifier(wordString)) {
                    tokens.add(new Token(TokenType.TYPE_SPECIFIER, wordString));
                } else {
                    tokens.add(new Token(TokenType.IDENTIFIER, wordString));
                }
            } else if (Character.isDigit(currentChar)) {
        
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
                    case '(':
                        tokens.add(new Token(TokenType.LEFT_PARENTHESES, Character.toString(currentChar)));
                        index++;
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.RIGHT_PARENTHESES, Character.toString(currentChar)));
                        index++;
                        break;
                    case '{':
                        tokens.add(new Token(TokenType.LEFT_BRACKET, Character.toString(currentChar)));
                        index++;
                        break;
                    case '}':
                        tokens.add(new Token(TokenType.RIGHT_BRACKET, Character.toString(currentChar)));
                        index++;
                        break;
                    case ';':
                        tokens.add(new Token(TokenType.SEMICOLON, Character.toString(currentChar)));
                        index++;
                        break;
                    case '+':
                        tokens.add(new Token(TokenType.SUM_OP, Character.toString(currentChar)));
                        index++;
                        break;
                    case '-':
                        tokens.add(new Token(TokenType.SUB_OP, Character.toString(currentChar)));
                        index++;
                        break;
                    case '*':
                        tokens.add(new Token(TokenType.MULT_OP, Character.toString(currentChar)));
                        index++;
                        break;
                    case '/':
                        tokens.add(new Token(TokenType.DIV_OP, Character.toString(currentChar)));
                        index++;
                        break;
                    case '=':
                        if (index + 1 < length && inputString.charAt(index + 1) == '=') {
                            tokens.add(new Token(TokenType.EQUALS, "=="));
                            index += 2;
                        } else {
                            tokens.add(new Token(TokenType.ASSIGN_OP, Character.toString(currentChar)));
                            index++;
                        }
                        break;
                    case '!':
                        if (index + 1 < length && inputString.charAt(index + 1) == '=') {
                            tokens.add(new Token(TokenType.NOT_EQUALS, "!="));
                            index += 2;
                        } else {
                            //error
                            index++;
                        }
                        break;
    
                    case '<':
                        if (index + 1 < length && inputString.charAt(index + 1) == '=') {
                            tokens.add(new Token(TokenType.LESS_THAN_OR_EQUALS, "<="));
                            index += 2;
                        } else {
                            tokens.add(new Token(TokenType.LESS_THAN, Character.toString(currentChar)));
                            index++;
                            break;
                        }
                        break;
                    case '>':
                        if (index + 1 < length && inputString.charAt(index + 1) == '=') {
                            tokens.add(new Token(TokenType.GREATER_THAN_OR_EQUALS, ">="));
                            index += 2;
                        } else {
                            tokens.add(new Token(TokenType.GREATER_THAN, Character.toString(currentChar)));
                            index++;
                            break;
                        }
                        break;
                    case '|':
                        if (index + 1 < length && inputString.charAt(index + 1) == '|') {
                            tokens.add(new Token(TokenType.OR_OP, "||"));
                            index += 2;
                        } else {
                            tokens.add(new Token(TokenType.UNKNOWN, Character.toString(currentChar)));
                            index++;
                            break;
                        }
                        break;
                    case '&':
                        if (index + 1 < length && inputString.charAt(index + 1) == '&') {
                            tokens.add(new Token(TokenType.AND_OP, "&&"));
                            index += 2;
                        } else {
                            tokens.add(new Token(TokenType.UNKNOWN, Character.toString(currentChar)));
                            index++;
                            break;
                        }
                        break;
                    default:
                        index++;
                        break;
                }
            }
        }

        return tokens;
    }

    public static TokenType[] getTokenTypes(String inputString) {
        List<Token> tokens = lexer(inputString);
        TokenType[] tokenTypes = new TokenType[tokens.size()];

        for (int i = 0; i < tokens.size(); i++) {
            tokenTypes[i] = tokens.get(i).getType();
        }

        return tokenTypes;
    }

    public static String[] getTokenValue(String inputString) {
        List<Token> tokens = lexer(inputString);
        String[] tokenValue = new String[tokens.size()];

        for (int i = 0; i < tokens.size(); i++) {
            tokenValue[i] = tokens.get(i).getValue();
        }

        return tokenValue;
    }


    private static boolean isTypeSpecifier(String word) {
        // Check if the word is a type specifier
        return word.equals("int") || word.equals("char") || word.equals("float") ||
               word.equals("double") || word.equals("short") || word.equals("long") ||
               word.equals("signed") || word.equals("unsigned");
    }

    private static boolean isMain(String word) {
        // Check if the word is a type specifier
        return word.equals("main");
    }

    private static boolean isIfStatement(String word) {
        // Check if the word is a type specifier
        return word.equals("if");
    }

    private static boolean isElseStatement(String word) {
        // Check if the word is a type specifier
        return word.equals("else");
    }
    private static boolean isWhileStatement(String word) {
        // Check if the word is a type specifier
        return word.equals("while");
    }

    public static void main(String[] args) {
        Path caminho = Paths.get("file.txt");
        try {
        byte[] texto = Files.readAllBytes(caminho);
        String leitura = new String(texto);
        String processedInput = processInput(leitura);
        System.out.println("Processed input: " + processedInput);
        List<Token> tokens = lexer(processedInput);
        System.out.println("Lexical Analysis: " + tokens);
        TokenType[] tokenTypes = getTokenTypes(processedInput);
            System.out.println("Token Types: " + Arrays.toString(tokenTypes));
        }catch(Exception e) {
        	System.out.println("Erro de Leitura!");
        } 
    }

    static String processInput(String input) {

        input = input.replaceAll("\\\\.*", "");

        StringBuilder expression = new StringBuilder();
        String[] lines = input.split("\n");
        for (String line : lines) {
            expression.append(line);
        }

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
    
    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(" + type + ", " + value + ")";
    }
}

enum TokenType {
    TYPE_SPECIFIER, IDENTIFIER, NUM, COMMA, SEMICOLON, LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET, UNKNOWN, 
    MAIN, LEFT_PARENTHESES, RIGHT_PARENTHESES, LEFT_BRACKET, RIGHT_BRACKET, SUM_OP, SUB_OP, MULT_OP, DIV_OP, 
    EQUALS, ASSIGN_OP, NOT_EQUALS, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUALS, GREATER_THAN_OR_EQUALS, OR_OP, AND_OP, 
    IF_STATEMENT, ELSE_STATEMENT, WHILE
}

