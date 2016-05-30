package ewu.embroidit.parkc.pattern;

import javafx.scene.paint.Color;

/*-----------------------------------------------------------------------*/
/**
 * Represents a colored embroidery thread used in an embroidery design.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbThread
{
    /*-----------------------------------------------------------------------*/
    
    private Color threadColor;              //Thread Color
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs an embroidery thread with the given color.
     * @param threadColor javafx.scene.paint.Color 
     */
    public EmbThread(Color threadColor)
    {
        this.validateObject(threadColor);
        this.threadColor = threadColor;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets the embroidery thread color
     * @return javafx.scene.paint.Color
     */
    public Color getThreadColor()
    { return this.threadColor; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Sets the embroidery thread to the given color.
     * @param threadColor javafx.scene.paint.Color
     */
    public void setThreadColor(Color threadColor)
    {
        this.validateObject(threadColor);
        this.threadColor = threadColor;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures the given object exists.
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
        { throw new RuntimeException("EmbThread: Null reference error"); }
    }
    
    /*-----------------------------------------------------------------------*/
    
}
