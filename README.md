# Queuing_System

https://theighties.herokuapp.com/

Registration requares no password. Press "Register", choose mentor and thats it.
Reservation check happens with provided serial number. Press check and enter serial.

In the top left corner is login button. Through it you can login to users and public screen.
Now there are two users. 
  Don Johnson:
    username: don
    password: pass1

  David Hasselhoff:
    username: dave
    password: pass2
    
In users screen can be started, ended or canceled meeting.

Info screen can be reached by loging in with credentials:
  username: screen
  password: screen
  
Log out button appiers in top left corner (insted of login button)
   
Time frames are described in /src/main/resources/application.properties

  Assumptions:
  
    works starts at 08:00
    
    work ends at 16:00
    
    every customer gets 15 min
