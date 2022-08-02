package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {


//    No need to have an @RequestBody
//    Why? 


    @GetMapping("/posts")
    @ResponseBody
    public String viewPosts() {
        return "posts index page\n";
    }

    @RequestMapping(path = "/posts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String individualPost(@PathVariable int id) {
        return String.format("view an individual post at id: %d\n", id);
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "view the form for creating a post\n";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String postCreatePost() {
        return "create a new post\n";
    }
}
