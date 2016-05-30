package ewu.embroidit.parkc.shape;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/*-----------------------------------------------------------------------*/
/**
 * A property wrapper for a rectangle.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbShapeWrapperTatamiFill extends A_EmbShapeWrapper
{
    /*-----------------------------------------------------------------------*/
    
    private double angle;           //Angle of fill stitches. (0-180 degrees)
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFX shape, and default values.
     * @param wrappedShape  Shape
     */
    public EmbShapeWrapperTatamiFill(Shape wrappedShape)
    {
        super(wrappedShape);
        this.angle = 0;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFX Shape, the shapes fill stitch 
     * length, and default values. Ensures a minimum stitch length of 1mm.
     * @param wrappedShape Shape
     * @param stitchLength double
     */
    public EmbShapeWrapperTatamiFill(Shape wrappedShape, double stitchLength)
    {
        super(wrappedShape, stitchLength);
        this.angle = 0;
    }
    
    /*-----------------------------------------------------------------------*/
    /**
     * Constructs a wrapper containing a JavaFX shape, the starting location
     * for stitch filling, the angle of the fill stitches and an empty list of 
     * stitches to hold fill data once calculated.
     * 
     * @param wrappedShape Shape
     * @param startPoint Point2D
     * @param angle double
     * @param stitchLength double
     */
    public EmbShapeWrapperTatamiFill(Shape wrappedShape, Point2D startPoint,
            double angle, double stitchLength)
    {
        super(wrappedShape, startPoint);
        
        this.angle = angle; //Figure out how to enforce range (0-180)
        this.stitchLength = stitchLength;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets the angle of the parallel stitches for the wrapped shape.
     * @return double
     */
    public double getAngle()
    { return this.angle; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets the angle of the parallel stitches for this wrapped shape.
     * @param angle double
     */
    public void setAngle(double angle)
    { this.angle = angle; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns an object containing the dimensional properties of this
     * wrappers contained shape.
     * @return EmbShapeDimension
     */
    @Override
    public EmbShapeDimension getDimensions()
    {
        Rectangle rect = (Rectangle) this.getWrappedShape();
        EmbShapeDimension dims = new EmbShapeDimension("rectangle",
                new Point2D(rect.getX(), rect.getY()),
                rect.getWidth(), rect.getHeight());
        return dims;
    }
    
    /*-----------------------------------------------------------------------*/
}
