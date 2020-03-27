package Chess;

import Chess.Board.Board;
import Chess.Board.Tile;
import Chess.Piece.Piece;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ClickAndMove extends MouseComboListener{
    private boolean clicked = false;
    private static boolean created = false;
    private static ClickAndMove instance;
    private Piece selection = null;
    private Board board;
    Vector difference = new Vector();
    private Vector initial = new Vector();
    private ClickAndMove() {}
    public static ClickAndMove getInstance() {
        if(!created) {
            instance = new ClickAndMove();
            created = true;
        }
        return instance;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        clicked = true;
        Point point = mouseEvent.getPoint();
        initial = new Vector(point.x, point.y);
        Vector gridLoc = getGridLocation(point);
        if(selection != null) {
            for (Tile tile : selection.getPossibleMoves()) {
                if (gridLoc.equals(tile.location)) {
                    selection.capture(tile);
                    selection = null;
                    difference = new Vector();
                    board.nextTurn();
                    return;
                }
            }
        }

        if(board.get(gridLoc).hasPiece())// && board.get(gridLoc).getPiece().getSide() == board.getCurrentTurn())
            selection = board.get(gridLoc).getPiece();
        else
            selection = null;
        difference = new Vector();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(selection == null)
            return;

        for(Tile tile : selection.getPossibleMoves())
        {
            if(getGridLocation(mouseEvent.getPoint()).equals(tile.location)) {
                    selection.capture(tile);
                    selection = null;
                    difference = new Vector();
                    board.nextTurn();
                    return;
            }
        }
        difference = new Vector();
        initial = null;
        clicked = false;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(clicked && selection != null) {
            difference = Vector.convert(mouseEvent.getPoint()).subtract(initial);
        }
    }

    @Override
    public boolean hasSelection() { return selection != null; }

    @Override
    public Piece getSelection() { return selection; }

    @Override
    public Vector getDifference() { return difference; }

    @Override
    public void setBoard(Board board){
        this.board = board;
    }
}
