package Chess.Piece;

import Chess.Board.Board;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
    private static final int POINT_VALUE = 1;
    public ArrayList<Vector> movePattern;
    private boolean moved = false;
    public Pawn(Tile tile, Color side) {
        super(tile, POINT_VALUE, new ArrayList<>(Arrays.asList(new Vector(0, side == Color.WHITE ? -1:1), new Vector(0, side == Color.WHITE ? -2:2))), side, Pawn.class);
        movePattern = super.getMovePattern();
        super.setImage(Piece.getImage(this.getClass(), side));
    }
    protected Pawn(Tile tile, int pointValue, ArrayList<Vector> movePattern, Color side, ArrayList<Piece> captures, Board board, int moves, boolean moved) {
        super(tile, pointValue, movePattern, side, Queen.class, captures, board, moves);
        this.moved = moved;
    }

    @Override
    public void updatePossibleMovesWithoutCheck() {
        ArrayList<Vector> movePattern = super.getMovePattern();
        ArrayList<Tile> possibleMoves = new ArrayList<>();
        Board board = super.getBoard();
        Vector current = super.getLocation();
        for(Vector move : movePattern)
        {
            Vector possible = move.add(current);
            try{
                if(!board.get(possible).hasPiece())
                    possibleMoves.add(board.get(possible));
                else
                    break;
            }
            catch(Exception e)
            { }
        }
        ArrayList<Vector> attackMoves = new ArrayList<>(Arrays.asList(movePattern.get(0).add(new Vector(-1, 0)), movePattern.get(0).add(new Vector(1, 0))));
        for(Vector move : attackMoves)
        {
            Vector possible = move.add(current);
            try{
                if(board.get(possible).hasPiece() && board.get(possible).getPiece().getSide() != this.getSide())
                    possibleMoves.add(board.get(possible));
            }
            catch(Exception e)
            {}
        }
        super.setPossibleMoves(possibleMoves);
    }

    @Override
    public void updatePossibleMoves() {
        updatePossibleMovesWithoutCheck();
        super.setPossibleMoves(this.getBoard().getKing(this.getSide()).inCheck() ? super.getCheckResponseMoves() : this.getPossibleMoves());
    }

    @Override
    public void capture(Tile tile) {
        super.capture(tile);
        if(movePattern.size() > 1)
            movePattern.remove(1);
        if(getLocation().y == (this.getSide() == Color.WHITE ? 0 : 7)) {
            this.convertToQueen();
        }
    }

}
