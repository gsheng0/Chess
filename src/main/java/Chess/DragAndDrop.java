package Chess;

import Chess.Board.Board;
import Chess.Board.Tile;
import Chess.Piece.Piece;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DragAndDrop extends MouseComboListener {
    private boolean clicked = false;
    private static boolean created = false;
    private static DragAndDrop instance;
    private Piece selection = null;
    private Board board;
    private Vector difference = new Vector();
    private Vector initial = new Vector();
    private DragAndDrop(){ }
    public static DragAndDrop getInstance() {
        if(!created) {
            instance = new DragAndDrop();
            created = true;
        }
        return instance;
    }
    @Override
    public boolean hasSelection() { return selection != null; }
    @Override
    public Piece getSelection() { return selection; }
    public Vector getDifference() { return difference; }
    @Override
    public void setBoard(Board board){
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        clicked = true;
        Point point = mouseEvent.getPoint();
        initial = new Vector(point.x, point.y);
        Vector gridLoc = getGridLocation(point);
        if(board.get(gridLoc).hasPiece())
            selection = board.get(gridLoc).getPiece();
        else
            return;
        difference = new Vector();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(selection == null)
            return;
        Vector gridLoc = getGridLocation(mouseEvent.getPoint());
        for(Tile tile : selection.getPossibleMoves())
            if(gridLoc.equals(tile.location))
                selection.capture(tile);
        selection = null;
        initial = null;
        clicked = false;
        difference = null;

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(clicked && selection != null) {
            difference = Vector.convert(mouseEvent.getPoint()).subtract(initial);
        }
    }
}
