package ewu.embroidit.parkc.util;

/*-----------------------------------------------------------------------*/

import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/*-----------------------------------------------------------------------*/
/**
 *
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbUtil
{
    /*-----------------------------------------------------------------------*/
    
    private EmbUtil()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    public static EmbUtil getInstance()
    { return EmbUtilHolder.INSTANCE; }
    
    /*-----------------------------------------------------------------------*/
    
    private static class EmbUtilHolder
    { private static final EmbUtil INSTANCE = new EmbUtil(); }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns a list of Colors from the shape wrapper threads in the order
     * which they appear.
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     * @return List&lt;Color&gt;
     */
    public static List<Color> getExportColorList(List<A_EmbShapeWrapper> wrapperList)
    {
        boolean isFirstPass = true;
        Color currentColor = null;
        List<Color> exportList;
        
        exportList = new ArrayList<>();
        
        for(A_EmbShapeWrapper wrapper : wrapperList)
        {
            if(isFirstPass)
            {
                currentColor = wrapper.getThreadColor();
                exportList.add(currentColor);
                isFirstPass = false;
            }
            else if(currentColor.getBlue() != wrapper.getThreadColor().getBlue()
            || currentColor.getGreen() != wrapper.getThreadColor().getGreen()
            || currentColor.getRed() != wrapper.getThreadColor().getRed())
            {
                currentColor = wrapper.getThreadColor();
                exportList.add(currentColor);
            }
        }
        
        return exportList;
    }
    
    /*-----------------------------------------------------------------------*/
}
