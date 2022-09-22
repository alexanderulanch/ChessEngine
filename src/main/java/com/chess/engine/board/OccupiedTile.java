package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * A subclass of Tile that handles occupied tiles (Any tile with a piece on it).
 */
public final class OccupiedTile extends Tile {
    private final Piece pieceOnTile;

    @Override
    public String toString() {
    return pieceOnTile.getPieceAlliance().isBlack() ?
           pieceOnTile.toString().toLowerCase() : pieceOnTile.toString();
    }

    protected OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
        super(tileCoordinate);
        this.pieceOnTile = pieceOnTile;
    }

    @Override
    public boolean isOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return pieceOnTile;
    }
}
