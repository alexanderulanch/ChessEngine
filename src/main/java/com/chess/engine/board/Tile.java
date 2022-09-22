package com.chess.engine.board;

import com.chess.engine.pieces.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * The class for implementing the individual tiles on the chess board.
 * @author Alex Ulanch
 */
public abstract class Tile {
    protected final int TILE_COORDINATE;
    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = populateEmptyTiles();

    protected Tile(final int tileCoordinate) {
        this.TILE_COORDINATE = tileCoordinate;
    }

    /**
     * Creates and returns an OccupiedTile if Piece parameter is not null.
     * else returns an EmptyTile from cache with given tileCoordinate.
     * @param tileCoordinate
     * @param piece
     * @return either an OccupiedTile or EmptyTile depending on piece parameter.
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ?
                new OccupiedTile(tileCoordinate, piece)
                : EMPTY_TILES_CACHE.get(tileCoordinate);
    }


    /**
     * Populate a full NUM_TILES length map with Key: Coordinate and Value: EmptyTile.
     * @return a HashMap of EmptyTile objects mapped to their coordinate value.
     */
    public static Map<Integer, EmptyTile> populateEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Collections.unmodifiableMap(emptyTileMap); // Makes map immutable.
    }

    public int getTileCoordinate() {
        return TILE_COORDINATE;
    }
}
