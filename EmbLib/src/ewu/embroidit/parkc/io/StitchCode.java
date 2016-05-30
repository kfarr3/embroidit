package ewu.embroidit.parkc.io;

/*-----------------------------------------------------------------------*/
/**
 * Holds StitchCode values for use in pattern creation. 
 * @author Chris Park (christopherpark@eagles.ewu.edu)
 */
public class StitchCode
{
    /*-----------------------------------------------------------------------*/
    
    public static final int NORMAL = 0;     //Stitch to (xx, yy)
    public static final int JUMP = 1;       //Move to (xx, yy)
    public static final int TRIM = 2;       //Trim + move to (xx, yy)
    public static final int STOP = 4;       //Pause machine for thread change
    public static final int SEQUIN = 8;     //Sequin
    public static final int END = 16;       //End of program
    
    /*-----------------------------------------------------------------------*/
    
    private StitchCode()
    {}
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns and instance of StitchCode
     * @return StitchCode
     */
    public static StitchCode getInstance()
    {
        return StitchCodeHolder.INSTANCE;
    }
    
    /*-----------------------------------------------------------------------*/
    
    private static class StitchCodeHolder
    {
        private static final StitchCode INSTANCE = new StitchCode();
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns whether this stitch is a TRIM, JUMP, or NORMAL stitch.
     * @param val int
     * @return int
     */
    public int getStitchCode(int val)
    {
        if((val & 0x20) != 0)
            return StitchCode.TRIM;
        if((val & 0x10) != 0)
            return StitchCode.JUMP;
        
        return StitchCode.NORMAL;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns whether this stitch is a END, STOP, or NORMAL stitch.
     * @param val1 int
     * @param val2 int
     * @return int
     */
    public int getStitchCode(int val1, int val2)
    {
        if(val1 == 0xFF && val2 == 0x00)
            return StitchCode.END;
        if(val1 == 0xFE && val2 == 0XB0)
            return StitchCode.STOP;
        
        return StitchCode.NORMAL;
    }
    
    /*-----------------------------------------------------------------------*/
}
