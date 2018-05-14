package ru.spbau.mit.kazakov.LParser;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.Lexer.LexerUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.spbau.mit.kazakov.Lexer.LexerUtils.Lexeme.*;


public class ErrorRecoveryUtils {
    private static Map<Nonterminal, Set<LexerUtils.Lexeme>> first = new HashMap<>();
    private static Map<Nonterminal, Set<LexerUtils.Lexeme>> follow = new HashMap<>();

    static {
        Set<LexerUtils.Lexeme> atom = new HashSet<>();
        atom.add(LEFT_BRACKET);
        atom.add(BOOLEAN);
        atom.add(NUM);
        atom.add(IDENTIFIER);
        first.put(Nonterminal.ATOM, atom);

        Set<LexerUtils.Lexeme> power = new HashSet<>(atom);
        first.put(Nonterminal.POWER, power);

        Set<LexerUtils.Lexeme> term = new HashSet<>(power);
        first.put(Nonterminal.TERM, term);

        Set<LexerUtils.Lexeme> arithm = new HashSet<>(term);
        first.put(Nonterminal.ARITHM, arithm);

        Set<LexerUtils.Lexeme> neq = new HashSet<>(arithm);
        first.put(Nonterminal.NEQ, neq);

        Set<LexerUtils.Lexeme> eq = new HashSet<>(neq);
        first.put(Nonterminal.EQ, eq);

        Set<LexerUtils.Lexeme> conj = new HashSet<>(eq);
        first.put(Nonterminal.CONJ, conj);

        Set<LexerUtils.Lexeme> clause = new HashSet<>(conj);
        first.put(Nonterminal.CLAUSE, clause);

        Set<LexerUtils.Lexeme> assign = new HashSet<>();
        assign.add(IDENTIFIER);
        first.put(Nonterminal.ASSIGN, assign);

        Set<LexerUtils.Lexeme> args = new HashSet<>();
        args.add(LEFT_BRACKET);
        first.put(Nonterminal.ARGS, args);

        Set<LexerUtils.Lexeme> call = new HashSet<>();
        call.add(IDENTIFIER);
        first.put(Nonterminal.CALL, call);

        Set<LexerUtils.Lexeme> expr = new HashSet<>();
        expr.addAll(assign);
        expr.addAll(clause);
        expr.addAll(call);
        first.put(Nonterminal.EXPR, expr);

        Set<LexerUtils.Lexeme> block = new HashSet<>();
        block.add(LEFT_BRACE);
        first.put(Nonterminal.BLOCK, block);

        Set<LexerUtils.Lexeme> whileStmt = new HashSet<>();
        whileStmt.add(WHILE);
        first.put(Nonterminal.WHILE_STMT, whileStmt);

        Set<LexerUtils.Lexeme> ifStmt = new HashSet<>();
        ifStmt.add(IF);
        first.put(Nonterminal.IF_STMT, ifStmt);

        Set<LexerUtils.Lexeme> stmt = new HashSet<>();
        stmt.addAll(whileStmt);
        stmt.addAll(ifStmt);
        stmt.addAll(expr);
        stmt.add(READ);
        stmt.add(WRITE);
        first.put(Nonterminal.STMT, stmt);

        Set<LexerUtils.Lexeme> stmts = new HashSet<>(stmt);
        first.put(Nonterminal.STMTS, stmts);

        Set<LexerUtils.Lexeme> params = new HashSet<>();
        params.add(LEFT_BRACKET);
        first.put(Nonterminal.PARAMS, params);

        Set<LexerUtils.Lexeme> funDef = new HashSet<>();
        funDef.add(DEF);
        first.put(Nonterminal.FUN_DEF, funDef);

        Set<LexerUtils.Lexeme> funDefs = new HashSet<>(funDef);
        first.put(Nonterminal.FUN_DEFS, funDefs);

        Set<LexerUtils.Lexeme> s = new HashSet<>();
        s.addAll(stmts);
        s.addAll(funDef);
        first.put(Nonterminal.S, s);

        expr = new HashSet<>();
        expr.add(RIGHT_BRACKET);
        follow.put(Nonterminal.EXPR, expr);

        atom = new HashSet<>();
        atom.add(EXPONENTIATION);
        follow.put(Nonterminal.ATOM, atom);

        term = new HashSet<>();
        term.add(MODULO);
        term.add(DIVISION);
        term.add(MULTIPLICATION);
        follow.put(Nonterminal.TERM, term);

        arithm = new HashSet<>();
        arithm.add(ADDITION);
        arithm.add(SUBTRACTION);
        follow.put(Nonterminal.ARITHM, arithm);

        neq = new HashSet<>();
        neq.add(LESS);
        neq.add(LESS_OR_EQ);
        neq.add(GREATER);
        neq.add(GREATER_OR_EQ);
        follow.put(Nonterminal.NEQ, neq);

        eq = new HashSet<>();
        neq.add(EQUAL);
        neq.add(NOT_EQUAL);
        follow.put(Nonterminal.EQ, eq);

        conj = new HashSet<>();
        neq.add(AND);
        follow.put(Nonterminal.CONJ, conj);

        clause = new HashSet<>();
        clause.add(OR);
        follow.put(Nonterminal.CLAUSE, clause);

        follow.get(Nonterminal.EXPR).add(COMMA);
        follow.get(Nonterminal.EXPR).add(SEMICOLON);

        args = new HashSet<>();
        follow.put(Nonterminal.ARGS, args);

        params = new HashSet<>();
        follow.put(Nonterminal.PARAMS, params);

        stmts = new HashSet<>();
        follow.put(Nonterminal.STMTS, stmts);

        block = new HashSet<>();
        block.add(ELSE);
        follow.put(Nonterminal.BLOCK, block);

        s = new HashSet<>();
        s.add(EOF);
        follow.put(Nonterminal.S, s);

        follow.put(Nonterminal.STMT, new HashSet<>());
        follow.put(Nonterminal.FUN_DEFS, new HashSet<>());
        follow.put(Nonterminal.FUN_DEF, new HashSet<>());
        follow.put(Nonterminal.WHILE_STMT, new HashSet<>());
        follow.put(Nonterminal.IF_STMT, new HashSet<>());
        follow.put(Nonterminal.ASSIGN, new HashSet<>());
        follow.put(Nonterminal.CALL, new HashSet<>());
        follow.put(Nonterminal.POWER, new HashSet<>());

        follow.get(Nonterminal.FUN_DEFS).addAll(first.get(Nonterminal.STMTS));
        follow.get(Nonterminal.STMT).addAll(first.get(Nonterminal.STMTS));
        follow.get(Nonterminal.FUN_DEF).addAll(first.get(Nonterminal.FUN_DEFS));

        follow.get(Nonterminal.STMTS).addAll(follow.get(Nonterminal.S));
        follow.get(Nonterminal.STMT).addAll(follow.get(Nonterminal.STMTS));
        follow.get(Nonterminal.FUN_DEFS).addAll(follow.get(Nonterminal.STMTS));
        follow.get(Nonterminal.FUN_DEF).addAll(follow.get(Nonterminal.FUN_DEFS));
        follow.get(Nonterminal.BLOCK).addAll(follow.get(Nonterminal.FUN_DEF));
        follow.get(Nonterminal.WHILE_STMT).addAll(follow.get(Nonterminal.STMT));
        follow.get(Nonterminal.IF_STMT).addAll(follow.get(Nonterminal.STMT));
        follow.get(Nonterminal.BLOCK).addAll(follow.get(Nonterminal.IF_STMT));
        follow.get(Nonterminal.EXPR).addAll(follow.get(Nonterminal.ASSIGN));
        follow.get(Nonterminal.CALL).addAll(follow.get(Nonterminal.EXPR));
        follow.get(Nonterminal.CLAUSE).addAll(follow.get(Nonterminal.EXPR));
        follow.get(Nonterminal.ASSIGN).addAll(follow.get(Nonterminal.EXPR));
        follow.get(Nonterminal.ARGS).addAll(follow.get(Nonterminal.CALL));
        follow.get(Nonterminal.CONJ).addAll(follow.get(Nonterminal.CLAUSE));
        follow.get(Nonterminal.EQ).addAll(follow.get(Nonterminal.CONJ));
        follow.get(Nonterminal.NEQ).addAll(follow.get(Nonterminal.EQ));
        follow.get(Nonterminal.ARITHM).addAll(follow.get(Nonterminal.NEQ));
        follow.get(Nonterminal.TERM).addAll(follow.get(Nonterminal.ARITHM));
        follow.get(Nonterminal.POWER).addAll(follow.get(Nonterminal.TERM));
        follow.get(Nonterminal.ATOM).addAll(follow.get(Nonterminal.POWER));
    }

    public enum Nonterminal {
        ATOM, POWER, TERM, ARITHM, NEQ, EQ, CONJ, CLAUSE, ASSIGN, ARGS, CALL, EXPR,
        BLOCK, WHILE_STMT, IF_STMT, STMT, STMTS, PARAMS, FUN_DEF, FUN_DEFS, S
    }

    @NotNull
    public static Set<LexerUtils.Lexeme> getFollow(@NotNull Nonterminal nonterminal) {
        return follow.get(nonterminal);
    }
}
