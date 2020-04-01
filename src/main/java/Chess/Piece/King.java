package Chess.Piece;

import Chess.Board.Board;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Vector;

import java.util.ArrayList;

public class King extends Piece {
    private static final int POINT_VALUE = 0;
    public static ArrayList<Vector> movePattern = new ArrayList<>();
    private boolean check = false;
    private ArrayList<Piece> threateningPieces = new ArrayList<>();
    public King(Tile tile, Color side){
        super(tile, POINT_VALUE, movePattern, side, King.class);
        super.setImage(Piece.getImage(this.getClass(), side));
    }
    protected King(Tile tile, int pointValue, Color side, ArrayList<Piece> captures, Board board, int moves) {
        super(tile, pointValue, movePattern, side, King.class, captures, board, moves);
    }

    static{
        movePattern.add(new Vector(1, 1));
        movePattern.add(new Vector(1, 0));
        movePattern.add(new Vector(1, -1));

        movePattern.add(new Vector(0, 1));
        movePattern.add(new Vector(0, -1));

        movePattern.add(new Vector(-1, 1));
        movePattern.add(new Vector(-1, 0));
        movePattern.add(new Vector(-1, -1));
    }

    @Override
    public void updatePossibleMovesWithoutCheck() {
        ArrayList<Vector> movePattern = super.getMovePattern();
        ArrayList<Tile> possibleMoves = new ArrayList<>();
        Board board = super.getBoard();
        Vector current = super.getLocation();
        updateCheck();
        for(Vector move : movePattern)
        {
            Vector possible = current.add(move);
            if(board.contains(possible) && isSafe(board.get(possible)) && (!board.get(possible).hasPiece() || board.get(possible).getPiece().getSide() != this.getSide()))
                possibleMoves.add(board.get(possible));
        }
        super.setPossibleMoves(possibleMoves);
    }

    @Override
    public void updatePossibleMoves() {
        this.updatePossibleMovesWithoutCheck();
        //super.setPossibleMoves(this.getBoard().getKing(this.getSide()).inCheck() ? super.getCheckResponseMoves() : this.getPossibleMoves());
    }

    public boolean inCheck() { return check; }
    public void updateCheck(){ check = !isSafe(getTile()); }

    private boolean isSafe(Tile tile)
    {
        ArrayList<Piece> opposition = getBoard().getPieces(this.getSide() == Color.WHITE ? Color.BLACK : Color.WHITE);
        for(Piece piece : opposition)
        {
            if(piece.truePieceType() != King.class) {
                for (Tile possible : piece.getPossibleMoves()) {
                    if (possible.equals(tile))
                        return false;
                }
            }
        }
        return true;
    }
}
