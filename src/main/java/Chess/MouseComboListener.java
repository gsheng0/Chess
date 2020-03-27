package Chess;

import Chess.Board.Board;
import Chess.Piece.Piece;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static Chess.App.TILE_SIZE;

public abstract class MouseComboListener<T> implements MouseListener, MouseMotionListener {

    @Override
    public abstract void mousePressed(MouseEvent mouseEvent);

    @Override
    public abstract void mouseReleased(MouseEvent mouseEvent);

    @Override
    public abstract void mouseDragged(MouseEvent mouseEvent);

    public abstract void setBoard(Board board);
    public abstract boolean hasSelection();
    public abstract Piece getSelection();
    public abstract Vector getDifference();

    @Override
    public void mouseMoved(MouseEvent mouseEvent){}
    @Override
    public void mouseClicked(MouseEvent mouseEvent){}
    @Override
    public void mouseEntered(MouseEvent mouseEvent){}
    @Override
    public void mouseExited(MouseEvent mouseEvent){}

    public static Vector getGridLocation(Point point)
    {
        return new Vector(point.x/TILE_SIZE, (point.y - 30)/TILE_SIZE);
    }
}
