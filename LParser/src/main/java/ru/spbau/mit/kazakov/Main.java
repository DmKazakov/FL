package ru.spbau.mit.kazakov;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.LParser.Parser;
import ru.spbau.mit.kazakov.LParser.ParsingException;
import ru.spbau.mit.kazakov.Lexer.Lexer;
import ru.spbau.mit.kazakov.Lexer.LexerException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(@NotNull String[] args) {
        if (args.length == 0) {
            System.out.println("No file specified.");
            return;
        }
        try {
            String sourceCode = FileUtils.readFileToString(new File(args[0]));
            Lexer lexer = new Lexer(sourceCode);
            Parser parser = new Parser(lexer.getLexemes());
            TreePrinter.PrintableNode AST = parser.getDerivationTree();
            if (AST == null) {
                System.out.println(String.join("\n", parser.getErrors()));
            } else {
                System.out.println(TreePrinter.print(AST));
            }
        } catch (IOException exception) {
            System.out.println("Unable to read from file " + args[0]);
        } catch (LexerException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
