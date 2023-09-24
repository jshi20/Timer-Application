# My Personal Project

## Timer Application Written in Java

The **main purpose** of this application is to allow users to create timers that set off
after a given time has passed. To make this application unique, 
I hope to add some **quality of life** functionalities such as allowing users 
to add titles and short descriptions to timers to make them more meaningful.
I believe a broad audience can make use of this application; 
for example, one can set timers for cooking food, for working out or to wake up. 
Time is a valuable asset and people may need tools to help manage their time.

This project is of interest to me because I would like to manage my time better 
and I believe this timer application may help me (*and potentially others*)
improve on the important skill of time management. I also want to be able to 
add **quality of life** features that I see fit which can improve 
my timer application. In terms of learning, I believe this project can demonstrate
and improve my coding abilities, especially in terms of 
design which is so crucial to successful programs. 


#



# Instructions

- You can generate the first required action related to adding Xs to a Y by first clicking the button labeled “Create new GroupTimer” 
which will prompt you with a pop up 
asking you to give it a title and description; 
after you fill in the two fields you should see the GroupTimer 
displayed in the GUI which represents the Y in this context; 
notice that you can click the button “Add BasicTimer into GroupTimer” and BasicTimer’s
are X’s in this context; you must first though click the “Create new BasicTimer” and 
fill its three required fields of title, description, and seconds till you can add 
any BasicTimer’s to your GroupTimer; after both X’s (arbitrary number of X) and Y have 
been created, you can click on the “Add BasicTimer into GroupTimer” option displayed 
below your GroupTimer to add BasicTimer’s in by title; note that the “Includes :” 
text of the GroupTimer will change

- You can generate the second required action related to adding Xs to a Y 
by pressing the play button; pressing play on a GroupTimer with 
BasicTimer’s added will begin to play all BasicTimer’s associated with that GroupTimer;
note that pressing play continuously on a GroupTimer will reset each of their 
associated BasicTimer’s seconds to their original time; pressing play on a BasicTimer
will begin playing and ticking away at their seconds; 
you can only reset an individual BasicTimer by clicking play after its finished playing
(seconds at 0)

- You can locate my visual component by clicking play on any timer as it will
continuously decrement and display their respective seconds in real time; 
also notice the custom images that appear on the popup when a user tries to
create a new BasicTimer or GroupTimer

- You can save the state of my application by clicking the button 
on the right hand side “Save Timers”

- You can reload the state of my application by clicking the button 
on the right hand side “Load Timers” 



# Sample of Key Events
Below is a representative sample of key events created by a user
that occur when the program is running.


Wed Aug 09 16:47:48 PDT 2023
BasicTimer TestBasicTimer1 has been created

Wed Aug 09 16:47:55 PDT 2023
GroupTimer TestGroupTimer1 has been created

Wed Aug 09 16:48:09 PDT 2023
BasicTimer TestBasicTimer2 has been created

Wed Aug 09 16:48:18 PDT 2023
GroupTimer TestGroupTimer1 has added TestBasicTimer2 to its timers

Wed Aug 09 16:48:20 PDT 2023
Began Playing BasicTimer TestBasicTimer1

Wed Aug 09 16:48:23 PDT 2023
GroupTimer TestGroupTimer1 has began playing all its timers

Wed Aug 09 16:48:23 PDT 2023
Began Playing BasicTimer TestBasicTimer2

