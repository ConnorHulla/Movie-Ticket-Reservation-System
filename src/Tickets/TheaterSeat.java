package Tickets;

public class TheaterSeat extends BaseNode
{
     //these our the nodes
     TheaterSeat up;
     TheaterSeat down;
     TheaterSeat left;
     TheaterSeat right;
    
     int numCol;
    
    TheaterSeat()
    {
        super();
        up = null;
        down = null;
        left = null;
        right = null; 
        numCol = 0;
    }
    TheaterSeat(int r, char s, boolean rs, char t) 
    {
        //calls the basenode constructor
        super(r, s, rs, t);
        //sets all of our nodes to null
        up = null;
        down = null;
        left = null;
        right = null;
        
    }
    
    //mutators
    public void setUp(TheaterSeat u)   { up = u;   }
    public void setDown(TheaterSeat d) { down = d; }
    public void setLeft(TheaterSeat l) { left = l; }
    public void setRight(TheaterSeat r){ right = r;}
    //accessors
    public TheaterSeat getUp()     { return up;   }
    public TheaterSeat getDown()   { return down; }
    public TheaterSeat getLeft()   { return left; }
    public TheaterSeat getRight()  { return right;}
    
    public boolean isAvaliable(int totalTickets)
    {
        int i = 0;
        TheaterSeat point = this;
        
        //while point doesnt equal node and while its not reserved, move right
        //and incrimate i
        while(point != null && i < totalTickets && point.reserved == false)
        {
            i++;
            point = point.right;
        }
        if(i < totalTickets) //if i does not equal the total tickets, then there wasn't enough space for the user
            return false;
        
        return true; //if we made it this far, it must be true
    }
    
    public void delete()
    {
        //when we delete we just make it non-reserved and we set the ticket type
        //to . to indicate that the seat is now avaliable
        this.reserved = false;
        this.ticketType = '.';
    }
    
}
