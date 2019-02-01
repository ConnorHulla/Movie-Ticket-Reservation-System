package Tickets;

public abstract class BaseNode 
{
    protected int row;        
    protected char seat;    
    protected boolean reserved;
    protected char ticketType;
   
    //default constructor
    BaseNode() 
    {
        row = 0;
        seat = 'A';
        reserved = false;
        ticketType = 'A';
    }
    
    
    //overloaded constructor
    BaseNode(int r, char s, boolean rs, char t)
    {
        row = r;
        seat = s;
        reserved = rs;
        ticketType = t;   
    }
    //accessors 
    public int getRow()          { return row;       }
    public char getSeat()        { return seat;      }
    public boolean getReserved() { return reserved;  }
    public char getTicketType()  { return ticketType;}
    //mutators
    public void setRow(int r) { row = r; } 
    public void setSeat(char s) { seat = s; } 
    public void setReserved(boolean rs) { reserved = rs; }
    public void setTicketType(char t)  { ticketType = t; } 
    
    
    
    
}
