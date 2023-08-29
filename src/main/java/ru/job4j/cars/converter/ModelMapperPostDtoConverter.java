package ru.job4j.cars.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.Post;

@Component
public class ModelMapperPostDtoConverter {

    private final ModelMapper modelMapper;

    public ModelMapperPostDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PostDto convertPostToPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public Post convertPostDtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
