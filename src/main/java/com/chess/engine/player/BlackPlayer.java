package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {


    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playersLegalMoves, Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck) {
            // Queen-Side Castle
            if (!this.board.getTile(5).isOccupied() &&
                !this.board.getTile(6).isOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if (calculateAttacksOnTile(5, opponentsLegalMoves).isEmpty() &&
                    calculateAttacksOnTile(6, opponentsLegalMoves).isEmpty() &&
                    rookTile.getPiece().getPieceType().isRook()) {
                    kingCastles.add(null); //TODO
                }
            }

            // King-Side Castle
            if (!this.board.getTile(1).isOccupied() &&
                !this.board.getTile(2).isOccupied() &&
                !this.board.getTile(3).isOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove() &&
                    Player.calculateAttacksOnTile(2, opponentsLegalMoves).isEmpty() &&
                    rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(
                                new Move.QueenSideCastleMove(board, playerKing,
                                        2, (Rook) rookTile.getPiece(),
                                        rookTile.getTileCoordinate(), 3)); //TODO
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
