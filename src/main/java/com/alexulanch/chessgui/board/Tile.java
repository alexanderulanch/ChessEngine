package com.alexulanch.chessgui.board;

import com.alexulanch.chessgui.pieces.*;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * The class for implementing the individual tiles on the chess board.
 * @author Alex Ulanch
 */
public abstract class Tile {
    protected static final int NUM_TILES = 64; // Total number of tiles on an 8x8 board.
    protected final int TILE_COORDINATE;
    protected final Color TILE_COLOR;
    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = populateEmptyTiles();

    protected Tile(final int tileCoordinate, final Color tileColor) {
        this.TILE_COORDINATE = tileCoordinate;
        this.TILE_COLOR = tileColor;
    }

    /**
     * Creates and returns an OccupiedTile if Piece parameter is not null.
     * else returns an EmptyTile from cache with given tileCoordinate.
     * @param tileCoordinate
     * @param tileColor
     * @param piece
     * @return either an OccupiedTile or EmptyTile depending on piece parameter.
     */
    public static Tile createTile(final int tileCoordinate,
                                  final Color tileColor,
                                  final Piece piece) {

        return piece != null ?
                new OccupiedTile(tileCoordinate, tileColor, piece)
                : EMPTY_TILES_CACHE.get(tileCoordinate);
    }


    /**
     * Populate a full NUM_TILES length map with Key: Coordinate and Value: EmptyTile.
     * @return a HashMap of EmptyTile objects mapped to their coordinate value.
     */
    public static Map<Integer, EmptyTile> populateEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++) {
            Color tileColor = i % 2 == 0 ? Color.BLACK : Color.WHITE;
            emptyTileMap.put(i, new EmptyTile(i, tileColor));
        }

        return Collections.unmodifiableMap(emptyTileMap); // Makes map immutable.
    }

    public int getTileCoordinate() {
        return TILE_COORDINATE;
    }

    public Color getColor() {
        return TILE_COLOR;
    }
}
