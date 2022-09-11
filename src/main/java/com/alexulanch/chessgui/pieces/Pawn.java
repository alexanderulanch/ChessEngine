package com.alexulanch.chessgui.pieces;

import com.alexulanch.chessgui.Alliance;
import com.alexulanch.chessgui.board.Board;
import com.alexulanch.chessgui.board.BoardUtils;
import com.alexulanch.chessgui.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pawn extends Piece  {
    private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16};

    Pawn(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            int candidateDestinationCoordinate =
                    this.getPiecePosition() +
                    (currentCandidateOffset *
                    this.getPieceAlliance().getDirection());

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && board.getTile(candidateDestinationCoordinate).isOccupied()) {
                //TODO To be amended.
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));;
            } else if (currentCandidateOffset == 16 &&
                    this.isFirstMove() &&
                    (BoardUtils.SECOND_RANK[this.getPiecePosition()] && this.getPieceAlliance().isWhite()) ||
                    (BoardUtils.SEVENTH_RANK[this.getPiecePosition()] && this.getPieceAlliance().isBlack())) {
                final int behindCandidateDestinationCoordinate = this.getPiecePosition() + (this.getPieceAlliance().getDirection() * 8);
                if (!Board.getTile(behindCandidateDestinationCoordinate).isOccupied() &&
                    !Board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    //FIXME
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));;
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }
}
