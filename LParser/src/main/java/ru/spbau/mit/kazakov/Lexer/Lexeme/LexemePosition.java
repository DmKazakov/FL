package ru.spbau.mit.kazakov.Lexer.Lexeme;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.Lexer.LexerUtils;

public class LexemePosition {
    protected int line;
    protected int begin;
    protected int end;
    protected LexerUtils.Lexeme lexeme;

    public LexemePosition(int line, int begin, int end, @NotNull LexerUtils.Lexeme lexeme) {
        this.line = line;
        this.begin = begin;
        this.end = end;
        this.lexeme = lexeme;
    }

    public LexemePosition(@NotNull LexerUtils.Lexeme lexeme) {
        this.line = -1;
        this.begin = -1;
        this.end = -1;
        this.lexeme = lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getBegin() {
        return begin;
    }

    @Override
    @NotNull
    public String toString() {
        return lexeme.toString() + "(" + line + ", " + begin + ", " + end + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LexemePosition) {
            LexemePosition l = (LexemePosition) o;
            return l.line == line && l.begin == begin && l.end == end && l.lexeme.equals(lexeme);
        } else {
            return false;
        }
    }

    public LexerUtils.Lexeme getLexeme() {
        return lexeme;
    }
}
/**
 * Grammar:
 * S          -> FunDefs Stmts | Stmts
 * FunDefs    -> FunDef FunDefs | FunDef
 * FunDef     -> def Ident Params Block
 * Params     -> (Ident, Params) | (Ident) | ()
 * Stmts      -> Stmt Stmts | Stmt
 * Stmt       -> If_stmt | While_stmt | Expr; | read (Ident); | write (Expr);
 * If_stmt    -> if (Expr) then Block else Block | if (Expr) then Block
 * While_stmt -> while (Expr) Block
 * Block      -> {Stmts}
 * Expr       -> Call | Assign | Clause
 * Call       -> Ident Args
 * Args       -> (Expr, Args) | (Expr) | ()
 * Assign     -> Ident := Expr
 * Literal    -> true | false | Num
 * Clause     -> Conj || Clause | Conj
 * Conj       -> Conj && Eq | Eq
 * Eq         -> Eq [==, !=] Neq | Neq
 * Neq        -> Neq [<, ,<=, >, >= ] Arithm | Arithm
 * Arithm     -> Aritm [+, -] Term | Term
 * Term       -> Term [*, /, %] Power | Power
 * Power      -> Atom ^ Power | Atom
 * Atom       -> (Expr) | Literal | Ident
 */