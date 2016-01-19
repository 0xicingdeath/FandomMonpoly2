/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fandommonopoly2;

/**
 *
 * @author nataliechin
 */
class Monopoly 
{
    private boolean owned;  //whether the property is owned yet 
    private boolean mortgaged;  //whether someone put the company up for mortgage
    private int buy; //how much is the property worth, if you purchase it 
    private int houseHotel;  //how many hotels are there (this variable is kind of useless as this implementaiton hasn't been used yet)
    private int payOut; //what if you sell it? 
    private int rent;  //how much rent do you pay? 
    private int number;   //number of times you've landed on the property 
    private String owner;  //who owns it
    private String name;  //what is the name of the property

    protected boolean getOwned()
    {
        return owned;
    } //end of getOwned method, using protected methods. 
    
    protected void setOwned (boolean other)
    {
        owned = other;
    }//end of setOwned method, use of protected methods. 
    
    protected boolean getMort()
    {
        return mortgaged; 
    }//end of getmortgage method, use of protected methods. 
    
    protected void setMort (boolean other)
    {
        mortgaged = other; 
    }//end of set mortgage method, use of protected methods. 
    
    protected int getBuy()
    {
         return buy; 
    }//end of get buy method, using protected methods and private variables. 
    
    protected void setBuy (int other)
    {
        buy = other; 
    }//end of set buy method, using protected methods and private variables 
    
    protected int getHH()
    {
         return houseHotel;
         //just a note; if the number is 1-3, then it's a house
         //if the number is 4-6, then it represents a hotel. 
    }//end of get house and hotel
    
    protected void setHH (int other) 
    {
        houseHotel = other; 
    }//this is only used in the beginning setting up, otherwise the next method should be called instead. 
    
    protected void hH()
    { 
      if (houseHotel<6) houseHotel++;  
      else  houseHotel = 6; 
    }//increases the number of houses and hotels by 1, as long as the number itself does not exceed 6.
    
    protected int getPayout()
    {
        return payOut;
    } //
    
    protected void setPayout(int other)
    {
        payOut = other; 
    }
    
    protected int getRent()
    {
        return rent; 
    }
    
    protected void setRent (int other)
    {
        rent = other; 
    }
    
    protected void number()
    {
        number++;
    } //end of method
    
    protected int getNumber()
    {
        return number; 
    }
    
    protected void setNumber (int other)
    {
        number = other; 
    }
    
    protected String getOwner()
    {
        return owner; 
    }
    
    protected void setOwner (String other) 
    {
        owner = other; 
    }
    
    protected String getName()
    {
        return name; 
    }
    
    protected void setName(String other) 
    {
        name = other;
    }
    
} //end of class.
