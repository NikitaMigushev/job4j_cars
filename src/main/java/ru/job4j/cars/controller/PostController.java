package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.converter.PostDtoConverter;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;
import ru.job4j.cars.validator.PostDtoValidator;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("/cars")
@AllArgsConstructor
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final CarService carService;
    private final BrandService brandService;
    private final CarModelService carModelService;
    private final CarBodyService carBodyService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final CarPassportService carPassportService;
    private final OwnerService ownerService;
    private final ColorService colorService;
    private final PostDtoValidator postDtoValidator;
    private final PostDtoConverter postDtoConverter;


    @GetMapping("/create")
    public String getCreatePostPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        PostDto postDto = new PostDto();
        model.addAttribute("user", user);
        model.addAttribute("postDto", postDto);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("carBodies", carBodyService.findAll());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        return "cars/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute PostDto postDto, Model model) throws IOException {
        postDtoValidator.validate(postDto);
        Post post = postDtoConverter.convertPostDtoToPost(postDto);
        var savedCarModel = carModelService.save(post.getCar().getCarModel());
        var savedEngine = engineService.save(post.getCar().getEngine());
        var savedOwner = ownerService.save(post.getCar().getCarPassport().getCurrentOwner());
        var carPassport = post.getCar().getCarPassport();
        carPassport.setCurrentOwner(savedOwner.get());
        var savedCarPassport = carPassportService.save(carPassport);
        var car = post.getCar();
        car.setCarModel(savedCarModel.get());
        car.setEngine(savedEngine.get());
        car.setCarPassport(savedCarPassport.get());
        var savedCar = carService.save(car);
        post.setCar(savedCar.get());
        var savedPost = postService.save(post,
                new PhotoDto(postDto.getPhoto().getOriginalFilename(), postDto.getPhoto().getBytes()));
        if (savedPost.isEmpty()) {
            model.addAttribute("message", "Не удалось создать объявление");
            return "errors/404";
        }
        return "redirect:/cars/list";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute PostDto postDto) throws IOException {
        postDtoValidator.validate(postDto);
        Post post = postDtoConverter.convertPostDtoToPost(postDto);
        if (!postDto.getPhoto().isEmpty()) {
            postService.update(post,
                    new PhotoDto(postDto.getPhoto().getOriginalFilename(), postDto.getPhoto().getBytes()));
        } else {
            postService.update(post);
        }
        return "redirect:/cars/list";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "cars/list";
    }

    @GetMapping("/view/{id}")
    public String getViewPage(Model model, HttpSession session, @PathVariable("id") int postId) {
        var post = postService.findById(postId).get();
        User user = (User) session.getAttribute("user");
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        return "cars/view";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(Model model, HttpSession session, @PathVariable("id") int postId) {
        var post = postService.findById(postId).get();
        PostDto postDto = postDtoConverter.convertPostToPostDto(post);
        User user = (User) session.getAttribute("user");
        model.addAttribute("postDto", postDto);
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("carBodies", carBodyService.findAll());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        return "cars/edit";
    }

    @GetMapping("/markSold/{id}")
    public String markFinished(Model model, @PathVariable("id") int postId)  {
        Post post = postService.findById(postId).get();
        var isSold = postService.markSold(post);
        if (!isSold) {
            model.addAttribute("message", "Не удалось отметить объявление, как проданное");
            return "errors/404";
        }
        return "redirect:/cars/list";
    }
}
