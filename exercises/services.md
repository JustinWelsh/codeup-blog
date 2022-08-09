#Spring Fundamentals
*Services*

##Notes/Setup:
This section is just for testing purposes.

* add dependency to [pom.xml](/Users/justinwelsh/IdeaProjects/springblog/pom.xml)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
    <version>2.1.2.RELEASE</version>
</dependency>
```

* add [application.properties](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/application.properties)

```springdataql
spring.mail.host=smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=username
spring.mail.password=password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.from=admin@example.com 
```
* Sign up for Mailtrap.io
* change username and password from mailtrap.
* (integration, Play-mailer)

* Add a 'services' package?
#Exercise:

1. Create your own [EmailService]() class and inject it into your [PostController](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/services/EmailService.java). The service should send an email when a post is created to the user who created it using the getEmail() from the [User](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/models/User.java) class. So far the user could be hardcoded (Later on we will grab the user who's currently logged in the app).

```java
//EmailService

@Service("mailService")
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void prepareAndSend(Post post, String subject) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(post.getUser().getEmail());
        msg.setSubject(subject);
        msg.setText(String.format("Your post has the following information:%nTitle: %s%nBody: %s", post.getTitle(), post.getBody()));
        try{
            this.emailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
```
```java
//PostController

 private final EmailService emailService;


    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
//        this.postDao = postDao;
//        this.userDao = userDao;
        this.emailService = emailService;
    }

//@PostMapping("/posts/create")
//public String create(@ModelAttribute Post post) {
//        post.setUser(userDao.getById(1L));
//        postDao.save(post);
        emailService.prepareAndSend(post, "Post saved");
//        return "redirect:/posts";
//        }
```

2. Create a few test posts so that you have some data that you can prove that it works fine and does not break the create post process.

##Walkthrough Notes:

* EmailService
* HelloController
* PostController
* Create a new post
* Go to mailtrap inbox to verify email sent.
* 