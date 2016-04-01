Overview:
In this homework assignment, I have created a simple note-taking app that allows you to take a photo and associate a caption for each picture. 

Following concepts of Android Application Development are used in this project: SQLite storage, file storage, and the camera.

Activities

The app has 3 activities:
1. List Activity:
The list activity displays the list of saved notes. Each row can be represented with a simple of piece of text. 
Displays a Floating Action Button for adding a new photo, which launches the "Add Photo Activity" (described below).

2. View Photo Activity:
This activity is simple: it displays the photo and the caption that was clicked on in the List Activity.
This is launched whenever you click on an item in the list.

3. Add Photo Activity
This activity has 3 fields:
EditText field for the caption
Button for taking the photo which launches the camera intent.
Save button. This returns to the list activity.

Details: The finish()method destroys the current activity and restore the previous activity.
SQLite

This app uses SQLite. There is a table with at least two fields:
Caption field - This is a user-specified text field.
Path field - This holds the absolute path to the photo.

Menu:
The Menu should have an Uninstall option (similar to Homework 2).

Orientation:
This app works both in landscape and portrait mode.

