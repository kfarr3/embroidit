package ewu.embroidit.parkc.util;

/*-----------------------------------------------------------------------*/

import java.util.Comparator;
import javafx.scene.shape.Line;

/*-----------------------------------------------------------------------*/
/**
 * Comparator strategy used to sort parallel JavaFX Lines
 * 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class VerticalLineSort implements Comparator<Line>
{
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Compares starting x positions between two JavaFX Lines.
     * 
     * @param lineA JavaFX Line
     * @param lineB JavaFX Line
     * @return int
     */
    @Override
    public int compare(Line lineA, Line lineB)
    {
        if (lineA.getStartX() < lineB.getStartX())
            return -1;
        if (lineA.getStartX() > lineB.getStartX())
            return 1;
        
        return 0;
    }
    /*-----------------------------------------------------------------------*/
    
}
