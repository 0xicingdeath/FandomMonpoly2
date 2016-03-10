/**Start Date of Program: November 2, 2015
 * End Date: Version 2.0 finished December 4, 2015 (++documentation added afterwards for things to improve) 
 *                  -> but already working on version 3.0
 *           Version 1.0 finished June 27, 2013, graphics created for the problem 
 *                  -> a disappointing version with JLabels instead of the images  
 *                  -> program didn't work properly...didn't even run :( 
 *                  -> but insufficient knowledge of multiple classes and inserting images made it nearly impossible 
 *                  -> one thing I've learned: name properties by the number, not by the name 
 * 
 * Description: This is a game of Monopoly creating a game board of fandom related locations - and having the ability to purchase, increase rent, and just have fun on the com pewter!
 *              This game involves properties and locations involved in Doctor Who, Merlin, Harry Potter, Narnia, Lord of the Rings, and many other fandoms.
 *
 * By: Natalie Chin, Student Number 1404356
 *
 * For: Multimedia 1A03
 *
 */

/*Ideas for Version 3.0
1. DONE 
   Implement the protected methods from the monopoly, player, and font classes, turning all local variables to private
   DONE -> Already completed for the mm monopoly class
   DONE  -> needs to be done for monopoly board (get() and set() methods already written)
   DONE  -> needs to be done for player objects
   NO NEED -> font shouldn't be that important, but should definitely implement protection against methods here.
2. Implementation of houses and hotels
    -> Started to think about it
    -> There's gotta be unifying factor that joins certain properties of the same colour together
3. Computer Artificial Intelligence in terms of whether to purchase something or not
        1. if player[0] owns 2 of those colours already, then you'll want to buy it to over-write their code
        2. if you have 2 of colours out of three, then you'll want to purchase the property
        3. if no one owns any of the colours, then you'll likely want to purchase teh product (just tart from a ertain number because otherwise the program will apporach 0
4. DONE Not having to hardcode all the data located in the initalize(); method
   DONE: using an infile buffered reader to read in everything from the file and put it straight into the array of objects, using the protection methods
    -> already done and completed for the main part of the file, as long as no one touches that file ever again! 
5. ?? Debug layered pane -> why is it that no one can find out how to display the pawns, even if they are at the topmost layer?
    -> consider asking Justin again or Thomas to take a look at the code
    -> all if statements in the mouseClicked method are "useless" other than rollDie and whereAmI if I can't get the pawns working
            -> could comment it out?
    -> but we still want an error message to show up if you press "pick up card" and you aren't even on that spot..
6. Known Bug: if you try to exit, and then press no, it reloads the pane but without the initial immage 
    -> not sure how to change this yet, unless i put all the main graphics into a different method for  
7. Making it an executable so you can just double click on it like an exe file. 
    -> Except that for executables, it's sort of a "windows" file instead of mac, so what's the mac equivalent of an exe? 
*/

package fandommonopoly2;

//import for border layout (layout of the screen)
import java.awt.BorderLayout;
//import for color
import java.awt.Color;
//mport for dimension
import java.awt.Dimension;
//import for different fonts
import java.awt.Font;
//Import for grid layout
import java.awt.GridLayout;
//import for listening for events
import java.awt.event.ActionEvent;
//import for listening for action
import java.awt.event.ActionListener;
//import for mouse event
import java.awt.event.MouseEvent;
//import for mouse listener
import java.awt.event.MouseListener;
//import for window adapter
import java.awt.event.WindowAdapter;
//import for ability to use windows
import java.awt.event.WindowEvent;
//import for input/output buffered reader 
import java.io.BufferedReader;
//import for file not found exceptions, in the catch statements for reading in and out of files. 
import java.io.FileNotFoundException;
//import for file reader
import java.io.FileReader;
//import for file writer 
import java.io.FileWriter;
//import for IOException
import java.io.IOException;
//import for print writer 
import java.io.PrintWriter;
//import for the border layout, except for menu items
import javax.swing.BorderFactory;
//import for image icons
import javax.swing.ImageIcon;
//import for buttons
import javax.swing.JButton;
//import for jframes
import javax.swing.JFrame;
//import for jlabels
import javax.swing.JLabel;
//import for layered panes
import javax.swing.JLayeredPane;
//import for jmenu
import javax.swing.JMenu;
//import for jmenu  bar
import javax.swing.JMenuBar;
//import for something in the menu
import javax.swing.JMenuItem;
//import for joption pane
import javax.swing.JOptionPane;
//import for jpanel
import javax.swing.JPanel;
//import for jscrollpane
import javax.swing.JScrollPane;
//import for jtext area
import javax.swing.JTextArea;
//import for jtextfield
import javax.swing.JTextField;
//import for interaction with windows
import javax.swing.WindowConstants;


public class HPMonopoly implements MouseListener, ActionListener
{
    private Player [] player = new Player[2];
    private Monopoly [] square = new Monopoly[36];
    private JButton location = new JButton("Where am I?");
    private int houses = 0;
    private int die1, die2;
    private int pot;
    private boolean whoseTurn = true;
    private String userName;
    private JTextField nameHere = new JTextField();

    //JLabel characterUser;

    private final boolean tracing = false;

    private final JLabel enterName = new JLabel("Please enter your name: ");
    private JTextArea name = new JTextArea();

    private JLabel propertyNorth[] = new JLabel[10];
    private JLabel propertyEast[] = new JLabel[8];
    private JLabel propertySouth[] = new JLabel[10];
    private JLabel propertyWest[] = new JLabel[8];

    private JLabel rent[] = new JLabel[36];

    private JLabel propertyNorthLarge[] = new JLabel[10];
    private JLabel propertyEastLarge[] = new JLabel[8];
    private JLabel propertySouthLarge[] = new JLabel[10];
    private JLabel propertyWestLarge[] = new JLabel[8];

    private JLabel gringottsUser  = new JLabel();
    private JLabel gringottsComp = new JLabel();

    private JButton goComPewter = new JButton("Go, Com Pewter");

    private JPanel getInfo = new JPanel(new GridLayout(0, 2));

    private JPanel namePanel = new JPanel(new GridLayout(3,0));

    private JButton continueName = new JButton("Continue");

    //JButtons for Welcome Screen:
    private JButton continueWelcome = new JButton("Menu");

    //Components needed for Menu Frame:
    private JButton continueMenu = new JButton("Play Game");

    private JButton roRCard = new JButton ("Draw card");
    private JButton roRCard2 = new JButton ("Draw card");

    //All declarations for the layered pane and the scroll pane
    private JLayeredPane pane = new JLayeredPane();
    private JScrollPane scroller = new JScrollPane(pane);
    private JLayeredPane north = new JLayeredPane();
    private JLayeredPane east = new JLayeredPane();
    private JLayeredPane south = new JLayeredPane();
    private JLayeredPane west = new JLayeredPane();

    //Declarations for purchasing properties
    private JButton[] buyPropertiesNorth = new JButton [10];
    private JButton[] buyPropertiesEast = new JButton [8];
    private JButton[] buyPropertiesSouth = new JButton [10];
    private JButton[] buyPropertiesWest = new JButton [8];

    //Required fonts
    private Font titleFont = new Font("Harry P", Font.PLAIN , 25);
    private Font normalFont = new Font("Herculanum", Font.PLAIN, 20);
    private Font welcomeFont = new Font("Harry P", Font.BOLD, 100);

    private JButton rollDie = new JButton("Roll the Dice. ");

    private JLabel centrePic = new JLabel(new ImageIcon("mainBoard.png"));

    //Declarations for the menu bar  on Welcome Screen. :
    private JMenuBar menuBarWelcome = new JMenuBar();
    private JMenu menuWelcome = new JMenu("Menu");
    private JMenuItem menuWelcomeItem [] = new JMenuItem[4];

    //For JMenu on Menu Screen.
    private JMenuBar menuBarMenu = new JMenuBar();
    private JMenu menuMenu = new JMenu("Menu");
    private JMenuItem[] menuMenuItem = new JMenuItem[3];

    //JMenu on Game Screen
    private JMenuBar gameBarMenu = new JMenuBar();
    private JMenu gameMenu = new JMenu("Menu");
    private JMenuItem[] menuGameItem = new JMenuItem[4];

    //Colours
    private Color brown = new Color(128, 64, 0);
    private Color moss = new Color(0, 128, 64);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
     new HPMonopoly();   //calling constructor
    } //end of main method.

    HPMonopoly() 
    {
        initialize(); //calling initialize (which yes, includes all the houses)
        welcome(); //calling welcome
    } //end of constructor method

    /****************************************************************************************************************************************************************************
     * Description of Method: Initializes all the information inside the array to the information that is a key to running the program.
     * Parameters and Return Type: It will not return anything, but the Object Monopoly must actually exist for something to be done.
     * Preconditions: The Object Array has to exist.
     * Postconditions: The Object Array will actually contain information.
     ****************************************************************************************************************************************************************************/

    private void initialize() 
    {

        if (tracing) System.out.println("Value of whoseTurn in initialize: " +whoseTurn);

        whoseTurn = true; //this can start at any value, just sort of randomly decided to decide that here, which means that the user plays first.

        if (tracing) System.out.println("Value of whoseTurn in initializee: " +whoseTurn);
        //************************************************************************************Declaring the North Small Icons Array*******************************************************************************
        for (int counter = 0; counter<10; counter++)
        {
             propertyNorth[counter] = new JLabel(new ImageIcon("N"+counter+".png")); //declaration of jlabels using the pictures in the folder.
             north.add(propertyNorth[counter]); //adding it to the layered pane
             pane.setLayer(propertyNorth[counter], 1); //each picture goes on the layer on top of it, with an increasing depth

             propertyNorth[counter].addMouseListener(this);

             //propertyNorth[counter].addMouseMotionListener(this);
             if (tracing) System.out.println("Printed out " +counter+ "of North");
        } //end of for loop.

        //**************************************************************************************Declaring the East Small Icon Array***********************************************************************************
        for (int counter = 0; counter<8; counter++)
        {
            propertyEast[counter] = new JLabel(new ImageIcon("E"+counter+".png")); //declaration of jlabels using pictures in the folder
             east.add(propertyEast[counter]); //adding it to the layered pane
             propertyEast[counter].addMouseListener(this); //adding the mouse listener to listen for mouse events
             pane.setLayer(propertyEast[counter], 1); //each picture goes onto the layer at depth [counter]

             //propertyEast[counter].addMouseMotionListener(this);

            if (tracing) System.out.println("Printed out " +counter+ "of EAST");
        } //end of for loop.

        //******************************************************************************************Declaring the South Small Icon Array***********************************************************************************
        for (int counter = 0; counter<10; counter++)
        {
            propertySouth[counter] = new JLabel(new ImageIcon("S"+counter+".png"));
             south.add(propertySouth[counter]);
             propertySouth[counter].addMouseListener(this);
             pane.setLayer(propertySouth[counter], 1);

             //propertySouth[counter].addMouseMotionListener(this);

             if (tracing) System.out.println("Added:  " +counter+ "of South");
        } //end of for loop.

        //********************************************************************************************Declaring the West Small Icon Array***************************************************************************************
        for (int counter = 0; counter<8; counter++)
        {
           propertyWest[counter] = new JLabel(new ImageIcon("W"+counter+".png"));
             west.add(propertyWest[counter]);
             propertyWest[counter].addMouseListener(this);
             pane.setLayer(propertyWest[counter], 1);

             //propertyWest[counter].addMouseMotionListener(this);

             if (tracing) System.out.println("Printed out " +counter+ "of West");
        } //end of for loop.

        //*********************************************************************************************Declaration of North Large Icon Array*************************************************************************************
        for (int counter = 0; counter<10; counter++)
        {
             propertyNorthLarge[counter] = new JLabel(new ImageIcon("BN"+counter+".png"));
             //north.add(propertyNorthLarge[counter]);
             //propertyNorthLarge[counter].addMouseListener(this);
             if (tracing) System.out.println("Printed out " +counter+ "of North Large");

             //pane.add(square[counter].rent, BorderLayout.SOUTH);
        } //end of for loop.

        //*********************************************************************************************Declaration of East Large Icon Array***************************************************************************************
        for (int counter = 0; counter<8; counter++)
        {
            propertyEastLarge[counter] = new JLabel(new ImageIcon("BE"+counter+".png"));
             //east.add(propertyEastLarge[counter]);
             //propertyEastLarge[counter].addMouseListener(this);
            if (tracing) System.out.println("Printed out " +counter+ "of propertyEastLarge");
        } //end of for loop.

        //**********************************************************************************************Declaration of South Large Icon Array***************************************************************************************
        for (int counter = 0; counter<10; counter++)
        {
            propertySouthLarge[counter] = new JLabel(new ImageIcon("BS"+counter+".png"));
             //south.add(propertySouthLarge[counter]);
             //propertySouthLarge[counter].addMouseListener(this);
             if (tracing) System.out.println("Printed out " +counter+ "of propertySouthLarge");
        } //end of for loop.

        //**************************************************************************************************Declaration of West Large Icon Array***************************************************************************************
        for (int counter = 0; counter<8; counter++)
        {
           propertyWestLarge[counter] = new JLabel(new ImageIcon("BW"+counter+".png"));
            // west.add(propertyWestLarge[counter]);
             //propertyWestLarge[counter].addMouseListener(this);
             if (tracing) System.out.println("Printed out " +counter+ "of propertyWestLarge");
        } //end of for loop.

        //Declaration of Property North Button Array--------------------------------------------------------------------------------*****************************************************************************---------------------------------------------

        for (int counter = 0; counter<10; counter++)
        {
            if(counter == 1) //Normal Properties
            {
                buyPropertiesNorth[counter] = new JButton("Buy Property");
                if (tracing) System.out.println("Buying Property Button Creation  north1: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 3)
            {
                buyPropertiesNorth[counter] = new JButton("Buy Property");
                if (tracing) System.out.println("Buying Property Button Creation  north3: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 5)
            {
                buyPropertiesNorth[counter] = new JButton("Buy Property");
                if (tracing) System.out.println("Buying Property Button Creation  north5: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 6)
            {
                buyPropertiesNorth[counter] = new JButton("Buy Property");
                if (tracing) System.out.println("Buying Property Button Creation  north6: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 8)
            {
                buyPropertiesNorth[counter] = new JButton("Buy Property");
                if (tracing) System.out.println("Buying Property Button Creation  north8: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 0)
            {
                buyPropertiesNorth[0] = new JButton("Collect 200 Galleons");
                if (tracing) System.out.println("Customizing creation of button North0: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 2)
            {
               buyPropertiesNorth[2]= new JButton("Pick up Card");
               if (tracing) System.out.println("Customizing creation of button NORTH2: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 4)
            {
                buyPropertiesNorth[4] = new JButton ("Pay 200 Galleons");
                if (tracing) System.out.println("Customizing creation of button north4: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 7) //this woudl be the equivalence of community chest or chance.
            {
                buyPropertiesNorth[7] = new JButton("Pick up Card");
                if (tracing) System.out.println("Customizing creation of button  north7: " + buyPropertiesNorth[counter]);
            } //end of if statement

            if (counter == 9)
            {
                buyPropertiesNorth[9] = new JButton("Pay 50 galleons to get out  north");
            } //end of if statement

            buyPropertiesNorth[counter].addMouseListener(this);
        } //end of for loop.

        //Declaration for Property East Buttons--------------------------------------------------------------------------------------------------------------------------******************************************************************************************--------------------

        for (int counter = 0; counter<8; counter++)
        {

            if (counter == 6)
            {
                buyPropertiesEast[counter] = new JButton("Pick up card");
            } //end of if statement

            else
            {
            buyPropertiesEast[counter]=new JButton("Buy Property");
            } //end of if statement

            buyPropertiesEast[counter].addMouseListener(this);

        } //end of for loop

        //Declaration for property south buttons----------------------------------------------------------------------***************************************************************------------------------------------------------
        for (int counter = 0; counter<10; counter++)
        {
            if(counter == 1)
            {
            buyPropertiesSouth[counter]= new JButton("Buy Property");
            } //end of if statement

            if (counter ==3)
            {
                 buyPropertiesSouth[counter]= new JButton("Buy Property");
            } //end of if statement

            if (counter == 4)
            {
                buyPropertiesSouth[counter]= new JButton("Buy Property");
            } //end of if statement

            if (counter == 5)
            {
                buyPropertiesSouth[counter]= new JButton("Buy Property");
            } //end of if statement

            if (counter == 6)
            {
                buyPropertiesSouth[counter]= new JButton("Buy Property");
            } //end of if statement

             if (counter == 8)
             {
                 buyPropertiesSouth[counter]= new JButton("Buy Property");
             }     //end of if statement

             if (counter == 2)
             {
                 buyPropertiesSouth[counter]= new JButton("Buy Property");
             } //end of if statement

             if (counter ==7)
             {
                 buyPropertiesSouth[counter]= new JButton("Pick Up Card");
             } //end of if statement

            if (counter == 0) //Go to  mungos
            {
                buyPropertiesSouth[counter] = new JButton("Go to St. Mungo");
            } //end of if statement

            if (counter == 9) //Game Board
            {
                buyPropertiesSouth[counter] = new JButton("Receive Galleons");
            } //end of if statement

            buyPropertiesSouth[counter].addMouseListener(this);

        } //end of for loop

        //Declaration for Property West Buttons-----------------------------------------------------------------------*********************************************************************-------------------------------------------------------
        for (int counter = 0; counter<8; counter++)
        {
            if (counter == 2)
            {
                buyPropertiesWest[counter] = new JButton ("Pick up a card. ");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            if (counter == 5)
            {
                buyPropertiesWest[counter] = new JButton("Pick up a card");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            if (counter == 0)
            {
                buyPropertiesWest[counter] = new JButton("Buy Property");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            if (counter == 1)
            {
                buyPropertiesWest[counter] = new JButton("Buy Property");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            if (counter == 3)
            {
                buyPropertiesWest[counter] = new JButton("Buy Property");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            if (counter == 4)
            {
                buyPropertiesWest[counter] = new JButton("Buy Property");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            if (counter == 6)
            {
                buyPropertiesWest[counter] = new JButton("Buy Property");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            if (counter == 7)
            {
                buyPropertiesWest[counter] = new JButton("Buy Property");

                if (tracing) System.out.println("Print out" +buyPropertiesWest[counter]);

            } //end of if statement

            buyPropertiesWest[counter].addMouseListener(this);

        } //end of for loop
 
        try 
        {
            //Attempting to apply infile reading to everything that happens next
            String tempS;
            boolean tempB;
            int tempI;
            
            BufferedReader infile = new BufferedReader(new FileReader("Properties.txt")); //tells the program to read from accommodation.txt (AW)                    
            for (int i = 0; i<36; i++)
            {
                square[i] = new Monopoly();
                tempB= Boolean.parseBoolean(infile.readLine()); 
                    square[i].setOwned(tempB);
                tempS = infile.readLine().trim(); 
                    square[i].setOwner(tempS);
                tempB = Boolean.parseBoolean(infile.readLine());
                    square[i].setMort(tempB);
                tempI = Integer.parseInt(infile.readLine());
                    square[i].setBuy(tempI);
                tempI = Integer.parseInt(infile.readLine());
                    square[i].setHH(tempI);
                tempI = Integer.parseInt(infile.readLine());
                    square[i].setPayout(tempI);
                tempI = Integer.parseInt(infile.readLine());
                    square[i].setRent(tempI);
                tempI = Integer.parseInt(infile.readLine());
                    square[i].setNumber(tempI);
                tempS = infile.readLine().trim();
                    square[i].setName(tempS);
            } //end of for loop
            boolean tracing2 = true; 
            
                if (tracing2)
                {
                    for (int i = 0; i<36; i++)
                    {
                        System.out.println(square[i].getOwned() +" \n"+ 
                                            square[i].getOwner()+" \n"+
                                            square[i].getMort() +" \n"+
                                            square[i].getHH() +" \n"+
                                            square[i].getPayout()+" \n"+
                                            square[i].getRent() +" \n"+
                                            square[i].getNumber() +" \n"+
                                            square[i].getName());
                    } //end of for statement
            } //end of tracing statement
            
        } //end of try statement
        catch (FileNotFoundException ex) 
        {
            System.out.println("Unable to find file, therefore unable to run the program.");
        }
        catch (IOException e)
        {
            System.out.println("Catching an IO Exception, unable to run program.");
        }
        catch (Exception e) 
        {
            System.out.println("Not one of the upper catch statements, not sure what the problem is.");
        }

        if (tracing) System.out.println("Printing to file");
        //printToFile();
        
        //*********Initialization for Gringotts Array (basically takes care of all the money).

        for (int i = 0; i<player.length; i++)
        {
            player[i] = new Player();
            player[i].setG(2000); //all players start with 2000
            player[i].setRow(0);
            player[i].setCol(0);
            player[i].setTurn(1);
        } //end of for loop

        goComPewter.addMouseListener(this); //adding the computer move afterwards.

        for (int q = 0; q<menuWelcomeItem.length; q++)
        {
            if (q == 0)
            {
                menuWelcomeItem[0] = new JMenuItem("About");
            } //end of if statement

            else if (q == 1)
            {
                menuWelcomeItem[1] = new JMenuItem("Instructions");
            } //end of else if statement

            else if (q==2)
            {
                menuWelcomeItem[2] = new JMenuItem("Other");
            } //end of else if statement

            else if (q ==3)
            {
                menuWelcomeItem[3] = new JMenuItem("Exit");
            } //end of else if statement

            menuWelcomeItem[q].addActionListener(this);

            menuWelcome.add(menuWelcomeItem[q]);

        } //end of for loop.

        for (int u = 0; u<rent.length; u++)
        {
            rent[u] = new JLabel("Rent: "+ square[u].getRent()+ " Owned: " +square[u].getOwned());
        } //end of for loop

        for (int q = 0; q<menuMenuItem.length; q++)
        {
            if (q ==0)
            {
                menuMenuItem[0] = new JMenuItem("About");
            } //end of if statement

            else if (q == 1)
            {
                menuMenuItem[1] = new JMenuItem("Instructions");
            } //end of else if statement

            else if (q==2)
            {
                menuMenuItem[2] = new JMenuItem("Exit");
            } //end of else if statement

            menuMenuItem[q].addActionListener(this);

            menuMenu.add(menuMenuItem[q]);

        } //end of for loop.

        for (int q = 0; q<menuGameItem.length; q++)
        {
            if (q ==0)
            {
                menuGameItem[0] = new JMenuItem("About");
            } //end of if statement

            else if (q == 1)
            {
                menuGameItem[1] = new JMenuItem("Instructions");
            } //end of else if statement

            else if (q == 2)
            {
                menuGameItem[2] = new JMenuItem("Where are my pawns? ");
            } //end of if statement

            else if (q==3)
            {
                menuGameItem[3] = new JMenuItem("Exit");
            } //end of else if statement

            menuGameItem[q].addActionListener(this);

            gameMenu.add(menuGameItem[q]);

        } //end of for loop.

    } //end of initialize method

    /****************************************************************************************************************************************************************************
     * Description of Method: Shows the user a welcomeScreen
     * Parameters and Return Type: It doesn't receive anything anything, but the mouseListener must have been imported then extended on the class and added to the JButton
     * Preconditions: The JButton must exist
     * Postconditions: The welcomeScreen will have been displayed and by clicking on the button, it will bring you to the menu. It will bypass instructions cause I'm going to find out how to make a JMenu and create an instruction thing there cause it's much too hard to explain in a screen and the about section will go there too.
     ****************************************************************************************************************************************************************************/

    private void welcome()
    {
        JFrame frame = new JFrame("Welcome to Monopoly: Fandom Style!");
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setFont(titleFont);
        frame.setBackground(moss);

        frame.addWindowListener


        (new WindowAdapter()

          {

            public void windowClosing(WindowEvent e)

            {

                         JOptionPane.showMessageDialog(null, "We hope you had fun in Fandom Monopoly", "So Long, Farewell", JOptionPane.INFORMATION_MESSAGE);

              System.exit(0);

            }

          }

        );

        JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setFont(titleFont);
            mainPanel.setBackground(brown);
            mainPanel.setForeground(moss);

        JPanel name = new JPanel(new GridLayout(0, 2));

        JLabel welcome = new JLabel("Welcome!");
            welcome.setFont(titleFont);
            welcome.setBackground(brown);
            welcome.setForeground(Color.white);
        JTextArea middle = new JTextArea("\n\n       We hope you have fun in the Game of Monopoly: Fandom Style. \n\n\n        It's meant to display the adjective 'Magnificent'. \n\n\n       Enjoy!");
            middle.setFont(titleFont);
            middle.setBackground(moss);
            middle.setForeground(Color.white);

        JPanel middleField = new JPanel(new GridLayout(2, 0));

        JTextArea enterName = new JTextArea("\n\n\n\n\n\n\n                Please enter your name: ");
                enterName.setFont(normalFont);
                enterName.setBackground(moss);
                enterName.setForeground(Color.white);

        name.setForeground(Color.black);
        name.add(enterName);
        name.add(nameHere);

        middleField.add(middle);
        middleField.add(name);
        //middleField.add(enterName);
        //middleField.add(nameHere);

        continueWelcome.addMouseListener(this);

        menuBarWelcome.add(menuWelcome);

        mainPanel.add(welcome, BorderLayout.NORTH);
        mainPanel.add(middleField, BorderLayout.CENTER);
        //mainPanel.add(enterName, BorderLayout.CENTER);
        //mainPanel.add(nameHere, BorderLayout.CENTER);
        mainPanel.add(continueWelcome, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setJMenuBar(menuBarWelcome);

    } //end of welcome screen.

    /****************************************************************************************************************************************************************************
     * Description of Method: Gives the user a choice between playing the game or exiting the program.
     * Parameters and Return Type: The Game Button must exit and the mouseListener must be imported and extended on the class
     * Preconditions: The JButton for the game needs to exist and there needs to be an if statement inside the mouseClicked method for this JButton
     * Postconditions: The menu screen will have been displayed
     ****************************************************************************************************************************************************************************/

    private void menu()
    {
        //int num = 0;
       JFrame frame = new JFrame("Menu");
        frame.setFont(titleFont);
        frame.setBackground(brown);

       JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(moss);
        mainPanel.setForeground(Color.black);

       //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

       frame.addWindowListener

        (new WindowAdapter()

          {

            public void windowClosing(WindowEvent e)

            {
                         JOptionPane.showMessageDialog(null, "We hope you had fun in Fandom Monopoly", "So Long, Farewell", JOptionPane.INFORMATION_MESSAGE);
              System.exit(0);
            } //end of public void windowClosing
          } //end of new WindowAdapter
        ); //end of frame

       //JPanel mainPanel = new JPanel(new BorderLayout());

       JTextArea welcome = new JTextArea("Hello " +userName+ "! As we have welcomed you to our program, would you like to play the game?");
            welcome.setBackground(moss);
            welcome.setForeground(Color.white);
            welcome.setLineWrap(true);
            welcome.setEditable(false);
            //welcome.setColor(brown);
            //welcome.setForeground(Color.white);
        welcome.setFont(welcomeFont);

       continueMenu.addMouseListener(this);

       //namePanel.add(enterName);
       //namePanel.add(name);
       //namePanel.add(continueName);

       continueMenu.setBackground(brown);
       //continueMenu.setForeground(Color.white);

       mainPanel.add(welcome, BorderLayout.NORTH);
       mainPanel.add(continueMenu, BorderLayout.SOUTH);
       //mainPanel.add(namePanel, BorderLayout.CENTER);

       menuBarMenu.add(menuMenu);

       whoseTurn = true; //redundancy - should have been set in initialize already.

       frame.setContentPane(mainPanel);
       frame.setSize(1000, 800);
       frame.setVisible(true);
       frame.setJMenuBar(menuBarMenu);

    } //end of menu method

    /****************************************************************************************************************************************************************************
     * Description of Method: This is where most of the game will take place - at least the board game.
     * Parameters and Return Type: There is nothing that this program is going to receive or return.
     * Preconditions: All the pictures must be in the folder nataliechin/Macintosh HD/NetBeansProjects/FandomMonopoly/MonopolyPics
     * Postconditions: The game will keep going until well, it dies.
     ****************************************************************************************************************************************************************************/

    private void game()
    {
        if (tracing) System.out.println("Inside the game method. ");
        JFrame frame =  new JFrame("Monopoly");

            pane.setBackground(brown);
            frame.setBackground(brown);


        frame.addWindowListener
        (new WindowAdapter()
          {
            public void windowClosing(WindowEvent e)
            {

                       JOptionPane.showMessageDialog(null, "We hope you had fun in Fandom Monopoly", "So Long, Farewell", JOptionPane.INFORMATION_MESSAGE);
                       if (tracing) System.out.println("I suppose I'm not as rebellious anymore though");
                       System.exit(0);

            } //end of method
          } //end of new window adapter
         ); //end of window listener

        whoseTurn = true;  //redundancy again.
        location.addMouseListener(this);

        //getInfo.add(goComPewter);
        getInfo.add(location);
        getInfo.add(rollDie);
            getInfo.setBackground(moss);
            //getInfo.setForeground(Color.white);
        //getInfo.add(getInfoUserComp[1]);

        pane.setLayout(new BorderLayout());
        pane.setPreferredSize(new Dimension(1200, 800));
        pane.setBorder(BorderFactory.createTitledBorder(
                                    "Monopoly"));

        west.setLayout(new GridLayout(8, 0));
        west.setVisible(true);
            west.setBackground(brown);

        south.setLayout(new GridLayout(0, 10));
        south.setVisible(true);
            south.setBackground(brown);

        east.setLayout(new GridLayout(8, 0));
        east.setVisible(true);
            east.setBackground(brown);

        north.setLayout(new GridLayout(0, 10));
        north.setVisible(true);
            north.setBackground(brown);

        // centreBoard.add(rollDie);
        rollDie.addMouseListener(this);

        //pane.add(characterUser, 3);

        //player[0].xCoordinate = 200;
        //player[0].yCoordinate= 480;


        //pane.moveToFront(characterUser);
        //pane.setLayer(characterUser, 3, 0);

        //characterUser.setBounds(player[0].xCoordinate, player[0].yCoordinate, 114, 80);
        //characterUser.setLocation(player[0].xCoordinate , player[0].yCoordinate);


        //System.out.println(characterUser.getX() + "," + characterUser.getY());

        JPanel centerBoard = new JPanel(new BorderLayout());

        centerBoard.add(getInfo, BorderLayout.SOUTH);
        centerBoard.add(centrePic, BorderLayout.CENTER);

        pane.add(north, BorderLayout.NORTH, 1);
        pane.add(east, BorderLayout.EAST, 1);
        pane.add(south, BorderLayout.SOUTH, 1);
        pane.add(west, BorderLayout.WEST, 1);
        pane.add(centerBoard, BorderLayout.CENTER, 1);
        //pane.add(getInfo,BorderLayout.CENTER);
        //pane.add(centrePic, BorderLayout.CENTER, 1);

        north.addMouseListener(this);
        east.addMouseListener(this);
        south.addMouseListener(this);
        west.addMouseListener(this);

        //pane.addMouseListener(this);

        if (tracing)
        {
            //System.out.println("Character in location after adding: " + characterUser.getLocation());
        } //end of if statement

        gameBarMenu.add(gameMenu);

        frame.setContentPane(scroller);
        frame.setSize(1200, 1200);
        //frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.setJMenuBar(gameBarMenu);

    }//end of game method
    
    /****************************************************************************************************************************************************************************
     * Description of the Method: runs the hotels and houses
     * Parameters and Return Type: Nothing and nothing 
     * Preconditions: the user must have owned all the properties of that colour 
     * Postconditions: the user will have purchased a house or a hotel. 
    ****************************************************************************************************************************************************************************/
    
    private void houseHotel()
    {
        
    } //end of house and hotel method. 

    /****************************************************************************************************************************************************************************
     * Description of Method: Runs the room of requirement.
     * Parameters and Return Type: Nothing and nothing
     * Preconditions: the user must have landed on the room of requirement, which is the only time that this method is called.
     * Postconditions: user would have "picked up" their card already.
     ****************************************************************************************************************************************************************************/

    private void roomOfRequirement()
    {

    long temp = 0; 
    
    int x = (int) ((Math.random()*13)+1);

        if (x == 0) 
        {
            player[0].setCol(0);
            JOptionPane.showMessageDialog(pane, "Advance to go and collect 200 galleons", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        } //end of if statement
        else if (x == 1) 
        {
            //player[0].gringottsAccount+=200;
            JOptionPane.showMessageDialog(pane, "There is a gringrotts error, and it is in your favour! \n Collect 200 galleons courtesy of us. ", "Good news!", JOptionPane.INFORMATION_MESSAGE);
            player[0].setG(player[0].getG()+200);
        } //end of if statement
        else if (x == 2) 
        {
            JOptionPane.showMessageDialog(pane, "You must go to Odin's Land to purchase/interact grain because Camelot is out of food. \n Pay 100 galleons", "Uh oh...", JOptionPane.INFORMATION_MESSAGE);
            player[0].setG(player[0].getG()+100);
        } //end of if statement
        else if (x == 3) 
        {
            int y = JOptionPane.showConfirmDialog(pane, "Your father falls ill, and you must visit the Court Physician, Gaius. \n Under normal circumstances, he wouldn't charge you, but this time, he asks for gold. \n You must provide him with 50 galleons otherwise he'll tell the King that you are a sorceress/sorcerer. \n Do you give him 50 galleons or risk him exposing your secret?", "Uh oh!", JOptionPane.YES_NO_OPTION);

            if (y == 0)//if the user pressed yes
            {
                player[0].setG(player[0].getG()-50);
                pot += 50;
                JOptionPane.showMessageDialog(pane, "We have taken 50 galleons out of your account.", "Sorry...", JOptionPane.INFORMATION_MESSAGE);
            } //end of if statement
            else if (y == 1)//if the user pressed no.
            {
                double p = 0.0;
                int a = (int) (Math.random() * 100);

                if (a % 2 == 0) //if divisible by 2
                {
                    player[0].setG(player[0].getG()/2);

                    pot += p;

                    JOptionPane.showMessageDialog(pane, "Gaius has been possessed by a goblin. He rats you out. You lose half of your total amount of money.", "Bad news...", JOptionPane.INFORMATION_MESSAGE);
                } //end of if statement
                else //if it is an odd number
                {
                    player[0].setG(player[0].getG()+50);
                    pot -= 50;
                    JOptionPane.showMessageDialog(pane, "Fortunately, Gaius hasn't said anything to the King! \n In fact, he was possessed by a Goblin, and he apologizes by giving you 50 galleons refund,", "Great news!", JOptionPane.INFORMATION_MESSAGE);
                } //end of if statement

            } //end of if statement

        } //end of if statement
        else if (x == 4) 
        {
            player[0].setCol(9);
            JOptionPane.showMessageDialog(pane, "Your friends are annoying you, and with a very random thought, \n you try to erase their memory. \n Your wand backfires and you have no recollection of who you are, what you're doing, or how you got to where you were.\n Go to St. Mungos, now.", "Bloody Briliat Job..or not", JOptionPane.INFORMATION_MESSAGE);
            player[0].setCol(3);

            JOptionPane.showMessageDialog(pane, "Please press okay to pay 50 galleons to get out of St. Mungos", "Health Fees", JOptionPane.INFORMATION_MESSAGE);

            player[0].setG(player[0].getG()-50);

            pot += 50;

        } //end of if statement
        else if (x == 5) 
        {
            JOptionPane.showMessageDialog(pane, "Happy happy birthday! \n Collect 20 galleons from each player and enjoy!", "HAPPY BIRTHDAY!", JOptionPane.INFORMATION_MESSAGE);
            player[1].setG(player[1].getG() -20);
            
            player[0].setG(player[0].getG() + 20);
            pot += 40;

        } //end of if statement
        else if (x == 6) 
        {
            JOptionPane.showMessageDialog(pane, "You and all your friends decide to spend money on the Chinese Theatre. \n Yes, that same one that John and his date (plus Sherlock). \n However, Sherlock only has 3 free tickets, no more, no less. \n All other players must pay 50 galleons. \n You, the person who picked up this card must chip in 10 galleons.", "Chinese Theatre", JOptionPane.INFORMATION_MESSAGE);
            
            player[1].setG(player[1].getG() -50);
            
            player[0].setG(player[0].getG()-10);
            pot += 60;
        } //end of if statement
        else if (x == 7) 
        {
            int b = JOptionPane.showConfirmDialog(pane, "Morgana has declared war on Camelot. \n She is using you to get through to King Arthur. \n You, can use magic and stop her (but then everyone else will know that you have magic); or you can let her do her worst.\n Do you use magic?", "Decisions, decisions", JOptionPane.YES_NO_OPTION);

            if (b == 0) //you use magic.
            {
                JOptionPane.showMessageDialog(pane, "Congratulations. You gain 200 galleons for standing up for yourself.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);

                player[0].setG(player[0].getG()+200);
            } //end if statement
            else if (b == 1) //you don't use magic.
            {
                JOptionPane.showMessageDialog(pane, "Sorry...bad decision. \n You lose 200 galleons", "Bad choice...", JOptionPane.INFORMATION_MESSAGE);

                player[0].setG(player[0].getG()-200);
            } //end of if statement (b)
        } //end of if statement
        else if (x == 8) 
        {
            JOptionPane.showMessageDialog(pane, " *TARDIS decoding message* \n EXTERMINATE! \n EXTERMINATE! \n EXTERMINATE \n The world is going into an economic crisis because the Daleks are taking over; you lose 100 galleons", "ALERT, ALERT, ALERT.", JOptionPane.ERROR_MESSAGE);

            player[0].setG(player[0].getG() -100);

        } //end of else if statement
        else if (x == 9) 
        {
            JOptionPane.showMessageDialog(pane, "Click, click, click, click...\n What is it? The clicking, the constant constant clicking \n Click click click click \n Footsteps.....\n *faintly in the background* DELETE DELETE DELETE \n I'm so sorry. \n The Cyberman are out to get you. \n The Cyberman........The Cyber---you lose 150 galleons", "ALIEN ALERT, ALIEN ALERT, ALIEN ALERT", JOptionPane.ERROR_MESSAGE);

            player[0].setG(player[0].getG()-150);
        } //end of else if statement for x = 9
        else if (x == 10) 
        {
            JOptionPane.showMessageDialog(pane, "Doctor................\n Doctor.............\n Doctor! \n  Congratulations, you've just met the 10th doctor. \n Gain 100 galleons", "", JOptionPane.INFORMATION_MESSAGE); 
            
            player[0].setG(player[0].getG()+100);
        } //end of else if statement for x = 10
        
        else if (x == 11)
        {
            JOptionPane.showMessageDialog(pane, "There's something, in the corner of your eye, that you can see, that you can sense. But you can't pinpoint. \n You have no idea what it actually is, but you have a feeling, a funny feeling that something is here. \n Something is here. \n Something is coming. \n But you don't know what. \n Turns out, it's the thing, it's the thing. The thing. The you-don't-know-what-it-is. You lose 20 galleons because you cannot identify it. ", "The Sil-", JOptionPane.INFORMATION_MESSAGE);
            
            player[0].setG(player[0].getG()-20);
            
        } //end of else if statement for x = 11
        
        //************************************************Transportation*
        else if (x == 8) 
        {
            JOptionPane.showMessageDialog(pane, "Advance to the nearest transportation spot (Taxi, Tardis, Wardrobe or Hogwarts Express).\n  If it is unowned, you can buy this from the bank. \n If it is owned, roll the dice a second time and pay the owner 10 times the sum of the die. ", "Advance", JOptionPane.INFORMATION_MESSAGE);

            //If they are in the ranges of after the Hogwarts Express but before the Tardis (or on the tardis).
            if (player[0].getCol() >= 32 && player[0].getCol() <= 5) {
                JOptionPane.showMessageDialog(pane, "You will advance to the Tardis.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                if (player[0].getCol() == 5) 
                {
                    JOptionPane.showMessageDialog(pane, "You are already on this square. Please press 'Go, Com Pewter' when you want the Com Pewter to go", "You're already there.", JOptionPane.INFORMATION_MESSAGE);
                } //end of if statement

                if (player[0].getCol() >= 32) 
                {
                    JOptionPane.showMessageDialog(pane, "You have passed the Start Square - we will provide you with 200 galleons now.", "Passed Start Square", JOptionPane.INFORMATION_MESSAGE);
                } //end of if statement

                player[0].setCol(5);

                if (square[player[0].getCol()].getOwned() == false) 
                {
                    int answer = JOptionPane.showConfirmDialog(pane, "You have landed on: " + square[player[0].getCol()].getName() + ". \n Would you like to purchase it? ", "You've landed!", JOptionPane.YES_NO_OPTION);

                    if (answer == 0) {
                        square[player[0].getCol()].setOwner("User");
                        square[player[0].getCol()].setOwned(true);
                        
                        
                        square[player[0].getCol()].setRent((int) (int) square[player[0].getCol()].getNumber() * (int) square[player[0].getCol()].getRent());
                        
                        player[0].setG(player[0].getG() - square[player[0].getCol()].getNumber());
                        JOptionPane.showMessageDialog(pane, "We have taken out: " + square[player[0].getCol()].getBuy() + " from your Gringotts Account. \n You now have: " + player[0].getG() + " galleons.", "Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                    } //end of if statement
                    else 
                    {
                        JOptionPane.showMessageDialog(pane, "We hope you will consider purchasing the property in the future! \n You now have: " + player[0].getG() + " galleons", "Property", JOptionPane.INFORMATION_MESSAGE);
                    } //end of else statement

                } //end of if statement
                else if (square[player[0].getCol()].getOwned() == true && square[player[0].getCol()].getOwner().equals("Com Pewter")) 
                {
                    JOptionPane.showMessageDialog(pane, "You landed on the Com Pewter's Property: " + square[player[0].getCol()].getName() + "\n Please press okay to roll the die.", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                    if (square[player[0].getCol()].getNumber() <= 3) 
                    {
                        square[player[0].getCol()].number();
                        
                        square[player[0].getCol()].setRent(square[player[0].getCol()].getRent() * square[player[0].getCol()].getNumber());
                    } //end of if statement

                    //JOptionPane.showMessageDialog(pane, "", "", "");
                    int totalDie = (int) ((Math.random() * 12) + 1);

                    JOptionPane.showMessageDialog(pane, "The result of your die is: " + totalDie + " . \n You must pay the Com Pewter: " + (int) (totalDie * 10) + " . \n ", "Com Pewter: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                    totalDie *= 10;
                    
                    player[0].setG(player[0].getG() - totalDie);
                    

                    player[1].setG(player[1].getG() + totalDie);

                } //end of if statement
                else if (square[player[0].getCol()].getOwned() && square[player[0].getCol()].getOwner().equals("User")) 
                {
                    JOptionPane.showMessageDialog(pane, "You have landed on"+   square[player[0].getCol()].getName() +", which is your property.\n Please press the button 'Go, Com Pewter' when you want the Com Pewter to go.", "Your own property!", JOptionPane.INFORMATION_MESSAGE);
                } //end of else if statement

            } //end of if statement
            /////////////////////////////////////////////////////////////TAAAXI////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //If they are after the tardis; and on or before the taxi.
            else if (player[0].getCol() >= 6 && player[0].getCol() <= 14) 
            {
                if (player[0].getCol() == 14) 
                {
                    JOptionPane.showMessageDialog(pane, "You are already on this square. Please press 'Go, Com Pewter' when you want the Com Pewter to go", "You're already there.", JOptionPane.INFORMATION_MESSAGE);
                } //end of if statement

                JOptionPane.showMessageDialog(pane, "You will advance to the Taxi.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                player[0].setCol(14);

                if (square[player[0].getCol()].getOwned() == false) {
                    int answer = JOptionPane.showConfirmDialog(pane, "You have landed on: " + square[player[0].getCol()].getName() + ". \n Would you like to purchase it? ", "You've landed!", JOptionPane.YES_NO_OPTION);

                    if (answer == 0) 
                    {
                        square[player[0].getCol()].getOwner().equals( "User");
                        square[player[0].getCol()].setOwned(true);
                        //square[player[0].getCol()].number++;

                        player[0].setG(player[0].getG() - square[player[0].getCol()].getBuy());
                        JOptionPane.showMessageDialog(pane, "We have taken out: " + square[player[0].getCol()].getBuy() + " from your Gringotts Account. \n You now have: " + player[0].getG() + " galleons.", "Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                    } //end of if statement
                    else {
                        JOptionPane.showMessageDialog(pane, "We hope you will consider purchasing the property in the future! \n You now have: " + player[0].getG() + " galleons", "Property", JOptionPane.INFORMATION_MESSAGE);
                    } //end of else statement

                } //end of if statement
                //if the Com Pewter owns the property.
                else if (square[player[0].getCol()].getOwned() == true && square[player[0].getCol()].getOwner().equals("Com Pewter")) 
                {
                    JOptionPane.showMessageDialog(pane, "You landed on the Com Pewter's Property. \n Please press okay to roll the die.", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                    int totalDie = (int) ((Math.random() * 12) + 1);

                    JOptionPane.showMessageDialog(pane, "The result of your die is: " + totalDie + " . \n You must pay the Com Pewter: " + (int) (totalDie * 10) + " . \n ", "Com Pewter: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                    totalDie *= 10;
                    
                    player[0].setG(player[0].getG()-totalDie);
                    
                    player[1].setG(player[1].getG()+totalDie);

                    if (square[player[0].getCol()].getNumber() <= 3) 
                    {
                        square[player[0].getCol()].number();
                    } //end of if statement to increase number. 

                } //end of if statement
                //if they own the property.
                else if (square[player[0].getCol()].getOwned() && square[player[0].getCol()].getOwner().equals("User")) {
                    JOptionPane.showMessageDialog(pane, "You have landed on your own property. Please press the button 'Go, Com Pewter' when you want the Com Pewter to go.", "Your own property!", JOptionPane.INFORMATION_MESSAGE);
                } //end of else if statement

            } //end of else if statement
            //////////////////////////////////////////////////////////WARDROBE////////////////////////////////////////
            //If they land after the tardis and or before/on the wardrobe.
            else if (player[0].getCol() >= 14 && player[0].getCol() <= 22) {
                if (player[0].getCol() == 22) {
                    JOptionPane.showMessageDialog(pane, "You are already on this square. Please press 'Go, Com Pewter' when you want the Com Pewter to go", "You're already there.", JOptionPane.INFORMATION_MESSAGE);
                } //end of if statement

                JOptionPane.showMessageDialog(pane, "You will advance to the Wardrobe.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                player[0].setCol(22);

                if (square[player[0].getCol()].getOwned() == false) {
                    int answer = JOptionPane.showConfirmDialog(pane, "You have landed on: " + square[player[0].getCol()].getName() + ". \n Would you like to purchase it? ", "You've landed!", JOptionPane.YES_NO_OPTION);

                    if (answer == 0) 
                    {
                        square[player[0].getCol()].setOwner("User");
                        square[player[0].getCol()].setOwned(true);
                        //square[player[0].getCol()].number++;
                        
                        temp = player[0].getG()-square[player[0].getCol()].getBuy();
                        player[0].setG(temp);
                        JOptionPane.showMessageDialog(pane, "We have taken out: " + square[player[0].getCol()].getBuy() + " from your Gringotts Account. \n You now have: " + player[0].getG() + " galleons.", "Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                    } //end of if statement
                    else {
                        JOptionPane.showMessageDialog(pane, "We hope you will consider purchasing the property in the future! \n You now have: " + player[0].getG() + " galleons", "Property", JOptionPane.INFORMATION_MESSAGE);
                    } //end of else statement

                } //end of if statement
                //if the Com Pewter owns the property.
                else if (square[player[0].getCol()].getOwned() == true && square[player[0].getCol()].getOwner().equals("Com Pewter")) {
                    JOptionPane.showMessageDialog(pane, "You landed on the Com Pewter's Property. \n Please press okay to roll the die.", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                    int totalDie = (int) ((Math.random() * 12) + 1);

                    JOptionPane.showMessageDialog(pane, "The result of your die is: " + totalDie + " . \n You must pay the Com Pewter: " + (int) (totalDie * 10) + " . \n ", "Com Pewter: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                    totalDie *= 10;
                    
                    player[0].setG(player[0].getG()-totalDie);
                    
                    player[1].setG(player[0].getG()+totalDie);

                    if (square[player[0].getCol()].getNumber() <= 3) 
                    {
                        square[player[0].getCol()].number();
                    }

                } //end of if statement
                //if they own the property.
                else if (square[player[0].getCol()].getOwned() && square[player[0].getCol()].getOwner().equals("User")) 
                {
                    JOptionPane.showMessageDialog(pane, "You have landed on your own property. Please press the button 'Go, Com Pewter' when you want the Com Pewter to go.", "Your own property!", JOptionPane.INFORMATION_MESSAGE);
                } //end of else if statement

            } //end of else if statement
            //////////////////////////////////////////////////////////Hogwarts Express////////////////////////////////////////
            //If they land after the tardis and or before/on the wardrobe.
            else if (player[0].getCol() >= 22 && player[0].getCol() <= 31) {
                if (player[0].getCol() == 31) {
                    JOptionPane.showMessageDialog(pane, "You are already on this square. Please press 'Go, Com Pewter' when you want the Com Pewter to go", "You're already there.", JOptionPane.INFORMATION_MESSAGE);
                } //end of if statement

                JOptionPane.showMessageDialog(pane, "You will advance to the Hogwarts Express.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                player[0].setCol(31);

                if (square[player[0].getCol()].getOwned() == false) {
                    int answer = JOptionPane.showConfirmDialog(pane, "You have landed on: " + square[player[0].getCol()].getName() + ". \n Would you like to purchase it? ", "You've landed!", JOptionPane.YES_NO_OPTION);

                    if (answer == 0) 
                    {
                        square[player[0].getCol()].setOwner("User");
                        square[player[0].getCol()].setOwned(true);
                        //square[player[0].getCol()].number++;
                        player[0].setG(player[0].getG() - square[player[0].getCol()].getBuy());
                        JOptionPane.showMessageDialog(pane, "We have taken out: " + square[player[0].getCol()].getBuy() + " from your Gringotts Account. \n You now have: " + player[0].getG() + " galleons.", "Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                    } //end of if statement
                    else {
                        JOptionPane.showMessageDialog(pane, "We hope you will consider purchasing the property in the future! \n You now have: " + player[0].getG() + " galleons", "Property", JOptionPane.INFORMATION_MESSAGE);
                    } //end of else statement

                } //end of if statement
                //if the Com Pewter owns the property.
                else if (square[player[0].getCol()].getOwned() == true && square[player[0].getCol()].getOwner().equals("Com Pewter")) {
                    JOptionPane.showMessageDialog(pane, "You landed on the Com Pewter's Property. \n Please press okay to roll the die.", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                    int totalDie = (int) ((Math.random() * 12) + 1);

                    JOptionPane.showMessageDialog(pane, "The result of your die is: " + totalDie + " . \n You must pay the Com Pewter: " + (int) (totalDie * 10) + " . \n ", "Com Pewter: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                    totalDie *= 10;
                    
                    player[0].setG(player[0].getG() - totalDie);
                    
                    player[1].setG(player[1].getG() + totalDie);

                    if (square[player[0].getCol()].getNumber() <= 3) {
                        square[player[0].getCol()].number();
                    }

                } //end of if statement
                //if they own the property.
                else if (square[player[0].getCol()].getOwned() && square[player[0].getCol()].getOwner().equals("User")) {
                    JOptionPane.showMessageDialog(pane, "You have landed on your own property. Please press the button 'Go, Com Pewter' when you want the Com Pewter to go.", "Your own property!", JOptionPane.INFORMATION_MESSAGE);
                } //end of else if statement

            } //end of else if statement

        } //end of if statement of x = 8 
        
        else if (x == 12)
        {
            JOptionPane.showMessageDialog(pane, "", "", JOptionPane.INFORMATION_MESSAGE); 
            
            player[0].setG(player[0].getG()+200);
        } //end of else if statement 
        
        else if (x == 13)
        {
            JOptionPane.showMessageDialog(pane, "The ood, the red-eyed ood. \n 'Would you like some tea?' he says in a monotonic voice with his ball lighting up. \n Fortunately, you get out of this ordeal as the 'virus' doesn't actually do anything...\n this time...", "The Ood", JOptionPane.INFORMATION_MESSAGE);
            
        } //end of else if statment for x = 12 
        
        else if (x == 14)
        {
             JOptionPane.showMessageDialog(pane, "Sometimes goes wrong with the Ood that is serving you, so much for you being happy as long as your ood is happy. \n It's rather clear that your ood is not happy...\n\n Lose 200 galleons - as you're faced with this insane, scary ood. ", "The dark ood, the virus ood, the wrong ood. ", JOptionPane.INFORMATION_MESSAGE);
             
             player[0].setG(player[0].getG()-200);
        } //end of else if statement for x = 13     
        
        else if (x == 15)
        {
            JOptionPane.showMessageDialog(pane, "It seems that you are needed at Buckingham Palace to aid Sherlock in his amazing discoveries.\n \n Gain 20 galleons for your work. ", "GOING ON AN ADVENTURE WITH SHERLOCK HOLMES", JOptionPane.INFORMATION_MESSAGE);
            
            player[0].setG(player[0].getG()-20);
        } //end of else if statement for x = 15

        //Shows no matter what happens 
        JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " + player[0].getG() + ". Your opponent, the Com Pewter, has: " + player[1].getG() + " .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);

    }//end of roomOfRequirement method

    /****************************************************************************************************************************************************************************
     * Description of Method: Determines whether the user has pressed and released anything on the gooey.
     * Parameters: It will receive all buttons, according to .addMouseListener(this);
     * Return Type: Returns void
     * Preconditions: The mouseListener must be imported, must be extended in the class and the button must add the mouseListener to it not to mention this method should contain if statements depending on what would happen if buttons were clicked on.
     * Postconditions: Whatever is in the if statement has been displayed to the screen or the thing has been completed.
     ****************************************************************************************************************************************************************************/

    public void mouseClicked(MouseEvent whatClicked) // use this method to perform actions when mouse is pressed and released
    {
        if (tracing) System.out.println("X Coordinate Clicking: " + whatClicked.getX() + " Y Coordinate Clicking : "+ whatClicked.getY());
            JFrame frame =  new JFrame("Property Listing");

        frame.addWindowListener
        (new WindowAdapter()
          {
            public void windowClosing(WindowEvent e)
            {
                       JOptionPane.showMessageDialog(null, "We hope you had fun in Fandom Monopoly", "So Long, Farewell", JOptionPane.INFORMATION_MESSAGE);
                       System.exit(0);
            } //end of method
          } //end of new window adapter
         ); //end of window listener

            JPanel panel = new JPanel(new BorderLayout());

        if (whatClicked.getSource() == continueWelcome)
        {
                    try
                    {
                        userName = nameHere.getText();
                    }
                    catch (Exception e)
                    {
                        userName = "User";
                    } //end of catch.

                    if (userName.length() == 0)
                    {
                            JOptionPane.showMessageDialog(panel, "Please enter your name in the Text Field", "Missing Entry", JOptionPane.INFORMATION_MESSAGE);
                    }//end of if statement

                    if (userName.length()>0)
                    {
                        menu();
                    }
        } //end of if statement

        if (whatClicked.getSource() == continueMenu)
        {
            game();
        } //end of if statement

//******************************************************************************NORTH SIDE OF BOARD*****************************************************************

        //Start Adventure large
        if (whatClicked.getSource()==(propertyNorth[0]))
        {
            panel.add(propertyNorthLarge[0], BorderLayout.CENTER);

            //Display this button no matter if it's owned or not.
            panel.add(buyPropertiesNorth[0], BorderLayout.SOUTH);

            //frame.setSize(200, 400);
            frame.setContentPane(panel);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //borgin and burkes large
        if (whatClicked.getSource()==(propertyNorth[1]))
        {
            panel.add(propertyNorthLarge[1], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[1].owned == false)
            //{
            panel.add(rent[1], BorderLayout.SOUTH);
            //}

            //frame.setSize(200, 400);
            frame.setContentPane(panel);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Room of Requirement 1 Large
        if (whatClicked.getSource()==(propertyNorth[2]))
        {

            panel.add(propertyNorthLarge[2], BorderLayout.CENTER);

            //This button must be displayed whether owned or not.
            panel.add(buyPropertiesNorth[2], BorderLayout.SOUTH);

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Roland Kerr College Large
        if (whatClicked.getSource()==(propertyNorth[3]))
        {

            panel.add(propertyNorthLarge[3], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[3].owned == false)
            //{
            panel.add(rent[3], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Annual Tax Large
        if (whatClicked.getSource()==(propertyNorth[4]))
        {

            panel.add(propertyNorthLarge[4], BorderLayout.CENTER);

            //This property will always be owned by the bank, but no matter what happens, it must always be displayed.
            panel.add(buyPropertiesNorth[4], BorderLayout.SOUTH);

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Tardis
        if (whatClicked.getSource()==(propertyNorth[5]))
        {
            panel.add(propertyNorthLarge[5], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[3].owned == false)
            //{
            panel.add(rent[5], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Redhaven Large
        if (whatClicked.getSource()==(propertyNorth[6]))
        {
            //JPanel grid = new JPanel(new BorderLayout());

            panel.add(propertyNorthLarge[6]);

            //If the square is owned, do not display the button.
            //if (square[6].owned == false)
            //{
                panel.add(rent[6], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Room of Requirement 2 Large
        if (whatClicked.getSource()==(propertyNorth[7]))
        {

            panel.add(propertyNorthLarge[7], BorderLayout.CENTER);

            //This must display and it will always be owned by the bank. Therefore, no if statement required.
            panel.add(buyPropertiesNorth[7], BorderLayout.SOUTH);

            frame.setContentPane(panel);

            frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Piccadilly Large
        if (whatClicked.getSource()==(propertyNorth[8]))
        {
            panel.add(propertyNorthLarge[8], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[8].owned == false)
            //{
            panel.add(rent[8], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //St. Mungos Large
        if (whatClicked.getSource()==(propertyNorth[9]))
        {
            //JPanel grid = new JPanel(new BorderLayout());

            panel.add(propertyNorthLarge[9], BorderLayout.CENTER);

            //if the user is in jail. Must check for this.
            panel.add(buyPropertiesNorth[9], BorderLayout.SOUTH);

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

//******************************************************************************EAST SIDE OF BOARD*****************************************************************

        //Platform 9 and 3/4.
        if (whatClicked.getSource()==(propertyEast[0]))
        {
            panel.add(propertyEastLarge[0], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[10].owned == false)
            //{
                panel.add(rent[10], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement


        //Speedy's Cafe
        if (whatClicked.getSource()==(propertyEast[1]))
        {
            panel.add(propertyEastLarge[1], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[11].owned == false)
            //{
                panel.add(rent[11], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement


        //Avalon
        if (whatClicked.getSource()==(propertyEast[2]))
        {
            panel.add(propertyEastLarge[2], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
           // if (square[12].owned == false)
            //{
                panel.add(rent[12], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement


        //Isengard
        if (whatClicked.getSource()==(propertyEast[3]))
        {
            panel.add(propertyEastLarge[3], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[13].owned == false)
            //{
                panel.add(rent[13], BorderLayout.SOUTH);


            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement


        //Resemble Sherlock and Take a Taxi
        if (whatClicked.getSource()==(propertyEast[4]))
        {
            panel.add(propertyEastLarge[4], BorderLayout.CENTER);

                panel.add(rent[14], BorderLayout.SOUTH);


            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement


        //Grimmauld Place
        if (whatClicked.getSource()==(propertyEast[5]))
        {
            panel.add(propertyEastLarge[5], BorderLayout.CENTER);

            //If the square is owned, do not display the button.
            //if (square[15].owned == false)
            //{
                panel.add(rent[15], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement


        //Room of Requirement
        if (whatClicked.getSource()==(propertyEast[6]))
        {
            panel.add(propertyEastLarge[6], BorderLayout.CENTER);

            //No if statement required here because the button must always be visible whether owned or not. But on that note, it will always be owned by the bank anyways.
                panel.add(rent[16], BorderLayout.SOUTH);


            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Quidditch Pitch
        if (whatClicked.getSource()==(propertyEast[7]))
        {
            panel.add(propertyEastLarge[7], BorderLayout.CENTER);

            //if (square[17].owned == false)
            //{
                panel.add(rent[17], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement
//******************************************************************************SOUTH SIDE OF BOARD*****************************************************************

        //Going to St. Mungos.
        if (whatClicked.getSource()==(propertySouth[0]))
        {
            panel.add(propertySouthLarge[0], BorderLayout.CENTER);

            panel.add(rent[27], BorderLayout.SOUTH);

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Ministry of Magic
        if (whatClicked.getSource()==(propertySouth[1]))
        {
            panel.add(propertySouthLarge[1], BorderLayout.CENTER);

            //if (square[26].owned == false)
            //{
                panel.add(rent[26], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Beaversdam
        if (whatClicked.getSource()==(propertySouth[2]))
        {
            panel.add(propertySouthLarge[2], BorderLayout.CENTER);

            //if (square[25].owned == false)
            //{
                panel.add(rent[25], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Northumberland Street
        if (whatClicked.getSource()==(propertySouth[3]))
        {
            panel.add(propertySouthLarge[3], BorderLayout.CENTER);

            //if (square[24].owned == false)
            //{
                panel.add(rent[24], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //221 B Baker Street
        if (whatClicked.getSource()==(propertySouth[4]))
        {
            panel.add(propertySouthLarge[4], BorderLayout.CENTER);

            //if (square[23].owned == false)
            //{
                panel.add(rent[23], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Wardrobe
        if (whatClicked.getSource()==(propertySouth[5]))
        {
            panel.add(propertySouthLarge[5], BorderLayout.CENTER);

            //if (square[22].owned == false)
            //{
                panel.add(rent[22], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Aslan's Country
        if (whatClicked.getSource()==(propertySouth[6]))
        {
            panel.add(propertySouthLarge[6], BorderLayout.CENTER);

            //if (square[21].owned == false)
            //{
                panel.add(rent[21], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Room of Requirement
        if (whatClicked.getSource()==(propertySouth[7]))
        {
            panel.add(propertySouthLarge[7], BorderLayout.CENTER);

            if (square[20].getOwned() == true)
            {
                panel.add(rent[20], BorderLayout.SOUTH);
            }

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Hagrid's Hut
        if (whatClicked.getSource()==(propertySouth[8]))
        {
            panel.add(propertySouthLarge[8], BorderLayout.CENTER);

            //if (square[19].owned == false)
            //{
                panel.add(rent[19], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Tavern
        if (whatClicked.getSource()==(propertySouth[9]))
        {
            panel.add(propertySouthLarge[9], BorderLayout.CENTER);

            if (square[18].getOwned() == true)
            {
                panel.add(rent[18], BorderLayout.SOUTH);
            }

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

//******************************************************************************WEST SIDE OF BOARD*****************************************************************

        //Hogwarts
        if (whatClicked.getSource()==(propertyWest[0]))
        {
            panel.add(propertyWestLarge[0], BorderLayout.CENTER);

            //if (square[35].owned == false)
            //{
                panel.add(rent[35], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //The Citadel
        if (whatClicked.getSource()==(propertyWest[1]))
        {
            panel.add(propertyWestLarge[1], BorderLayout.CENTER);

            //if (square[34].owned == false)
            //{
                panel.add(rent[34], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Room of Requirement
        if (whatClicked.getSource()==(propertyWest[2]))
        {
            panel.add(propertyWestLarge[2], BorderLayout.CENTER);

            //if (square[33].owned == true)
            //{
                panel.add(rent[33], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //The Ealdor
        if (whatClicked.getSource()==(propertyWest[3]))
        {
            panel.add(propertyWestLarge[3], BorderLayout.CENTER);

            //if (square[32].owned == false)
            //{
                panel.add(rent[32], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Hogwarts Express
        if (whatClicked.getSource()==(propertyWest[4]))
        {
            panel.add(propertyWestLarge[4], BorderLayout.CENTER);

            //if (square[31].owned == false)
            //{
                panel.add(rent[31], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //Room of Requirement
        if (whatClicked.getSource()==(propertyWest[5]))
        {
            panel.add(propertyWestLarge[5], BorderLayout.CENTER);

            if (square[30].getOwned() == true)
            {
                panel.add(rent[30], BorderLayout.SOUTH);
            }

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

        //bagshot row
        if (whatClicked.getSource()==(propertyWest[6]))
        {
            panel.add(propertyWestLarge[6], BorderLayout.CENTER);

            //if (square[29].getOwned() == false)
            //{
                panel.add(rent[29], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);
        } //end of if statement

        //mordor
        if (whatClicked.getSource()==(propertyWest[7]))
        {
            panel.add(propertyWestLarge[7], BorderLayout.CENTER);

            //if (square[28].owned == false)
            //{
                panel.add(rent[28], BorderLayout.SOUTH);
            //}

            frame.setContentPane(panel);

            //frame.setSize(200, 400);
            frame.setVisible(true);
            frame.setBounds(1200, 250, 200, 400);

        } //end of if statement

//MAIN PART OF THE PROGRAM -> pressing "roll die." 
        if (whatClicked.getSource()==rollDie)
        {
            if (whoseTurn == true)
            {
                    int temp = 0;

                    temp = player[0].getCol();

                    die1 = (int)((Math.random()*6)+1);
                    die2 = (int)((Math.random()*6)+1);
                    JOptionPane.showMessageDialog(pane, "The value of your dice is: " +(die1+die2)+ ".", "Dice", JOptionPane.INFORMATION_MESSAGE);

                    //player[0].col = player[0].col + ((die1+die2));
                    
                    player[0].setCol(player[0].getCol() + (die1+die2));

                    if (tracing) System.out.println("Die 1: " +die1+ " Die 2: " +die2);
                    if (tracing) System.out.println("Player[0].column: " +player[0].getCol());
                    //if (tracing) System.out.println("Landed on property: " + square[player[0].getCol() + (die1) + (die2)].getName());

                    /**These if statements:
                     *
                     * if (the player's column number is greater than that of the array)
                     * {
                     * make it equal to the new value.
                     * }
                     *
                     */
                    if ((player[0].getCol())==36)
                    {
                        player[0].setCol(0);
                    } //end of if statement

                    else if ((player[0].getCol()) == 37)
                    {
                        player[0].setCol(1);
                    } //end of if statement

                    else if ((player[0].getCol()) == 38)
                    {
                        player[0].setCol(2);
                    } //end of if statement

                    else if ((player[0].getCol()) == 39)
                    {
                        player[0].setCol(3);
                    } //end of if statement

                    else if ((player[0].getCol()) == 40)
                    {
                        player[0].setCol(4);
                    } //end of else if statement.

                    else if ((player[0].getCol()) == 41)
                    {
                        player[0].setCol(5);
                    } //end of else if statement.

                    else if ((player[0].getCol()) == 42)
                    {
                        player[0].setCol(6);
                    } //end of else if statement

                    else if ((player[0].getCol()) == 43)
                    {
                        player[0].setCol(7);
                    } //end of else if statement.

                    else if ((player[0].getCol()) == 44)
                    {
                        player[0].setCol(8);
                    } //end of else if statement

                    else if ((player[0].getCol()) == 45)
                    {
                        player[0].setCol(9);
                    } //end of else if statement

                    else if ((player[0].getCol()) == 46)
                    {
                        player[0].setCol(10);
                    } //end of else if statement

                    else if ((player[0].getCol()) == 47)
                    {
                        player[0].setCol(11);
                    } //end of else if statement.

                    else if ((player[0].getCol()) == 48)
                    {
                        player[0].setCol(12);
                    } //end of else if statement

                    if ((player[0].getCol())<temp)
                    {
                        player[0].setG(player[0].getG()+200);
                        //player[0].gringottsAccount+=200;
                        JOptionPane.showMessageDialog(pane, "You have passed the Start Adventure Square. \n You will receive 200 galleons ", "200 Galleons ", JOptionPane.INFORMATION_MESSAGE);
                    } //end of if statement

                    if (tracing) System.out.println("Value of player[0].getCol(): " +player[0].getCol());

                    if (tracing) System.out.println("User at location: " +square[player[0].getCol()].getName()+ " which is owned?:  " +square[player[0].getCol()].getOwned());


                    //if the property is not owned:

                    if ((square[player[0].getCol()].getOwned()) != true)
                    {
                        if (tracing) System.out.println("Inside the square false if statement: " +square[player[0].getCol()].getName()+ " and the ownned is:  " +square[player[0].getCol()].getOwned());
                    int x = JOptionPane.showConfirmDialog(pane, "You have landed on: " +square[player[0].getCol()].getName() + ". \n Would you like to purchase it? ", "You've landed!", JOptionPane.YES_NO_OPTION);
                    if (tracing) System.out.println("Value of X: " +x);
                            if (x ==0)
                            {
                                square[player[0].getCol()].setOwner("User");
                                square[player[0].getCol()].setOwned(true);
                                square[player[0].getCol()].number();
                                
                                temp = (int) (player[0].getG() - square[player[0].getCol()].getBuy());
                                
                                player[0].setG(temp);
//                                player[0].gringottsAccount-=(square[player[0].getCol()].buy);

                                    if (player[0].getG() <=0)
                                        {
                                            int a = JOptionPane.showConfirmDialog(pane, "You have ran out of galleons! \n We hope you enjoyed your time with us! \n Would you like to play again? ", "Bankrupt!", JOptionPane.YES_NO_OPTION);
                                            if (a == 0)
                                            {
                                                initialize();
                                                menu();
                                            } //end of if statement

                                            else
                                            {
                                                JOptionPane.showMessageDialog(pane, "Farewell! Thank you!", "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                                System.exit(0);
                                            } //end of if statement

                                                            JOptionPane.showMessageDialog(pane, "We have taken out: " +square[player[0].getCol()].getBuy()+ " from your Gringotts Account. \n You now have: " + player[0].getG()+ " galleons.", "Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                                        } //end of if statement

                            JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);
                            } //end of if statement

                            else
                            {
                                    JOptionPane.showMessageDialog(pane, "We hope you will consider purchasing the property in the future! \n You now have: " +player[0].getG()+ " galleons", "Property", JOptionPane.INFORMATION_MESSAGE);
                            } //end of else statement

                    } //end of if statement

                    else if (player[0].getCol() == 18)
                    {
                        JOptionPane.showMessageDialog(pane, "Congratulations! \nYou landed on The Tavern! \n You will receive: " +pot+ " galleons.", "Tavern!", JOptionPane.INFORMATION_MESSAGE);
                        player[0].setG(player[0].getG()+pot);

                        JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);

                        pot = 0;
                    } //end of if statement

                    else if (player[0].getCol() == 27)
                    {
                        JOptionPane.showMessageDialog(pane, "You have been sent to St. Mungos. Please press ok to pay 50 galleons to get out", "Go to St. Mungos.", JOptionPane.INFORMATION_MESSAGE);

                        player[0].setG(player[0].getG()-50);

                        pot += 50;

                        JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);

                    } //end of if statement

                    else if (player[0].getCol() == 2 || player[0].getCol() == 7 || player[0].getCol() == 16 || player[0].getCol() == 20 || player[0].getCol() == 33 || player[0].getCol() == 30)
                    {
                        JOptionPane.showMessageDialog(pane, "You landed on Room of Requirement", "Room of Requirement", JOptionPane.INFORMATION_MESSAGE);

                        roomOfRequirement();

                    } //end of if statement room of requirement.

                    else if (square[player[0].getCol()].getOwned() == true && square[player[0].getCol()].getOwner().equals("Com Pewter"))
                    {
                            JOptionPane.showMessageDialog(pane, "You landed on the Com Pewter's property. The rent for this is: " +square[player[0].getCol()].getRent()+ " .", "Com Pewter's Property", JOptionPane.INFORMATION_MESSAGE);

                            player[0].setG((int) (player[0].getG() - square[player[0].getCol()].getRent()));
                           
                            player[1].setG((int) (player[1].getG() - square[player[0].getCol()].getRent()));

                            if (square[player[0].getCol()].getNumber()<=3)
                            square[player[0].getCol()].number();

                            JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);
                    } //end of if statement

                    else if (player[0].getCol() == 4)
                    {
                                JOptionPane.showMessageDialog(pane, "You have landed on the Camelot's Annual Tax. 200 galleons will be added to the pot.", "Camelot's Annual Tax", JOptionPane.INFORMATION_MESSAGE);

                                player[0].setG(player[0].getG()-200);

                                pot+=200;

                                JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);
                    } //end of if statement

                    else
                    {
                        JOptionPane.showMessageDialog(pane, "You landed on: " +square[player[0].getCol()].getName()+ " . ", "Landing", JOptionPane.INFORMATION_MESSAGE);
                    }//end of if statement
                    whoseTurn = false;
                    if (tracing) System.out.println("Value of whoseTurn: " +whoseTurn);
          } //end of if statement


          if (whoseTurn == false) //this is after the user's turn! :)
          {
                JOptionPane.showMessageDialog(pane, "It is now the Com Pewter's turn!", "Com Pewter's Move!", JOptionPane.INFORMATION_MESSAGE);

                                int counter = 0;

                                if (tracing)
                                {
                                  System.out.println("Value of Counter before loop: " +counter);
                                  System.out.println("Location before loop: " +player[1].getCol());
                                  System.out.println("Gringotts Account before loop: " +player[1].getG());
                                } //end of if statement

                                //for (; counter<player[0].turn; counter--) //if the user is stuck in jail, let the computer go two more times.
                                //{
                                    if (tracing)
                                    {
                                      System.out.println("Value of Counter beginning loop: " +counter);
                                      System.out.println("Location beginning loop: " +player[1].getCol());
                                      System.out.println("Gringotts Account beginning loop: " +player[1].getG());
                                    } //end of if statement
                                    int dieComp1 = (int)((Math.random()*6)+1);
                                    int dieComp2 = (int)((Math.random()*6)+1);

                                    int totalComDie = dieComp1 + dieComp2;

                                        player[1].setCol(player[1].getCol() + totalComDie);
                                        //player[1].col+=totalComDie;


                                        /**These if statements:
                                         *
                                         * if (the player's column number is greater than that of the array)
                                         * {
                                         * make it equal to the new value.
                                         * }
                                         *
                                         */
                                        if ((player[1].getCol())==36)
                                        {
                                            player[1].setCol(0);
                                        } //end of if statement

                                        else if ((player[1].getCol()) == 37)
                                        {
                                            player[1].setCol(1);
                                        } //end of if statement

                                        else if ((player[1].getCol()) == 38)
                                        {
                                            player[1].setCol(2);
                                        } //end of if statement

                                        else if ((player[1].getCol()) == 39)
                                        {
                                            player[1].setCol(3);
                                        } //end of if statement

                                        else if ((player[1].getCol()) == 40)
                                        {
                                            player[1].setCol(4);
                                        } //end of else if statement.

                                        else if ((player[1].getCol()) == 41)
                                        {
                                            player[1].setCol(5);
                                        } //end of else if statement.

                                        else if ((player[1].getCol()) == 42)
                                        {
                                            player[1].setCol(6);
                                        } //end of else if statement

                                        else if ((player[1].getCol()) == 43)
                                        {
                                            player[1].setCol(7);
                                        } //end of else if statement.

                                        else if ((player[1].getCol()) == 44)
                                        {
                                            player[1].setCol(8);
                                        } //end of else if statement

                                        else if ((player[1].getCol()) == 45)
                                        {
                                            player[1].setCol(9);
                                        } //end of else if statement

                                        else if ((player[1].getCol()) == 46)
                                        {
                                            player[1].setCol(10);
                                        } //end of else if statement

                                        else if ((player[1].getCol()) == 47)
                                        {
                                            player[1].setCol(11);
                                        } //end of else if statement.

                                        else if ((player[1].getCol()) == 48)
                                        {
                                            player[1].setCol(12);
                                        } //end of else if statement

                                        JOptionPane.showMessageDialog(pane, "The Com Pewter has rolled: " +totalComDie+ " .", "Com Pewter's Roll", JOptionPane.INFORMATION_MESSAGE);

                                        if ((player[1].getCol())<totalComDie)
                                        {
                                            player[1].setG(player[1].getG()+200);
                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has passed the Start Adventure Square.", "200 Galleons ", JOptionPane.INFORMATION_MESSAGE);
                                        } //end of if statement

                                        if (tracing)
                                        {
                                            System.out.println("Value of Player[1].getCol() is: " +player[1].getCol());
                                            System.out.println("Square on: " +square[player[1].getCol()].getName()+ " .");
                                        } //end of if statement

                                        //If the com pewter landed on the user's property.
                                        if (square[player[1].getCol()].getOwned() == true && square[player[1].getCol()].getOwner().equals("User"))
                                        {
                                            int p;

                                            p = square[player[1].getCol()].getRent();

                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on your property: " +square[player[1].getCol()].getName()+ " . \n The Com Pewter will give you: " +((square[player[1].getCol()].getRent()) * (square[player[1].getCol()].getNumber())), "The Com Pewter landed!", JOptionPane.INFORMATION_MESSAGE);
                                            
                                            player[1].setG(player[1].getG() - (square[player[1].getCol()].getRent() * square[player[1].getCol()].getNumber()));

                                            if (square[player[1].getCol()].getNumber()<=3)
                                            {
                                                square[player[1].getCol()].number();//this means that the com pewter has landed on this property __ number of times. But it will only ever multiply by 4.
                                            } //end of if statement
                                            
                                            square[player[1].getCol()].setRent((int) square[player[1].getCol()].getRent() * square[player[1].getCol()].getNumber());
                                            //square[player[1].getCol()].rent*=square[player[1].getCol()].number;

                                            JOptionPane.showMessageDialog(pane, "The Original Rent is: " +p+ " galleons, it has increased to: " + square[player[1].getCol()].getRent()+ " galleons.", "", JOptionPane.INFORMATION_MESSAGE);

                                        } //end of if statement

                                        //If the Com Pewter lands on the Room of Requirement
                                        else if (player[1].getCol() == 2 || player[1].getCol() == 7 || player[1].getCol() == 16 || player[1].getCol() == 20 || player[1].getCol() == 30 || player[1].getCol() == 33)
                                        {
                                                                 int x = (int) ((Math.random()*9)+1);

                                                        if (x == 0)
                                                        {
                                                            player[1].setCol(0);
                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has advanced to go and has received 200 galleons", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                        } //end of if statement

                                                        else if (x == 1)
                                                        {
                                                            //player[0].gringottsAccount+=200;
                                                            JOptionPane.showMessageDialog(pane, "There is a gringrotts error, in the Com Pewter's favour! \n Com Pewter is to collect 200 galleons courtesy of Gringotts. ", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                            player[1].setG(player[1].getG()+200);
                                                        } //end of if statement

                                                        else if (x==2)
                                                        {
                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter must go to Odin's Land to purchase/interact grain because Camelot is out of food. \n Pay 100 galleons", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                            player[1].setG(player[1].getG()+100);
                                                        } //end of if statement

                                                        else if (x ==3)
                                                        {
                                                           int y = (int) ((Math.random()*10000)+1);

                                                           if ((y%2)==0)
                                                           {
                                                               JOptionPane.showMessageDialog(pane, "The Com Pewter's daughter, Com Panion is falling ill. \n Com Pewter tries to code, but nothing works. \n Com Pewter visits Gaius in Camelot for help. \n Gaius knows about his true identity. \n He is the only one in Camelot who knows this. \n Gaius fixes Com Panion very quickly. \n However, Gaius asks for recompensation of 50 galleons for his efforts. \n Com Pewter refuses to pay. \n Gaius goes to the King; and the Com Pewter loses half of its' bank account.", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);

                                                               player[1].setG(player[1].getG()/2);

                                                           } //end of if statement

                                                           else
                                                           {
                                                               JOptionPane.showMessageDialog(pane, "The Com Pewter's daughter, Com Panion is falling ill. \n Com Pewter tries to code, but nothing works. \n Com Pewter visits Gaius in Camelot for help. \n Gaius knows about his true identity. \n He is the only one in Camelot who knows this. \n Gaius fixes Com Panion very quickly. \n However, Gaius asks for recompensation of 50 galleons for his efforts. \n Com Pewter attempts to use a virus to attack, but Gaius sees it coming. \n Com Pewter pays 50 galleons; but gets a refund after the goblin has been taken out of Gaius", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);

                                                               pot+=50;

                                                           } //end of if statement

                                                        } //end of if statement

                                                        else if (x==4)
                                                        {
                                                            player[1].setCol(9);
                                                            JOptionPane.showMessageDialog(pane, "Grundy Gollem tries to put a virus into Com Pewter.\n Com Pewter, expecting this, shields it with a code that Com Passion wrote. \n However, the program crashes, and the gooey that was aimed at Grundy flies backwards. \n Com Pewter must stay at St. Mungos for at least 3 turns or pay 50 galleons bail.", "Com Pewter's move", JOptionPane.INFORMATION_MESSAGE);
                                                            player[1].setTurn(3);

                                                            JOptionPane.showMessageDialog(pane, "However, the Com Pewter pays 50 galleons to get out immediately. You may now proceed with your turn.", "Sent to St. Mungos", JOptionPane.INFORMATION_MESSAGE);

                                                            player[1].setG(player[1].getG()-50);
                                                            //player[1].gringottsAccount-=50;

                                                            pot += 50;

                                                        } //end of if statement

                                                        else if (x==5)
                                                        {
                                                          JOptionPane.showMessageDialog(pane, "It is the Com Pewter's birthday \n The Com Pewter must collect 20 galleons from each player!", "Com Pewter's Move!", JOptionPane.INFORMATION_MESSAGE);
                                                          player[0].setG(player[0].getG()-20);
                                                          player[1].setG(player[0].getG()+20);

                                                          pot += 40;

                                                        } //end of if statement

                                                        else if (x==6)
                                                        {
                                                            JOptionPane.showMessageDialog(pane, "Com Pewter, Com Panion and you decide to spend money on the Chinese Theatre. \n Yes, that same one that John and his date (plus Sherlock). \n However, Sherlock only has 3 free tickets, no more, no less. \n All other players must pay 50 galleons. \n The Com Pewter, the person who picked up this card must chip in 10 galleons.", "Chinese Theatre", JOptionPane.INFORMATION_MESSAGE);
                                                            player[0].setG(player[0].getG()+10);
                                                            player[1].setG(player[1].getG()-10);
                                                            pot += 60;
                                                        } //end of if statement

                                                        else if (x==7)
                                                        {
                                                            int j  = 0;
                                                            int index = 0, a = 0 ;
                                                            int pay = 0;

                                                            int b = (int)((Math.random()*1000)+1);
                                                            //JOptionPane.showMessageDialog(pane, "Morgana has declared war on Camelot and the Region of Water. \n She is using the Com Pewter to get through to King Arthur. \n The Com Pewter can give her the virus that Grundy gave him; or surrender him and his family.\n The Com Pewter chooses to spit a copy of the virus in Morgana's Face", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);

                                                            int p = (int)((Math.random()*10)+1);

                                                            if ((p%2) == 1)
                                                            {
                                                               JOptionPane.showMessageDialog(pane, "Congratulations. The Com Pewter gains 200 galleons for standing up for yourself.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);

                                                               player[1].setG(player[1].getG()+200);

                                                            } //end if statement

                                                            else
                                                            {
                                                                JOptionPane.showMessageDialog(pane, "Sorry...bad decision. \n The Com Pewter lose 200 galleons", "Bad choice...", JOptionPane.INFORMATION_MESSAGE);

                                                                player[1].setG(player[1].getG()-200);
                                                            } //end of if statement (b)

                                                        } //end of if statement

                                                        //************************************************Transportation*

                                                        else if (x == 8)
                                                        {
                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter is currently at: " +square[player[1].getCol()].getName()+ "\n He/She is to move to the nearest transportation. \n  If it is unowned, he/she can buy this from the bank. \n If it is owned, roll the dice a second time and pay the owner 10 times the sum of the die. ", "Advance", JOptionPane.INFORMATION_MESSAGE);


                                                            //If they are in the ranges of after the Hogwarts Express but before the Tardis (or on the tardis).
                                                            if (player[1].getCol()>= 32 &&player[1].getCol()<=5)
                                                            {
                                                                JOptionPane.showMessageDialog(pane, "The Com Pewter will advance to the Tardis.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                                                                if (player[1].getCol() == 5)
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter is already located on this square. You may play now. ", "You're already there.", JOptionPane.INFORMATION_MESSAGE);

                                                                    whoseTurn = true;

                                                                } //end of if statement

                                                                if (player[1].getCol()>=32)
                                                                {
                                                                    player[1].setG(player[1].getG()+200);
                                                                     JOptionPane.showMessageDialog(pane, "The Com Pewter has passed the Start Square - we will provide it with 200 galleons now.", "Passed Start Square", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of if statement

                                                                player[1].setCol(5);

                                                                if (square[player[1].getCol()].getOwned() == false)
                                                                {

                                                                    int answer = (int)((Math.random()*10000)+1);

                                                                    if ((answer%2) ==0)
                                                                    {
                                                                    square[player[1].getCol()].setOwned(true);
                                                                    square[player[1].getCol()].setOwner("Com Pewter");
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has purchased: " +square[player[1].getCol()].getName()+ " .", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                                    
                                                                    player[1].setG((int)player[1].getG() - square[player[1].getCol()].getBuy());
//                                                                    player[1].gringottsAccount-=(square[player[1].getCol()].buy);

                                                                    } //end of if statement

                                                                    else
                                                                    {
                                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase this property. You may proceed with your turn. ", "Com Pewter" , JOptionPane.INFORMATION_MESSAGE);
                                                                    } //end of else statement

                                                                } //end of if statement

                                                                else if (square[player[1].getCol()].getOwned() == true && square[player[1].getCol()].getOwner().equals("User"))
                                                                {
                                                                    //JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on your property", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                                                                    if (square[player[1].getCol()].getNumber()<=3)
                                                                    {
                                                                        square[player[1].getCol()].number();
                                                                        
                                                                        square[player[1].getCol()].setRent(square[player[1].getCol()].getRent() * square[player[1].getCol()].getNumber());
                                                                        //square[player[1].getCol()].rent *= square[player[1].getCol()].number;
                                                                    } //end of if statement

                                                                    //JOptionPane.showMessageDialog(pane, "", "", "");

                                                                    int totalDie = (int)((Math.random()*12)+1);

                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on your property. \n The result of the die is: " +totalDie+ " . \n The computer will pay you: : " +(int)(totalDie*10)+ " . \n ", "User: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                                                                    totalDie*=10;

                                                                    player[1].setG(player[1].getG()-totalDie);
                                                                    player[0].setG(player[1].getG()+totalDie);

                                                                } //end of if statement

                                                                else if (square[player[1].getCol()].getOwned() && square[player[1].getCol()].getOwner().equals("Com Pewter"))
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter landed on it's own property. You may proceed. ", "Your own property!", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of else if statement

                                                                else
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase the property. ", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of if statement

                                                            } //end of if statement

                                                            /////////////////////////////////////////////////////////////TAAAXI////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                                            //If they are after the tardis; and on or before the taxi.
                                                            else if (player[1].getCol()>=6 && player[1].getCol()<=14)
                                                            {
                                                                if (player[1].getCol() == 14)
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter is already on this square. \n You may proceed with your turn.", "Com Pewter's already there.", JOptionPane.INFORMATION_MESSAGE);

                                                                    //boolean or an int variable? increase to make sure the user can't go when it isn't their turn?
                                                                } //end of if statement

                                                                JOptionPane.showMessageDialog(pane, "The Com Pewter will advance to the taxi.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                                                                player[1].setCol(14);

                                                                if (square[player[1].getCol()].getOwned() == false)
                                                                {
                                                                    int answer = (int)((Math.random()*10000)+1);

                                                                        if ((answer%2) == 0)
                                                                        {
                                                                            square[player[1].getCol()].setOwner("Com Pewter");
                                                                            square[player[1].getCol()].setOwned(true);
                                                                            //square[player[1].getCol()].number++;
                                                                            
                                                                            player[1].setG((int) player[1].getG() - square[player[1].getCol()].getBuy());
                                                                            JOptionPane.showMessageDialog(pane, "Com Pewter has chosen to purchase this property. \n We have taken out: " +square[player[1].getCol()].getBuy()+ " from the Com Pewter's Gringotts Account. \n The Com Pewter now has: " + player[1].getG()+ " galleons.", "Com Pewter's Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                                                                        } //end of if statement

                                                                        else
                                                                        {
                                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase this property. You may proceed with your turn. ", "Com Pewter" , JOptionPane.INFORMATION_MESSAGE);
                                                                        } //end of if statement

                                                                } //end of if statement

                                                                //if the user owns the property.
                                                                else if (square[player[1].getCol()].getOwned() == true && square[player[1].getCol()].getOwner().equals("Com Pewter"))
                                                                {
                                                                    //JOptionPane.showMessageDialog(pane, "You landed on the Com Pewter's Property. \n Please press okay to roll the die.", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                                                                    int totalDie = (int)((Math.random()*12)+1);

                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on your property. \n The result of the die is: " +totalDie+ " . \n The computer will pay you: : " +(int)(totalDie*10)+ " . \n ", "User: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                                                                    totalDie*=10;

                                                                    player[1].setG(player[1].getG()-totalDie);
                                                                    player[0].setG(player[0].getG()+totalDie);

                                                                    if (square[player[1].getCol()].getNumber()<=3)
                                                                        square[player[1].getCol()].number();

                                                                } //end of if statement

                                                                //if they own the property.
                                                                else if (square[player[1].getCol()].getOwned() && square[player[1].getCol()].getOwner().equals("User"))
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter landed on it's own property. You may proceed. ", "Own property!", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of else if statement

                                                                else
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase the property. ", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of if statement

                                                            } //end of else if statement



                                                            //////////////////////////////////////////////////////////WARDROBE////////////////////////////////////////

                                                            //If they land after the tardis and or before/on the wardrobe.
                                                            else if (player[1].getCol()>=14 && player[1].getCol()<=22)
                                                            {
                                                                if (player[1].getCol() == 22)
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter is already on this square. You may proceed with your turn. ", "Already there.", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of if statement

                                                                JOptionPane.showMessageDialog(pane, "The Com Pewter will advance to the Wardrobe.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                                                                player[1].setCol(22);

                                                                if (square[player[1].getCol()].getOwned() == false)
                                                                {
                                                                    int answer = (int)((Math.random()*10000)+1);

                                                                        if ((answer%2) == 0)
                                                                        {
                                                                            square[player[1].getCol()].setOwner("User");
                                                                            square[player[1].getCol()].setOwned(true);
                                                                            //square[player[1].getCol()].number++;
                                                                            
                                                                            player[1].setG((int) player[1].getG() - square[player[1].getCol()].getBuy());
                                                                            JOptionPane.showMessageDialog(pane, "Com Pewter has chosen to purchase this property. \n We have taken out: " +square[player[1].getCol()].getBuy()+ " from the Com Pewter's Gringotts Account. \n They now have: " + player[1].getG()+ " galleons.", "Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                                                                        } //end of if statement

                                                                        else
                                                                        {
                                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase this property. You may proceed with your turn. ", "Com Pewter" , JOptionPane.INFORMATION_MESSAGE);
                                                                        } //end of if statement

                                                                } //end of if statement

                                                                //if the user owns the property.
                                                                else if (square[player[0].getCol()].getOwned() == true && square[player[0].getCol()].getOwner().equals("User"))
                                                                {
                                                                    //JOptionPane.showMessageDialog(pane, "Com Pewter landed on your property. \n Please press okay to roll the die.", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                                                                    int totalDie = (int)((Math.random()*12)+1);

                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on your property. \n The result of the die is: " +totalDie+ " . \n The computer will pay you: : " +(int)(totalDie*10)+ " . \n ", "User: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                                                                    totalDie*=10;

                                                                    player[1].setG(player[1].getG()-totalDie);
                                                                    player[0].setG(player[1].getG()+totalDie);

                                                                    if (square[player[1].getCol()].getNumber()<=3)
                                                                            square[player[1].getCol()].number();

                                                                } //end of if statement

                                                                //if they own the property.
                                                                else if (square[player[1].getCol()].getOwned() && square[player[1].getCol()].getOwner().equals("User"))
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter landed on it's own property. You may proceed. ", "Own property!", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of else if statement

                                                                else
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase the property. ", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of if statement

                                                            } //end of else if statement

                                                            //////////////////////////////////////////////////////////Hogwarts Express////////////////////////////////////////

                                                            //If they land after the tardis and or before/on the wardrobe.
                                                            else if (player[1].getCol()>22 && player[1].getCol()<=31)
                                                            {
                                                                if (player[1].getCol() == 31)
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter is already located on this square. You may proceed with your turn.", "Com Pewter's already there.", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of if statement

                                                                JOptionPane.showMessageDialog(pane, "The Com Pewter will advance to the Hogwarts Express.", "Advance to", JOptionPane.INFORMATION_MESSAGE);

                                                                player[1].setCol(31);

                                                                if (square[player[1].getCol()].getOwned() == false)
                                                                {
                                                                    int answer = (int)((Math.random()*10000)+1);

                                                                        if ((answer%2) == 0)
                                                                        {
                                                                            square[player[1].getCol()].setOwner("User");
                                                                            square[player[1].getCol()].setOwned(true);
                                                                            //square[player[1].getCol()].number++;
                                                                            //player[1].gringottsAccount-=(square[player[1].getCol()].getBuy());
                                                                            player[1].setG(player[1].getG() - square[player[1].getCol()].getBuy());
                                                                            JOptionPane.showMessageDialog(pane, "Com Pewter has chosen to purchase this property \n We have taken out: " +square[player[1].getCol()].getBuy()+ " from your Gringotts Account. \n You now have: " + player[1].getG()+ " galleons.", "Gringotts Account", JOptionPane.INFORMATION_MESSAGE);
                                                                        } //end of if statement

                                                                        else
                                                                        {
                                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase this property. You may proceed with your turn. ", "Com Pewter" , JOptionPane.INFORMATION_MESSAGE);
                                                                        } //end of else statement

                                                                } //end of if statement

                                                                //if the user owns the property.
                                                                else if (square[player[1].getCol()].getOwned() == true && square[player[1].getCol()].getOwner().equals("User"))
                                                                {
                                                                    //JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on your property. It will pay you 200 galleons.", "Transportation", JOptionPane.INFORMATION_MESSAGE);

                                                                    int totalDie = (int)((Math.random()*12)+1);

                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on your property. \n The result of the die is: " +totalDie+ " . \n The computer will pay you: : " +(int)(totalDie*10)+ " . \n ", "User: Thank you!", JOptionPane.INFORMATION_MESSAGE);

                                                                    totalDie*=10;

                                                                    player[1].setG(player[1].getG()-totalDie);
                                                                    player[0].setG(player[0].getG()+totalDie);

                                                                    if (square[player[1].getCol()].getNumber()<=3)
                                                                            square[player[1].getCol()].number();

                                                                } //end of if statement

                                                                //if they own the property.
                                                                else if (square[player[1].getCol()].getOwned() && square[player[1].getCol()].getOwner().equals("Com Pewter"))
                                                                {
                                                                     JOptionPane.showMessageDialog(pane, "The Com Pewter landed on it's own property. You may proceed. ", "Own property!", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of else if statement

                                                                else
                                                                {
                                                                    JOptionPane.showMessageDialog(pane, "The Com Pewter has chosen not to purchase the property. ", "Com Pewter's Move", JOptionPane.INFORMATION_MESSAGE);
                                                                } //end of if statement

                                                            } //end of else if statement

                                                            else
                                                            {
                                                                JOptionPane.showMessageDialog(pane, "The Com Pewter does not wish to purchase the property. You may proceed with your turn", "Go on", JOptionPane.INFORMATION_MESSAGE);
                                                                whoseTurn = true;
                                                            } //end of else statement

                                                        } //end of else if statement (x = 8)

                                                        else
                                                        {
                                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has gone on a hunt for a fez, and happens to find it in the museum that was attacked by the Zygons. \n All players must pay 20 galleons to bargain with them. ", "I wear a fez now, fezzes are cool.", JOptionPane.INFORMATION_MESSAGE);
                                                            player[0].setG(player[0].getG()-20);
                                                            player[1].setG(player[0].getG()-20);
                                                            pot += 40;
                                                        } //end of else statement (x != anything)

                                                        JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);
                                        } //end of if statement

                                        //the program lands on an unowned square
                                        else if (square[player[1].getCol()].getOwned() == false)
                                        {
                                            int x = (int)((Math.random()*10)+1);
                                            if((x%2)==1)//as long as the com pewter has 500 galleons in its' bank account, it will purchase it no matter what.
                                            {
                                                square[player[1].getCol()].setOwned(true);
                                                square[player[1].getCol()].setOwner("Com Pewter");
                                                
                                                player[1].setG((int)player[1].getG() - square[player[1].getCol()].getBuy());
                                                //player[1].gringottsAccount-=square[player[1].getCol()].getBuy();

                                                JOptionPane.showMessageDialog(pane, "The Com Pewter has purchased: " +square[player[1].getCol()].getName()+ " . \n The price of rent is: " +square[player[1].getCol()].getRent()+ " and it will increase after you land on it.", "Com Pewter has spent galleons!", JOptionPane.INFORMATION_MESSAGE);
                                            } //end of if statement

                                            else
                                            {
                                                JOptionPane.showMessageDialog(pane, "The Com Pewter landed on: " +square[player[1].getCol()].getName()+ " but is not purchasing it. \n You may proceed with your turn. ", "Com Pewter: Skip", JOptionPane.INFORMATION_MESSAGE);
                                            } //end of if statement

                                                        JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);

                                        } //end of if statement

                                        //if the computer ends up going to st. mungos (being sent to hospital)
                                        else if (player[1].getCol() == 27)
                                        {
                                            player[1].setTurn(3);
                                            player[1].setTurn(9);

                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has been sent to St. Mungos. However, she/he pays 50 galleons to get out immediately. You may proceed with your turn. ", "Sent to St. Mungos!", JOptionPane.INFORMATION_MESSAGE);
                                            

                                            player[1].setG(player[1].getG() -50);
                                            player[1].setTurn(0);
                                            pot+=50;

                                        } //end of if statement

                                        else if (player[1].getCol() == 18)//if the computer stops at the tavern
                                        {
                                            JOptionPane.showMessageDialog(pane, "Congratulations to the Com Pewter! \n She/he landed on the Taven. The Com Pewter recevies: " +pot + " . The Com Pewter now has: " +player[1].getG()+ "  galleons.", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                                            
                                            player[1].setG(player[1].getG() + pot);

                                            pot = 50;

                                            JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);
                                        } //end of if statement

                                        else if (player[1].getCol() == 4)
                                        {
                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on the Camelot's Annual Tax. 200 galleons will be added to the pot.", "Camelot's Annual Tax", JOptionPane.INFORMATION_MESSAGE);

                                            
                                            player[1].setG(player[1].getG()-200);
                                            pot+=200;

                                            JOptionPane.showMessageDialog(pane, "Your gringotts account now has: " +player[0].getG()+ ". Your opponent, the Com Pewter, has: " +player[1].getG()+" .", "Gringotts Accounts", JOptionPane.INFORMATION_MESSAGE);
                                        } //end of if statement

                                        else if (player[1].getCol() == 9) //if they land on st. mungos
                                        {
                                            JOptionPane.showMessageDialog(pane, "The Com Pewter has landed on St. Mungos. Com Pewter is just visiting so no worries.", "Com Pewter's Landing", JOptionPane.INFORMATION_MESSAGE);
                                        } //end of if statement

                                //} //end of for loop

                                if (tracing)
                                {
                                  System.out.println("Value of Counter after loop: " +counter);
                                  System.out.println("Location after loop: " +player[1].getCol());
                                  System.out.println("Gringotts Account after loop: " +player[1].getG());
                                } //end of if statement
                                whoseTurn = true;

        } //end of if statement whoseTurn.



                    if (player[0].getG() <=0)
                    {
                            int a = JOptionPane.showConfirmDialog(pane, "You have ran out of galleons! \n We hope you enjoyed your time with us! \n Would you like to play again? ", "Bankrupt!", JOptionPane.YES_NO_OPTION);
                            if (a == 0)
                            {
                                initialize();
                                menu();
                            } //end of if statement

                            else
                            {
                                JOptionPane.showMessageDialog(pane, "Farewell! Thank you!", "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                System.exit(0);
                            } //end of if statement
                   } //end of if statement

                        else if (player[1].getG()<=0)
                        {
                            int b = JOptionPane.showConfirmDialog(pane, "The Com Pewter has run out of galleons!\n  We hope you enjoyed your time with us! \n Would you like to play again? " , "Bankrupt!", JOptionPane.YES_NO_OPTION);

                            if ( b == 0)
                            {
                                initialize();
                                menu();
                            } //end of if statement

                            else
                            {
                                JOptionPane.showMessageDialog(pane, "Farewell! Thank you!", "Farewell", JOptionPane.INFORMATION_MESSAGE);
                                System.exit(0);
                            } //end of if statement

                        } //end of if statement

         } //end of roll die if statement.

        if (whatClicked.getSource() == location)
        {
            if (tracing) System.out.println("Inside location whatClicked.");

            JFrame where = new JFrame("Where am I? ");
                where.setBackground(moss);
                where.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel organize = new JPanel(new BorderLayout());
                organize.setBackground(brown);

            JTextArea whereAreYou = new JTextArea("Players' Locations");
                whereAreYou.setForeground(Color.white);
                whereAreYou.setBackground(moss);
                whereAreYou.setFont(titleFont);
                whereAreYou.setEditable(false);

            JPanel locations = new JPanel(new GridLayout(2,2));
                locations.setSize(400, 400);

            JTextArea[] info = new JTextArea[4];

            for (int i = 0; i<info.length; i++)
            {
                if (i == 0)
                {
                    info[i] = new JTextArea(userName);
                    info[i].setFont(titleFont);
                    info[i].setBackground(brown);
                    info[i].setForeground(Color.white);
                    locations.add(info[i]);
                } //end of if statement

                if (i == 1)
                {
                    info[i] = new JTextArea("Com Pewter");
                    info[i].setFont(titleFont);
                    locations.add(info[i]);
                    info[i].setForeground(Color.white); 
                    info[i].setBackground(brown);
                } //end of if statement

                if (i == 2)
                {
                    info [i] = new JTextArea("\n\nLocation: \n\n\n" +square[player[0].getCol()].getName());
                    info[i].setFont(normalFont);
                    //info[i].setLineWrap(true);
                    locations.add(info[i]);
                    info[i].setBackground(moss);
                    info[i].setForeground(Color.white);
                } //end of if statement

                if (i == 3)
                {
                    info[i] = new JTextArea("\n\nLocation: \n\n\n" +square[player[1].getCol()].getName());
                    info[i].setFont(normalFont);
                    locations.add(info[i]);
                    info[i].setBackground(moss);
                    info[i].setForeground(Color.white);
                } //end of if statement
                
                info[i].setEditable(false);
            } //end of for loop.

            organize.add(whereAreYou, BorderLayout.NORTH);
            organize.add(locations, BorderLayout.CENTER);

            where.setContentPane(organize);
            where.setAlwaysOnTop(true);
            where.setVisible(true);
                        where.setSize(800, 400);

        } //end of if statement

    } //end of mouseClicked.

    /****************************************************************************************************************************************************************************
     * Description of Method: Determines whether the user has pressed something on the gooey and if they have, this method will contain if statements mentioning what would happen if it was clicked on.
     * Parameters and Return Type: It will receive all the buttons.
     * Preconditions: The JButtons must exist, the mouseListener must be imported, mouseListener would have to be extended in the class and the mouseListener must be added to this method. Also, if it is sent to this method, this method must contain if statements for what to do if it is clicked on.
     * Postconditions: Whatever is in the if statement has been displayed to the screen or has been performed.
     ****************************************************************************************************************************************************************************/

    public void mousePressed(MouseEvent e) // use this method to perform actions when the mouse button is pressed
    {

    } //end of mousePressed method.

    /****************************************************************************************************************************************************************************
     * Description of Method: Determines whether the user has released something on the gooey and if they have, this method will contain if statements mentioning what would happen if it was clicked on.
     * Parameters and Return Type: It will receive all the buttons (mouseListener must be added to all buttons).
     * Preconditions: The JButtons must exist, the mouseListener must be imported, mouseListener would have to be extended in the class and the mouseListener must be added to this method. Also, if it is sent to this method, this method must contain if statements for what to do if it is clicked on.
     * Postconditions: Whatever is in the if statement has been displayed to the screen or has been performed.
     ****************************************************************************************************************************************************************************/

    public void mouseReleased(MouseEvent e) // use this method to perform actions when the mouse button is released
    {

    } //end of mouseReleased method.

    /****************************************************************************************************************************************************************************
     * Description of Method: Determines whether the user has entered something/a component on the gooey and if they have, this method will contain if statements mentioning what would happen if it was clicked on.
     * Parameters and Return Type: It will receive all the buttons (mouseListener must be added to all buttons).
     * Preconditions: The JButtons must exist, the mouseListener must be imported, mouseListener would have to be extended in the class and the mouseListener must be added to this method. Also, if it is sent to this method, this method must contain if statements for what to do if it is clicked on.
     * Postconditions: Whatever is in the if statement has been displayed to the screen or has been performed.
     ****************************************************************************************************************************************************************************/

    public void mouseEntered(MouseEvent e)  // use this method to perform actions when the mouse enters a component
    {
        /*if (whatClicked.getSource()==start)
        {
        JFrame frame =  new JFrame("Start Your Adventure");

                  frame.addWindowListener
                              (new WindowAdapter()
                                {
                                  public void windowClosing(WindowEvent e)
                                  {
                                    System.exit(0);
                                  }
                                }
                              );

                  JPanel startPanel = new JPanel();
                  JLabel start  = new JLabel(new ImageIcon("BStart"));

                  startPanel.add(start);

                  frame.setSize(500, 500);
                  frame.setVisible(true);
                  frame.setContentPane(startPanel);
        } //end of start if statement
        */


    }//end of mouseEntered method.


    /****************************************************************************************************************************************************************************
     * Description of Method: Determines whether the user has exited something/a component on the gooey and if they have, this method will contain if statements mentioning what would happen if it was clicked on.
     * Parameters and Return Type: It will receive all the buttons - mouseMotionListener must be added all the buttons.
     * Preconditions: The JButtons must exist, the mouseMotionListener must be imported, mouseListener would have to be extended in the class and the mouseListener must be added to this method. Also, if it is sent to this method, this method must contain if statements for what to do if it is clicked on.
     * Postconditions: Whatever is in the if statement has been displayed to the screen or has been performed.
     ****************************************************************************************************************************************************************************/

    public void mouseExited(MouseEvent e)  // use this method to perform actions when the mouse exits a component
    {

    } //end of mouseExited method.

    /****************************************************************************************************************************************************************************
     * Description of Method: Sees whether the user interacted with the JMenu.
     * Parameters and Return Type: Needs to be added to the actionListener.
     * Preconditions: Jmenu, menu bar, and menuitems must exist.
     * Postconditions: Frame will be displayed.
     ****************************************************************************************************************************************************************************/
    
    //JMenuBars can only be used with actionPerformed methods. 
    public void actionPerformed(ActionEvent what)
    {
        if (what.getSource()==menuWelcomeItem[0])
        {
            if (tracing) System.out.println("Inside the about actionListener.");

            JFrame aboutFrame = new JFrame("About");
                aboutFrame.setBackground(moss);
                //aboutFrame.setForeground(Color.white);


            aboutFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel aboutPanel = new JPanel(new BorderLayout());

            JTextArea about = new JTextArea("Welcome to the Fandom Monopoly! \n \n "
                                            + "This program was created for Multimedia 1A03 for Liss Platt and Kristen Shaw. "
                    + "                     \n \n Version: 2.0. "
                    + "                     \n Author: Natalie Chin (with help from Ryk & Justin)");
            about.setLineWrap(true);
            about.setEditable(false);
            about.setBackground(moss);
            about.setForeground(Color.black);
            about.setFont(normalFont);

            JScrollPane aboutScroll = new JScrollPane(about);

            aboutPanel.add(aboutScroll, BorderLayout.CENTER);

            aboutFrame.setContentPane(aboutPanel);
            aboutFrame.setSize(400, 400);
            aboutFrame.setVisible(true);
            aboutFrame.setAlwaysOnTop(true);

        } //end of if statement

        if (what.getSource() == menuWelcomeItem[1])
        {
            if (tracing) System.out.println("Inside the about instructions.");

            JFrame instrFrame = new JFrame("Instructions.");
            instrFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            instrFrame.setFont(titleFont);

            instrFrame.setBackground(moss);

            JPanel instrPanel = new JPanel(new BorderLayout());

            JTextArea instructions = new JTextArea("The Object Of The Game: "
                    + "                             \n The object of this game is to get rich with galleons. \n \n"
                    + "                             \n\nEquipment for The Game: \n\n You will need a monitor to see the game board. "
                    + "                             You will require a keyboard to enter your name, and sufficient RAM and hard disk drive space "
                    + "                             depending on how long you would like to play. "
                    + "                             Under normal circumstances, you will require about 2 GB of RAM, but may require more if you plan for playing for a long time. "
                    + ""
                    + "                             \n\n Preparation: \n\n As you are playing on a Com Pewter, "
                    + "                             the most you’ll have to do is open the file and run it! "
                    + "                             We will set up all your galleons and make sure you get 2000 galleons each   "
                    + "                             and we will take care of all the properties. \n \n "
                    + ""
                    + "                             PLAYING THE GAME: \n\nThe Com Pewter will be your banker unlike a game if you were to play it with other people. "
                    + "                             \n A frame will pop up telling you where you have landed."
                    + "                              \n Our bank will NEVER run out of money, unlike a paper one. "

                    + "                             \n\nHOW TO PLAY: \n\nClick on “roll die,” and the die will tell you where you have landed. "
                    + "                             \n If you land on an unowned property"
                    + "                             Click yes if you’d like to purchase it, click no if you don’t. "
                    + "                             The program will then tell you how much money you have left in your Gringotts Account. \n "
                    + "                             If you pass go, the Gringotts Account will automatically provide you with 200 galleons. "
                    + "                             If you land on St. Mungos, there will be no penalty because you are just visiting someone in the hospital. "
                    + "                             If you land on the Tavern, the program will give you the amount of money in the pot from room of requirement cards and camelot tax."
                    + "                              After the user or the com pewter picks up what is in the tavern, it restarts to 0. "
                    + "                             If you land on “Go to St. Mungos,” unlike the real monopoly, you have no choice of waiting in ‘jail’ for three turns. "
                    + "                             If you get into St. Mungos, you must pay 50 galleons to get out. "
                    + "                             \n Unlike the real game, there are no houses and hotels in this version of the program. "
                    + ""
                    + "                             \n\nIf the “Com Pewter” lands on your property for the first time, the rent will stay the same. "
                    + "                             For the second time, the rent will double. "
                    + "                             If the “Com Pewter” land on it for a third time, the rent will triple. "
                    + "                             And lastly, fourth time, the rent will quadruple, the rent will quadruple. "
                    + "                             This will be the last time it goes up. \n "
                    + ""
                    + "                             \n\nIf you would like to enlarge a property, simply do so by clicking it. "
                    + "                             \nThe numbers at the bottom tell you how much the rent is."
                    + ""
                    + "                             \n\nThis is only viewable in properties which can be bought. (Not for Camelot Tax)"
                    + ""
                    + "                             \n\n You do not have to wait for the Com Pewter to go, in fact, right after your turn, the com pewter will automatically take its’ own turn. "
                    + ""
                    + "                             \n\nThe program will let you know what card the Com Pewter has picked up from Room of Requirement, "
                    + "                             \nand/or whether the Com Pewter has landed on your property, or purchased a property. \n "
                    + "                             If you would like to find out where you are in the game, press “Where am I?” beside the “Roll Dice” button."
                    + "                             \n\n We really hope you enjoy the game!");
            instructions.setLineWrap(true);
            instructions.setBackground(moss);
            instructions.setEditable(false);
            instructions.setForeground(Color.black);
            instructions.setFont(normalFont);

            JScrollPane aboutScroll = new JScrollPane(instructions);

            instrPanel.add(aboutScroll, BorderLayout.CENTER);

            instrFrame.setContentPane(instrPanel);
            instrFrame.setSize(500, 500);
            instrFrame.setVisible(true);
            instrFrame.setAlwaysOnTop(true);
        } //end of if statement

        if (what.getSource()==menuWelcomeItem[2])
        {
            if (tracing) System.out.println("Inside the about instructions.");

            JFrame envFrame = new JFrame("Other");
            envFrame.setFont(titleFont);
            envFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel envPanel = new JPanel(new BorderLayout());

            JTextArea ea = new JTextArea(" ENVIRONMENTAL MESSAGE: This game of monopoly is entirely automated. Unlike a game of Monopoly, there is no need for a game board, pawns, (electronic banking system), paper money, community chest cards, chance cards, or houses and hotels. This cuts down on a lot of environmental resources and can last forever. However, on the other hand, this does require RAM and battery power. But however much one plays this game, the environmental resources will almost never add up to that to create the paper and the plastic for the physical game. This can be mentioned in the about screen - but I don't think it can incorporated in the game. If they want more information about the film sets of things like the Hobbit, then it can definitely be applicable. But that's not what my program is about, so... \n \n EMERGING AREAS OF COMPUTER SCIENCE: Some emerging areas nowadays include Bioinformatics, Cybersecurity, Artificial Intelligence and Grid Computing. Unfortunately, I don't see how any of these areas of computer science even relates to Monopoly. Unless someone would like to play a biology-related Monopoly game? And sort it in...a...Monopoly format? I mean, other than that, there really isn't that much other stuff we can do in terms of Bioinformatics. This program wouldn't help in cybersecurity, because it is just a game - for fun. It doesn't teach you much; and doesn't protect you from anything, either. But on the AI, I suppose I can see us teaching robots how to properly play Monopoly. Or using logic (from a computer standpoint) to try to win the game instead of just wandering in circles. Lastly, grid computing doesn't relate at all to Monopoly, to be honest. This is when computers work together to create or run one thing. And monopoly, even though it's a long game (and tedious but fun to program), wouldn't need multiple computers to run. \n \n COMPUTER SCIENCE AND EDUCATION: Programming Monopoly (if you have the patience) is actually so much fun. The reward from creating something from nothing is incredible. After finishing programs, you get a sense of 'I did it.' With (almost) finishing Monopoly, I get a feeling of 'FUDGE! I did it! Well, almost diddit! Did most of it!' And it's awesome. It's super useful - and you get to learn or use many new things: JOptionPanes, if statements, loops, arrays of imageIcons, arrays of JLabels, arrays of objects, tracing statements, multiple classes, JLayeredPanes, the list goes on and on.");
            ea.setFont(normalFont);
            ea.setBackground(moss);
            ea.setEditable(false);
            ea.setForeground(Color.black);

            ea.setLineWrap(true);

            JScrollPane aboutScroll = new JScrollPane(ea);

            envPanel.add(aboutScroll, BorderLayout.CENTER);

            envFrame.setContentPane(envPanel);
            envFrame.setSize(400, 400);
            envFrame.setVisible(true);
            envFrame.setAlwaysOnTop(true);
        } //end of if statement

        if (what.getSource() == menuWelcomeItem[3])
        {
            int x = JOptionPane.showConfirmDialog(pane, "Are you sure you have to go??", "Okay...", JOptionPane.YES_NO_OPTION);

            if (x ==0)
            {
                JOptionPane.showMessageDialog(pane, "Okay...press the ok button", "Farewell!", JOptionPane.INFORMATION_MESSAGE);

                System.exit(0);

            } //end of if statement

            else
            {
                JOptionPane.showMessageDialog(pane, "Hurray! Okay, just press the ok button and we'll bring up the game!", "YES!", JOptionPane.INFORMATION_MESSAGE);
                game();
            } //end of else statement

        } //end of if statement

        if (what.getSource() == menuMenuItem[0])
        {
            JFrame aboutFrame = new JFrame("About");
                aboutFrame.setBackground(moss);
                //aboutFrame.setForeground(Color.white);


            aboutFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel aboutPanel = new JPanel(new BorderLayout());

            JTextArea about = new JTextArea("Welcome to the Fandom Monopoly! \n \n This program was creatd for Multimedia 1A03. \n \n Version: 2.0. \n Author; Natalie Chin (with help from Ryk & Justin)");
            about.setLineWrap(true);
            about.setEditable(false);
            about.setBackground(moss);
            about.setForeground(Color.white);
            about.setFont(normalFont);

            JScrollPane aboutScroll = new JScrollPane(about);

            aboutPanel.add(aboutScroll, BorderLayout.CENTER);

            aboutFrame.setContentPane(aboutPanel);
            aboutFrame.setSize(400, 400);
            aboutFrame.setVisible(true);
            aboutFrame.setAlwaysOnTop(true);
        } //end of if statement

        if (what.getSource() == menuMenuItem[1])
        {
                    JFrame instrFrame = new JFrame("Instructions");
                    instrFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    instrFrame.setFont(titleFont);

                    instrFrame.setBackground(moss);

                    JPanel instrPanel = new JPanel(new BorderLayout());

                    JTextArea instructions = new JTextArea("The Object Of The Game: \n The object of this game is to get rich with galleons. \n Equipment for The Game: \n You will need a monitor to see the game board. You will require a keyboard to enter your name, and sufficient RAM and hard disk drive space depending on how long you would like to play. Under normal circumstances, you will require about 2 GB of RAM. \n Preparation: \n As you are playing on a Com Pewter, the most you’ll have to do is open the file and run it! We will set up all your galleons and make sure you all get 2000 galleons each and we will take care of all the properties. \n \n PLAYING THE GAME: The Com Pewter will be your banker unlike a game if you were to play it with other people. \n We will make sure that you are know who will lose points and who will gain points with a frame popping up letting you know. \n Our bank will never run out of money unlike a paper game. You will have no need to draw new paper money as this game is entirely electronic. \n \n HOW TO PLAY: Click on “roll die,” and the die will tell you where you have landed. \n If you land on a property which you can purchase, hurray. Click yes if you’d like to purchase it, click no if you don’t. The program will then tell you how much money you have left in your Gringotts Account. \n If you pass go, the Gringotts Account will automatically provide you with 200 galleons. If you land on St. Mungos, there will be no penalty because you are just visiting someone in the hospital. If you land on the Tavern, the program will give you the amount of money in the pot from room of requirement cards and camelot tax. After the user or the com pewter picks up what is in the tavern, it restarts to 0. If you land on “Go to St. Mungos,” unlike the real monopoly, you have no choice of waiting in ‘jail’ for three turns. So right when you get in, you must pay 50 galleons to get out. \n Unlike the real game, there are no houses and hotels  in this program. However, if the “Com Pewter” lands on your property for the first time, the rent will stay the same. However, for the second time, the rent will double. If the “Com Pewter” land on it for a third time, the rent will triple. And lastly, if the “Com Pewter” lands on it for a fourth time, the rent will quadruple. This will be the last time it goes up. \n If you would like to enlarge a property, simply do so by clicking it. The numbers at the bottom represent how much the rent is, and whether it is owned or not. This is only seeable in properties which can be bought. \n You do not have to wait for the Com Pewter to go, in fact, right after your turn, the com pewter will automatically take its’ own turn. The program will let you know what card the Com Pewter has picked up from Room of Requirement, and/or whether the Com Pewter has landed on your property, or purchased a property. \n If you would like to find out which tile you are in a game, press “Where am I?” beside the “Roll Dice” button.");
                    instructions.setLineWrap(true);
                    instructions.setBackground(moss);
                    instructions.setEditable(false);
                    instructions.setForeground(Color.white);
                    instructions.setFont(normalFont);

                    JScrollPane aboutScroll = new JScrollPane(instructions);

                    instrPanel.add(aboutScroll, BorderLayout.CENTER);

                    instrFrame.setContentPane(instrPanel);
                    instrFrame.setSize(400, 400);
                    instrFrame.setVisible(true);
                    instrFrame.setAlwaysOnTop(true);

        } //end of if statement

        if (what.getSource() == menuMenuItem[2])
        {
            int x = JOptionPane.showConfirmDialog(pane, "Are you sure you have to go??", "Okay...", JOptionPane.YES_NO_OPTION);

            if (x ==0)
            {
                JOptionPane.showMessageDialog(pane, "Okay...press the ok button", "Farewell!", JOptionPane.INFORMATION_MESSAGE);

                System.exit(0);

            } //end of if statement

            else
            {
                JOptionPane.showMessageDialog(pane, "Hurray! Okay, just press the ok button and we'll bring up the game!", "YES!", JOptionPane.INFORMATION_MESSAGE);
                game();
            } //end of else statement
        } //end of if statement

        if (what.getSource() == menuGameItem[0])
        {
            JFrame aboutFrame = new JFrame("About");
                aboutFrame.setBackground(moss);
                //aboutFrame.setForeground(Color.white);


            aboutFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel aboutPanel = new JPanel(new BorderLayout());

            JTextArea about = new JTextArea("Welcome to the Fandom Monopoly! \n \n This program was creatd for Multimedia 1A03 for Liss Platt. \n \n Version: 2.0. \n Author; Natalie Chin (with help from Ryk & Justin)");
            about.setLineWrap(true);
            about.setEditable(false);
            about.setBackground(moss);
            about.setForeground(Color.white);
            about.setFont(normalFont);

            JScrollPane aboutScroll = new JScrollPane(about);

            aboutPanel.add(aboutScroll, BorderLayout.CENTER);

            aboutFrame.setContentPane(aboutPanel);
            aboutFrame.setSize(400, 400);
            aboutFrame.setVisible(true);
            aboutFrame.setAlwaysOnTop(true);
            //aboutFrame.toFront(true);
        } //end of if statement

        if (what.getSource() == menuGameItem[1])
        {
                    JFrame instrFrame = new JFrame("Instructions");
                    instrFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    instrFrame.setFont(titleFont);

                    instrFrame.setBackground(moss);

                    JPanel instrPanel = new JPanel(new BorderLayout());

                    JTextArea instructions = new JTextArea("The Object Of The Game: \n The object of this game is to get rich with galleons. \n Equipment for The Game: \n You will need a monitor to see the game board. You will require a keyboard to enter your name, and sufficient RAM and hard disk drive space depending on how long you would like to play. Under normal circumstances, you will require about 2 GB of RAM. \n Preparation: \n As you are playing on a Com Pewter, the most you’ll have to do is open the file and run it! We will set up all your galleons and make sure you all get 2000 galleons each and we will take care of all the properties. \n \n PLAYING THE GAME: The Com Pewter will be your banker unlike a game if you were to play it with other people. \n We will make sure that you are know who will lose points and who will gain points with a frame popping up letting you know. \n Our bank will never run out of money unlike a paper game. You will have no need to draw new paper money as this game is entirely electronic. \n \n HOW TO PLAY: Click on “roll die,” and the die will tell you where you have landed. \n If you land on a property which you can purchase, hurray. Click yes if you’d like to purchase it, click no if you don’t. The program will then tell you how much money you have left in your Gringotts Account. \n If you pass go, the Gringotts Account will automatically provide you with 200 galleons. If you land on St. Mungos, there will be no penalty because you are just visiting someone in the hospital. If you land on the Tavern, the program will give you the amount of money in the pot from room of requirement cards and camelot tax. After the user or the com pewter picks up what is in the tavern, it restarts to 0. If you land on “Go to St. Mungos,” unlike the real monopoly, you have no choice of waiting in ‘jail’ for three turns. So right when you get in, you must pay 50 galleons to get out. \n Unlike the real game, there are no houses and hotels  in this program. However, if the “Com Pewter” lands on your property for the first time, the rent will stay the same. However, for the second time, the rent will double. If the “Com Pewter” land on it for a third time, the rent will triple. And lastly, if the “Com Pewter” lands on it for a fourth time, the rent will quadruple. This will be the last time it goes up. \n If you would like to enlarge a property, simply do so by clicking it. The numbers at the bottom represent how much the rent is, and whether it is owned or not. This is only seeable in properties which can be bought. \n You do not have to wait for the Com Pewter to go, in fact, right after your turn, the com pewter will automatically take its’ own turn. The program will let you know what card the Com Pewter has picked up from Room of Requirement, and/or whether the Com Pewter has landed on your property, or purchased a property. \n If you would like to find out which tile you are in a game, press “Where am I?” beside the “Roll Dice” button.");
                    instructions.setLineWrap(true);
                    instructions.setBackground(moss);
                    instructions.setEditable(false);
                    instructions.setForeground(Color.white);
                    instructions.setFont(normalFont);

                    JScrollPane aboutScroll = new JScrollPane(instructions);

                    instrPanel.add(aboutScroll, BorderLayout.CENTER);

                    instrFrame.setContentPane(instrPanel);
                    instrFrame.setSize(400, 400);
                    instrFrame.setAlwaysOnTop(true);
                    instrFrame.setVisible(true);
                    instrFrame.setAlwaysOnTop(true);

        } //end of if statement

        if (what.getSource() == menuGameItem[2])
        {

            if (tracing) System.out.println("Inside location whatClicked.");

            JFrame where = new JFrame("Where am I? ");
                where.setBackground(moss);
                where.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel organize = new JPanel(new BorderLayout());
                organize.setBackground(brown);

            JTextField whereAreYou = new JTextField("Where are you? Where's the Com Pewter?");
                whereAreYou.setForeground(Color.white);
                whereAreYou.setEditable(false); 

            JPanel locations = new JPanel(new GridLayout(2,2));
                locations.setSize(400, 400);


            JTextField[] info = new JTextField[4];

            for (int i = 0; i<info.length; i++)
            {
                if (i == 0)
                {
                    info[i] = new JTextField(userName);
                    info[i].setFont(titleFont);
                    info[i].setEditable(false); 
                    locations.add(info[i]);
                } //end of if statement

                if (i == 1)
                {
                    info[i] = new JTextField("Com Pewter");
                    info[i].setFont(titleFont);
                    info[i].setEditable(false);
                    locations.add(info[i]);
                } //end of if statement

                if (i == 2)
                {
                    info [i] = new JTextField("Location: \n \n" +square[player[0].getCol()].getName());
                    info[i].setFont(normalFont);
                    info[i].setEditable(false);
                    //info[i].setLineWrap(true);
                    locations.add(info[i]);
                    info[i].setBackground(brown);
                    info[i].setForeground(Color.white);
                } //end of if statement

                if (i == 3)
                {
                    info[i] = new JTextField("Location: \n\n" +square[player[1].getCol()].getName());
                    info[i].setFont(normalFont);
                    info[i].setEditable(false);
                    locations.add(info[i]);
                    info[i].setBackground(brown);
                    info[i].setForeground(Color.white);
                } //end of if statement
                info[i].setEditable(false);

            } //end of for loop.

            organize.add(whereAreYou, BorderLayout.NORTH);
            organize.add(locations, BorderLayout.CENTER);

            where.setContentPane(organize);
            where.setAlwaysOnTop(true);
            where.setVisible(true);
            where.setSize(800, 400);
            where.setAlwaysOnTop(true);

        } //end of if statement

        if (what.getSource() == menuGameItem[3])
        {
            int x = JOptionPane.showConfirmDialog(pane, "Are you sure you have to go??", "Okay...", JOptionPane.YES_NO_OPTION);

            if (x ==0)
            {
                JOptionPane.showMessageDialog(pane, "Okay...press the ok button", "Farewell!", JOptionPane.INFORMATION_MESSAGE);

                System.exit(0);

            } //end of if statement

            else
            {
                JOptionPane.showMessageDialog(pane, "Hurray! Okay, just press the ok button and we'll bring up the game!", "YES!", JOptionPane.INFORMATION_MESSAGE);
                game();
            } //end of else statement
        } //end of if statement for menuGame Item 
    }//end of method.

    private void printToFile() 
    {
    String fileName = "Properties.txt";
    PrintWriter outfile;
        try
        {
            outfile = new PrintWriter (new FileWriter(fileName)); //declares the print to file line of code (AW)        
            for (int i = 0; i<36; i++)
            {
                outfile.println(square[i].getOwned());
                outfile.println(square[i].getOwner());
                outfile.println(square[i].getMort());
                outfile.println(square[i].getBuy());
                outfile.println(square[i].getHH());
                outfile.println(square[i].getPayout());
                outfile.println(square[i].getRent());
                outfile.println(square[i].getNumber());
                outfile.println(square[i].getName());
            } //end of for loop
            
            outfile.close();
            
        } //end of try statement
        catch (Exception e)
        {
            System.out.println("Unable to open file");
        } //end of catch statement
    }

} //end of class WOOT WOOT :D 

/**Note: all the pictures that were used in the creation of this program are NOT owned by me, and are all courtesy of: 
 *      Walt Disney: Narnia 
 *      New Line Cinema: Lord of the Rings/The Hobbit
 *      Warner Brothers: Harry Potter
 *      BBC: Doctor Who, Sherlock, Merlin
 */