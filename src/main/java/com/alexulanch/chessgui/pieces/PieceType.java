package com.alexulanch.chessgui.pieces;

public enum PieceType {
    PAWN,
    BISHOP,
    KNIGHT,
    ROOK,
    KING,
    QUEEN;

    public boolean isKing(Piece piece) {
        return piece.getPieceType() == KING;
    }
}
