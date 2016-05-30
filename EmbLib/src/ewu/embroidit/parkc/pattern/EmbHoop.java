package ewu.embroidit.parkc.pattern;

/*-----------------------------------------------------------------------*/
/**
 * Represents an embroidery hoop and its respective values/characteristics.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class EmbHoop
{
    /*-----------------------------------------------------------------------*/
    
    private double width;           //Hoop Width
    private double height;          //Hook Height
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a new Embroidery Hoop whose width and height are specified
     * by the arguments of the same name.
     * @param width double
     * @param height double
     */
    public EmbHoop(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Get the width of the hoop.
     * @return double
     */
    public double getWidth()
    { return this.width; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Set the width of the hoop.
     * @param width double
     */
    public void setWidth(double width)
    { this.width = width; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Get the height of the hoop
     * @return double
     */
    public double getHeight()
    { return this.height; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Set the height of the hoop.
     * @param height double
     */
    public void setHeight(double height)
    { this.height = height; }
    
    /*-----------------------------------------------------------------------*/
    
}
