package Tickets;

import java.util.ArrayList;

public class Orders 
{
    //auditorium, seats, number of each ticket type
    private int numAdult, numChild, numSenior;
    private int aud;
    private ArrayList<String> seats = new ArrayList<>();
    
    Orders()
    {
        numAdult = 0;
        numChild = 0;
        numSenior = 0;
    }
    
    Orders(int na, int nc, int ns, int r, int t, char c)
    {
        String ins = ""; //used for inserting the seats into the arrayList
        numAdult = na;
        numChild = nc;
        numSenior = ns;

        aud = t;
        /*We will store the seats of each order as a string.
        start by adding the row (r) to the string
        then add the seat (c + i), type cast it so it shows up as a letter
        add the seat into our string arrayList
        set ins back to ""
        repeate until we run out of seats
        the number of seats we have is the sum of the adult, child, 
        and senior tickets*/
        for(int i = 0; i < na + nc + ns; i++)
        {
           ins += (r);
           ins += (char)(c + i);
           seats.add(ins);
           ins = "";
        }
    }
    
    //setters
    public void setAdultTickets(int num)  { numAdult = num; }
    public void setChildTickets(int num)  { numChild = num; }
    public void setSeniorTickets(int num) { numSenior = num; }
    public void setAuditorium(int a) {aud = a; }
    //getters
    public int getAud() {return aud; }
    public int getTicketnum() { return (numAdult + numChild + numSenior); } 
    public int getAdult() { return numAdult; }
    public int getChild() { return numChild; }
    public int getSenior(){ return numSenior; }
    //toString
    public String toString()
    {
        /* 
            print the number of adult tickets, child tickets, senior tickets
            and the auditorium number
            then print all the seats from our arrayList
        */
        String order = "";
        order += ("Adult Tickets: " + numAdult + ", ");  
        order += ("Child Tickets: " + numChild + ", ");
        order += ("Senior Tickets: " + numSenior + ", ");
        order += ("Auditorium: #" + aud + '\n');
        for(int i = 0; i < numAdult + numChild + numSenior; i++)
            order += seats.get(i) + " ";
        return order;
    }
    
    public void addSeats(int na, int nc, int ns, int r, char c)
    {
        //this adds to each ticket type
        String ins = "";
        numAdult += na;
        numChild += nc;
        numSenior += ns;
        /*We will store the seats of each order as a string.
        start by adding the row (r) to the string
        then add the seat (c + i), type cast it so it shows up as a letter
        add the seat into our string arrayList
        set ins back to ""
        repeate until we run out of seats
        the number of seats we have is the sum of the adult, child, 
        and senior tickets*/
        for(int i = 0; i < na + nc + ns; i++)
        {
           ins += (r);
           ins += (char)(c + i);
           seats.add(ins);
           ins = "";
        }
    }
    
    public boolean deleteSeats(int r, char c)
    {
        //this will delete the seats
        String ins = "";
        /*first, we will create a string of the seat we want to delete
        our format is row and column (no space) so we just have to add the row 
        # to our string then we add the character that represents the letter
  
        */
        ins += r;
        ins += c;
        //seats.remove returns a boolean. true if it was removed, false if it
        //wasn't. so return the boolean value the arrayList returns
        return seats.remove(ins);
    }
    
    public boolean isEmpty()
    {
        //checks to see if our seat arraylist is empty
        return seats.isEmpty();
    }
    
    //decreases the number of seats of a type
    public void dec(char a)
    {
        //if our char is A, lower adult
        //else if its C, lower child
        //else if its S, lower senior
        if(a == 'A')
            numAdult--;
        else if(a == 'C')
            numChild--;
        else if(a == 'S')
            numSenior--;
            
    }
    //prints out the seats specifically
    public String printSeats()
    {
        //adds the seats to a string until we get all of them
        String order = "";
        for(int i = 0; i < numAdult + numChild + numSenior; i++)
            order += seats.get(i) + " ";
        return order;
    }
}
