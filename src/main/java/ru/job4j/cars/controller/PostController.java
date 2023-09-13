package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.converter.ModelMapperPostDtoConverter;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;
import ru.job4j.cars.validator.PostDtoValidator;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;


@Controller
@RequestMapping("/cars")
@AllArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final BrandService brandService;
    private final CarBodyService carBodyService;
    private final TransmissionService transmissionService;

    private final ColorService colorService;
    private final PostDtoValidator postDtoValidator;

    private final ModelMapperPostDtoConverter converter;
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

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
        Set<ConstraintViolation<PostDto>> violations = VALIDATOR.validate(postDto);
        if (!violations.isEmpty()) {
            logError("PostDto has not passed validation", new ConstraintViolationException(violations));
            return "errors/404";
        }
        postDtoValidator.validate(postDto);
        var savedPost = postService.save(postDto,
                new PhotoDto(postDto.getPhoto().getOriginalFilename(), postDto.getPhoto().getBytes()));
        if (savedPost.isEmpty()) {
            model.addAttribute("message", "Не удалось создать объявление");
            return "errors/404";
        }
        return "redirect:/cars/list";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute PostDto postDto) throws IOException {
        Set<ConstraintViolation<PostDto>> violations = VALIDATOR.validate(postDto);
        if (!violations.isEmpty()) {
            logError("PostDto has not passed validation", new ConstraintViolationException(violations));
            return "errors/404";
        }
        postService.update(postDto);
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
        PostDto postDto = converter.convertPostToPostDto(post);
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

    private void logError(String message, Throwable e) {
        log.error(message, e);
    }
}
