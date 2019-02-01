# Movie-Ticket-Reservation-System
Allows users to reserve seats at a movie theater 

The code can be found in the src file

The user is first asked to enter their username and password. Using Hashing, we check their username and password in O(1) time.
The user can then reserve seats, remove seats, view their reciept, update their orders, or logout.

If their order isn't possible (usually because someones seat is already reserved), the program will accomodate them by finding 
the seating that has enough consecutive empty seats to fill their order while also finding the closest seat to the center.

There is also an admin login. The admin is lazy, so his username is admin. Once he logs in, he can exit the program or view the status of
every theater. The admin can print a report thad displays the amount of money made from every theater.
