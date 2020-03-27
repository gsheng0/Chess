package Chess.Board;

import Chess.Piece.*;
import Chess.Vector;

import java.util.ArrayList;

public class BoardPreset {
    private Vector size;
    private ArrayList<Piece> pieces;
    private Tile[][] board;
    public static BoardPreset NORMAL;
    static {
        Tile[][] board = setUpTileBoard(new Vector(8, 8));
        ArrayList<Piece> pieces = new ArrayList<>();

        for(int i = 0; i < 8; i++)
        {
            pieces.add(new Pawn(board[i][1], Color.BLACK));
            pieces.add(new Pawn(board[i][6], Color.WHITE));
        }

        pieces.add(new Rook(board[0][0], Color.BLACK));
        pieces.add(new Rook(board[7][0], Color.BLACK));
        pieces.add(new Rook(board[7][7], Color.WHITE));
        pieces.add(new Rook(board[0][7], Color.WHITE));

        pieces.add(new Knight(board[1][0], Color.BLACK));
        pieces.add(new Knight(board[6][0], Color.BLACK));
        pieces.add(new Knight(board[1][7], Color.WHITE));
        pieces.add(new Knight(board[6][7], Color.WHITE));

        pieces.add(new Bishop(board[2][0], Color.BLACK));
        pieces.add(new Bishop(board[5][0], Color.BLACK));
        pieces.add(new Bishop(board[2][7], Color.WHITE));
        pieces.add(new Bishop(board[5][7], Color.WHITE));

        pieces.add(new Queen(board[3][0], Color.BLACK));
        pieces.add(new King(board[4][0], Color.BLACK));
        pieces.add(new Queen(board[3][7], Color.WHITE));
        pieces.add(new King(board[4][7], Color.WHITE));

        NORMAL = new BoardPreset(board, pieces);
    }
    public BoardPreset(Tile[][] board, ArrayList<Piece> pieces)
    {
        this.board = board;
        this.size = new Vector(board.length, board[0].length);
        this.pieces = pieces;
    }
    public static Tile[][] setUpTileBoard(Vector dim) {
        Tile[][] board = new Tile[dim.x][dim.y];
        Color temp = Color.WHITE;
        for(int x = 0; x < board.length; x++)
        {
            for(int y = 0; y < board[0].length; y++)
            {
                try {
                    board[x][y] = new Tile(new Vector(x, y), board[x - 1][y].getColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
                }
                catch(Exception e) {
                    board[x][y] = new Tile(new Vector(x, y), temp);
                    temp = temp == Color.WHITE ? Color.BLACK : Color.WHITE;
                }
            }
        }


        return board;
    }
    public Tile[][] getBoard() { return board; }
    public Vector dims() { return size; }
    public ArrayList<Piece> getPieces() { return pieces; }
}
