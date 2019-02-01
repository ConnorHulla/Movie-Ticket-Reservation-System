package Tickets;
import java.util.ArrayList;
public class Customer 
{
    private String username;
    private String password;
    private ArrayList<Orders> orderList = new ArrayList();
    private int numOrders;
    
    Customer()
    {
        username = "username";
        password = "password";
        numOrders = 0;
    }
    
    Customer(String u, String p)
    {
        username = u;
        password = p;
        numOrders = 0;
    }
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getNumorders()   { return numOrders; }
    public Orders getOrder(int n) {return orderList.get(n); }
    public void setUsername(String u) { username = u; }
    public void setPassword(String p) { password = p; }

    public void addOrder(Orders t)    { orderList.add(t); 
                                        numOrders++;     }
    public void removeOrder(Orders t) { orderList.remove(t); 
                                        numOrders--; }
    
    //toString
    @Override
    public String toString()
    {
        String orderPrint = "";
        //for the list of the orders
        //print the ordernumber, call the toString
        //go to the next line
        for(int i = 0; i < orderList.size(); i++)
        {
            orderPrint += ("Order #" + (i + 1) + ":\n"); 
            orderPrint += orderList.get(i).toString();
            orderPrint += '\n';
        }
        return orderPrint;
    }
    
    //returns a receipt in the form of a string
    public String Receipt()
    {
        String receipt = "";
        double total = 0, orderTotal = 0;
        for(int i = 0; i < orderList.size(); i++)
        {
            //adult * 10 + child * 5 + senior * 7.50 will be the total
            orderTotal = (orderList.get(i).getAdult() * 10.0 + 
                    orderList.get(i).getChild() * 5.0 + 
                    orderList.get(i).getSenior() * 7.50);
            //adds the order #
            receipt += String.format("%-18s", "Order #: ");
            receipt += (i + 1);
            receipt += '\n';
            //adds the adult tickets
            receipt += String.format("%-18s", "Adult Tickets: ");
            receipt += orderList.get(i).getAdult();
            receipt += '\n';
            //adds the child tickets
            receipt += String.format("%-18s", "Child Tickets: ");
            receipt += orderList.get(i).getChild();
            receipt += '\n';
            //adds the senior tickets
            receipt += String.format("%-18s", "Senior Tickets: ");
            receipt += orderList.get(i).getSenior();
            receipt += '\n';
            //adds the auditorium number
            receipt += String.format("%-18s", "Auditorium #");
            receipt += orderList.get(i).getAud();
            receipt += '\n';
            //prints all of the seats
            receipt += String.format("%-18s", "Seats: " 
                    + orderList.get(i).printSeats());
            receipt += '\n';
            receipt += String.format("%-18s", "Price of Order: ");
            receipt += "$" + orderTotal;
            total += orderTotal;
            receipt += '\n';
        }
        //prints  the total price
        receipt += String.format("%-18s", "Total: ");
        receipt += "$" + total + '\n';
        return receipt;
    }
   
    
    
}
