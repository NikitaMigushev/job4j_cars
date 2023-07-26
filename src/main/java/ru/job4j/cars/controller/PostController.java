package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.CarService;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/cars")
@AllArgsConstructor
public class PostController {
    private final CarService carService;

    /**
     * Add necessary attributes to model to load Post creation page.
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String getCreatePostPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        return "cars/list";
    }
}
