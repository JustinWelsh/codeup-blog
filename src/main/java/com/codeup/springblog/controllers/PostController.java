package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Scanner;

@Controller
public class PostController {
//    TODO 6. Use dependency injection to use an instance of this new Posts interface.
    private PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
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

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    public String viewCreatePostForm() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String savePost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
        postDao.save(new Post(title, body));
        return "redirect:/posts";
    }
}