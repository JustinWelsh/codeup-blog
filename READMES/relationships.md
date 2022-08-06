#Spring Fundamentals
*Relationships*

###Notes:

#Exercise:
1. Create a models/[User](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/models/User.java) class, with (at least) fields for id, username, email, and password.

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;
}
```


2. In your User and Post classes, define the post - user relationship.

```java
//User Class
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    //"user" points to the user field in the Post Class
    private List<Post> posts;
```
```java
//Post Class
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; 
```

3. Log in to the MySQL server and verify that the generated table structure matches what you are expecting.


4. Manually insert a user record in the database.


5. Create a [UserRepository](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/repositories/UserRepository.java) interface and inject it into the [PostController](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/controllers/PostController.java).

```java
//repositories/UserRepository Interface
public interface UserRepository extends JpaRepository<User, Long> {
}
```
```java
//PostController
    private final UserRepository userDao;
    
    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }
```

6. When a post is created, and before it is saved to the database, assign a user to it. For now, it does not matter which user is assigned, so long as some user is assigned. In the next lesson we will make this functionality more robust.

```java
//@PostMapping("/posts/create")
//public String savePost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
        postDao.save(new Post(title, body, userDao.getById(1L)));
//        return "redirect:/posts";
//        }
```

7. On the [show](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/templates/posts/show.html) page for an individual post, display the email of the user that created the post.

```html
<p th:if="${singlePost.user != null}" th:text="${singlePost.user.email}"></p>
<p th:if="${singlePost.user == null}">No Assigned User.</p>
```


##Walkthrough Notes: