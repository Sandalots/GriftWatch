# GriftWatch
## Application Overview
**Developer Name:**  Sandy MacDonald - 2003619 <br>

**Application Name:**  GriftWatch <br>

**Summary:** A mobile application to curate and rate grifters (GriftRank ascending), the app will already be pre-populated with noteworthy grifters and users can add new ones if they so desire. <br>

**App Functionalities:** <br>
Number of Pages: **6:** Activities: **1**, Fragments: **5**
* **Home** - Where the feed of grifters in displayed on application launch along with the create new grifter button, the sole activity.
* **Feed** - Contains entrys of a grifter, along with their claim to fame plus their current ranking and a star button to increment the rank, as-well as include a button to add new Grifter entries.
* **Create a Grifter** - Contains input fields to create a new grifter to the Application grifter database.
* **GriftBoard** - The leaderboard of grifters, you rate a grifter they gain a place on the Griftboard, Ranking Descending.
* **Help and Settings** - A page to help users use the application and serves as a tutorial as-well to give options to turn off Notifications or reset the Application.
* **Resources** - A page filled with third-party resources to help you identify grifters and grifts.

All pages bar the Home Activity are implemented as fragments making use of ConstraintViews for Android UI best practice (better performance and total negation of layout nestation), for use in the Home Activity to help with sharing data between the abstracted pages.

**Features:**
* Home displays the feed list that uses the RecyclerView Class to display the grifter entires.
* The entries are saved locally using a RoomSQL database and syncs using a HTPP Firebase API request. 
* The feed also contains a button that allows for user input of a new Grifter entry.
* The GrifterBoard that sorts each Grifter entry by their rank that the user increments/rates.
* The ability to get to the resources page that hosts third-party links to ways to spot a grifter.
* The ability to long press a grifter to open in a web browser.
* The ability to reset the feed and request notification permissions and get app build number.
* Settings switch toggles to toggle features on and off.
* The ability to rank and derank grifter entries. 

**Word Count: 274.**

### Demonstrations
[verticalDemo.webm](https://github.com/RobertGordonUniversity/cm3110-coursework-Sandalots/assets/59518103/b3c3bdef-c649-4f5e-9a0e-f2c4fdab44d6)

[HorizontalDemo.webm](https://github.com/RobertGordonUniversity/cm3110-coursework-Sandalots/assets/59518103/12a59029-f0a9-4b5e-8e83-008b46be186c)

## Application Design
### Use Case Diagrams
#### End User
![End User](<Assets/User Case/endUser.png>)

#### Web Service Adminstrator
![Web Service Adminstrator](<Assets/User Case/webAdmin.png>)

### Task Flow
![Create Grifter Task](<Assets/createGrifterTask.png>)

### User Flow
![User Flow](<Assets/flow.png>)

### Wireframes
#### Feed
##### Figma
![Feed Screenshot](<Assets/feedScreen.png>)

##### Final
![Feed Final](<Assets/FeedFinal.png>)

#### Navigation Drawer
![Navigation Drawer Screenshot](<Assets/navScreen.png>)

##### Final
![Drawer Final](<Assets/DrawerFinal.png>)

#### GriftBoard
##### Figma
![Griftboard Screenshot](<Assets/griftBoardScreen.png>)

##### Final
![GriftBoard Final](<Assets/GriftBoardFInal.png>)

##### Components
![GriftBoard Components](<Assets/GriftBoardCom.png>)

#### Resources
##### Figma
![Resources Screenshot](<Assets/resourcesScreen.png>)

##### Final
![Resources FInal](<Assets/ResourcesFinal.png>)

##### Components
![Resources Components](<Assets/resourcesCom.png>)

#### Help and Settings
##### Figma
![Help & Settings Screenshot](<Assets/helpScreen.png>)

##### Final
![Help & Settings Final](<Assets/HelpFinal.png>)

##### Components
![Help Components](<Assets/HelpCom.png>)

#### Create a New Grifter
##### Figma
![Create a New Grifter Screenshot](<Assets/createNewScreen.png>)

##### Final
![Create Grifter Final](<Assets/createFinal.png>)

##### Components
![Create New Components](<Assets/CreateNewCom.png>)

### Navigation 
#### Figma
![Navigation Figma Design](<Assets/navigationFigma.png>)

### **Web Service:**
URL: https://griftwatchwebservices-default-rtdb.europe-west1.firebasedatabase.app/grifters.json <br><br>
The Firebase Realtime web-service will define the current grifter database and allow for the application to make string requests and upload to it upon new grifter creations.
* The application uploads the current state of the Grifter database so that new users always get the most recent version of the Grifter Database.
* The Web Service serves out the JSON representation of the database to the Java Application whereby the GriftWatch application and parses the JSON using volley and updates itself from the web-service in-order to maintain data even when offline.
* Downloads the latest changes to the initial database status and uploads them to the user on the Application.
* Uploads new grifters and the newly changed griftrank upon these events.
* Will show error toast if the connection is unsuccesful.

### **RoomSQL Database:**
Stores Grifter entries into one table named 'Grifter' with the following columns of data:
* Name
* Grifts
* Country
* GriftRank (initially set to 0 upon creation of a new grifter) Saves the ranked/deranked state/count of the rank
* ID (automatically assigned without user input) (Also the primary key for the table will replace upon conflict)

### **Mobile Specific Features:**
* Orientation - Draws the Application contents in either Portrait or Landscape modes. (The Application is works fully in landscape with additional scrollviews and other tweaks to make sure the layout caters to the landscape paradigm)
* Toast Notifications - Alerts the user to feed changes and cancellation of new grifter creations among other smaller things.
* Web Browser interactivity - Long press a grifter entry to go to a google search request of said grifter in the default web-browser.
* Links also open in the relevant web browser so users can easily navigate to said such helpful links.
* Dialogue to request notification permissions from Settings.
* Data persistance and fall-back - current state of the feed is stored in case of no web connectivity. Will update the feed when wen commectivity resumes. 

### Lifetime Events
* App will respond to orientation change and display the correct styling when either on Portrait or Landscape state.
* The Feed will repopulate itself after app start to redraw how it appeared as before. It will get the current instance of the Firebase Realtime Database and populate the feed on the current state of the Database.
* After creation of a new grifter, the application will append this new entry to the repo and later to the Firebase Realtime Database.
* The GriftBoard will update to showcase the new grift rankings of all grifters, first second and third place will also get given a customised emoji to showcase their important positioning.
* The feed will always update upon new entry or griftrank change. 

**Word Count: 221.**

## Reflective Statement
On reflection I severly underestimated the importance of web-service functionality in modern mobile applications as it is integral to the lively and pre-poluated and updated GriftWatch database of grifters. Also to note, the styling of the grifter-feed was also underestimated as it was difficult to parse the recyclerview from the firebase and database derided data and to style it in a stylish and readable way.<br>

Furthermore making the UI as responsive and fluid as possible was very important to ensure that my Application was accessible on all types of Android devices, next time I would ensure that the constraints are perfect to ensure no hazards later on. <br> If I were to improve on my implementation I would allow for images built into feed-grifter entries and possibly an onclick that would find an entry and showcase their Wikipedia entry in the default browser. <br>

I would state that the implementation of GriftWatch was a success as all core features were successfully implemented and matched my figma design. However I could have made the feed much more interactive with animations and greater functionality. <br>

Furthermore, the uploading and download of the feed from Firebase had several bugs throughout the implementation state. With uploading occuring before downloading sometimes causing negative race conditions which was eventually resolved but could have been more thoroughly designed and researched more before implementation. <br>

The design was also revamped several times to ensure landscape compatibility, however if the proper constraints were designed upon before these revamps the over-all landscape compatibility would have taken a lot less time to implement. 

## Submission Details
May need to invalidate cache(s) if SDK not found shows. 
