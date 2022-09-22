package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {
    private final int piecePosition;
    private final Alliance pieceAlliance;
    private final boolean isFirstMove;
    private final PieceType pieceType;
    private final int cachedHashCode;

    Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance) {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        isFirstMove = false;
        cachedHashCode = computeHashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) { return true; }

        if (!(other instanceof Piece)) { // If not an instance of Piece class return false
            return false;
        }

        final Piece otherPiece = (Piece) other;

        return piecePosition == otherPiece.piecePosition &&
               pieceType == otherPiece.getPieceType() &&
               pieceAlliance == otherPiece.getPieceAlliance() &&
               isFirstMove == otherPiece.isFirstMove();

    }

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    private int computeHashCode() {
        final int PRIME = 31;
        int result = pieceType.hashCode();
        result = PRIME * result + pieceAlliance.hashCode();
        result = PRIME * result + piecePosition;
        result = PRIME * result + (isFirstMove ? 1 : 0);

        return result;
    }

    public Alliance getPieceAlliance() { return pieceAlliance; }
    public int getPiecePosition() { return piecePosition; }
    public boolean isFirstMove() { return isFirstMove; }
    public PieceType getPieceType() { return pieceType; }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public abstract Piece movePiece(Move move);
}
