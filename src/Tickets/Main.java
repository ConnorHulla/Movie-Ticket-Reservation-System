//Connor Hulla
//Project 5, CS 2336 (Programming 2)
package Tickets;

import java.util.Scanner;
import java.util.HashMap;
import java.io.*;
import java.util.InputMismatchException;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {

        boolean adminExit = false, loggedin = false, isValid = false;
        String username = "", password = "";
        int choice = 0, audChoice = 0;
        char yesno = ' ';
        String check = "";
        //hashmap that stores the user data
        HashMap<String, Customer> customerList = new HashMap();
        File file = new File("userdb.dat");
        
        if(!file.exists())
        {
            System.out.println("The file could not be found");
            return;
        }
        Scanner fileinput = new Scanner(file);
        getInput(customerList, fileinput);
        Scanner input = new Scanner(System.in);
 
        Customer account = new Customer();
        /*
            gets the inputs for all the auditoriums
            A1 = auditorium 1
            A2 = auditorium 2
            A3 = auditorium 3
        
            The movie auditorium will store the auditorium that the user
            selects
        */
        file = new File("A1.txt");
        //if the file exists, tell the user that the file could not be found
        if(!file.exists())
        {
            System.out.println("A1.txt could not be found");
            return;
        }
        fileinput = new Scanner(file);
        Auditorium aone = new Auditorium(fileinput);
   
        file = new File("A2.txt");
        //if the file doesnt exist, warn the user and terminate the program
        if(!file.exists())
        {
            System.out.println("A2.txt could not be found");
            return;
        }
        fileinput = new Scanner(file);
        Auditorium atwo = new Auditorium(fileinput);
        //if the file doesnt exist, warn the user and terminate the program
        file = new File("A3.txt");
        if(!file.exists())
        {
            System.out.println("A3.txt could not be found");
            return;
        }
        fileinput = new Scanner(file);
        Auditorium athree = new Auditorium(fileinput);
        
        Auditorium movie = new Auditorium();
        
        //loop runs until admin exit is true
        do
        {
            while(loggedin == false)
            {
                //starting point
                System.out.print("Enter your username: ");
                username = input.next();
                /*if they enter the password incorrectly 3 times, return to
                the starting point */
                for(int i = 0; i < 3 ; i++)
                {
                    System.out.print("Enter your password: ");
                    password = input.next();
          
                    try
                    {
                        account = customerList.get(username);
                        if(password.compareTo(account.getPassword()) == 0)
                        {
                            loggedin = true;
                            break;
                        }
                        else
                            System.out.println("Incorrect password, "
                                    + "please try again");
                    }
                    catch(NullPointerException ex)
                    {
                        //if there is a null pointer exception, that means
                        //the username was not found in the hash table
                        System.out.println("Incorrect username, "
                                + "please try again");
                        break;
                    }
                }
            }
            
            if(username.equals("admin"))
            {
                do //loop runs until loggedin = false
                {
                    do
                    {
                        try
                        {
                            //print the menu
                            System.out.print("1. Print Report\n" +
                                             "2. Logout\n" +
                                             "3. Exit\n");
                            choice = input.nextInt();
                            //if choice is less than one or greater than thing,
                            //its not valid
                            if(choice < 1 || choice > 3)
                                isValid = false;
                            else
                                isValid = true;
                        }
                        catch(InputMismatchException E)
                        {
                            //invalid if an exception is caught
                            isValid = false;
                            input.nextLine();
                        }
                    }while(isValid == false);
                    //if they select 1, printreporrt
                    if(choice == 1)
                    {
                        printReport(aone, atwo, athree);
                    }
                    //if they want to log out, loggedin = false
                    else if(choice == 2) 
                        loggedin = false;
                    //if they exit, set loggedin to false to leave this loop,
                    //set adminExit to true to leave the main loop
                    else 
                    {
                        loggedin = false;
                        adminExit = true;
                    }
                }
                while(loggedin == true);
            }
            else
            {
                do //loops until the choice is valid
                {
                    //prints the menu
                    System.out.print("1. Reserve Seats\n" +
                                     "2. View Orders\n" +
                                     "3. Update Orders\n" +
                                     "4. Display Reciept\n" + 
                                     "5. Log Out\n");
                    try
                    {
                        choice = input.nextInt();
                    }
                    catch(InputMismatchException E)
                    {
                        input.nextLine();
                    }
                    if(choice == 1)
                    { 
                        do //loops until the choice is valid
                        {
                            System.out.print("1. Auditorium 1\n" +
                                             "2. Auditorium 2\n" + 
                                             "3. Auditorium 3\n");
                            try
                            {
                                audChoice = input.nextInt();
                                //choices 1-3 are the only valid optios
                                if(audChoice < 1 || audChoice > 3)
                                    isValid = false;
                                else
                                {
                                    //set movie to the auditorium they choose
                                    if(audChoice == 1)
                                        movie = aone;
                                    else if(audChoice == 2)
                                        movie = atwo;
                                    else
                                        movie = athree;
                                    isValid = true;
                                }
                            }
                            catch(InputMismatchException e)
                            {
                                //if the input was en error, its not valid
                                isValid = false;
                                input.nextLine();
                            }
                                    
                        }while(isValid == false);
                        //show the user the theater
                        System.out.println(movie.toString());
                        //call the reserve seats function
                        reserveSeats(movie, audChoice, input, account);
                    } 
                    else if(choice == 2)
                    {
                        //shows the orders
                        System.out.print(account.toString());
                    }
                    else if(choice == 3)
                    {
                        //update orders
                        updateOrder(account, aone, atwo, athree, input);
                    }
                    else if(choice == 4)
                    {
                        //prints receipt
                        System.out.println(account.Receipt());
                    }
                }while(choice != 5);
                loggedin = false; //logs out
            }
            
            
            
        }while(adminExit == false);
       
        //update the auditoriums
        PrintWriter output = new PrintWriter("A1.txt");
        
        aone.fileOutput(output);
        
        output.close();
        
        output = new PrintWriter("A2.txt");
        atwo.fileOutput(output);
        output.close();
        
        output = new PrintWriter("A3.txt");
        athree.fileOutput(output);
        output.close();
    }   
    
    public static void getInput(HashMap customers, Scanner input)
    {
        String username = "", password = "";
        while(input.hasNext())
        {
           username = input.next();
           password = input.next();
           customers.put(username, new Customer(username, password));
        }
    }
    
    public static double calcDistance(double x1, double x2, double y1, double y2)
    {
        //uses the distance formula to calculate distance
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); 
    }
    
    public static int[] findBest(Auditorium movie, int numA, int numC, int numS)
    {
        int i = 0;
        int j = 0;
        int coordinates[] = {-1, -1}; //if this is never updated, then there was never a best seat. we wil know there was no best seta if the array has -1
        int totalTickets = numA + numC + numS; 
        double centerx = (movie.getnumCol() + 1) / 2.0; //finds the center of the columns
        double centery = (movie.getnumRow() + 1) / 2.0; //finds the center of the rows
        double centerSeat = (totalTickets + 1) / 2.0;   //finds the center of our selection
        TheaterSeat nav = movie.getFirst();             //gets the fisrt node of our movie to traverse
        TheaterSeat headOfline = nav;                   //set the first node of the line = to nav
        
        double shortestDistance = calcDistance(0, centerx, 0, centery); //change this to + centerSeat on the x later
        double distance = 0;
        
        //outerloop traverses the rows
        while(i < movie.getnumRow() && nav != null)
        {
            //innerloop traverses the columns 
            while(j + totalTickets - 1 < movie.getnumCol() && nav != null) 
            {
                if(nav.isAvaliable(totalTickets) == true) //if this is a valid spot for us, we will calculate this distance
                {
                    distance = calcDistance(j + centerSeat , centerx, i + 1 ,centery);
                    //if our new distance is less than our shortest distance
                    if(distance < shortestDistance)
                    {
                        shortestDistance = distance;  //set shortest distance = to our new distance
                        coordinates[0] = i + 1;      //update the coordinates array to keep track of this new point/
                        coordinates[1] = j;
                    }
                    if(distance == shortestDistance) //if theres a tie
                    {
                        if(Math.abs(coordinates[0] - centery) > Math.abs((i + 1) - centery)) //pick the coordinates with the closest row
                        {
                            shortestDistance = distance; //update the distance in coordinates
                            coordinates[0] = i + 1;
                            coordinates[1] = j;
                        }
                        else if ((Math.abs(coordinates[0] - centery) == Math.abs((i + 1) - centery))) //else if both of the tie breaker points are on the same row
                        {
                            if(i + 1 < coordinates[0]) //pick the smaller one
                            {
                                shortestDistance = distance;  //update the distance and coordinates
                                coordinates[0] = i + 1;   
                                coordinates[1] = j;
                            }
                        }
                    }
                    
                }
                nav = nav.getRight(); //move our pointer to the right
                j++; 
            }
            j = 0; //after completing a row, set j back to 0
            nav = headOfline; //set nav equal to the first node in the row
            nav = nav.getDown(); //move nav down
            headOfline = nav;  //set the headof the line
            i++; 
        }
        
        
        return coordinates;
    }
    
    public static void reserveSeats(Auditorium movie, int audChoice, Scanner input, 
              Customer account)
    {
        int numA = 0, numC = 0, numS = 0, row = 0;
        boolean isValid = false;
        String check = "";
        int [] bestseat = {0, 0};
        char yesno = ' ', col = ' ';
        do //trys to cath the error while the 
        {
            try
            {
                System.out.println("Enter the row number: "); //ask the user to input the row number
                row = input.nextInt();
                isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception
            {
                isValid = false; //its not valid
                input.next(); 
            }
            if(row < 1 || row > (movie.getnumRow())) //you cant have a rowNum less than one, so this is inavlid 
            {
                System.out.println("The row number must be a number"
                        + " between 1 and " + (movie.getnumRow()));
                isValid = false;
                input.nextLine();
            }
            else 
                isValid = true;
        }while(isValid == false);

        do //trys to cath the error for the saet input
        {
            try
            {
                System.out.println("Enter the seat letter: ");
                check = input.next();
                col = check.charAt(0);
                isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception
            {
                isValid = false; //its not valid
                input.next(); 
            }
            //if the seat is out of bounds or not a letter that is on the screen
            //then its an error
            if(col < (char)65 || col > (movie.getnumCol() + 65) || check.length() > 1)  
            {
                System.out.println("The seat letter must be a letter from A - "
                        + (char)((movie.getnumCol()) + 64));
                isValid = false;
                input.nextLine();
            }
            else //otherwise this is valid
                isValid = true;
        }
        while(isValid == false);

        do //trys to cath the error for the user input
        {
            try //try to find a mismatch exception
            {
                System.out.println("Enter the number of adult tickets: ");
                numA = input.nextInt();
                //if the user enters a negative number, the input is invalid
                //otherwise, the input is valid
                if(numA < 0)
                    isValid = false;
                else 
                    isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception thrown in the try
            {
                isValid = false; //its not valid
                input.nextLine(); 
            }
        }
        while(isValid == false);

        do //trys to cath the error for the user input
        {
            try //looks for a mismatch exceptoon
            {
                System.out.println("Enter the number of child tickets: ");
                numC = input.nextInt();
                //if the user enters a negative number, the input is invalid
                //otherwise, the input is valid
                if(numC < 0)
                    isValid = false;
                else 
                    isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception thrown in the try
            {
                isValid = false; //its not valid
                input.nextLine(); 
            }
        }
        while(isValid == false);

        do //trys to cath the error for the user input
        {
            try //trys to find an inputmismatch exception
            {
                //asks the user for the number of senior tickets
                System.out.println("Enter the number of senior tickets: ");
                numS = input.nextInt();
                //if the user enters a negative number, the input is invalid
                //otherwise, the input is valid
                if(numS < 0)
                    isValid = false;
                else 
                    isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception thrown in the try
            {
                isValid = false; //its not valid
                input.nextLine(); 
            }
        }while(isValid == false);


        //if the number of adults, childre, and seniors is greater than the number of colums
        //then there can't be any seats avalible

        //otherwise, if seatsavalible returns true then the seats can be reserved
        if(numA + numC + numS > movie.getnumCol())
            System.out.println("There are no seats avaiable");
        else if(movie.seatsAvaliable(row - 1, numA, numC, numS, col))
        {
            //int na, int nc, int ns, int r, char c
            account.addOrder(new Orders(numA, numC, numS, row, audChoice, col));
            System.out.println("Your seats have been reserved, thank you!");
        }
        else //otherwise, we need to find the best seat
        {
            bestseat = findBest(movie, numA, numC, numS);
            if(bestseat[0] < 0 && bestseat[1] < 0) //if the coordinates are negative, then no seats are avaliable
                System.out.println("There is no seat avaliable");
            else //otherwise, prompt the user and see if they want to buy tickets at the row that was found
            {
                System.out.println("We found a seat at row " + bestseat[0] 
               + " and column " + (char)(bestseat[1] + 65) + "-" + 
                        (char)(bestseat[1] + 64 + numA + numC + numS));
                System.out.println("Would you like to reserve this seat? (Y/N)");
                yesno = input.next().charAt(0);   
                if(yesno == 'Y' || yesno == 'y') //if they say yes, then update the auditorium
                {
                    movie.updateAuditorium(bestseat[0] - 1, bestseat[1], numA, numC, numS);
                    account.addOrder(new Orders(numA, numC, numS, bestseat[0],
                            audChoice, (char)(bestseat[1] + 65)));
                }
            }

        }
    }
    
    public static void updateOrder(Customer account, Auditorium a1,
            Auditorium a2, Auditorium a3, Scanner input)
    {
        int choice = 0, orderChoice = 0, auditChoice = 0;
        boolean isValid = false;
        Auditorium movie = new Auditorium();
        //if there are no orders, no reason to update
        if(account.getNumorders() == 0)
        {
            System.out.println("There are no orders to update");
            return;
        }
        //show the user the orders
        System.out.println(account.toString());
        do
        {
            try
            {
                //if the order number isn't valid, set this to false
                System.out.println("Enter the order # you want to update: ");
                orderChoice = input.nextInt();
                if(orderChoice < 0 || orderChoice > account.getNumorders())
                    isValid = false;
                else
                    isValid = true;
            }
            catch(InputMismatchException e)
            {
                isValid = false; //set is valid to false
                input.nextLine();
            }
        }while(isValid == false);
        do //trys to cath the error for the user input
        {
            try //try to find a mismatch exception
            {
                System.out.print("1. Add tickets to order\n" +
                                 "2. Delete tickets from order\n" +
                                 "3. Cancel Order\n");
                choice = input.nextInt();
                //if the user enters a negative number, the input is invalid
                //otherwise, the input is valid
                if(choice < 0 || choice > 3)
                    isValid = false;
                else
                    isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception thrown in the try
            {
                isValid = false; //its not valid
                input.nextLine(); 
            }
        }while(isValid == false);
            
        //set movie to their choice of auditorium
        
        Orders n = account.getOrder(orderChoice - 1);
        auditChoice = n.getAud();
        if(auditChoice == 1)
            movie = a1;
        else if(auditChoice == 2)
            movie = a2;
        else
            movie = a3;
        //if choice is 1, add to their order
        if(choice == 1)
        {
            addToOrder(n, movie, input);
        }
        //if choice is two, delete a seat from their order
        else if(choice == 2)
        {
            deleteTickets(n, movie, account, input);
        }
        //otherwise, delete the entire order
        else
        {
            int row = 0;
            char col = ' ';
            int index = 0; //will keep track on the blank space
            String del = n.printSeats();
            String element = "";
            //while delete isn't empty
            while(del != "")
            {
                index = del.indexOf(' ');
                //if index is -1, there are no spaces left
                if(index == -1)
                {            
                    del = "";
                }
                else
                {
                    //first, we get the individual seat
                    element = del.substring(0, index);
                    //the second character denotes the column
                    col = element.charAt(1);
                    //the first character is the row
                    element = element.substring(0, 1);
                    //turn teh string into an ent
                    row = Integer.parseInt(element);
                    //delete the element from our string
                    del = del.substring(index + 1);
                }
                //delete the seat at the row and column
                movie.deleteSeat(row, col);
            }
            //removes the order
            account.removeOrder(n);
            //tell the user that it was deleted
            System.out.println("Your order has been deleted");
        }
        
    }
    
    public static void deleteTickets(Orders n, Auditorium movie, 
            Customer acc, Scanner input)
    {
        String c = "";
        int row = 0;
        char col = ' ';
        boolean isValid = false;
        //loops unti isValid is true
        do
        {
            /*ask the user for the row. if the row does not exist, it will loop
            again*/
            try
            {
                System.out.println("What row is the ticket on "
                        + "that you'd like to delete?");
                row = input.nextInt();
                /*if the row is less than one or greater than the total number
                of rows, isValid is false*/
                if(row < 1 || row > movie.getnumRow())
                    isValid = false;
                else //otherwise, isValid is true and the loop will end
                    isValid = true;
              
            }
            catch(InputMismatchException e)
            {
                //if the users input isnt valid, isvalid will be false
                isValid = false;
                input.nextLine();
            }
        }while(isValid == false);
        //loops until isValid = true
        do
        {
            try
            {
                //ask the user what they want
                System.out.println("What column is the ticket on "
                        + "that you'd like to delete?");
                c = input.next();
                col = c.charAt(0);
                //of the colloumn isn't a letter between A- (End letter), its invalid
                if(col < (char)65 || col > (movie.getnumCol() + 65) || c.length() > 1)  
                {
                    System.out.println("The seat letter must be a letter from A - "
                            + (char)((movie.getnumCol()) + 64));
                    isValid = false;
                    input.nextLine();
                }
                else //otherwise its valid
                    isValid = true;
            }
            catch(InputMismatchException e)
            {
                //for invalidinput, isValid is still false
                isValid = false;
                input.next(); 
            }
        }while(isValid == false);
        //if delete seats succesfully deleted the seat
        if(n.deleteSeats(row, col))
        {
            //delete those same seats from the movie
            movie.deleteSeat(row, col, n);
            //if there are no seats left
            if(n.isEmpty())
            {
                //remove the order
                acc.removeOrder(n);
            }
            //tell the user that the seats have been deleted 
            System.out.println("Your seats have been succesfully deleted!");
        }
        else //if the seats were not deleted sucesfully, tell the user they could not be deleted
            System.out.println("Your seats cannot be deleted");
            
    }
    
    public static void addToOrder(Orders n, Auditorium movie, Scanner input)
    {
        int numA = 0, numC = 0, numS = 0, row = 0;
        char col = ' ';
        boolean isValid = false;
        String check = "";
        System.out.println(movie.toString());
        do //trys to cath the error while the 
        {
            try
            {
                System.out.println("Enter the row number: "); //ask the user to input the row number
                row = input.nextInt();
                isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception
            {
                isValid = false; //its not valid
                input.next(); 
            }
            if(row < 1 || row > (movie.getnumRow())) //you cant have a rowNum less than one, so this is inavlid 
            {
                System.out.println("The row number must be a number"
                        + " between 1 and " + (movie.getnumRow()));
                isValid = false;
                input.nextLine();
            }
            else 
                isValid = true;
        }while(isValid == false);

        do //trys to cath the error for the saet input
        {
            try
            {
                //ask the user to enter a seat letter
                System.out.println("Enter the seat letter: ");
                //get their input
                check = input.next();
                col = check.charAt(0);
                isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception
            {
                isValid = false; //its not valid
                input.next(); 
            }
            //if the seat is out of bounds or not a letter that is on the screen
            //then its an error
            if(col < (char)65 || col > (movie.getnumCol() + 65) || check.length() > 1)  
            {
                System.out.println("The seat letter must be a letter from A - "
                        + (char)((movie.getnumCol()) + 64));
                isValid = false;
                input.nextLine();
            }
            else //otherwise this is valid
                isValid = true;
        }
        while(isValid == false);

        do //trys to cath the error for the user input
        {
            try //try to find a mismatch exception
            {
                System.out.println("Enter the number of adult tickets: ");
                numA = input.nextInt();
                //if the user enters a negative number, the input is invalid
                //otherwise, the input is valid
                if(numA < 0)
                    isValid = false;
                else 
                    isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception thrown in the try
            {
                isValid = false; //its not valid
                input.nextLine(); 
            }
        }
        while(isValid == false);

        do //trys to cath the error for the user input
        {
            try //looks for a mismatch exceptoon
            {
                System.out.println("Enter the number of child tickets: ");
                numC = input.nextInt();
                //if the user enters a negative number, the input is invalid
                //otherwise, the input is valid
                if(numC < 0)
                    isValid = false;
                else 
                    isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception thrown in the try
            {
                isValid = false; //its not valid
                input.nextLine(); 
            }
        }
        while(isValid == false);

        do //trys to cath the error for the user input
        {
            try //trys to find an inputmismatch exception
            {
                //asks the user for the number of senior tickets
                System.out.println("Enter the number of senior tickets: ");
                numS = input.nextInt();
                //if the user enters a negative number, the input is invalid
                //otherwise, the input is valid
                if(numS < 0)
                    isValid = false;
                else 
                    isValid = true;
            }
            catch(InputMismatchException e) //if there is an input mismatch exception thrown in the try
            {
                isValid = false; //its not valid
                input.nextLine(); 
            }
        }while(isValid == false);


        //if the number of adults, childre, and seniors is greater than the number of colums
        //then there can't be any seats avalible

        //otherwise, if seatsavalible returns true then the seats can be reserved
        if(numA + numC + numS > movie.getnumCol())
            System.out.println("There are no seats avaiable");
        else if(movie.seatsAvaliable(row - 1, numA, numC, numS, col))
        {
            n.addSeats(numA, numC, numS, row, col);
            System.out.println("Your seats have been reserved, thank you!");
        }
        else
        {
            System.out.println("Your seats are not avaliable");
        }
    }
    
    public static void printReport(Auditorium a1, Auditorium a2, Auditorium a3)
    {
        //35 empty spaces right justified, then all other elements will be
        //18 spaces apart.
        //then the category is left justified
        //7 row, 3 columns (one for each auditorium
        System.out.printf("%35s", "Auditorium 1");
        System.out.printf("%18s", "Auditorium 2");
        System.out.printf("%18s", "Auditorium 3");
        System.out.println();
        
        //prints the number of empty seats for each auditorum
        System.out.print("Open Seats:            ");
        System.out.printf("%-18s", a1.getNumEmpty());
        System.out.printf("%-18s", a2.getNumEmpty());
        System.out.printf("%-18s", a3.getNumEmpty());
        System.out.println();
        
        //prints the number of reserved seats for each auditorium
        System.out.print("Total Reserved Seats:  ");
        System.out.printf("%-18s", a1.getNumReserved());
        System.out.printf("%-18s", a2.getNumReserved());
        System.out.printf("%-18s", a3.getNumReserved());
        System.out.println();
        
        //prints the nummber of adult tickets for each auditorium
        System.out.print("Adult Seats:           ");
        System.out.printf("%-18s", a1.getNumAdult());
        System.out.printf("%-18s", a2.getNumAdult());
        System.out.printf("%-18s", a3.getNumAdult());
        System.out.println();
        
        //prints the number of children seats for each auditorium
        System.out.print("Child Seats:           ");
        System.out.printf("%-18s", a1.getNumChild());
        System.out.printf("%-18s", a2.getNumChild());
        System.out.printf("%-18s", a3.getNumChild());
        System.out.println();
        
        //prints the number of senior seats for each auditorium
        System.out.print("Senior Seats:          ");
        System.out.printf("%-18s", a1.getNumSenior());
        System.out.printf("%-18s", a2.getNumSenior());
        System.out.printf("%-18s", a3.getNumSenior());
        System.out.println();
        
        //prints the total sales for each auditorium
        System.out.print("Ticket Sales:          ");
        System.out.printf("%-18s", a1.getMoney());
        System.out.printf("%-18s", a2.getMoney());
        System.out.printf("%-18s", a3.getMoney());
        System.out.println();
    }
    
    
}
