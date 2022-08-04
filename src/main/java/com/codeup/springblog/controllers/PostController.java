package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

//    TODO: Create a new array list and add two post objects to it, then pass that list to the view.md.
    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public String viewAllPosts(Model model) {
        List<Post> allPosts = new ArrayList<>();
        allPosts.add(new Post(1, "First Post ever!", "Wow Spring is so cool!"));
        allPosts.add(new Post(2, "Second Post ever!", "Wow Spring is still so cool!"));
        allPosts.add(new Post(3, "Third Post ever!", "Wow Spring not so cool!"));
        allPosts.add(new Post(4, "Fourth Post ever!", "Wow Spring is not cool at all!"));

        model.addAttribute("posts", allPosts);

        return "/posts/index";
    }


//    TODO: Create a new post object and pass it to the view.md.
    @RequestMapping(path = "/posts/{id}", method = RequestMethod.GET)
    public String viewIndividualPosts(@PathVariable long id, Model model) {
        model.addAttribute("singlePost", new Post(5, "Single post ready to mingle", "New post, new number, new me, new person?"));
        return "/posts/show";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
    public String viewCreatePostForm() {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String savePost() {
        return null;
    }
}