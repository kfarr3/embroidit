
package ewu.embroidit.parkc.io.XML;

import ewu.embroidit.parkc.pattern.EmbStitch;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*-----------------------------------------------------------------------*/
/**
 *
 * Builds an XML binding that stores all necessary stitch information for writing
 * a patterns stitch list to XML.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
@XmlRootElement(name = "emb-stitch")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLStitchAdapter
{
    private double xCoord, yCoord;
    private int flag, colorIndex;
    private XMLThreadAdapter color;
    
    /*-----------------------------------------------------------------------*/
    
    public XMLStitchAdapter()
    {}
    
    /*-----------------------------------------------------------------------*/
    /**
     * Creates a new Stitch adapter with the given properties.
     * @param stitch EmbStitch - The stitch being bound to XML.
     */
    public XMLStitchAdapter(EmbStitch stitch)
    {
        this.flag = stitch.getFlag();
        this.colorIndex = stitch.getColorIndex();
        this.xCoord = stitch.getStitchPosition().getX();
        this.yCoord = stitch.getStitchPosition().getY();
        this.color = new XMLThreadAdapter(stitch.getColor().getRed(),
                                          stitch.getColor().getGreen(),
                                          stitch.getColor().getBlue());
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the stitch flag.
     * @return int
     */
    public int getFlag()
    { return this.flag; }
    
    /**
     * Returns the stitch color index.
     * @return int
     */
    public int getColorIndex()
    { return this.colorIndex; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the stitch x coordinate.
     * @return double
     */
    public double getXCoord()
    { return this.xCoord; }
    
    /**
     * Returns the stitch y coordinate.
     * @return double
     */
    public double getYCoord()
    { return this.yCoord; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the stitch thread color.
     * @return XMLThreadAdapter
     */
    public XMLThreadAdapter getThreadColor()
    { return this.color; }
    
    /*-----------------------------------------------------------------------*/
}
