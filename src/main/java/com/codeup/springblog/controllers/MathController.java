package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MathController {

    @RequestMapping(path = "/add/{num1}/and/{num2}", method = RequestMethod.GET)
    @ResponseBody
    public String add(@PathVariable int num1, @PathVariable int num2) {
        int sum = num1 + num2;
        return String.format("%d", sum);
    }

    @RequestMapping(path = "/subtract/{num1}/from/{num2}", method = RequestMethod.GET)
    @ResponseBody
    public String subtract(@PathVariable int num1, @PathVariable int num2) {
        int difference = num1 - num2;
        return String.format("%d", difference);
    }

    @RequestMapping(path = "/multiply/{num1}/and/{num2}", method = RequestMethod.GET)
    @ResponseBody
    public String multiply(@PathVariable int num1, @PathVariable int num2) {
        int multipliedTotal = num1 * num2;
        return String.format("%d", multipliedTotal);
    }
    @RequestMapping(path = "/divide/{num1}/by/{num2}", method = RequestMethod.GET)
    @ResponseBody
    public String divide(@PathVariable int num1, @PathVariable int num2) {
        int dividedTotal = num1 / num2;
        return String.format("%d", dividedTotal);
    }
}
