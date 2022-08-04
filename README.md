Controllers
===

Notes:
===
* Will need to type out the 'localhost:8080' inside of the URL to view the project.

-------------------
Fundamentals/View
===
Notes:
===
* Frontend stuff will be in the resources package

Exercise:
===

Passing data to views

1. Create a page at /roll-dice that asks the user to guess a number. There should be links on this page for 1 through 6 that should make a GET request to /roll-dice/n where n is the number guessed. This page should display a random number (the dice roll), the user's guess and a message based on whether or not they guessed the correct number.


    DiceGuessController
    roll-dice.html

BONUS

"Roll" a series of dice on each page load, as opposed to an individual die. Show all the rolls to the user and display how many rolls matched their guess.

--------------------------
Templating

1. Create partials for a navbar, header, and footer.


    fragment.html

2. Include your partials as necessary on various pages throughout your site.
--------------------------
Build out views for posts

1. Create a Post class. This class will ultimately represent a POST record from our database. The class should have private properties and getters and setters for a title and body.


    models/Post

2. Create views for viewing all the posts and for viewing one individual post. 


* Create a subdirectory named posts inside of templates.
* Name these two files index.html and show.html.


    index.html
    show.html

3. Edit your PostController class to return the views you created above.


    PostController

* Inside the method that shows an individual post, create a new post object and pass it to the view.
* Inside the method that shows all the posts, create a new array list and add two post objects to it, then pass that list to the view.
In these two pages, you should display information based on the data passed from the controller.


Walkthrough Notes:
===
-------------------
Fundamentals/Repositories and JPA
===
Notes:
===

Exercise:
===

Walkthrough Notes:
===

-------------------
Fundamentals/Relationships
===
Notes:
===

Exercise:
===

Walkthrough Notes:
===

-------------------
Fundamentals/Form Model Binding
===
Notes:
===

Exercise:
===

Walkthrough Notes:
===

-------------------
Fundamentals/Services
===
Notes:
===

Exercise:
===

Walkthrough Notes:
===

-------------------
Fundamentals/Security
===
Notes:
===

Exercise:
===

Walkthrough Notes:
===

-------------------
Fundamentals/Intergration Tests
===
Notes:
===

Exercise:
===

Walkthrough Notes:
===




