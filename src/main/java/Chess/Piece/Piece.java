package Chess.Piece;

import Chess.Board.Board;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Vector;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Piece implements Cloneable {
    private Tile tile;
    private int moves = 0;
    private ArrayList<Piece> captures = new ArrayList<>();
    private int pointValue;
    private ArrayList<Vector> movePattern;
    private Color side;
    private Board board;
    private Class<? extends Piece> type;
    private ArrayList<Tile> possibleMoves = new ArrayList<>();
    private Image image;
    public Piece(Tile tile, int pointValue, ArrayList<Vector> movePattern, Color side, Class<? extends Piece> type)
    {
        this.tile = tile;
        this.pointValue = pointValue;
        this.movePattern = movePattern;
        this.side = side;
        this.type = type;
        tile.putPiece(this);
    }
    protected Piece(Tile tile, int pointValue, ArrayList<Vector> movePattern, Color side, Class<? extends Piece> type, ArrayList<Piece> captures, Board board, int moves)
    {
        this.tile = tile;
        this.pointValue = pointValue;
        this.movePattern = movePattern;
        this.side = side;
        this.type = type;
        this.captures = captures;
        this.board = board;
        this.moves = moves;
    }
    public Class<? extends Piece> truePieceType() { return type; }
    public void setImage(Image image){
        this.image = image;
    }
    public static Image getImage(Class<? extends Piece> type, Color side)
    {
        return new ImageIcon("C:\\myapp\\Chess\\src\\main\\resources\\" + (side == Color.WHITE ? "WHITE":"BLACK") + "_" + type.getSimpleName().toUpperCase() + ".png").getImage();
    }
    public Image getImage() { return image; }
    public void setBoard(Board board) { this.board = board; }
    public Board getBoard() { return board; }
    public Color getSide() { return side; }
    public boolean sameSide(Piece other) { return other.side == side; }
    public Tile getTile() { return tile; }
    public Vector getLocation() { return tile.location; }
    public ArrayList<Vector> getMovePattern() {return movePattern; }
    public ArrayList<Piece> getCaptures() { return captures; }
    public int getMoves() { return moves; }
    public void capturedBy(Piece other) {
        board.remove(this);
        this.tile = null;
        other.captures.add(this);
    }

    public void capture(Tile tile)
    {
        if(tile.getPiece() != null) {
            tile.getPiece().capturedBy(this);
            captures.add(tile.getPiece());
            tile.removePiece();
        }

        this.tile.removePiece();
        this.tile = tile;
        tile.putPiece(this);
        moves++;
        board.nextTurn();
    }

    protected void setPossibleMoves(ArrayList<Tile> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
    public ArrayList<Tile> getPossibleMoves() { return possibleMoves; }

    public int getCaptureCount() { return captures.size(); }
    public void updatePossibleMovesWithoutCheck(){
        ArrayList<Vector> movePattern = getMovePattern();
        ArrayList<Tile> possibleMoves = new ArrayList<>();
        Vector current = tile.location;
        for(Vector move : movePattern)
        {
            for(int i = 1; i < 8; i++)
            {
                Vector possible = current.add(move.times(i));
                if(board.contains(possible))
                    if(!board.get(possible).hasPiece() || board.get(possible).getPiece().getSide() != side)
                        possibleMoves.add(board.get(possible));

                if(!board.contains(possible) || board.get(possible).hasPiece())
                    break;
            }
        }
        this.possibleMoves = possibleMoves;
    }
    public void updatePossibleMoves() {
        updatePossibleMovesWithoutCheck();
        this.possibleMoves = board.getKing(this.getSide()).inCheck() ? getCheckResponseMoves(possibleMoves) : possibleMoves;
    }

    public String toString() {
        return side.name() + " Piece: " + type.getSimpleName() + " Location: " + tile.location;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Piece && ((Piece) obj).toString().equals(this.toString());
    }

    public void convertToQueen()
    {
        board.remove(this);
        Queen replacement = new Queen(tile, 1, side, captures, board, moves);
        board.get(tile.location).putPiece(replacement);
        board.getPieces().add(replacement);
    }

    public ArrayList<Tile> getCheckResponseMoves(ArrayList<Tile> possibleMoves) {
        ArrayList<Piece> threateningPieces = board.getKing(this.getSide()).getThreateningPieces();
        ArrayList<Tile> output = new ArrayList<>();
        for(Piece threat : threateningPieces)
        {
            if(threat.truePieceType() == Knight.class) {
                if(this.canMoveTo(threat.tile))
                    output.add(threat.tile);
            }
            else {
                for (Tile possible : threat.possibleMoves)
                    if (this.canMoveTo(possible))
                        output.add(possible);
            }
        }
        return possibleMoves;
    }

    public boolean canMoveTo(Tile tile) {
        for(Tile possible : possibleMoves)
            if(possible.equals(tile))
                return true;
        return false;
    }
}
