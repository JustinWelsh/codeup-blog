#Spring Fundamentals

#Repositories & JPA

Notes:
---

#Exercise:

1. Create a new database and database user for your application.

2. Put your database credentials in the `application.properties` file.

3. Update the `example.properties` file with the keys that you added to the `application.properties` file, but not the actual values (i.e. don't put your real database credentials here).

4. Add the appropriate JPA annotations to your `Post` class to create the table and columns.

5. Create an interface for posts named `PostRepository` that extends `JpaRepository`.

6. Use dependency injection to use an instance of this new Posts interface.

7. Create a `posts/create.html` view inside of the templates folder. This HTML page should contain a form for creating a new post.

8. Change your controller method for showing the post creation form to actually show the form created in the step above.

9. Use what you have learned in this lesson to have the post creation form submit a `POST` request to the controller. Have the controller create a `Post` object and persist it using the `PostRepository`.

10. After the Post is created, you should redirect the user to the posts index page (i.e. /posts). You can redirect by returning a string from a controller method that starts with `"redirect:"`. For example:

## Code block
```java
    @GetMapping("/rick-roll")
    public String rickRoll() {
    // redirecting to an absolute url
    return "redirect:https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    }
    @GetMapping("/redirect-me")
    public String redirect() {
    // a relative (to the base domain) redirect, usually you will use this
    // version
    // Will redirect the users to `/about`
    return "redirect:/about";
    }
```


1. Refactor your `PostController`, posts index page, and posts show page to implement create and read functionality.

2. As of right now, we are rendering hardcoded Post objects from the last lesson. Instead, we want to read data that is coming from our database, as well as save new posts.

Walkthrough Notes:
---