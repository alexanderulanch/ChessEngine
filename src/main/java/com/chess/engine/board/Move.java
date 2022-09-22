package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

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

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        result = PRIME * result + destinationCoordinate;
        result = PRIME * result + movedPiece.hashCode();

        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) { return true; }
        if (!(other instanceof Move)) { return false; }

        final Move otherMove = (Move) other;

        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
               getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public boolean isAttack() { return false; }
    public boolean isCastlingMove() { return false;}

    public int getCurrentCoordinate() { return movedPiece.getPiecePosition(); }
    public int getDestinationCoordinate() { return destinationCoordinate; }
    public Piece getMovedPiece() { return movedPiece; }
    public Piece getAttackedPiece() { return null; }

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

    public static final class MajorMove extends Move {
        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public boolean isAttack() { return true; }

        @Override
        public Piece getAttackedPiece() { return attackedPiece; }

        @Override
        public int hashCode() { return attackedPiece.hashCode() + super.hashCode(); }

        @Override
        public boolean equals(final Object other) {
            if (this == other) { return true; }
            if (!(other instanceof  AttackMove)) { return false; }

            final AttackMove otherAttackMove = (AttackMove) other;

            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }
    }

    public static final class PawnMove extends Move {
        public PawnMove(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class PawnJump extends Move {
        public PawnJump(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();

            for (final Piece piece : board.getCurrentPlayer().getActivePieces()) {
                if (!movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn) getMovedPiece().movePiece(this);

            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(board.getCurrentPlayer().getAlliance());

            return builder.build();

        }
    }

    public static class PawnAttackMove extends AttackMove {
        public PawnAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {
        public PawnEnPassantAttackMove(final Board board,
                                       final Piece movedPiece,
                                       final int destinationCoordinate,
                                       final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    static abstract class CastleMove extends Move {
        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;


        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();

            for (final Piece piece : board.getCurrentPlayer().getActivePieces()) {
                if (!movedPiece.equals(piece) && this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPiece(movedPiece.movePiece(this));
            builder.setPiece(new Rook(castleRookDestination, castleRook.getPieceAlliance()));
            builder.setMoveMaker(board.getCurrentPlayer().getOpponent().getAlliance());

            return builder.build();
        }
    }

    public static class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate,
                                  final Rook castleRook,
                                  final int castleRookStart,
                                  final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStart,
                                   final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    static class NullMove extends Move {
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot execute null move");
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("Not Instantiable!");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestinationCoordinate() == destinationCoordinate) {
                        return move;
                }
            }

            return new NullMove();
        }
    }
}
