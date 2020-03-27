package Chess.Piece;

import Chess.Board.Board;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Vector;

import java.awt.*;
import java.util.ArrayList;


public class Rook extends Piece {
    private static final int POINT_VALUE = 4;
    public static ArrayList<Vector> movePattern = new ArrayList<>();
    public Rook(Tile tile, Color side){
        super(tile, POINT_VALUE, movePattern, side, Rook.class);
        super.setImage(Piece.getImage(this.getClass(), side));
    }
    protected Rook(Tile tile, int pointValue, Color side, ArrayList<Piece> captures, Board board, int moves) {
        super(tile, pointValue, movePattern, side, Rook.class, captures, board, moves);
    }


    static {
        movePattern.add(new Vector(1, 0));
        movePattern.add(new Vector(0, 1));
        movePattern.add(new Vector(-1, 0));
        movePattern.add(new Vector(0, -1));
    }
}
