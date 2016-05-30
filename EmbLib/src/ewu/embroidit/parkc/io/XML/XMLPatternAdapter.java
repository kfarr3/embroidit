package ewu.embroidit.parkc.io.XML;

import ewu.embroidit.parkc.io.FileManager;
import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.pattern.EmbThread;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/*-----------------------------------------------------------------------*/
/**
* Builds an XML binding that stores all necessary pattern information for writing
* to XML.
* @author Chris Park (christopherpark@eagles.ewu.edu)
*/
@XmlRootElement(name = "emb-pattern")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLPatternAdapter
{
    /*-----------------------------------------------------------------------*/
    
    private int colorIndex;
    private double lastX, lastY;
    private double homePointX, homePointY;
    private String name;
    
    @XmlElementWrapper(name = "stitch-list")
    @XmlElement(name = "stitch")
    private List<XMLStitchAdapter> stitchAdapterList;
    @XmlElementWrapper(name = "thread-list")
    @XmlElement(name = "thread")
    private List<XMLThreadAdapter> threadAdapterList;
    @XmlElementWrapper(name = "shape-list")
    @XmlElement(name = "shape")
    private List<XMLShapeAdapter> shapeAdapterList;
    
    /*-----------------------------------------------------------------------*/
    
    public XMLPatternAdapter()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates a new pattern adapter with the given properties.
     * @param pattern EmbPattern - The pattern being bound to XML.
     */
    public XMLPatternAdapter(EmbPattern pattern)
    {
        this.colorIndex = pattern.getColorIndex();
        this.lastX = pattern.getLastX();
        this.lastY = pattern.getLastY();
        this.homePointX = pattern.getHomePoint().getX();
        this.homePointY = pattern.getHomePoint().getY();
        this.name = pattern.getName();
        this.stitchAdapterList = this.adaptStitchList(pattern.getStitchList());
        this.threadAdapterList = this.adaptThreadList(pattern.getThreadList());
        this.shapeAdapterList = 
                this.adaptShapeList(FileManager.getInstance().getWrapperList(pattern));
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Maps the stitch list data to XMLStitchAdapter objects.
     * @param stitchList List&lt;EmbStitch&gt;
     * @return List&lt;XMLStitchAdapter&gt;
     */
    private List<XMLStitchAdapter> adaptStitchList(List<EmbStitch> stitchList)
    {
        List<XMLStitchAdapter> adapterList = new ArrayList<>();
        
        for(EmbStitch stitch : stitchList)
            adapterList.add(new XMLStitchAdapter(stitch));
        
        return adapterList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Maps the thread list data to XMLThreadAdapter objects.
     * @param threadList List&lt;EmbThread&gt;
     * @return List&lt;XMLThreadAdapter&gt;
     */
    private List<XMLThreadAdapter> adaptThreadList(List<EmbThread> threadList)
    {
        List<XMLThreadAdapter> adapterList = new ArrayList<>();
        
        for(EmbThread thread : threadList)
            adapterList.add(new XMLThreadAdapter(thread.getThreadColor().getRed(),
                                                 thread.getThreadColor().getGreen(),
                                                 thread.getThreadColor().getBlue()));
        
        return adapterList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Maps the shape and shape wrapper data to XMLShapeAdapter objects.
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     * @return List&lt;XMLShapeAdapter&gt;
     */
    private List<XMLShapeAdapter> adaptShapeList(List<A_EmbShapeWrapper> wrapperList)
    {
        List<XMLShapeAdapter> adapterList = new ArrayList<>();
        
        for(A_EmbShapeWrapper wrapper : wrapperList)
            adapterList.add(new XMLShapeAdapter(wrapper.getDimensions(), wrapper));
        
        return adapterList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the name of the pattern.
     * @return String
     */
    public String getName()
    { return this.name; }
    
    /**
     * Returns the color index of the pattern.
     * @return int
     */
    public int getColorIndex()
    { return this.colorIndex; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the last x stitch index of this pattern.
     * @return double
     */
    public double getLastX()
    { return this.lastX; }
    
    /**
     * Returns the last y stitch index of this pattern.
     * @return double
     */
    public double getLastY()
    { return this.lastY; }
    
    /**
     * Returns the x home point stitch for this pattern.
     * @return 
     */
    public double getHomePointX()
    { return this.homePointX; }
    
    /**
     * Returns the y home point stitch for this pattern.
     * @return 
     */
    public double getHomePointY()
    { return this.homePointY; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the stitch adapter list for this pattern.
     * @return List&lt;XMLStitchAdapter&gt;
     */
    public List<XMLStitchAdapter> getStitchAdapterList()
    { return this.stitchAdapterList; }
    
    /**
     * Returns the thread adapter list for this pattern.
     * @return List&lt;XMLThreadAdapter&gt;
     */
    public List<XMLThreadAdapter> getThreadAdapterList()
    { return this.threadAdapterList; }
    
    /**
     * Returns the shape adapter list for this pattern.
     * @return List&lt;XMLShapeAdapter&gt;
     */
    public List<XMLShapeAdapter> getShapeAdapterList()
    { return this.shapeAdapterList; }
    
    /*-----------------------------------------------------------------------*/
    
}
