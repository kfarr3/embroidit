package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.shape.*;
import ewu.embroidit.parkc.util.math.EmbMath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

/*-----------------------------------------------------------------------*/
/**
 * Base abstract class for shape filling strategies. 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public abstract class A_EmbFill
{
    /*-----------------------------------------------------------------------*/
    public static final double SCALE_VALUE = 1.0;
    /*-----------------------------------------------------------------------*/
    
    public A_EmbFill()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Defines a strategy for filling a shape with stitches. Implemented in
     * sub classes.
     * @param shapeWrapper A_EmbShapeWrapper
     */
    public abstract void fillShape(A_EmbShapeWrapper shapeWrapper);
    
    /*-----------------------------------------------------------------------*/
       
    /**
     * Takes the shapeWrappers line list and breaks it down into chains of
     * smaller stitches based on that wrappers stitch length property.
     * @param shapeWrapper A_EmbShapeWrapper
     */
    protected void subDivideFillLines(A_EmbShapeWrapper shapeWrapper)
    {
        List<Line> lineList;
        List<Line> modifiedLineList;
        double stitchLength;
        boolean isOdd;
        
        this.validateObject(shapeWrapper);
        lineList = shapeWrapper.getLineList();
        modifiedLineList = new ArrayList<>();
        stitchLength = shapeWrapper.getStitchLength();
        isOdd = true;
        
        for(Line line : lineList)
        {
            if(isOdd)
            {
                modifiedLineList.addAll(singleLineDivide(line, stitchLength));
                isOdd = false;
            }
            else
            {
                modifiedLineList.addAll(invertLineSegments(singleLineDivide(line, stitchLength)));
                isOdd = true;
            }
        }
        shapeWrapper.setLineList(modifiedLineList);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Break a line segment down into as many sub lines of the stitch length
     * as possible.
     * @param line Line
     * @param stitchLength double
     * @return List&lt;Line&gt;
     */
    protected List<Line> singleLineDivide(Line line, double stitchLength)
    {
        List<Line>dividedList;
        Line tempLine;
        Point2D startPoint, endPoint, unitVec, maxEndPoint;

        dividedList = new ArrayList<>();
        maxEndPoint = new Point2D(line.getEndX(), line.getEndY());
        tempLine = new Line(line.getStartX(), line.getStartY(),
                            line.getEndX(), line.getEndY());
        
        while(true)
        {
            startPoint = new Point2D(tempLine.getStartX(), tempLine.getStartY());
            unitVec = new Point2D((tempLine.getEndX() - tempLine.getStartX()),
                                  (tempLine.getEndY() - tempLine.getStartY()));
            unitVec = unitVec.normalize();
            unitVec = unitVec.multiply(stitchLength);
            endPoint = startPoint.add(unitVec);
            
            //if the new line segment extends beyond the old one exit the loop.
            if(EmbMath.calculateDistance(startPoint, endPoint) >= 
                    EmbMath.calculateDistance(startPoint, maxEndPoint))
                break;
            
            dividedList.add(new Line(startPoint.getX(), startPoint.getY(),
                            endPoint.getX(), endPoint.getY()));
            
            tempLine = new Line(endPoint.getX(), endPoint.getY(),
                                tempLine.getEndX(), tempLine.getEndY());
        }
            
        dividedList.add(new Line(startPoint.getX(), startPoint.getY(),
                        tempLine.getEndX(), tempLine.getEndY()));
        
        return dividedList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Helper method for inverting segment order for improved stitch digitizing.
     * Reverses the Line list order, and swaps start and end coordinates. This
     * Sets segments coordinates up for sequential breakdown, and eliminates
     * potential in fill jump stitches.
     * @param segmentList List&lt;Line&gt;
     */
    private List<Line> invertLineSegments(List<Line> segmentList)
    {
        double tempX, tempY;
        
        Collections.reverse(segmentList);
        for(Line line : segmentList)
        {
            tempX = line.getStartX();
            tempY = line.getStartY();
            line.setStartX(line.getEndX());
            line.setStartY(line.getEndY());
            line.setEndX(tempX);
            line.setEndY(tempY);
        }
        
        return segmentList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the object sent as a parameter exists.
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
        { throw new RuntimeException("A_EmbFill: Null reference error"); }
    }
    
    /*-----------------------------------------------------------------------*/
}
