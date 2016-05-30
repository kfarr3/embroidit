package ewu.embroidit.parkc.util;

/*-----------------------------------------------------------------------*/

import ewu.embroidit.parkc.shape.A_EmbShapeWrapper;

/*-----------------------------------------------------------------------*/

/**
 * Stores the index, shape and wrapper corresponding to a editor action.
 * Used for undo/redo functionality.
 * @author Desolis (christopherpark@eagles.ewu.edu)
 */
public class EmbCommand
{
    /*-----------------------------------------------------------------------*/
    
    private int listIndex;
    private boolean isAdding;
    private A_EmbShapeWrapper wrapper;
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * constructs a command object with the given parameters.
     * @param listIndex int
     * @param wrapper A_EmbShapeWrapper
     */
    public EmbCommand(int listIndex, boolean isAdding, A_EmbShapeWrapper wrapper)
    {
        this.listIndex = listIndex;
        this.isAdding = isAdding;
        this.wrapper = wrapper;
    }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the list index that this action was last stored at.
     * @return int
     */
    public int getListIndex()
    { return this.listIndex; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape wrapper that corresponds to this operation.
     * @return A_EmbShapeWrapper
     */
    public A_EmbShapeWrapper getWrapper()
    { return this.wrapper; }
    
    /*-----------------------------------------------------------------------*/
    
    /**
     * Returns the shape wrapper that corresponds to this operation.
     * @return A_EmbShapeWrapper
     */
    public boolean getAddingFlag()
    { return this.isAdding; }
    
    /*-----------------------------------------------------------------------*/
}
