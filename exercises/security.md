#Spring Fundamentals
*Security*

##Notes/Setup:
*[pom.xml](/Users/justinwelsh/IdeaProjects/springblog/pom.xml) file dependency*
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        <version>3.0.4.RELEASE</version>
    </dependency>
```
---------------

####Authentication

After this lesson you'll add/modify several classes: 
* two models ([User](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/models/User.java), [UserWithRoles](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/models/UserWithRoles.java))
* one repository ([UserRepository](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/repositories/UserRepository.java))
* one service ([UserDetailsLoader](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/services/UserDetailsLoader.java))
* configuration class ([SecurityConfiguration](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/config/SecurityConfiguration.java)).
* two controllers ([AuthenticationController](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/controllers/AuthController.java), [UserController](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/controllers/UserController.java))

In order to test the integration of this component we will also need:

* [signup](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/templates/signup.html) page 
* [login](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/templates/login.html) page 
* logout form
-------------------

#Exercise:
1. Using the examples from this lesson, secure your blog application such that you have to log in to create a post.

*models*
```java
//User (update)

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
    }
```

```java
//UserWithRoles (create)

public class UserWithRoles extends User implements UserDetails {

    public UserWithRoles(User user) {
        super(user);  // Call the copy constructor defined in User
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = ""; // Since we're not using the authorization part of the component
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```
*repository*
```java
//UserRepository

public interface UserRepository extends JpaRepository<User, Long> {
    //Method to find users by their username.
    User findByUsername(String username);
}
```
*services*
```java
//UserDetailsLoader (create)

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository users;

    public UserDetailsLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new UserWithRoles(user);
    }
}
```
*Configuration*
```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // append a query string value
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                .antMatchers("/", "/posts", "/profile") // anyone can see the home and the ads pages
                .permitAll()
                /* Pages that require authentication */
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/posts/create", // only authenticated users can create posts
                        "/posts/{id}/edit" // only authenticated users can edit post
                )
                .authenticated()
        ;
        return http.build();
    }

}
```
*Controllers*
```java
//UserController (create)

public class UserController {
    private UserRepository usersDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String showSignUp(Model model) {
        model.addAttribute("newUser", new User());
        return "signup";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        usersDao.save(user);
        return "redirect:/profile";
    }
```

```java
//AuthController (create)

@Controller
public class AuthController {
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
```



2. Test this functionality: ensure that you get redirected to the login page if you try to create a post without being logged in.


3. When a post is created, assign the logged in user as the owner of that post.

##Walkthrough Notes:
* Security configuration
```java
                        "/posts/create", // only authenticated users can create posts
                        "/posts/{id}/edit" // only authenticated users can edit post
```
* PostController
```java
//    @PostMapping("/posts/create")
//    public String create(@ModelAttribute Post post) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(currentUser);
//        postDao.save(post);
//        emailService.prepareAndSend(post, "Post saved");
//        return "redirect:/posts";
//    }

@RequestMapping(path = "/posts/{id}/edit", method = RequestMethod.GET)
public String viewEditPostForm(Model model, @PathVariable long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentUser.getId() == postsDao.getById(id).getUser().getId()) {
        model.addAttribute("title", "Edit post");
        model.addAttribute("post", postsDao.getById(id));
        return "posts/create";

        } else{
        return "redirect:/login";
        }
        }

@PostMapping("/posts/{id}/edit")
public String submitEditForm(@PathVariable long id, @RequestParam String title, @RequestParam String body) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post postToEdit = postsDao.getById(id);

        postToEdit.setTitle(title);
        postToEdit.setBody(body);
        postToEdit.setUser(currentUser);

        postsDao.save(postToEdit);
        return "redirect:/posts/" + id;
        }
```
* fragment.html
```html
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">


            <th:block sec:authorize="isAuthenticated()">
                <form class="d-flex" th:action="@{/logout}" th:method="POST">
                    <button type="submit" class="btn btn-outline-success">Logout</button>
                </form>
            </th:block>
            <th:block sec:authorize="!isAuthenticated()">
                <div class="d-flex">
                    <a href="/login" class="btn btn-outline-success">Login</a>
                </div>
            </th:block>
```

