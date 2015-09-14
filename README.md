# ScoutListGenerator
Generates team lists for scouts. 

How to use!

Using this is a few step process. 
- First, when the schedule comes out on [The Blue Aliance](https://www.thebluealliance.com), navigate to the schedule page. Highlight the table, then open up a new google sheets spreadsheet. 

![Alt text](https://github.com/pwamsley2015/ScoutListGenerator/blob/master/readme_screenshots/highlighting.png)

- Next, Paste the table into the spreadsheet. Remove every column except for match number and the teams in the match.

![Alt text](https://github.com/pwamsley2015/ScoutListGenerator/blob/master/readme_screenshots/in%20ss.png)

- Export the spreadsheet as a .csv file.
![Alt text](https://github.com/pwamsley2015/ScoutListGenerator/blob/master/readme_screenshots/Screen%20Shot%202015-08-10%20at%203.11.35%20PM.png)

- Next (or well in advance), in your text editor of choice, edit check the PowerhouseTeams.csv (found in the project folder) to match the scouting team's prefriday opinions. 

- Now you have to actually run the program. The program takes paramaters from the command line as such:

 java ListGenerator filePathToScheduleCSV filePathToPowerhouseTeamsCSV numberOfScouts

 How to run, step by step: 

    - You'll probably have to recompiile all the .java files because we use different machines. You can do this with either `javac .java` or by just opening up the project in eclipse and clicking Project -> Clean... -> OK

 	- Open Terminal or Command Prompt 
 	- Change the directory to the projects bin folder.

 	`cd MySuperAwesomeFolderName/Programming/ScoutListGenerator/bin`
 	
 	- Then run java with the following paramaters: 

 	`java ListGenerator filePathToScheduleCSV filePathToPowerhouseTeamsCSV numberOfScouts`

 - Once the program runs, just follow dialogs given by the program until the lists print (either to the console or to the default printer). 
