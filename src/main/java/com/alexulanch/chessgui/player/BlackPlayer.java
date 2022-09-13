package com.alexulanch.chessgui.player;

import com.alexulanch.chessgui.Alliance;
import com.alexulanch.chessgui.board.Board;
import com.alexulanch.chessgui.board.Move;
import com.alexulanch.chessgui.pieces.Piece;

import java.util.Collection;

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
}
