package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;
import ru.job4j.cars.validator.PostDtoValidator;

import javax.servlet.http.HttpSession;
import java.util.Optional;


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
    private final PostDtoValidator postDtoValidator = new PostDtoValidator();

    @GetMapping("/create")
    public String getCreatePostPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("carBodies", carBodyService.findAll());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        return "cars/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute PostDto postDto, Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            postDto.setUser(user.getId());
            postDtoValidator.validate(postDto);
            Optional<Car> car = createCarFromPostDto(postDto);
            Post post = new Post();
            post.setDescription(postDto.getDescription());
            post.setUser(userService.findById(postDto.getUser()).get());
            post.setCar(car.get());
            post.setPrice(postDto.getPrice());
            postService.save(post, new PhotoDto(postDto.getPhoto().getOriginalFilename(), postDto.getPhoto().getBytes()));
            return "redirect:/cars/list";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    private Optional<Car> createCarFromPostDto(PostDto postDto) {
        postDtoValidator.validate(postDto);
        Car car = new Car();
        Optional<Brand> brand = brandService.findById(postDto.getBrand());
        car.setBrand(brand.get());
        CarModel carModel = new CarModel();
        carModel.setName(postDto.getCarModel());
        Optional<CarModel> savedCarModel = carModelService.save(carModel);
        car.setCarModel(savedCarModel.get());
        Optional<CarBody> carBody = carBodyService.findById(postDto.getCarBody());
        car.setCarBody(carBody.get());
        Optional<Color> color = colorService.findById(postDto.getColor());
        car.setColor(color.get());
        car.setCarYear(postDto.getCarYear());
        car.setMileage(postDto.getMileage());
        car.setVin(postDto.getVin());
        Engine engine = new Engine();
        engine.setName(postDto.getEngine());
        Optional<Engine> savedEngine = engineService.save(engine);
        car.setEngine(savedEngine.get());
        Optional<Transmission> transmission = transmissionService.findById(postDto.getTransmission());
        car.setTransmission(transmission.get());
        Owner owner = new Owner();
        owner.setName(postDto.getOwnerName());
        owner.setPassport(postDto.getOwnerPassport());
        var savedOwner = ownerService.save(owner);
        CarPassport carPassport = new CarPassport();
        carPassport.setPassportNumber(postDto.getCarPassport());
        carPassport.setCurrentOwner(savedOwner.get());
        carPassport.setCar(car);
        Optional<CarPassport> savedCarPassport = carPassportService.save(carPassport);
        car.setCarPassport(savedCarPassport.get());
        return carService.save(car);
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "cars/list";
    }
}
