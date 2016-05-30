package ewu.embroidit.parkc.fill;

import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Line;

/*-----------------------------------------------------------------------*/
/**
 * Fill strategy for line segments.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbFillLine extends A_EmbFill
{
 
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a Line segment stitch fill strategy.
     */
    public EmbFillLine()
    {
        super();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Implements a shape filling strategy for line segments.
     * @param shapeWrapper A_EmbShapeWrapper
     */
    @Override
    public void fillShape(A_EmbShapeWrapper shapeWrapper)
    {
        List<Line> lineList;
        
        lineList = new ArrayList<>();
        lineList.add((Line) shapeWrapper.getWrappedShape());
        shapeWrapper.setLineList(lineList);
        this.subDivideFillLines(shapeWrapper);
        shapeWrapper.toStitchList();
    }
    
    /*-----------------------------------------------------------------------*/
    
}
