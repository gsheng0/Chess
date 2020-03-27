package Chess.Board;

import Chess.Piece.Piece;
import Chess.Vector;

public class Tile
{
    public Vector location;
    private Color color;
    private Piece piece = null;
    public Tile(Vector location, Color color)
    {
        this.location = location;
        this.color = color;
    }
    public boolean hasPiece() { return piece != null; }
    public Piece getPiece()
    {
        return piece;
    }
    public Color getColor() { return color; }
    public void removePiece()
    {
        piece = null;
    }
    public void putPiece(Piece piece)
    {
        this.piece = piece;
    }
    public boolean equals(Object other){
        return other instanceof Tile && ((Tile) other).location.equals(this.location);
    }
    public String toString() {
        return "Location: " + location + " Piece: " + piece;
    }

}
