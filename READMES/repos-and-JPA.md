#Spring Fundamentals

#Repositories & JPA

##Notes:


#Exercise:

* Configuration - Add Spring Boot dependency to your [pom.xml](/Users/justinwelsh/IdeaProjects/springblog/pom.xml) file.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
<groupId>mysql</groupId>
<artifactId>mysql-connector-java</artifactId>
</dependency>
```


1. Create a new database and database user for your application.
* login to mySQL server using root user then proceed with exercise.

```mysql
CREATE DATABASE IF NOT EXISTS springblog_db;

CREATE USER 'springblog_user'@'localhost' IDENTIFIED BY 'spring';
GRANT ALL ON springblog_db.* TO springblog_user@localhost;

# Terminal MySQL Commands:
# mysql -u springblog_user -p
# spring
```
*mySQL commands to verify work*
```mysql
SELECT user, host FROM mysql.user;
# springblog_user

SHOW DATABASES; 
# springblog_db

```

2. Put your database credentials in the [application.properties](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/application.properties) file.


3. Update the [example.properties](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/example.properties) file with the keys that you added to the `application.properties` file, but not the actual values (i.e. don't put your real database credentials here).


4. Add the appropriate JPA annotations to your [Post](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/models/Post.java) class to create the table and columns.

```java
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String body;
```
*mySQL commands to verify work*
```mysql
USE springblog_db;
SHOW TABLES;
DESCRIBE posts;
```

5. Create an interface for posts named [PostRepository](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/repositories/PostRepository.java) that extends `JpaRepository`

```java
public interface PostRepository extends JpaRepository<Post, Long> {
}
```
6. Use dependency injection to use an instance of this new Posts interface.

```java
    // Inside the PostController
    // Defining a single instance of the DAO to be used in the Controller
    // Used to communicate with and make changes to the Database
    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }
```
7. Create a [posts/create.html](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/templates/posts/create.html) view inside of the templates folder. This HTML page should contain a form for creating a new post.


8. Change your controller method for showing the post creation form to actually show the form created in the step above.


9. Use what you have learned in this lesson to have the post creation form submit a `POST` request to the controller. Have the controller create a `Post` object and persist it using the `PostRepository`.


10. After the Post is created, you should redirect the user to the posts index page (i.e. /posts). You can redirect by returning a string from a controller method that starts with `"redirect:"`. For example:

### Code Block
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