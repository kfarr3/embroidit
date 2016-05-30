package ewu.embroidit.parkc.shape;

import javafx.geometry.Point2D;

/*-----------------------------------------------------------------------*/
/**
 * Structural value class used to hold the dimensional values of an object for
 * use in embroidery shape editing.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbShapeDimension
{
    Point2D startCoord, endCoord;
    private double width, height;
    private String type;
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructor for Rectangle and Ellipse dimensions.
     * @param type String
     * @param startCoord Point2D
     * @param width double
     * @param height double
     */
    public EmbShapeDimension(String type, Point2D startCoord, double width, double height)
    {
        this.type = type;
        this.startCoord = startCoord;
        this.width = width;
        this.height = height;
        
        this.endCoord = new Point2D(startCoord.getX() + width,
        startCoord.getY() + height);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructor for Line dimensions.
     * @param type String
     * @param startCoord Point2D
     * @param endCoord  Point2D
     */
    public EmbShapeDimension(String type, Point2D startCoord, Point2D endCoord)
    {
        this.type = type;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
        
        width = height = 0;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the type of shape these dimensions belong to
     * in string format.
     * @return String
     */
    public String type()
    { return this.type; }
    
    /**
     * Return the starting coordinate of this dimension.
     * @return Point2D
     */
    public Point2D getStartCoord()
    { return this.startCoord; }
    
    /**
     * return the ending coordinate of this dimension.
     * @return Point2D
     */
    public Point2D getEndCoord()
    { return this.endCoord; }
    
    /**
     * Return the width value of this dimension.
     * @return double
     */
    public double getWidth()
    { return this.width; }
    
    /**
     * Return the height value of this dimension.
     * @return double
     */
    public double getHeight()
    { return this.height; }
    
    /*-----------------------------------------------------------------------*/
    
}
