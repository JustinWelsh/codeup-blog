package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiceGuessController {

    @GetMapping("/roll-dice")
    public String dieGuessPage() {
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{number}")
    @ResponseBody
    public String showResults(@PathVariable int number) {
        int random_int = (int) (Math.random() * 6) + 1;

        if (random_int == number) {
            return String.format("<h1>CORRECT!</h1>\nDie was indeed %d", random_int);
        } else {
            return String.format("<h3>Sorry, that's wrong</h3>\nDie was %d", random_int);
        }
    }

    /*INSTRUCTOR SOLUTION*/

//    @GetMapping("/roll-dice")
//    public String showRollOptions() {
//        return "roll-dice";
//    }
//
//    @GetMapping("/roll-dice/{num}")
//    @ResponseBody
//    public String seeResults(@PathVariable int num) {
//        int i = (int) (Math.random() * 6) + 1;
//
//        if(i == num) {
//            return "You're right.";
//        } else {
//            return "You're wrong. Sorry.";
//        }
//    }
}
