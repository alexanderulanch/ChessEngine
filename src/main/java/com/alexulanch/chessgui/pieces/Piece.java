package com.alexulanch.chessgui.pieces;

import com.alexulanch.chessgui.Alliance;
import com.alexulanch.chessgui.board.Board;
import com.alexulanch.chessgui.board.Move;

import java.util.Collection;

public abstract class Piece {
    private final int piecePosition;
    private final Alliance pieceAlliance;
    private final boolean isFirstMove;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        isFirstMove = false;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }
    public int getPiecePosition() {
        return piecePosition;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }
}
