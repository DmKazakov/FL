package ru.spbau.mit.kazakov.Parser;

import java.util.ArrayList;
import java.util.List;

public class TreePrinter {
    public interface PrintableNode {
        PrintableNode getLeftChild();

        PrintableNode getRightChild();

        String getText();
    }

    public static void print(PrintableNode root) {
        List<List<String>> lines = new ArrayList<>();
        List<PrintableNode> levels = new ArrayList<>();
        List<PrintableNode> next = new ArrayList<>();

        levels.add(root);
        int offset = 1;
        int widest = 0;

        while (offset != 0) {
            List<String> line = new ArrayList<>();

            offset = 0;
            for (PrintableNode level : levels) {
                if (level == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = level.getText();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(level.getLeftChild());
                    next.add(level.getRightChild());

                    if (level.getLeftChild() != null) offset++;
                    if (level.getRightChild() != null) offset++;
                }
            }

            if (widest % 2 == 1) widest++;
            lines.add(line);
            List<PrintableNode> tmp = levels;
            levels = next;
            next = tmp;
            next.clear();
        }

        int size = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int horizontal = (int) Math.floor(size / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char currentChar = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            currentChar = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) currentChar = '└';
                        }
                    }
                    System.out.print(currentChar);

                    if (line.get(j) == null) {
                        for (int k = 0; k < size - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < horizontal; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < horizontal; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            for (String f : line) {
                if (f == null) f = "";
                int offset1 = (int) Math.ceil(size / 2f - f.length() / 2f);
                int offset2 = (int) Math.floor(size / 2f - f.length() / 2f);

                for (int k = 0; k < offset1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < offset2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            size /= 2;
        }
    }
}
