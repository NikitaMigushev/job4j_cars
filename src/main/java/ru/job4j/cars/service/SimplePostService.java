package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.converter.ModelMapperPostDtoConverter;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final PhotoService photoService;
    private final CarModelService carModelService;
    private final EngineService engineService;
    private final OwnerService ownerService;
    private final CarPassportService carPassportService;
    private final CarService carService;
    private final ModelMapperPostDtoConverter converter;


    @Override
    public Optional<Post> save(Post post, PhotoDto photo) {
        saveNewPhoto(post, photo);
        return postRepository.save(post);
    }

    public Optional<Post> save(PostDto postDto, PhotoDto photo) {
        Post post = converter.convertPostDtoToPost(postDto);
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
        return save(post, photo);
    }

    private void saveNewPhoto(Post post, PhotoDto photo) {
        var savedPhoto = photoService.save(photo);
        post.setPhoto(savedPhoto.get());
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean update(Post post, PhotoDto photo) {
        photoService.deleteFile(post.getPhoto().getPath());
        saveNewPhoto(post, photo);
        return postRepository.update(post);
    }

    @Override
    public boolean update(PostDto postDto) {
        Post post = converter.convertPostDtoToPost(postDto);
        try {
            if (!postDto.getPhoto().isEmpty()) {
                return update(post, new PhotoDto(postDto.getPhoto().getOriginalFilename(), postDto.getPhoto().getBytes()));
            }
            return update(post);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        return postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Collection<Post> getLastDayPosts() {
        return postRepository.getLastDayPosts();
    }

    @Override
    public Collection<Post> getPostsWithPhoto() {
        return postRepository.getPostsWithPhoto();
    }

    @Override
    public Collection<Post> getPostsByBrand(String brand) {
        return postRepository.getPostsByBrand(brand);
    }

    @Override
    public boolean markSold(Post post) {
        return postRepository.markSold(post);
    }
}
