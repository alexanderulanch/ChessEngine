package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

/**
 * A subclass of Tile that handles tiles without a piece on it.
 */
public final class EmptyTile extends Tile {
    protected EmptyTile(int tileCoordinate) {
        super(tileCoordinate);
    }

    @Override
    public String toString() {
        return "-";
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }

}
