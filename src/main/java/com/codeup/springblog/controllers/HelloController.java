package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; //imports ALL annotations

@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "<h1>Hello from Spring!</h1>";
    }

//    @GetMapping("/hello/{name}")
//    @ResponseBody
//    public String sayHello(@PathVariable String name) {
//        return "Hello " + name + "!";
//    }


//    INSTRUCTOR CODE:
    @RequestMapping(path ="/hello/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String helloToYou(@PathVariable String name) {
        return String.format("Nice to meet you, %s", name);
    }


    @GetMapping("/number/{num}")
    @ResponseBody
    public String reportNumber(@PathVariable int num) {
        String intro =  String.format("Here are some truths of the number %d", num);
        String isEven = String.format("The number %d is even: %b.", num, num % 2 == 0);
        return String.format("<h3>%s</h3><ul><li>%s</li></ul>", intro, isEven);
    }
}
