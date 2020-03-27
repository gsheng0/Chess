package Chess.Piece;

import Chess.Board.Board;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends Piece{
    private static final int POINT_VALUE = 9;
    public static ArrayList<Vector> movePattern = new ArrayList<>();
    public Queen(Tile tile, Color side) {
        super(tile, POINT_VALUE, movePattern, side, Queen.class);
        super.setImage(Piece.getImage(this.getClass(), side));
    }
    protected Queen(Tile tile, int pointValue, Color side, ArrayList<Piece> captures, Board board, int moves) {
        super(tile, pointValue, movePattern, side, Queen.class, captures, board, moves);
    }

    static {
        movePattern.add(new Vector(1, 1));
        movePattern.add(new Vector(1, 0));
        movePattern.add(new Vector(1, -1));

        movePattern.add(new Vector(0, 1));
        movePattern.add(new Vector(0, -1));

        movePattern.add(new Vector(-1, 1));
        movePattern.add(new Vector(-1, 0));
        movePattern.add(new Vector(-1, -1));
    }
}
