package ewu.embroidit.parkc.shape;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/*-----------------------------------------------------------------------*/
/**
 * Shape wrapper for lines.
 * @author: Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbShapeWrapperLine extends A_EmbShapeWrapper
{
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFX shape, and default values.
     * @param wrappedShape Shape
     */
    public EmbShapeWrapperLine(Shape wrappedShape)
    {
        super(wrappedShape);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a wrapper containing a JavaFXS Shape, the shapes fill stitch
     * length, and default values. Ensures a minimum stitch length of 1mm.
     * @param wrappedShape Shape
     * @param stitchLength double
     */
    public EmbShapeWrapperLine(Shape wrappedShape, double stitchLength)
    {
        super(wrappedShape, stitchLength);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns an object containing the dimensional properties of this
     * wrappers contained shape.
     * @return EmbShapeDimension
     */
    @Override
    public EmbShapeDimension getDimensions()
    {
        Line line = (Line) this.getWrappedShape();
        EmbShapeDimension dims = new EmbShapeDimension("line",
                new Point2D(line.getStartX(), line.getStartY()),
                new Point2D(line.getEndX(), line.getEndY()));
        
        return dims;
    }
    
    /*-----------------------------------------------------------------------*/
}
