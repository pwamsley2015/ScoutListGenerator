# ScoutListGenerator
Generates team lists for scouts. 

TODO: write a good readme. 

How to use!

Using this is a few step process. 
- First, when the schedule comes out on thebluealliance.com, navigate to the schedule page. Highlight the table, then open up a new google sheets spreadsheet. 
Picture: ![Alt text](https://github.com/pwamsley2015/ScoutListGenerator/blob/master/readme_screenshots/highlighting.png)

- Next, Paste the table into the spreadsheet. Remove every column except for match number and the teams in the match. Picture: ![Alt text](https://github.com/pwamsley2015/ScoutListGenerator/blob/master/readme_screenshots/in%20ss.png)

- Export the spreadsheet as a .csv file. 
Picture: ![Alt text](https://github.com/pwamsley2015/ScoutListGenerator/blob/master/readme_screenshots/Screen%20Shot%202015-08-10%20at%203.11.35%20PM.png)


- Next, in your text editor of choice, edit check the PowerhouseTeams.csv (found in the project folder) to match the scouting team's prefriday opinions. 

- Now you have to actually run the program. The program takes paramaters from the command line as such:

 java ListGenerator filePath NumberOfScouts

 How to run, step by step: 

 	- Open Terminal or Command Prompt 
 	- Change the directory to the projects src folder. 
 	    `cd documents/programming/ScoutListGenerator/src`
 	- Compile all the .java files
 	     `javac *.java`
 	     Note: The reason we recompile these files, rather than just using the ones in /bin/ is to avoid a MainClassNotFoundException causes by the eclipse compiler working differently than javac. 
 	- Run java with the follow paramaters: 
 	    java ListGenerator filePathToSchedule numberOfScouts

 - Once the program runs, just follow dialogs given by the program until the lists print (either to the console or to the default printer). 