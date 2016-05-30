package ewu.embroidit.parkc.io;

import ewu.embroidit.parkc.pattern.EmbStitch;
import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;
import ewu.embroidit.parkc.util.EmbUtil;
import ewu.embroidit.parkc.util.math.EmbMath;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * Singleton class used to write stitch information to the PEC section
 * of a .PES file.
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class PECEncoder
{
    /*-----------------------------------------------------------------------*/
    
    private final byte[][] EMPTY_BORDER_IMAGE = {
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
    {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
    {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
    {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };
            
    /*-----------------------------------------------------------------------*/
    
    private PECEncoder()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    public static PECEncoder getInstance()
    { return PECEncoderHolder.INSTANCE; }
    
    /*-----------------------------------------------------------------------*/
    
    private static class PECEncoderHolder
    { private static final PECEncoder INSTANCE = new PECEncoder(); }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes all stitch coordinate, type, and image preview information to the
     * given output file.
     * @param fileStream RandomAccessFile
     * @param fileName String
     * @param wrapperListList List&lt;A_EmbShapeWrapper&gt;
     * @param stitchList List&lt;EmbStitch&gt;
     * @throws IOException 
     */
    public void writeStitches(RandomAccessFile fileStream, String fileName,
            List<A_EmbShapeWrapper> wrapperListList, 
            List<EmbStitch> stitchList) throws IOException
    {
        long graphicsOffsetLocation, graphicsOffsetValue;
        int trimPoint, padSize, numColors, colorIndex;
        int height, width,i, j, x, y;
        double xFactor, yFactor;
        byte[][] imageArray;
        List<Color> colorList;
        BoundingBox bounds;
        Point2D tempCoord;
        
        bounds = EmbMath.calcBoundingRect(stitchList);
        
        //Trim off file extension
        trimPoint = fileName.lastIndexOf(".");
        if(trimPoint > 0)
            fileName = fileName.substring(0, trimPoint);
        
    
        fileStream.writeBytes("LA:");
        fileStream.writeBytes(fileName);
        
        padSize = 16 - fileName.length();
        if(padSize > 0)
            this.padBytes(fileStream, 0x20, padSize);
            
        fileStream.writeByte(0x0D);
        this.padBytes(fileStream, 0x20, 12);
        fileStream.writeByte(0xFF);
        fileStream.writeByte(0x00);
        fileStream.writeByte(0x06);
        fileStream.writeByte(0x26);
        this.padBytes(fileStream, 0x20, 12);
        
        numColors = EmbMath.colorCount(wrapperListList);
        fileStream.writeByte(numColors - 1);
        colorList = EmbUtil.getExportColorList(wrapperListList);
        
        for(i = 0; i < numColors; i++)
        {
            colorIndex = EmbMath.approximateColorIndex(colorList.get(i));
            fileStream.writeByte(colorIndex);
        }
        
        this.padBytes(fileStream, 0x20, (0x1CF - numColors));
        fileStream.writeShort(0x0000);
        
        graphicsOffsetLocation = fileStream.getFilePointer();
        fileStream.writeByte(0x00);
        fileStream.writeByte(0x00);
        fileStream.writeByte(0x00);
        fileStream.write(0x31);
        fileStream.write(0xFF);
        fileStream.write(0xF0);
        
        height = EmbMath.roundDouble(bounds.getHeight());
        width = EmbMath.roundDouble(bounds.getWidth());
        fileStream.writeShort(width);
        fileStream.writeShort(height);
        fileStream.writeShort(0x1E0);
        fileStream.writeShort(0x1B0);
        fileStream.writeShort(0x9000 | -EmbMath.roundDouble(bounds.getMinX()));
        fileStream.writeShort(0x9000 | -EmbMath.roundDouble(bounds.getMinY()));
        this.encode(fileStream, stitchList); //Encode Stitch Coordinates
        
        graphicsOffsetValue = 
                fileStream.getFilePointer() - graphicsOffsetLocation + 2;
        fileStream.seek(graphicsOffsetLocation);
        
        fileStream.writeByte((int)graphicsOffsetValue & 0xFF);
        fileStream.writeByte((int)((graphicsOffsetValue >>> 8) & 0xFF));
        fileStream.writeByte((int)((graphicsOffsetValue >>> 16) & 0xFF));
        
        fileStream.seek(fileStream.length());
        
        //Writing all colors
        imageArray = this.EMPTY_BORDER_IMAGE.clone();
        yFactor = 32.0/height;
        xFactor = 42.0/ width;
        
        /*
        try
        {
            for(EmbStitch stitch : stitchList)
            {
                tempCoord = stitch.getStitchPosition();
                x = EmbMath.roundDouble( (tempCoord.getX() - bounds.getMinX()) * xFactor) + 3;
                y = EmbMath.roundDouble( (tempCoord.getY() - bounds.getMinY()) * yFactor) + 3;
                imageArray[y][x] = 1;

                System.err.println("DEBUG - PECENCODER: Wrote a coordinate to the array");
            }
            this.writeImage(fileStream, imageArray);
        
        }
        catch(Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }
        
        //Writing each individual color
        j = 0;
        for(i = 0; i < numColors; i++)
        {
            imageArray = this.EMPTY_BORDER_IMAGE.clone();
            while(j < stitchList.size())
            {
                tempCoord = stitchList.get(j).getStitchPosition();
                x = EmbMath.roundDouble(((tempCoord.getX() - bounds.getMinX()) * xFactor)) + 3;
                y = EmbMath.roundDouble(((tempCoord.getY() - bounds.getMinY()) * yFactor)) + 3;
                
                if((stitchList.get(j).getFlag() & StitchCode.STOP) != 0)
                {
                    j++;
                    break;
                }
                
                imageArray[y][x] = 1;
                j++;
            }
            this.writeImage(fileStream, imageArray);
        }
        */
        
        this.writeImage(fileStream, imageArray);
        this.writeImage(fileStream, imageArray);
      
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Encodes stitch coordinates to the output file, modified by stitch code
     * if necessary.
     * @param fileStream RandomAccessFile
     * @param stitchList List&lt;EmbStitch&gt;
     * @throws IOException 
     */
    private void encode(RandomAccessFile fileStream,
            List<EmbStitch> stitchList) throws IOException
    {
        int deltaX, deltaY;
        double currentX, currentY;
        byte stopCode = 2;
        Point2D tempCoords;
        
        currentX = currentY = 0.0;
        
        for(EmbStitch stitch : stitchList)
        {
            tempCoords = stitch.getStitchPosition();
            deltaX = EmbMath.roundDouble(tempCoords.getX() - currentX);
            deltaY = EmbMath.roundDouble(tempCoords.getY() - currentY);
            
            currentX += (double) deltaX;
            currentY += (double) deltaY;
            
            if((stitch.getFlag() & StitchCode.STOP) != 0)
            {
                this.encodeStop(fileStream, stopCode );
                
                if(stopCode == 2)
                    stopCode = 1;
                else
                    stopCode = 2;
            }
            else if((stitch.getFlag() & StitchCode.END) != 0)
            {
                fileStream.writeByte(0xFF);
                break;
            }
            else if(deltaX < 63 && deltaX > -64
                 && deltaY < 63 && deltaY > -64
                 && !((stitch.getFlag() 
                  &  (StitchCode.JUMP | StitchCode.TRIM) ) != 0))
            {
                fileStream.writeByte((deltaX < 0) ? deltaX + 0x80 : deltaX);
                fileStream.writeByte((deltaY < 0) ? deltaY + 0x80 : deltaY);
            }
            else
            {
                this.encodeJump(fileStream, deltaX, stitch.getFlag());
                this.encodeJump(fileStream, deltaY, stitch.getFlag());
            }
        }    
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Encodes a STOP stitch to the output file.
     * @param fileStream
     * @param stopCode
     * @throws IOException 
     */
    private void encodeStop(RandomAccessFile fileStream, 
            byte stopCode) throws IOException
    {
        fileStream.writeByte(0xFE);
        fileStream.writeByte(0xB0);
        fileStream.writeByte(stopCode);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Encodes a JUMP stitch to the output file.
     * @param fileStream RandomAccessFile
     * @param delta int
     * @param flag int
     * @throws IOException 
     */
    private void encodeJump(RandomAccessFile fileStream,
            int delta, int flag) throws IOException
    {
        int outputVal = Math.abs(delta) & 0x7FF;
        int orVal = 0x80;
        
        if((flag & StitchCode.TRIM) != 0)
            orVal |= 0x20;
        else if((flag & StitchCode.JUMP) != 0)
            orVal |= 0x10;
        
        if(delta < 0)
        {
            outputVal = delta + 0x1000 & 0x7FF;
            outputVal |= 0x800;
        }
        
        fileStream.writeByte( ((outputVal >>> 8) & 0x0F) | orVal);
        fileStream.writeByte(outputVal & 0xFF);
        
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes the value to the file stream the given number of times.
     * @param fileStream RandomAccessFile
     * @param value int
     * @param amount int
     * @throws IOException 
     */
    private void padBytes(RandomAccessFile fileStream, int value, 
            int amount) throws IOException
    {
        for(int i = 0; i < value; i++)
            fileStream.writeByte(value);
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Writes a byte array of image information to the output.
     * @param fileStream RandomAccessFile
     * @param imageArray byte[][]
     * @throws IOException 
     */
    private void writeImage(RandomAccessFile fileStream,
            byte[][] imageArray) throws IOException
    {   
        int offset;
        byte output;
                
        for(int i = 0; i < 38; i++)
        {
            for(int j = 0; j < 6; j++)
            {
                offset = j * 8;
                output = 0;
                
                output |= ( imageArray[i][offset] != 0) ? 1 : 0;
                output |= ( imageArray[i][offset + 1] != 0 << 1) ? 1 : 0;
                output |= ( imageArray[i][offset + 2] != 0 << 2) ? 1 : 0;
                output |= ( imageArray[i][offset + 3] != 0 << 3) ? 1 : 0;
                output |= ( imageArray[i][offset + 4] != 0 << 4) ? 1 : 0;
                output |= ( imageArray[i][offset + 5] != 0 << 5) ? 1 : 0;
                output |= ( imageArray[i][offset + 6] != 0 << 6) ? 1 : 0;
                output |= ( imageArray[i][offset + 7] != 0 << 7) ? 1 : 0;
                fileStream.writeByte(output);
            }
        }
    }
    
    /*-----------------------------------------------------------------------*/
}
