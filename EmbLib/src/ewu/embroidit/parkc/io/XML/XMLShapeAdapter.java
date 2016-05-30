package ewu.embroidit.parkc.io.XML;

import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.shape.EmbShapeDimension;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*-----------------------------------------------------------------------*/
/**
 *  Builds an XML binding that stores all necessary shape information for writing
 *  a patterns shape to XML.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
@XmlRootElement(name = "emb-shape")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLShapeAdapter
{
    private String type, wrapperName;
    private double startX, startY, endX, endY, width, height, stitchLength;
    private XMLThreadAdapter color;
    
    /*-----------------------------------------------------------------------*/
    
    public XMLShapeAdapter()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates a new shape adapter with the given properties.
     * @param dims EmbShapeDimension - The shape dimensions.
     * @param wrapper A_EmbShapeWrapper - The shape wrapper.
     */
    public XMLShapeAdapter(EmbShapeDimension dims, A_EmbShapeWrapper wrapper)
    {
        this.type = dims.type();
        this.startX = dims.getStartCoord().getX();
        this.startY = dims.getStartCoord().getY();
        this.endX = dims.getEndCoord().getX();
        this.endY = dims.getEndCoord().getY();
        this.width = dims.getWidth();
        this.height = dims.getHeight();
        this.wrapperName = wrapper.getName();
        this.stitchLength = wrapper.getStitchLength();
        this.color = new XMLThreadAdapter(wrapper.getThreadColor().getRed(),
                                          wrapper.getThreadColor().getGreen(),
                                          wrapper.getThreadColor().getBlue());
    }
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape type.
     * @return String - The name of the shape type
     */
    public String getType()
    { return this.type; }
    
    /**
     * Returns the shape wrapper name
     * @return String
     */
    public String getWrapperName()
    { return this.wrapperName; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape starting x coordinate.
     * @return double
     */
    public double getStartX()
    { return this.startX; }
    
    /**
     * Returns the shape starting y coordinate.
     * @return double
     */
    public double getStartY()
    { return this.startY; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape ending x coordinate
     * @return double
     */
    public double getEndX()
    { return this.endX; }
    
    /**
     * Returns the shape ending y coordinate
     * @return double
     */
    public double getEndY()
    { return this.endY; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape width.
     * @return double
     */
    public double getWidth()
    { return this.width; }
    
    /**
     * Returns the shape height.
     * @return double
     */
    public double getHeight()
    { return this.height; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape wrapper stitch length.
     * @return double
     */
    public double getStitchLength()
    { return this.stitchLength; }
    
    /**
     * Returns the shape wrapper thread color.
     * @return XMLThreadAdapter
     */
    public XMLThreadAdapter getThreadAdapter()
    { return this.color; }
    
    /*-----------------------------------------------------------------------*/
}
