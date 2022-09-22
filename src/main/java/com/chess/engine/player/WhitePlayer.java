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

public class WhitePlayer extends Player {


    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playersLegalMoves, Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck) {
            // King-Side Castle
            if(!this.board.getTile(61).isOccupied() &&
               !this.board.getTile(62).isOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(calculateAttacksOnTile(61, opponentsLegalMoves).isEmpty() &&
                       calculateAttacksOnTile(62, opponentsLegalMoves).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(board, playerKing,
                                62, (Rook) rookTile.getPiece(),
                                rookTile.getTileCoordinate(), 61));
                    }
                }
            }

            // Queen-Side Castle
            if(!this.board.getTile(57).isOccupied() &&
               !this.board.getTile(58).isOccupied() &&
               !this.board.getTile(59).isOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(calculateAttacksOnTile(58, opponentsLegalMoves).isEmpty() &&
                       calculateAttacksOnTile(59, opponentsLegalMoves).isEmpty() &&
                       rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(board, playerKing,
                                58, (Rook) rookTile.getPiece(),
                                rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
