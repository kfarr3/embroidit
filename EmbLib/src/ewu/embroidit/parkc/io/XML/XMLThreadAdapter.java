package ewu.embroidit.parkc.io.XML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*-----------------------------------------------------------------------*/
/**
 * Builds an XML binding that stores all necessary color information for writing
 * a patterns thread list to XML.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
@XmlRootElement(name = "emb-thread")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLThreadAdapter
{
    /*-----------------------------------------------------------------------*/
    
    private double red, green, blue;
    
    /*-----------------------------------------------------------------------*/
    
    public XMLThreadAdapter()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates a new thread adapter with the given properties.
     * @param r double - Red rgb value.
     * @param g double - Green rgb value.
     * @param b double - Blue rgb value.
     */
    public XMLThreadAdapter(double r, double g, double b)
    {
        this.red = r;
        this.green = g;
        this.blue = b;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the threads red color value.
     * @return double
     */
    public double getRed()
    { return this.red; }
    
    /**
     * Returns the threads green color value.
     * @return double
     */
    public double getGreen()
    { return this.green; }
    
    /**
     * Returns the threads blue color value.
     * @return double
     */
    public double getBlue()
    { return this.blue; }
    
    /*-----------------------------------------------------------------------*/
}
