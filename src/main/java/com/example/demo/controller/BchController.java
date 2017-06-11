package com.example.demo.controller;

import com.example.demo.service.eth.EthService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BchController {

  @Autowired EthService ethService;

  @RequestMapping("/")
  public ModelAndView greeting(
      @RequestParam(value = "name", required = false, defaultValue = "User") String name,
      Model model) {
    model.addAttribute("name", name);
    model.addAttribute("time", new Date());
    return new ModelAndView("welcome");
  }

  @RequestMapping("/transactions")
  public ModelAndView getTransactions(Model model) {
    model.addAttribute("transactions", ethService.getBchPaymentList());
    model.addAttribute("lastBlock", ethService.getLastBlockNumber());
    return new ModelAndView("transactions");
  }
}
