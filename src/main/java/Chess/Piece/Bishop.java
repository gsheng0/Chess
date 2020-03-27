package Chess.Piece;

import Chess.Board.Board;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    private static final int POINT_VALUE = 3;
    public static ArrayList<Vector> movePattern = new ArrayList<>();
    public Bishop(Tile tile, Color side) {
        super(tile, POINT_VALUE, movePattern, side, Bishop.class);
        super.setImage(Piece.getImage(this.getClass(), side));
    }
    protected Bishop(Tile tile, int pointValue, Color side, ArrayList<Piece> captures, Board board, int moves) {
        super(tile, pointValue, movePattern, side, Bishop.class, captures, board, moves);
    }

    static {
        movePattern.add(new Vector(1, 1));
        movePattern.add(new Vector(-1, 1));
        movePattern.add(new Vector(-1, -1));
        movePattern.add(new Vector(1, -1));
    }
}
