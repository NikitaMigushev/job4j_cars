package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cars.converter.ModelMapperPostDtoConverter;
import ru.job4j.cars.converter.PostDtoConverter;
import ru.job4j.cars.service.*;
import ru.job4j.cars.validator.PostDtoValidator;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class PostControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Mock
    private CarService carService;

    @Mock
    private BrandService brandService;

    @Mock
    private CarModelService carModelService;

    @Mock
    private CarBodyService carBodyService;

    @Mock
    private EngineService engineService;

    @Mock
    private TransmissionService transmissionService;

    @Mock
    private CarPassportService carPassportService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private ColorService colorService;

    @Mock
    private PostDtoValidator postDtoValidator;

    @Mock
    private PostDtoConverter postDtoConverter;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private PostController postController;

    @Mock
    private ModelMapperPostDtoConverter converter;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        postService = mock(PostService.class);
        carService = mock(CarService.class);
        brandService = mock(BrandService.class);
        carModelService = mock(CarModelService.class);
        postDtoValidator = mock(PostDtoValidator.class);
        carBodyService = mock(CarBodyService.class);
        engineService = mock(EngineService.class);
        transmissionService = mock(TransmissionService.class);
        carPassportService = mock(CarPassportService.class);
        ownerService = mock(OwnerService.class);
        colorService = mock(ColorService.class);
        postDtoConverter = mock(PostDtoConverter.class);
        converter = mock(ModelMapperPostDtoConverter.class);
        model = mock(Model.class);
        postController = new PostController(
                postService,
                brandService,
                carBodyService,
                transmissionService,
                colorService,
                postDtoValidator,
                converter
        );
        session = mock(HttpSession.class);
    }

    @Test
    public void testGetCreatePage() {
        model = new ConcurrentModel();
        var view = postController.getCreatePostPage(model, session);
        assertThat(view).isEqualTo("cars/create");
    }
}