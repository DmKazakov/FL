package ru.spbau.mit.kazakov.LParser;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.spbau.mit.kazakov.Lexer.Lexer;
import ru.spbau.mit.kazakov.Lexer.LexerException;
import ru.spbau.mit.kazakov.TreePrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ParserTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testWhitespaces() throws LexerException {
        String input = "id\n:=    3\t +1\r\f;";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "└── Statements\n" +
                "    └── ASSIGN(2, 1, 2)\n" +
                "        ├── IDENTIFIER(id, 1, 1, 2)\n" +
                "        └── ADDITION(2, 10, 10)\n" +
                "            ├── NUM(3.0, 2, 7, 7)\n" +
                "            └── NUM(1.0, 2, 11, 11)\n", getAST(input));
    }

    @Test
    public void testArithmetic() throws LexerException {
        String input = "id := 42 ^ 24 ^ (1 - 2) - 156 % 123 / 32;";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "└── Statements\n" +
                "    └── ASSIGN(1, 4, 5)\n" +
                "        ├── IDENTIFIER(id, 1, 1, 2)\n" +
                "        └── SUBTRACTION(1, 25, 25)\n" +
                "            ├── EXPONENTIATION(1, 10, 10)\n" +
                "            │   ├── NUM(42.0, 1, 7, 8)\n" +
                "            │   └── EXPONENTIATION(1, 15, 15)\n" +
                "            │       ├── NUM(24.0, 1, 12, 13)\n" +
                "            │       └── SUBTRACTION(1, 20, 20)\n" +
                "            │           ├── NUM(1.0, 1, 18, 18)\n" +
                "            │           └── NUM(2.0, 1, 22, 22)\n" +
                "            └── DIVISION(1, 37, 37)\n" +
                "                ├── MODULO(1, 31, 31)\n" +
                "                │   ├── NUM(156.0, 1, 27, 29)\n" +
                "                │   └── NUM(123.0, 1, 33, 35)\n" +
                "                └── NUM(32.0, 1, 39, 40)\n", getAST(input));
    }

    @Test
    public void testLogic() throws LexerException {
        String input = "id := (10 >= a) && true || (32 - 1 != 4) == true;";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "└── Statements\n" +
                "    └── ASSIGN(1, 4, 5)\n" +
                "        ├── IDENTIFIER(id, 1, 1, 2)\n" +
                "        └── OR(1, 25, 26)\n" +
                "            ├── AND(1, 17, 18)\n" +
                "            │   ├── GREATER_OR_EQ(1, 11, 12)\n" +
                "            │   │   ├── NUM(10.0, 1, 8, 9)\n" +
                "            │   │   └── IDENTIFIER(a, 1, 14, 14)\n" +
                "            │   └── BOOLEAN(true, 1, 20, 23)\n" +
                "            └── EQUAL(1, 42, 43)\n" +
                "                ├── NOT_EQUAL(1, 36, 37)\n" +
                "                │   ├── SUBTRACTION(1, 32, 32)\n" +
                "                │   │   ├── NUM(32.0, 1, 29, 30)\n" +
                "                │   │   └── NUM(1.0, 1, 34, 34)\n" +
                "                │   └── NUM(4.0, 1, 39, 39)\n" +
                "                └── BOOLEAN(true, 1, 45, 48)\n", getAST(input));
    }

    @Test
    public void testFunDefs() throws LexerException {
        String input = "def foo(a, b, c) {a := a + b - c; a;} def bar() {q := 3; t:=0; t + q;}";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "│   ├── DEF(1, 1, 3):IDENTIFIER(foo, 1, 5, 7)\n" +
                "│   │   ├── Parameters\n" +
                "│   │   │   ├── IDENTIFIER(a, 1, 9, 9)\n" +
                "│   │   │   ├── IDENTIFIER(b, 1, 12, 12)\n" +
                "│   │   │   └── IDENTIFIER(c, 1, 15, 15)\n" +
                "│   │   └── Statements\n" +
                "│   │       ├── ASSIGN(1, 21, 22)\n" +
                "│   │       │   ├── IDENTIFIER(a, 1, 19, 19)\n" +
                "│   │       │   └── SUBTRACTION(1, 30, 30)\n" +
                "│   │       │       ├── ADDITION(1, 26, 26)\n" +
                "│   │       │       │   ├── IDENTIFIER(a, 1, 24, 24)\n" +
                "│   │       │       │   └── IDENTIFIER(b, 1, 28, 28)\n" +
                "│   │       │       └── IDENTIFIER(c, 1, 32, 32)\n" +
                "│   │       └── IDENTIFIER(a, 1, 35, 35)\n" +
                "│   └── DEF(1, 39, 41):IDENTIFIER(bar, 1, 43, 45)\n" +
                "│       ├── Parameters\n" +
                "│       └── Statements\n" +
                "│           ├── ASSIGN(1, 52, 53)\n" +
                "│           │   ├── IDENTIFIER(q, 1, 50, 50)\n" +
                "│           │   └── NUM(3.0, 1, 55, 55)\n" +
                "│           ├── ASSIGN(1, 59, 60)\n" +
                "│           │   ├── IDENTIFIER(t, 1, 58, 58)\n" +
                "│           │   └── NUM(0.0, 1, 61, 61)\n" +
                "│           └── ADDITION(1, 66, 66)\n" +
                "│               ├── IDENTIFIER(t, 1, 64, 64)\n" +
                "│               └── IDENTIFIER(q, 1, 68, 68)\n" +
                "└── Statements\n", getAST(input));
    }

    @Test
    public void testFunCall() throws LexerException {
        String input = "def foo(a, b, c) {a := a + b - c; a;} id := r + foo(true, 4-2, f * (d * d)); foo();";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "│   └── DEF(1, 1, 3):IDENTIFIER(foo, 1, 5, 7)\n" +
                "│       ├── Parameters\n" +
                "│       │   ├── IDENTIFIER(a, 1, 9, 9)\n" +
                "│       │   ├── IDENTIFIER(b, 1, 12, 12)\n" +
                "│       │   └── IDENTIFIER(c, 1, 15, 15)\n" +
                "│       └── Statements\n" +
                "│           ├── ASSIGN(1, 21, 22)\n" +
                "│           │   ├── IDENTIFIER(a, 1, 19, 19)\n" +
                "│           │   └── SUBTRACTION(1, 30, 30)\n" +
                "│           │       ├── ADDITION(1, 26, 26)\n" +
                "│           │       │   ├── IDENTIFIER(a, 1, 24, 24)\n" +
                "│           │       │   └── IDENTIFIER(b, 1, 28, 28)\n" +
                "│           │       └── IDENTIFIER(c, 1, 32, 32)\n" +
                "│           └── IDENTIFIER(a, 1, 35, 35)\n" +
                "└── Statements\n" +
                "    ├── ASSIGN(1, 42, 43)\n" +
                "    │   ├── IDENTIFIER(id, 1, 39, 40)\n" +
                "    │   └── ADDITION(1, 47, 47)\n" +
                "    │       ├── IDENTIFIER(r, 1, 45, 45)\n" +
                "    │       └── Call:IDENTIFIER(foo, 1, 49, 51)\n" +
                "    │           └── Arguments\n" +
                "    │               ├── BOOLEAN(true, 1, 53, 56)\n" +
                "    │               ├── SUBTRACTION(1, 60, 60)\n" +
                "    │               │   ├── NUM(4.0, 1, 59, 59)\n" +
                "    │               │   └── NUM(2.0, 1, 61, 61)\n" +
                "    │               └── MULTIPLICATION(1, 66, 66)\n" +
                "    │                   ├── IDENTIFIER(f, 1, 64, 64)\n" +
                "    │                   └── MULTIPLICATION(1, 71, 71)\n" +
                "    │                       ├── IDENTIFIER(d, 1, 69, 69)\n" +
                "    │                       └── IDENTIFIER(d, 1, 73, 73)\n" +
                "    └── Call:IDENTIFIER(foo, 1, 78, 80)\n" +
                "        └── Arguments\n", getAST(input));
    }

    @Test
    public void testRead() throws LexerException {
        String input = "read(w);";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "└── Statements\n" +
                "    └── READ(1, 1, 4)\n" +
                "        └── IDENTIFIER(w, 1, 6, 6)\n", getAST(input));
    }

    @Test
    public void testWrite() throws LexerException {
        String input = "write(w + 4 * 2);";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "└── Statements\n" +
                "    └── WRITE(1, 1, 5)\n" +
                "        └── ADDITION(1, 9, 9)\n" +
                "            ├── IDENTIFIER(w, 1, 7, 7)\n" +
                "            └── MULTIPLICATION(1, 13, 13)\n" +
                "                ├── NUM(4.0, 1, 11, 11)\n" +
                "                └── NUM(2.0, 1, 15, 15)\n", getAST(input));
    }

    @Test
    public void testIf() throws LexerException {
        String input = "if (a < 4) then {a := 3; read(b);} else { write (4);}";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "└── Statements\n" +
                "    └── IF(1, 1, 2)\n" +
                "        ├── LESS(1, 7, 7)\n" +
                "        │   ├── IDENTIFIER(a, 1, 5, 5)\n" +
                "        │   └── NUM(4.0, 1, 9, 9)\n" +
                "        ├── Statements\n" +
                "        │   ├── ASSIGN(1, 20, 21)\n" +
                "        │   │   ├── IDENTIFIER(a, 1, 18, 18)\n" +
                "        │   │   └── NUM(3.0, 1, 23, 23)\n" +
                "        │   └── READ(1, 26, 29)\n" +
                "        │       └── IDENTIFIER(b, 1, 31, 31)\n" +
                "        └── Statements\n" +
                "            └── WRITE(1, 43, 47)\n" +
                "                └── NUM(4.0, 1, 50, 50)\n", getAST(input));
    }

    @Test
    public void testWhile() throws LexerException {
        String input = "while (true) {t := t + 1; k := k * t;}";
        assertEquals("root\n" +
                "├── Definitions\n" +
                "└── Statements\n" +
                "    └── WHILE(1, 1, 5)\n" +
                "        ├── BOOLEAN(true, 1, 8, 11)\n" +
                "        └── Statements\n" +
                "            ├── ASSIGN(1, 17, 18)\n" +
                "            │   ├── IDENTIFIER(t, 1, 15, 15)\n" +
                "            │   └── ADDITION(1, 22, 22)\n" +
                "            │       ├── IDENTIFIER(t, 1, 20, 20)\n" +
                "            │       └── NUM(1.0, 1, 24, 24)\n" +
                "            └── ASSIGN(1, 29, 30)\n" +
                "                ├── IDENTIFIER(k, 1, 27, 27)\n" +
                "                └── MULTIPLICATION(1, 34, 34)\n" +
                "                    ├── IDENTIFIER(k, 1, 32, 32)\n" +
                "                    └── IDENTIFIER(t, 1, 36, 36)\n", getAST(input));
    }

    @Test
    public void testErrorRecoverySemicolon() throws Throwable {
        String input = "id := 3\nid := 5";
        assertEquals("Line 2, position 1: SEMICOLON expected.\n" +
                "Line 2, position 8: SEMICOLON expected.", getErrors(input));
    }

    @Test
    public void testErrorRecoveryIdentifier() throws Throwable {
        String input = "id := 4 +  ;\nid := ;";
        assertEquals("Line 1, position 12: IDENTIFIER expected.\n" +
                "Line 2, position 7: IDENTIFIER expected.", getErrors(input));
    }

    @Test
    public void testErrorRecoveryBracket() throws Throwable {
        String input = "id := (34 + id;\nfoo(4,6;";
        assertEquals("Line 1, position 15: RIGHT_BRACKET expected.\n" +
                "Line 2, position 8: RIGHT_BRACKET expected.", getErrors(input));
    }

    @Test
    public void testErrorRecoveryBrace() throws Throwable {
        String input = "def foo() a+b+c;} :=5;";
        assertEquals("Line 1, position 11: LEFT_BRACE expected.\n" +
                "Line 1, position 17: IDENTIFIER expected.", getErrors(input));
    }

    @Test
    public void testErrorRecoveryThen() throws Throwable {
        String input = "if(true) {a := 4;} :=3;";
        assertEquals("Line 1, position 10: THEN expected.\n" +
                "Line 1, position 18: IDENTIFIER expected.", getErrors(input));
    }

    @Test
    public void testErrorRecoveryCall() throws Throwable {
        String input = "foo(4,);";
        assertEquals("Line 1, position 7: IDENTIFIER expected.", getErrors(input));
    }

    @Test
    public void testErrorRecoveryWrite() throws Throwable {
        String input = "write();";
        assertEquals("Line 1, position 7: IDENTIFIER expected.", getErrors(input));
    }

    @NotNull
    private String getAST(@NotNull String input) throws LexerException {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer.getLexemes());
        return TreePrinter.print(parser.getDerivationTree());
    }

    @NotNull
    private String getErrors(@NotNull String input) throws Throwable {
        try {
            Lexer lexer = new Lexer(input);
            Parser parser = new Parser(lexer.getLexemes());
            assertNull(parser.getDerivationTree());
            return String.join("\n", parser.getErrors());
        } catch (ExceptionInInitializerError exceptionInInitializerError) {
            throw exceptionInInitializerError.getCause();
        }
    }
}