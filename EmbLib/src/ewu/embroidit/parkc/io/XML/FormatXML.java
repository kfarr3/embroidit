package ewu.embroidit.parkc.io.XML;

import ewu.embroidit.parkc.fill.EmbFillLine;
import ewu.embroidit.parkc.fill.EmbFillRadial;
import ewu.embroidit.parkc.fill.EmbFillTatamiRect;
import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.shape.EmbShapeWrapperLine;
import ewu.embroidit.parkc.shape.EmbShapeWrapperRadialFill;
import ewu.embroidit.parkc.shape.EmbShapeWrapperTatamiFill;
import java.io.File;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/*-----------------------------------------------------------------------*/
/**
 * Formats the information contained in a pattern file to and from XML.
 * @author Chris Park  (christopherpark@eagles.ewu.edu)
 */
public class FormatXML
{
    
    /*-----------------------------------------------------------------------*/
    
    private EmbPattern pattern;
    
    /*-----------------------------------------------------------------------*/
    
    private FormatXML()
    {}
    
    public static FormatXML getInstance()
    { return FormatXMLHolder.INSTANCE; }
    
    private static class FormatXMLHolder
    { private static final FormatXML INSTANCE = new FormatXML(); }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Builds the information from an XML format file into an XMLPatternAdapter,
     * and then reconstructs a pattern from the resulting information.
     * @param file File
     * @return EmbPattern
     */
    public EmbPattern loadFile(File file)
    {
        Shape tempShape;
        Color tempColor;
        List<XMLStitchAdapter> stitchAdapterList;
        List<XMLThreadAdapter> threadAdapterList;
        List<XMLShapeAdapter> shapeAdapterList; 
        A_EmbShapeWrapper tempWrapper = null;
        XMLPatternAdapter patternAdapter = new XMLPatternAdapter();
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(XMLPatternAdapter.class,
                    XMLStitchAdapter.class, XMLThreadAdapter.class, XMLShapeAdapter.class);
            Unmarshaller um = context.createUnmarshaller();
            patternAdapter = (XMLPatternAdapter) um.unmarshal(file);
        }
        catch(Exception e)
        {
            System.err.println(e);
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from xml file:\n"
                                + file.getPath());
            
            alert.showAndWait();
        }
        
        this.pattern = new EmbPattern();
        this.pattern.setColorIndex(patternAdapter.getColorIndex());
        this.pattern.setLastX(patternAdapter.getLastX());
        this.pattern.setLastY(patternAdapter.getLastY());
        this.pattern.setHomePoint(new Point2D(patternAdapter.getHomePointX(),
                                              patternAdapter.getHomePointY()));
        this.pattern.setName(patternAdapter.getName());
        
        stitchAdapterList = patternAdapter.getStitchAdapterList();
        threadAdapterList = patternAdapter.getThreadAdapterList();
        shapeAdapterList = patternAdapter.getShapeAdapterList();
        
        for(XMLStitchAdapter stitch : stitchAdapterList)
            this.pattern.addStitchAbs(stitch.getXCoord(), stitch.getYCoord(),
                                      stitch.getFlag(), stitch.getColorIndex());
        
        for(XMLThreadAdapter thread : threadAdapterList)
            this.pattern.addThreadByValue(Color.color(thread.getRed(),
                    thread.getGreen(), thread.getBlue()));
        
        for(XMLShapeAdapter shape : shapeAdapterList)
        {
            tempColor = Color.color(shape.getThreadAdapter().getRed(),
                        shape.getThreadAdapter().getGreen(),
                        shape.getThreadAdapter().getBlue());
            
            tempShape = this.buildShape(shape);
            
            if(shape.getType().equals("line"))
                tempWrapper = new EmbShapeWrapperLine(tempShape,
                        shape.getStitchLength());
            
            if(shape.getType().equals("rectangle"))
                tempWrapper = new EmbShapeWrapperTatamiFill(tempShape,
                        shape.getStitchLength());

            
            if(shape.getType().equals("ellipse"))
                tempWrapper = new EmbShapeWrapperRadialFill(tempShape,
                        shape.getStitchLength()); 
            
            tempWrapper.setName(shape.getWrapperName());
            tempWrapper.setThreadColor(tempColor);
            this.pattern.addShape(tempShape);
            this.pattern.addShapeWrapper(tempWrapper);
        }
        
        return this.pattern;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates an XML bound pattern adapter that contains pattern information
     * necessary for saving a file to XML.
     * @param pattern EmbPattern - The pattern to save
     * @param file File - The file to write the data to.
     */
    public void saveFile(EmbPattern pattern, File file)
    {
        XMLPatternAdapter patternAdapter = new XMLPatternAdapter(pattern);
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(XMLPatternAdapter.class,
                    XMLStitchAdapter.class, XMLThreadAdapter.class, XMLShapeAdapter.class);
            Marshaller m = context.createMarshaller();
            
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(patternAdapter, file);
        }
        catch(Exception e)
        {
            System.err.println(e);
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to xml file:\n"
                                + file.getPath());
            
            alert.showAndWait();
        }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Builds and returns a JavaFX Shape based on the XMLShapeAdapter type
     * @param shape XMLShapeAdapter
     * @return Shape 
     */
    private Shape buildShape(XMLShapeAdapter shape)
    {
        double radX, radY;
        Shape tempShape = new Line(0,0,0,0); //Dummy initial shape
        
        if(shape.getType().equals("line"))
            tempShape = new Line(shape.getStartX(), shape.getStartY(),
                        shape.getEndX(), shape.getEndY());
       
        if(shape.getType().equals("rectangle"))
            tempShape = new Rectangle(shape.getStartX(), shape.getStartY(),
                        shape.getWidth(), shape.getHeight());
       
        if(shape.getType().equals("ellipse"))
        {
            radX = (shape.getWidth() / 2);
            radY = (shape.getHeight() / 2);
            tempShape = new Ellipse(shape.getStartX() + radX,
            shape.getStartY() + radY, radX, radY);
        }
       
        return tempShape;
    }
    
    /*-----------------------------------------------------------------------*/
}
