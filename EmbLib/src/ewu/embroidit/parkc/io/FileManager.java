package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.io.XML.FormatXML;
import ewu.embroidit.parkc.fill.A_EmbFill;
import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.math.EmbMath;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;


/*-----------------------------------------------------------------------*/
/**
 * Handles file and formatting operations: Open, Save, Import (More to come...)
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class FileManager
{
    
    /*-----------------------------------------------------------------------*/
    
    private FileManager()
    {}
    
    /**
     * Returns an instance of the file manager.
     * @return  FileManager
     */
    public static FileManager getInstance()
    {
        return FileManagerHolder.INSTANCE;
    }
    
    private static class FileManagerHolder
    { private static final FileManager INSTANCE = new FileManager(); }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the pattern stored in the given file.
     * @param file File
     * @return EmbPattern
     */
    public EmbPattern openPattern(File file)
    { return FormatXML.getInstance().loadFile(file); }
    
    /*-----------------------------------------------------------------------*/

    /**
     * Saves a pattern to the given file.
     * @param pattern EmbPattern
     * @param file File
     */
    public void savePattern(EmbPattern pattern, File file)
    { FormatXML.getInstance().saveFile(pattern, file); }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns a pattern constructed from the given PES file.
     * @param file File
     * @return EmbPattern
     */
    public EmbPattern pesToPattern(File file)
    {
        FormatPES pesFormatter;
        
        this.validateObject(file);
        pesFormatter = new FormatPES(file);
        return pesFormatter.getPattern();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Exports a pattern to PES file. Shapes are pre-sorted by color to reduce
     * unnecessary thread changes. (thrashing)
     * @param file File
     * @param pattern EmbPattern
     */
    public void patternToPes(File file, EmbPattern pattern)
    {
        List<A_EmbShapeWrapper> wrapperList;
        List<A_EmbShapeWrapper> sortedWrapperList;
        FormatPES pesFormatter;
        
        wrapperList = this.getWrapperList(pattern);
        sortedWrapperList = this.sortWrappersByColor(wrapperList);
        this.assignStitchCodes(sortedWrapperList);
        pesFormatter = new FormatPES(pattern, file, sortedWrapperList);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Iterates through all shape stitch lists, and assigns stitch code flag 
     * values for use in encoding. First it makes a complete pass and sets all
     * stitch flags to normal. Then it iterates over the lists again looking for
     * specific jump/stop/end code instances, and inserts the necessary duplicate
     * stitches (encodings) where needed.
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     */
    public void assignStitchCodes(List<A_EmbShapeWrapper> wrapperList)
    {
        A_EmbShapeWrapper prevWrapper;
        List<EmbStitch> tempStitchList;
        EmbStitch prevStitch, startStitch, duplicateStitch;
        Point2D tempPoint;
        boolean isFirstShape = true;
        double dist;
        
        for(A_EmbShapeWrapper wrapper : wrapperList)
        {
            tempStitchList = wrapper.getStitchList();
            for(EmbStitch stitch : tempStitchList)
                stitch.setFlag(StitchCode.NORMAL);
        }
        
        System.err.println("Wrapper list size: " + wrapperList.size() );
        prevWrapper = wrapperList.get(0);
        prevStitch = new EmbStitch(new Point2D(0, 0));
        for(A_EmbShapeWrapper wrapper : wrapperList)
        {
            if(!isFirstShape)
            {   
                startStitch = wrapper.getStitchList().get(0);
                dist = EmbMath.calculateDistance(prevStitch.getStitchPosition(),
                        startStitch.getStitchPosition());
                tempPoint = new Point2D(startStitch.getStitchPosition().getX(),
                    startStitch.getStitchPosition().getX());
                duplicateStitch = new EmbStitch(tempPoint);
                this.encodeJumpStop(wrapper, prevWrapper, duplicateStitch, dist);
            }
            
            prevWrapper = wrapper;
            tempStitchList = prevWrapper.getStitchList();
            prevStitch = tempStitchList.get(tempStitchList.size() - 1);
            
            if(isFirstShape)
                isFirstShape = false;
        }
        
        tempStitchList = wrapperList.get(wrapperList.size() - 1).getStitchList();
        tempPoint = new Point2D(
                tempStitchList.get(tempStitchList.size() - 1).getStitchPosition().getX(),
                tempStitchList.get(tempStitchList.size() - 1).getStitchPosition().getY());
        duplicateStitch = new EmbStitch(tempPoint);
        duplicateStitch.setFlag(StitchCode.END);
        tempStitchList.add(duplicateStitch);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Helper method. Given two wrappers, the distance between their ordered
     * stitches, and a copy of the arriving stitch between them, the method 
     * checks for encoding conditions and applies the appropriate encoding as 
     * necessary.
     * @param curWrapper A_EmbShapeWrapper
     * @param prevWrapper A_EmbShapeWrapper
     * @param duplicateStitch EmbStitch
     * @param dist double
     */
    private void encodeJumpStop(A_EmbShapeWrapper curWrapper, A_EmbShapeWrapper prevWrapper,
            EmbStitch duplicateStitch, double dist)
    {
        if(curWrapper.getThreadColor().equals(prevWrapper.getThreadColor())
                && dist >= 12.01 * A_EmbFill.SCALE_VALUE)
                {                    
                    duplicateStitch.setFlag(StitchCode.JUMP);   
                    curWrapper.getStitchList().add(0, duplicateStitch);
                }
                else if(!curWrapper.getThreadColor().equals(prevWrapper.getThreadColor()))
                {   
                    duplicateStitch.setFlag(StitchCode.STOP);   
                    curWrapper.getStitchList().add(0, duplicateStitch);
                }
    }
    
    /*-----------------------------------------------------------------------*/
    /**
     * Returns the shape wrapper list for the given pattern.
     * @param pattern EmbPattern
     * @return List&lt;EmbPattern&gt;
     */
    public List<A_EmbShapeWrapper> getWrapperList(EmbPattern pattern)
    {
        List<Shape> shapeList;
        List<A_EmbShapeWrapper> wrapperList;
        
        shapeList = pattern.getShapeList();
        wrapperList = new ArrayList<>();
        
        for(Shape shape: shapeList)
            wrapperList.add(pattern.getShapeWrapper(shape));
        
        return wrapperList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns a color sorted (with color group ordering arbitrary) version
     * of the given list.
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     * @return List&lt;A_EmbShapeWrapper&gt;
     */
    public List<A_EmbShapeWrapper> sortWrappersByColor(List<A_EmbShapeWrapper> wrapperList)
    {
        A_EmbShapeWrapper coloredShape;
        List<A_EmbShapeWrapper> sortedWrapperList;
        List<A_EmbShapeWrapper> colorChunk;
        
        sortedWrapperList = new ArrayList<>();
        
        while(!wrapperList.isEmpty())
        {
            coloredShape = wrapperList.get(0);
            colorChunk = new ArrayList<>();
            
            for(A_EmbShapeWrapper wrapper : wrapperList)
                if(coloredShape.getThreadColor().equals(wrapper.getThreadColor()))
                    colorChunk.add(wrapper);
            
            for(A_EmbShapeWrapper wrapper : colorChunk)
                wrapperList.remove(wrapper);
            
            sortedWrapperList.addAll(colorChunk);
        }
        
        return sortedWrapperList;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the object sent as a parameter exists.
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("PECDecoder: Null reference error");
    }
    
    /*-----------------------------------------------------------------------*/
}
