package com.chess.engine.board;

public abstract class BoardUtils {
    public static final boolean[] A_FILE = initFile(0);
    public static final boolean[] B_FILE = initFile(1);
    public static final boolean[] G_FILE = initFile(6);
    public static final boolean[] H_FILE = initFile(7);

    public static final boolean[] EIGHTH_RANK = initRank(0);
    public static final boolean[] SEVENTH_RANK = initRank(8);
    public static final boolean[] SIXTH_RANK = initRank(16);
    public static final boolean[] FIFTH_RANK = initRank(24);
    public static final boolean[] FOURTH_RANK = initRank(32);
    public static final boolean[] THIRD_RANK = initRank(40);
    public static final boolean[] SECOND_RANK = initRank(48);
    public static final boolean[] FIRST_RANK = initRank(56);

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;


    private static boolean[] initFile(int columnNumber) {
        final var column = new boolean[NUM_TILES];

        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while ( columnNumber < NUM_TILES );

        return column;
    }

    private static boolean[] initRank(int rowNumber) {
        final var row = new boolean[NUM_TILES];
        int nextRowNumber = rowNumber + NUM_TILES_PER_ROW;

        for (int i = rowNumber; i < nextRowNumber; i++) {
            row[i] = true;
        }

        return row;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
}
