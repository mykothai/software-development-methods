package io.javabrains.coronavirustracker.controllers;

import io.javabrains.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // renders html
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("locationStats", coronaVirusDataService.getAllStats()); // attributes will be accessible from the HTML file
        return "home";
    }
}
