package Chess.Piece;

import Chess.Board.Board;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece {
    private static final int POINT_VALUE = 3;
    public static ArrayList<Vector> movePattern = new ArrayList<>();
    public Knight(Tile tile, Color side) {
        super(tile, POINT_VALUE, movePattern, side, Knight.class);
        super.setImage(Piece.getImage(this.getClass(), side));
    }
    protected Knight(Tile tile, int pointValue, Color side, ArrayList<Piece> captures, Board board, int moves) {
        super(tile, pointValue, movePattern, side, Knight.class, captures, board, moves);
    }

    static {
        movePattern.add(new Vector(1, 2));
        movePattern.add(new Vector(2, 1));
        movePattern.add(new Vector(2, -1));
        movePattern.add(new Vector(1, -2));

        movePattern.add(new Vector(-1, -2));
        movePattern.add(new Vector(-2, -1));
        movePattern.add(new Vector(-2, 1));
        movePattern.add(new Vector(-1, 2));
    }

    @Override
    public void updatePossibleMovesWithoutCheck() {
        ArrayList<Vector> movePattern = super.getMovePattern();
        ArrayList<Tile> possibleMoves = new ArrayList<>();
        Board board = super.getBoard();
        Vector current = super.getLocation();
        for(Vector move : movePattern)
        {
            Vector possible = current.add(move);
            if(board.contains(possible) && (!board.get(possible).hasPiece() || board.get(possible).getPiece().getSide() != this.getSide()))
                possibleMoves.add(board.get(possible));
        }
        super.setPossibleMoves(possibleMoves);
    }
    @Override
    public void updatePossibleMoves() {
        this.updatePossibleMovesWithoutCheck();
        super.setPossibleMoves(this.getBoard().getKing(this.getSide()).inCheck() ? super.getCheckResponseMoves(this.getPossibleMoves()) : this.getPossibleMoves());
    }
}
