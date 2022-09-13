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
    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public String toString() { return "P"; }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate =
                    this.getPiecePosition() +
                    (currentCandidateOffset *
                    this.getPieceAlliance().getDirection());

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && board.getTile(candidateDestinationCoordinate).isOccupied()) {
                //TODO To be amended.
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && // Handles pawn's ability to jump on first move
                    this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.getPiecePosition()] && this.getPieceAlliance().isWhite()) ||
                    (BoardUtils.SEVENTH_ROW[this.getPiecePosition()] && this.getPieceAlliance().isBlack())) {
                final int behindCandidateDestinationCoordinate = this.getPiecePosition() + (this.getPieceAlliance().getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isOccupied() &&
                    !board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    //FIXME
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 && // Handles condition where pawn is on edge file.
                      !((BoardUtils.EIGHTH_COLUMN[this.getPiecePosition()] && this.getPieceAlliance().isWhite()) ||
                      (BoardUtils.FIRST_COLUMN[this.getPiecePosition()] && this.getPieceAlliance().isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    final Piece pieceOnCandidateTile = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.getPieceAlliance() != pieceOnCandidateTile.getPieceAlliance()) {
                        //FIXME after creating AttackMove class
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            } else if (currentCandidateOffset == 9 && // Handles condition where pawn is on edge file.
                    !((BoardUtils.FIRST_COLUMN[this.getPiecePosition()] && this.getPieceAlliance().isWhite()) ||
                    (BoardUtils.EIGHTH_COLUMN[this.getPiecePosition()] && this.getPieceAlliance().isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isOccupied()) {
                    final Piece pieceOnCandidateTile = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.getPieceAlliance() != pieceOnCandidateTile.getPieceAlliance()) {
                        //FIXME after creating AttackMove class
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

                return Collections.unmodifiableList(legalMoves);
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }
}
