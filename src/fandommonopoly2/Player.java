/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fandommonopoly2;

/**
 *
 * @author nataliechin
 */
class Player 
{
    private int row = 0;
    private int col = 0; 
    private long gringottsAccount = 2000; 
    private int turn = 0; 
    
    protected void setRow (int other) 
    {
        row = other; 
    }
    
    protected int getRow()
    {
        return row; 
    } //end of getRow
    
    protected void setCol(int other) 
    {
        col = other; 
    } //end of setCol
    
    protected int getCol()
    {
        return col; 
    } //end of getcol
    
    protected void setG(long other) 
    {
        gringottsAccount = other; 
    } //end of setG
    
    protected long getG ()
    {
        return gringottsAccount; 
    } //end of getG
    
    protected void setTurn (int other) 
    {
        turn = other; 
    } //end of setTurn
    
    protected int getTurn()
    {
        return turn; 
    } //end of getTurn
    
} //end of class
