package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by ValkSam on 09.06.2017.
 */
@Controller
public class GreetingController {
  @RequestMapping("/")
  public ModelAndView greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    model.addAttribute("name", name+"!!!!!!!!!!!!!!!");
    model.addAttribute("time", new Date());
    return new ModelAndView("welcome");
  }
}
