package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
//    TODO 6. Use dependency injection to use an instance of this new Posts interface.
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;


    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    //    TODO: Create a new array list and add two post objects to it, then pass that list to the view.md.
//    @RequestMapping(path = "/posts", method = RequestMethod.GET)
//    public String viewAllPosts(Model model) {
//        List<Post> allPosts = new ArrayList<>();
//        allPosts.add(new Post(1, "First Post ever!", "Wow Spring is so cool!"));
//        allPosts.add(new Post(2, "Second Post ever!", "Wow Spring is still so cool!"));
//        allPosts.add(new Post(3, "Third Post ever!", "Wow Spring not so cool!"));
//        allPosts.add(new Post(4, "Fourth Post ever!", "Wow Spring is not cool at all!"));
//
//        model.addAttribute("posts", allPosts);
//
//        return "/posts/index";
//    }

    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public String viewAllPosts(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "/posts/index";
    }

//    TODO: Create a new post object and pass it to the view.md.
    @RequestMapping(path = "/posts/{id}", method = RequestMethod.GET)
    public String viewIndividualPosts(@PathVariable long id, Model model) {
    model.addAttribute("singlePost", postDao.getById(id));
    return "/posts/show";
}

//    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
//    public String viewCreatePostForm() {
//        return "posts/create";
//    }
//
//    @PostMapping("/posts/create")
//    public String savePost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
//        postDao.save(new Post(title, body, userDao.getById(1L)));
//        return "redirect:/posts";
//    }

//    TODO FORM MODEL BINDING 1.
//    Refactor your PostController and create form to implement form model binding.
    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String create(@ModelAttribute Post post) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(currentUser);        // save the post...
        postDao.save(post);
        emailService.prepareAndSend(post, "Post saved");
        // redirect to the index with all the ads
        return "redirect:/posts";
    }
//    TODO FORM MODEL BINDING 2.
//    Create a controller method and HTML template for viewing a form to edit a specific post.
//    @GetMapping("/posts/{id}/edit")
//    public String editPosts(Model model, @PathVariable long id) {
//        model.addAttribute("post", postDao.getById(id));
//        return "posts/create";
//    }
@RequestMapping(path = "/posts/{id}/edit", method = RequestMethod.GET)
public String viewEditPostForm(Model model, @PathVariable long id) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(currentUser.getId() == postDao.getById(id).getUser().getId()) {
        model.addAttribute("title", "Edit post");
        model.addAttribute("post", postDao.getById(id));
        return "posts/create";

    } else{
        return "redirect:/login";
    }
}

    @PostMapping("/posts/{id}/edit")
    public String submitEditForm(@PathVariable long id, @RequestParam String title, @RequestParam String body) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post postToEdit = postDao.getById(id);

        postToEdit.setTitle(title);
        postToEdit.setBody(body);
        postToEdit.setUser(currentUser);

        postDao.save(postToEdit);
        return "redirect:/posts/" + id;
    }
}