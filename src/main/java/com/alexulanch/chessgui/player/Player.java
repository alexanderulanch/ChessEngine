package com.alexulanch.chessgui.player;

import com.alexulanch.chessgui.Alliance;
import com.alexulanch.chessgui.board.Board;
import com.alexulanch.chessgui.board.Move;
import com.alexulanch.chessgui.board.MoveTransition;
import com.alexulanch.chessgui.pieces.King;
import com.alexulanch.chessgui.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    protected final Collection<Move> opponentMoves;
    protected final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {

        this.board = board;
        this.legalMoves = legalMoves;
        this.opponentMoves = opponentMoves;
        this.playerKing = establishKing();
        this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing(piece)) {
                return (King) piece;
            }
        }
        return null;
    }

    private static Collection<Move> calculateAttacksOnTile(final int piecePosition,
                                                           final Collection<Move> opponentMoves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : opponentMoves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }

        return Collections.unmodifiableList(attackMoves);
    }

    private boolean hasEscapeMoves() {
        for (final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return isInCheck;
    }

    public boolean isInCheckMate() {

        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        return null;
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}
