package Chess;

import Chess.Board.Board;
import Chess.Board.BoardPreset;
import Chess.Board.Color;
import Chess.Board.Tile;
import Chess.Piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class App extends JPanel {
    private JFrame frame;
    private MouseComboListener listener;
    private ArrayList<Board> games = new ArrayList<>();
    private Board board;
    static final int TILE_SIZE = 50;
    public App(){
        listener = ClickAndMove.getInstance();
        board = new Board(BoardPreset.NORMAL);
        games.add(new Board(BoardPreset.NORMAL));
        listener.setBoard(board);
        frame = new JFrame("Chess");
        frame.add(this);
        frame.addMouseListener(listener);
        frame.addMouseMotionListener(listener);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        for(int x = 0; x < board.getBoard().length; x++)
        {
            for(int y = 0; y < board.getBoard()[0].length; y++)
            {
                g.setColor(board.get(x, y).getColor() == Color.WHITE ? java.awt.Color.WHITE : java.awt.Color.BLACK);
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        if(listener.hasSelection()) {
            g.setColor(java.awt.Color.YELLOW);
            g.fillRect(listener.getSelection().getLocation().x * TILE_SIZE, listener.getSelection().getLocation().y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            g.setColor(java.awt.Color.CYAN);
            for (Tile tile : listener.getSelection().getPossibleMoves()) {
                g.fillRect(tile.location.x * TILE_SIZE, tile.location.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        for(Piece piece : board.getPieces()) {
            if(listener.hasSelection() && piece.equals(listener.getSelection())) {
                Vector newLoc = piece.getLocation().times(TILE_SIZE).add(listener.getDifference());
                g.drawImage(piece.getImage(), newLoc.x, newLoc.y, TILE_SIZE, TILE_SIZE, (image, i, i1, i2, i3, i4) -> false);
            }
            else
                g.drawImage(piece.getImage(), piece.getLocation().x * TILE_SIZE, piece.getLocation().y * TILE_SIZE, TILE_SIZE, TILE_SIZE, (image, i, i1, i2, i3, i4) -> false);

        }

        repaint();
    }
    public Board getBoard() { return board; }
    public static void main(String args[])
    {
        App app = new App();
    }
}
