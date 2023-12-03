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

                String wordString = word.toString();
                if ("True".equals(wordString)) {
                    tokens.add(new Token(TokenType.TRUE_BOOLEAN, wordString));
                } else if ("False".equals(wordString)) {
                    tokens.add(new Token(TokenType.FALSE_BOOLEAN, wordString));
                } else {
                    tokens.add(new Token(TokenType.VAR, wordString));
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
                    case '(':
                        tokens.add(new Token(TokenType.LPAREN, Character.toString(currentChar)));
                        index++;
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.RPAREN, Character.toString(currentChar)));
                        index++;
                        break;
                    case '+':
                        tokens.add(new Token(TokenType.OR, Character.toString(currentChar)));
                        index++;
                        break;
                    case '*':
                        tokens.add(new Token(TokenType.AND, Character.toString(currentChar)));
                        index++;
                        break;
                    case '-':
                        if (index < length - 1 && inputString.substring(index, index + 2).equals("->")) {
                            tokens.add(new Token(TokenType.IMPLIES, "->"));
                            index += 2;
                        } else {
                            tokens.add(new Token(TokenType.UNKNOWN, Character.toString(currentChar)));
                            index++;
                        }
                        break;
                    case '<':
                        if (index < length - 2 && inputString.substring(index, index + 3).equals("<->")) {
                            tokens.add(new Token(TokenType.IFF, "<->"));
                            index += 3;
                        } else {
                            tokens.add(new Token(TokenType.UNKNOWN, Character.toString(currentChar)));
                            index++;
                        }
                        break;
                    case '¬':
                        tokens.add(new Token(TokenType.NOT, Character.toString(currentChar)));
                        index++;
                        break;
                    case '=':
                        tokens.add(new Token(TokenType.EQUAL, Character.toString(currentChar)));
                        index++;
                        break;
                    default:
                        tokens.add(new Token(TokenType.UNKNOWN, Character.toString(currentChar)));
                        index++;
                        break;
                }
            }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String input = "A+B -> 7 <-> I \\o";
        String processedInput = processInput(input);
        System.out.println("Processed input: " + processedInput);

        List<Token> tokens = lexer(processedInput);
        System.out.println("Tokens: " + tokens);
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
    VAR, NUM, LPAREN, RPAREN, OR, AND, IMPLIES, IFF, NOT, EQUAL, TRUE_BOOLEAN, FALSE_BOOLEAN, UNKNOWN
}
