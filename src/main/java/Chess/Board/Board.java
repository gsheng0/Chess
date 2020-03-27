package Chess.Board;

import Chess.Piece.King;
import Chess.Piece.Piece;
import Chess.Vector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private ArrayList<Piece> pieces;
    private Color currentTurn = Color.WHITE;
    private Tile[][] board;
    private BoardPreset preset;
    private HashMap<Color, King> kings = new HashMap<>();
    private static int instances = 0;
    private int number = 0;
    public Board(BoardPreset preset)
    {
        number = instances;
        instances++;
        this.preset = preset;
        board = preset.getBoard();
        JToggleButton button = new JToggleButton();

        this.pieces = preset.getPieces();
        for(Piece piece : pieces) {
            piece.setBoard(this);
            piece.updatePossibleMovesWithoutCheck();
            if(piece.truePieceType() == King.class)
                kings.put(piece.getSide(), ((King) piece));
        }
    }
    public ArrayList<Piece> getPieces(Color side)
    {
        ArrayList<Piece> output = new ArrayList<>();
        for(Piece piece : pieces)
            if(piece.getSide() == side)
                output.add(piece);
        return output;
    }
    public int getBoardNumber() { return number; }
    public void remove(Piece piece) { pieces.remove(piece); }
    public Vector size() { return new Vector(board.length, board[0].length); }
    public Tile get(Vector location) { return get(location.x, location.y);}
    public int getPieceCount() { return pieces.size(); }
    public Tile get(int x, int y) { return board[x][y];}
    public BoardPreset getPreset() { return preset; }
    public Tile[][] getBoard() { return board; }
    public Color getCurrentTurn() { return currentTurn; }
    public ArrayList<Piece> getPieces() { return pieces; }
    public boolean contains(Vector vector) { try{ get(vector); return true;} catch(Exception e){ return false; } }
    public void nextTurn() {
        currentTurn = currentTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
        for(Piece piece : pieces) {
            piece.updatePossibleMoves();
            if(piece.truePieceType() == King.class)
                ((King) piece).updateThreateningPieces();
        }
    }
    public King getKing(Color side) { return kings.get(side); }
}
