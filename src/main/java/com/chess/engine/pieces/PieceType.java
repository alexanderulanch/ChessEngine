package com.chess.engine.pieces;

public enum PieceType {
    PAWN,
    BISHOP,
    KNIGHT,
    ROOK,
    KING,
    QUEEN;

    public boolean isKing() {
        return this == KING;
    }

    public boolean isRook() {
        return this == ROOK;
    }
}
