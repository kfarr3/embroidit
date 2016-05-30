package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.pattern.EmbPattern;
import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.math.EmbMath;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/*-----------------------------------------------------------------------*/
/**
 * This Class opens a .PES file and interprets the stitch data extracted
 * by the PECDecoder class. This data is used to create the stitches for
 * an embroidery pattern.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class FormatPES
{
    /*-----------------------------------------------------------------------*/
    
    private final long PEC_OFFSET = 8;  //Location in the PES file that contains
                                        //the starting address of the PEC code block.
    
    private int pecStart;               //Starting location of PEC code block.
    private int threadCount;            //Number of threads used in this PES file.
    private RandomAccessFile fileStream;//The input stream.
    private EmbPattern pattern;         //Pattern used to hold PES data.
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Basic constructor that does not initialize any importing functionality.
     * Use for exporting functionality
     */
    public FormatPES()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Constructs a pattern from the imported file.
     * @param file File
     */
    public FormatPES(File file)
    {
        this.validateObject(file);
        this.openFile(file, "r");
        this.getPECStart();
        this.pattern = new EmbPattern();
        
        this.createThreads();
        this.readPEC();
        this.closeFile();
        this.validateLastStitch();
        
        EmbMath.scaleStitches(this.pattern.getStitchList(), 4.0);
        EmbMath.offsetStitchList(this.pattern.getStitchList());
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes a PES output file with the given pattern information.
     * @param pattern EmbPattern
     * @param file File
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     */
    public FormatPES(EmbPattern pattern, File file, List<A_EmbShapeWrapper> wrapperList)
    {
        
        this.validateObject(pattern);
        this.pattern = pattern;
        this.validateObject(file);
        this.validateObject(wrapperList);
        this.writePES(wrapperList, file);
    }
    
    /**
     * Creates a new RandomAccessFile with the given file pointer.
     * @param file File
     */
    private void openFile(File file, String mode)
    {   
        try
        { this.fileStream = new RandomAccessFile(file, mode); }
        catch(FileNotFoundException e)
        { System.err.println("FormatPES openFile: " + e); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Closes the file being read by this object.
     */
    private void closeFile()
    {   
        try
        { this.fileStream.close(); }
        catch(IOException e)
        { System.err.println("FormatPES closeFile: " + e); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Gets the starting position of the PEC code block inside the open file
     */
    private void getPECStart()
    {   
        try
        {
            this.fileStream.seek(PEC_OFFSET);
            this.pecStart = this.fileStream.readUnsignedByte();
            this.pecStart = this.pecStart | this.fileStream.readUnsignedByte() << 8;
            this.pecStart = this.pecStart | this.fileStream.readUnsignedByte() << 16;
            this.pecStart = this.pecStart | this.fileStream.readUnsignedByte() << 24;
        }
        catch(IOException e)
        { System.err.println("FormatPES: getPECStart: " + e); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Creates a thread object and adds it to the thread list of the pattern.
     */
    private void createThreads()
    {
        try
        {
            this.fileStream.seek(this.pecStart + 48);
            this.threadCount = this.fileStream.readUnsignedByte()+ 1;
            
            for(int i = 0; i < this.threadCount; i++) 
                this.pattern.addThread(this.fileStream.readUnsignedByte());
        }
        catch(IOException e)
        { System.err.println("FormatPES: createThreads:" + e); }
    }
    /*-----------------------------------------------------------------------*/
    
    /**
     * Uses PECDecoder to read stitch information into the EmbPattern held by 
     * this class.
     */
    private void readPEC()
    {
        try
        {
            this.fileStream.seek(this.pecStart + 532);
            PECDecoder.getInstance().readStitches(this.pattern, this.fileStream);
        }
        catch(IOException e)
        { System.err.println("FormatPES: Error in readPEC():" + e); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes pattern stitch information to a .PES file using a list of
     * pre-sorted and encoded shape wrappers.
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     * @param file File
     */
    private void writePES(List<A_EmbShapeWrapper> wrapperList, File file)
    {
        List<EmbStitch> masterStitchList;
        int pecLocation;
        
        masterStitchList = new ArrayList<>();
        this.validateObject(file);
        this.validateObject(wrapperList);
        
        //Transfer wrapper color to all stitches.
        for(A_EmbShapeWrapper wrapper : wrapperList)
            wrapper.colorStitchList();

        for(A_EmbShapeWrapper wrapper : wrapperList)
            masterStitchList.addAll(wrapper.getStitchList());
        
        try
        {
            this.openFile(file, "rw");   
            
            EmbMath.flipStitchList("vertical", masterStitchList);
            EmbMath.scaleStitches(masterStitchList, 10.0);
                                                      
            System.err.println("DEBUG: The file Opened!");
            
            this.fileStream.writeBytes("#PES0001");

            //Write PECPointer
            this.fileStream.writeInt(0x00);

            this.fileStream.writeShort(0x01);
            this.fileStream.writeShort(0x01);

            //Write Object Count
            this.fileStream.writeShort(0x01);
            this.fileStream.writeShort(0xFFFF);
            this.fileStream.writeShort(0x00);
            
            this.writeEmbOneSection(masterStitchList); 
            this.writeSegmentSection(masterStitchList);
            
            System.err.println("DEBUG: After writeSections in PES!");
            
            pecLocation = (int) this.fileStream.getFilePointer();
            
            this.fileStream.seek(0x08);
            this.fileStream.writeChar(pecLocation & 0x0FF);
            this.fileStream.writeChar((pecLocation >>> 8) & 0xFF);
            this.fileStream.writeChar((pecLocation >>> 16) & 0xFF);
            this.fileStream.seek(this.fileStream.length());
            
            PECEncoder.getInstance().writeStitches(fileStream,
                    file.getName(), wrapperList, masterStitchList);
               
            this.closeFile();
            System.err.println("DEBUG: The file closed!");
        }
        catch(Exception e)
        { System.err.println("Error: FormatPES - writePES"); }
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes stitch block segment information to the output file. Information
     * includes number of thread colors, number of stitch blocks and stitches per
     * color.
     * @param masterStitchList List&lt;EmbStitch&gt;
     * @throws IOException
     */
    private void writeSegmentSection(List<EmbStitch> masterStitchList) throws IOException
    {
        Point2D tempCoords;
        BoundingBox bounds;
        Color stitchColor;
        
        int stitchCount, blockCount, colorCount;
        int flag, stitchType, offset; 
        int currentColorCode, approximateColorCode, colorInfoIndex;
        short[] colorInfo;
        
        currentColorCode = -1;
        stitchCount = blockCount = colorCount = offset = colorInfoIndex = 0;
        bounds = EmbMath.calcBoundingRect(masterStitchList);
        
        for(int i = 0; i < masterStitchList.size(); i++)
        {
            flag = masterStitchList.get(i).getFlag();
            stitchColor = masterStitchList.get(i).getColor();
            approximateColorCode = EmbMath.approximateColorIndex(stitchColor);
            
            if(currentColorCode != approximateColorCode)
            {
                colorCount++;
                currentColorCode = approximateColorCode;
            }
             
            while(i < masterStitchList.size() &&
                  flag == masterStitchList.get(i).getFlag())
            {
                stitchCount++;
                i++;
            }
            
            blockCount++;
        }
        
        fileStream.writeShort(blockCount);
        fileStream.writeShort(0xFFFF);          //Unsigned?
        fileStream.writeShort(0x00);
        fileStream.writeShort(0x07);            //Strlen
        fileStream.writeBytes("CSewSeg");
        
        colorInfo = new short[colorCount * 2];
        blockCount = 0;
        currentColorCode = -1;
        for(int i = 0; i < masterStitchList.size(); i++)
        {
            i = offset;
            flag = masterStitchList.get(i).getFlag();
            stitchColor = masterStitchList.get(i).getColor();
            approximateColorCode = EmbMath.approximateColorIndex(stitchColor);
                
            if(currentColorCode != approximateColorCode)
            {
                colorInfo[colorInfoIndex++] = (short) blockCount;
                colorInfo[colorInfoIndex++] = (short) approximateColorCode;
                currentColorCode = approximateColorCode;
            }
            
            stitchCount = 0;
            while(i < masterStitchList.size() &&
                  flag == masterStitchList.get(i).getFlag())
            {
                stitchCount++;
                i++;
            }
            
            if((flag & StitchCode.JUMP) != 0)   
                stitchType = 1;
            else
                stitchType = 0;
            
            this.fileStream.writeShort(stitchType);         
            this.fileStream.writeShort(currentColorCode);
            this.fileStream.writeShort(stitchCount);        //Stitches in Block
            
            i = offset;
            while(i < masterStitchList.size() &&
                  flag == masterStitchList.get(i).getFlag())
            {
                tempCoords = masterStitchList.get(i).getStitchPosition();
                this.fileStream.writeShort((short) (tempCoords.getX() - bounds.getMinX()) );
                this.fileStream.writeShort((short) (tempCoords.getY() - bounds.getMinY()) );
                i++;
            }
            
            if(masterStitchList.get(i) != null)
                this.fileStream.writeShort(0x8003);
            
            blockCount++;
            offset = i;
        }
        
        this.fileStream.writeShort(colorCount);
        
        for(int i = 0; i < colorCount; i++)
        {
            fileStream.writeShort(colorInfo[i * 2]);
            fileStream.writeShort(colorInfo[i * 2 + 1]);
        }
            
        fileStream.writeInt(0);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes the first section of the PES format file to output. This section
     * includes the hoop dimension pattern offset, and an affine transform for
     * scaling.
     * @param wrapperList List&lt;A_EmbShapeWrapper&gt;
     * @throws IOException 
     */
    private void writeEmbOneSection(List<EmbStitch> wrapperList) throws IOException
    {
        int i, hoopWidth, hoopHeight;
        BoundingBox bounds;
        
        hoopHeight = 1800;
        hoopWidth = 1300;
        bounds = EmbMath.calcBoundingRect(wrapperList);
        
        this.fileStream.writeShort(0x07);
        this.fileStream.writeBytes("CEmbOne");
        this.fileStream.writeShort(0);
        this.fileStream.writeShort(0);
        this.fileStream.writeShort(0);
        this.fileStream.writeShort(0);
        this.fileStream.writeShort(0);
        this.fileStream.writeShort(0);
        this.fileStream.writeShort(0);
        this.fileStream.writeShort(0);
        
        //Affine Transform
        this.fileStream.writeFloat(1.0f);
        this.fileStream.writeFloat(0.0f);
        this.fileStream.writeFloat(0.0f);
        this.fileStream.writeFloat(1.0f);
       
        this.fileStream.writeFloat( (float) (bounds.getWidth() - hoopWidth) / 2);
        this.fileStream.writeFloat( (float) (bounds.getHeight() - hoopHeight) / 2);
        
        this.fileStream.writeShort(1);
        this.fileStream.writeShort(0); //Translate X
        this.fileStream.writeShort(0); //Translate Y
        
        this.fileStream.writeShort( (short) bounds.getWidth());
        this.fileStream.writeShort( (short) bounds.getHeight());
        
        for(i = 0; i < 8; i++)
            this.fileStream.writeByte(0);
    }
    
    /*-----------------------------------------------------------------------*/
    /**
     * Returns the pattern containing the thread and stitch lists created from 
     * the imported PES file.
     * @return EmbPattern
     */
    public EmbPattern getPattern()
    {
        this.validateObject(this.pattern);
        return this.pattern;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the object sent as a parameter exists.
     * @param obj Object
     */
    private void validateObject(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("FormatPES: Null reference error");
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Ensures that the last stitch in the pattern is labeled as an END stitch.
     */
    private void validateLastStitch()
    {
        int listSize = this.pattern.getStitchList().size();
        
        EmbStitch lastStitch = this.pattern.getStitchList().get(listSize - 1);
        
        if(lastStitch.getFlag() != StitchCode.END)
            this.pattern.addStitchRel(0.0, 0.0, StitchCode.END, 1);
    }
    
    /*-----------------------------------------------------------------------*/
    
}

