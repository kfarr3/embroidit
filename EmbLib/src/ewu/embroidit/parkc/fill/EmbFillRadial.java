package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.math.EmbMath;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

/*-----------------------------------------------------------------------*/
/**
 * Fill strategy from a central point for an ellipse.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbFillRadial extends A_EmbFill
{
    /*-----------------------------------------------------------------------*/
    
    private static final int NUM_STITCHES = 30;
    
    /*-----------------------------------------------------------------------*/
    /**
     * Constructs a Radial stitch filling strategy.
     */
    public EmbFillRadial()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Implements a shape filling strategy for radial style fill stitches.
     * @param shapeWrapper A_EmbShapeWrapper
     */
    @Override
    public void fillShape(A_EmbShapeWrapper shapeWrapper)
    {
        Ellipse ellipse;
        List<Line> lineList;
        
        ellipse = (Ellipse) shapeWrapper.getWrappedShape();
        lineList = new ArrayList<>();
        fillEllipse(ellipse, lineList);
        shapeWrapper.setLineList(lineList);
        this.subDivideFillLines(shapeWrapper);
        shapeWrapper.toStitchList();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Determines the radial line segment at angles evenly subdivided  by a
     * preset number of stitches.
     * @param ellipse Ellipse
     * @param lineList List&lt;Line&gt;
     */
    private void fillEllipse(Ellipse ellipse, List<Line> lineList)
    {   
        double endX, endY, radius, degree, degreeInterval;
        Line radialLine;
        
        degreeInterval = 360 / NUM_STITCHES;
        degree = 0;
        for(int i = 0; i < NUM_STITCHES; i++)
        {
            radius = EmbMath.getEllipseRadiusDeg(ellipse, degree);
            
            endX = ellipse.getCenterX() + 
                    ( radius * Math.cos(Math.toRadians(degree)) );
            endY = ellipse.getCenterY() + 
                    ( radius * Math.sin(Math.toRadians(degree)) );
            radialLine = new Line(ellipse.getCenterX(), ellipse.getCenterY(),
                    endX, endY);

            lineList.add(radialLine);
            degree += degreeInterval;
        }
    }
    
    /*-----------------------------------------------------------------------*/
}
