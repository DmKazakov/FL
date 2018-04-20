package ru.spbau.mit.kazakov.Lexer;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.Lexer.Lexeme.LexemePosition;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(@NotNull String[] args) {
        if (args.length == 0) {
            System.out.println("No file specified.");
            return;
        }
        try {
            String sourceCode = FileUtils.readFileToString(new File(args[0]));
            Lexer lexer = new Lexer(sourceCode);

            List<LexemePosition> lexemes = lexer.getLexemes();
            for (LexemePosition lexeme : lexemes) {
                System.out.print(lexeme.toString() + "; ");
            }
            System.out.println();
        } catch (IOException exception) {
            System.out.println("Unable to read from file " + args[0]);
        } catch (LexerException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
