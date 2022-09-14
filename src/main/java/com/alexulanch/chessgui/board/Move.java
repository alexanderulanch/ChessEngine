package com.alexulanch.chessgui.board;

import com.alexulanch.chessgui.pieces.Piece;

public abstract class Move {
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;

    Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public int getDestinationCoordinate() { return destinationCoordinate; }
    public Piece getMovedPiece() { return movedPiece; }

    public abstract Board execute();

    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                  final Piece movedPiece,
                  final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();

            // Sets current players unmoved pieces on board builder.
            for (final Piece piece : board.getCurrentPlayer().getActivePieces()) {
                if (!movedPiece.equals(piece)) { //TODO hashcode and equals for Piece class
                    builder.setPiece(piece);
                }
            }

            // Sets opponents unmoved pieces on board builder.
            for (final Piece piece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
                    builder.setPiece(piece);
            }

            builder.setPiece(movedPiece.movePiece(this));
            builder.setMoveMaker(board.getCurrentPlayer().getOpponent().getAlliance());


            return builder.build();
        }
    }
    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate,
                   final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
