package ewu.embroidit.parkc.pattern;

import ewu.embroidit.parkc.io.StitchCode;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;


/*-----------------------------------------------------------------------*/
/**
 * Represents a single embroidery stitch in a pattern.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbStitch
{
    /*-----------------------------------------------------------------------*/
    
    
    private int flag;                //Machine code stitch flag
    private int colorIndex;          //Color index in threadlist for import
    private Color stitchColor;
    private Point2D absPosition;     //Absolute (x,y) position
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs an embroidery stitch with the given position, initialized
     * with a normal encoding.
     * @param absPosition Point2D
     */
    public EmbStitch(Point2D absPosition)
    {
        this.validateObject(absPosition);
        this.absPosition = absPosition;
        this.flag = StitchCode.NORMAL;
        this.stitchColor = Color.BLACK;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs an embroidery stitch with the given position and encoding
     * flag.
     * @param absPosition Point2D
     * @param flag int
     */
    public EmbStitch(Point2D absPosition, int flag)
    {
        this.validateObject(absPosition);
        this.absPosition = absPosition;
        this.flag = flag;
        this.stitchColor = Color.BLACK;
    }
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs an embroidery stitch with the given absolute position, color,
     * and machine code flag.
     * @param absPosition javafx.geometry.Point2D
     * @param color int
     * @param flag int
     */
    public EmbStitch(Point2D absPosition, int color, int flag )
    {
        this.validateObject(absPosition);
        this.absPosition = absPosition;
        this.colorIndex = color;
        this.flag = flag;
        this.stitchColor = Color.BLACK;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets this stitches machine code flag.
     * @return int
     */
    public int getFlag()
    { return this.flag; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets this stitches machine code flag.
     * @param flag int
     */
    public void setFlag(int flag)
    { this.flag = flag; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets this stitches color index.
     * @return int
     */
    public int getColorIndex()
    { return this.colorIndex; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets this stitches color index.
     * @param color int
     */
    public void setColorIndex(int color)
    { this.colorIndex = color; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets this stitches absolute position.
     * @return javafx.geometry.Point2D
     */
    public Point2D getStitchPosition()
    { return this.absPosition; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets this stitches absolute position.
     * @param point javafx.geometry.Point2D
     */
    public void setStitchPosition(Point2D point)
    {
        this.validateObject(point);
        this.absPosition = point;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     *Gets the color of this stitch.
     * @return Color
     */
    public Color getColor()
    { return this.stitchColor; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets the color of this stitch.
     * @param color Color
     */
    public void setColor(Color color)
    { this.stitchColor = color; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the object sent as a parameter exists.
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
        { throw new RuntimeException("EmbStitch: Null reference error"); }
    }
    
    /*-----------------------------------------------------------------------*/
    
}
