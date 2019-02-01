package Tickets;

import java.io.PrintWriter;
import java.util.Scanner;
public class Auditorium 
{
    private TheaterSeat first;
    private int numCol;
    private int numRow;
    
    Auditorium() {}
            
            
    Auditorium(Scanner input)
    {
        numRow = 0; //this will keep track of the number of rows we have
        TheaterSeat ptr = new TheaterSeat(); // this creates a new node
        first = ptr; //our first will equal this node
        TheaterSeat rowCheck; 
        String line; 
        line = input.nextLine(); //get the first line of the file
        int i = 1;

        numCol = line.length(); //sets the number of columbs equal to the length of the string
        //get the head first
        numRow++; //add to numrow
        first.row = 1;
        first.seat = 'A';
        first.ticketType = line.charAt(0);
        first.reserved = (first.ticketType != '.');
     
        
        rowCheck = ptr; //this pointer is to keep track of the previous row. we will need it when we go to the next row
        
        //we will 
        //now we will get the first row
        for(int j = 1; j < line.length(); j++)
        {
            TheaterSeat hold = new TheaterSeat(); //create a new node
            hold.row = 1;                       //get the row and the seat
            hold.seat = (char)(j + 65);
            
            
            //now get the data
            hold.reserved = (line.charAt(j) != '.'); //if its reserved, set this to true
                                                     //otherwise, its false
            hold.ticketType = line.charAt(j);       //se
            
            ptr.right = hold; //link ptr to hold
            hold.left = ptr; //link hold to ptr
            ptr = hold;      //move ptr to hold
        }
        
        while(input.hasNext())
        {   
            //gets the first node in the row set up
            line = input.nextLine();
            numRow++;
            TheaterSeat rowTracker = new TheaterSeat(i, 'A', 
                    line.charAt(0) != '.', line.charAt(0)); 
             //Row Tracker will keep track of the previous node in the row
            TheaterSeat firstInRow = rowTracker; 
            
            rowTracker.up = rowCheck; //link rowTracker to the row abovie us
            rowCheck.down = rowTracker; //link the node above to rowTracker
            rowCheck = rowCheck.right; //move rowCheck over
            
            for(int k = 1; k < line.length(); k++)
            {
                //get the data (same as before
                TheaterSeat hold = new TheaterSeat();
                hold.row = i;
                hold.seat = (char)(k + 65);
                hold.ticketType = line.charAt(k);
                hold.reserved = (hold.ticketType != '.');
                
                
                hold.up = rowCheck; //connects this node to the one above it
                rowCheck.down = hold; //connects the node above it to the one below
                rowCheck = rowCheck.right; //moves rowCheck to the right
                hold.left = rowTracker; //link hold to the previous
                rowTracker.right = hold;  //link the previous to our current
                rowTracker = hold; //move rowt racker to hold
            }
            rowCheck = firstInRow; //go back to the start of the row
            
            i++;
        }
    }
    
    public void setFirst(TheaterSeat f) { first = f; }
    public TheaterSeat getFirst() { return first; }
    public int getnumCol() { return numCol; }
    public int getnumRow() { return numRow; }
    
    //this will put the contents of the class into a string
    @Override
    public String toString() 
    {
        String audit = "  ";
        
        TheaterSeat row = first; //we will use these to traverse the data structure
        TheaterSeat col = first;
        //print the letters
        int i = 0;
        while(col != null) //while column isn't null
        {
            audit += (char)(i + 65); //print i + 65, this brings is to the ASCII values for the alphabet
            i++; 
            col = col.right; //move to the next seat
        }
        col = first; //set column back to being first
        audit += '\n'; //add a newline character
        
        i = 0;
        while(row != null) //while row does not equal null
        {
            audit += (i + 1); //this will be the number for our row
            audit += " ";     //add a space
            while(col != null)
            {
                if(col.ticketType == '.') //if theres a ., print a dot
                    audit += '.';
                else
                    audit += '#'; //else, print a #
                
                col = col.right; //move column
            }
            row = row.down; //move row down
            col = row;     //set col equal to the row pointer
            audit += '\n'; //add a newline 
            i++; 
        }
        
        return audit;
    }
    
    public boolean seatsAvaliable(int r, int at, int ct, int st, char c)
    {
        TheaterSeat nav = first;
        int i = 0;
        //we use i to get to the correct row
        while(i < r)
        {
            if(nav.down == null) //if its null then its not avaliable
                return false;
            nav = nav.down;     //move down otherwise
            i++;
        }
        
        int j = 0;  //we use j to get the correct column
        while(j < (c - 65)) //c - 65 because ASCII starts counting at 65 for the alphabet
        {
            if(nav.right == null) //if its null than this is false
                return false;
            nav = nav.right;
            j++;
        }
        
        int k = 0;
        TheaterSeat update = nav; //this will be where we update our seats 
        //first, we make sure there are enough seats to store our tickets.
        //we do this by updating k until we reach a null pointer OR until we find
        //a reserved seat
        while(nav != null && k < (at + ct + st) && nav.getReserved() == false) 
        {
            nav = nav.right;
            k++;
        }
        //if k does not equal the number of tickets we ordered, then there isn't
        //enough space. otherwise, update the auditorium
        if(k == (at + ct + st)) 
        {
            //for all of our adult tickets
            for(int m = 0; m < at; m++)
            {
                update.ticketType = 'A';  //set the ticket type to 'A' for adult 
                update.reserved = true;   //its reserved now
                update = update.right;   //go to the next seat
            }
            //for all the child tickets
            for(int n = 0; n < ct; n++) 
            {
                update.ticketType = 'C'; //set ticketType to C for child
                update.reserved = true; //it reserved now
                update = update.right; //go to the next seat
            }
            //for all of our senior tickets
            for(int z = 0; z < st; z++)
            {
                update.ticketType = 'S'; //set ticket ticket to S for senior
                update.reserved = true; //it has now been reserved so this is true
                update = update.right; //move to the next seat
            }
            return true; //return true
        }
        else  //otherwise, return fasle
            return false;
    }
    
    public void updateAuditorium(int r, int c, int aT, int cT, int sT)
    {
        TheaterSeat nav = first;
        for(int i = 0; i < r; i++) //goes to the row we want to update
            nav = nav.down;
        
        for(int j = 0; j < c; j++) //goes to the column we want to update
            nav = nav.right;
       
        for(int k = 0; k < aT; k++) //adds in all the adult tickets
        {
            nav.ticketType = 'A';
            nav.reserved = true;
            nav = nav.right;
        }
        
        for(int l = 0; l < cT; l++) //adds in all the child tickets
        {
            nav.ticketType = 'C';
            nav.reserved = true;
            nav = nav.right;
        }
        
        for(int q = 0; q < sT; q++) //this adds in all the senior tickets
        {
            nav.ticketType = 'S';
            nav.reserved = true;
            nav = nav.right;
        }
    }
    
    public void fileOutput(PrintWriter output)
    {
        TheaterSeat nav = first; //this variable will navigate our auditorium 
        TheaterSeat rowT = nav;  //this variable will keep track of the start of the row
        for(int i = 0; i < numRow; i++) //goes through the 
        {
            for(int j = 0; j < numCol; j++)
            {
                output.print(nav.ticketType); //prints the ticket type
                nav = nav.right;              
            }
            output.println(); //prints a newline
            rowT = rowT.down; //go down
            nav = rowT; //set our navigator to the next line
        }
    }
    
    public TheaterSeat getTheaterSeat(int r, char c)
    {
        //returns null if not found
        TheaterSeat nav = new TheaterSeat();
        nav = first;
        for(int i = 0; i < r; i++) //get to the reow
        {
            if(nav == null)
                return null;
            nav = nav.down;
        }
        
        for(int j = 0; j < (c - 65); j++) //get to the column
        {
            if(nav == null)
                return null;
            nav = nav.right;
        }
        
        return nav; //return what we foud
    }
    
    public void deleteSeat(int r, char c, Orders n)
    {
        TheaterSeat del = this.getTheaterSeat(r - 1, c);
        if(del != null)
        {
            n.dec(del.getTicketType());
            del.delete();
        }
    }
    public void deleteSeat(int r, char c)
    {
        TheaterSeat del = this.getTheaterSeat(r - 1, c);
        if(del != null)
        {
            del.delete();
        }
    }
    
        
    public int getNumAdult()
    {
        TheaterSeat nav = first; //this variable will navigate our auditorium 
        TheaterSeat rowT = nav;  //this variable will keep track of the start of the row
        int numAdult = 0;
        for(int i = 0; i < numRow; i++) //goes through the 
        {
            for(int j = 0; j < numCol; j++)
            {
                if(nav.ticketType == 'A') //if its A
                    numAdult++;
                nav = nav.right;              
            }
            rowT = rowT.down; //go down
            nav = rowT; //set our navigator to the next line
        }
        
        return numAdult;
    }
    
    
    public int getNumChild()
    {
        TheaterSeat nav = first; //this variable will navigate our auditorium 
        TheaterSeat rowT = nav;  //this variable will keep track of the start of the row
        int numChild = 0;
        for(int i = 0; i < numRow; i++) //goes through the 
        {
            for(int j = 0; j < numCol; j++)
            {
                if(nav.ticketType == 'C')    ///if its a c, add to numChild
                    numChild++;
                nav = nav.right;              
            }
            rowT = rowT.down; //go down
            nav = rowT; //set our navigator to the next line
        }
        //return the number of children found
        return numChild;
    }
    
    public int getNumSenior()
    {
        TheaterSeat nav = first; //this variable will navigate our auditorium 
        TheaterSeat rowT = nav;  //this variable will keep track of the start of the row
        int numSenior = 0;
        for(int i = 0; i < numRow; i++) //goes through the 
        {
            for(int j = 0; j < numCol; j++)
            {
                if(nav.ticketType == 'S')
                    numSenior++;
                nav = nav.right;              
            }
            rowT = rowT.down; //go down
            nav = rowT; //set our navigator to the next line
        }
        
        return numSenior;
    }
    
    public int getNumEmpty()
    {
        TheaterSeat nav = first; //this variable will navigate our auditorium 
        TheaterSeat rowT = nav;  //this variable will keep track of the start of the row
        int numEmpty = 0;
        for(int i = 0; i < numRow; i++) //goes through the 
        {
            for(int j = 0; j < numCol; j++)
            {
                if(nav.ticketType == '.') //if its A
                    numEmpty++;
                nav = nav.right;              
            }
            rowT = rowT.down; //go down
            nav = rowT; //set our navigator to the next line
        }
        
        return numEmpty;
    }
    
    public int getNumReserved()
    {
        TheaterSeat nav = first; //this variable will navigate our auditorium 
        TheaterSeat rowT = nav;  //this variable will keep track of the start of the row
        int numRes = 0;
        for(int i = 0; i < numRow; i++) //goes through the 
        {
            for(int j = 0; j < numCol; j++)
            {
                if(nav.ticketType != '.') //if its A
                    numRes++;
                nav = nav.right;              
            }
            rowT = rowT.down; //go down
            nav = rowT; //set our navigator to the next line
        }
        
        return numRes;
    }
    
    public double getMoney()
    {
        TheaterSeat nav = first; //this variable will navigate our auditorium 
        TheaterSeat rowT = nav;  //this variable will keep track of the start of the row
        double money = 0;
        for(int i = 0; i < numRow; i++) //goes through the 
        {
            for(int j = 0; j < numCol; j++)
            {
                
                //if its an adult ticket, add $10.00
                //if its a child ticket, add $5.00
                //if its a senior ticket, add $7.50
                //else, don't add anything
                if(nav.ticketType == 'A')
                    money += 10.0;
                else if(nav.ticketType == 'C')
                    money += 5.0;
                else if(nav.ticketType == 'S')
                    money += 7.50;
                nav = nav.right;              
            }
            rowT = rowT.down; //go down
            nav = rowT; //set our navigator to the next line
        }
        
        return money;
    }

}
