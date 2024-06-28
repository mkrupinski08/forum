package pl.edu.pb.pabwj.forum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.edu.pb.pabwj.forum.dto.request.CategoryRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.CategoryHomeResponseDto;
import pl.edu.pb.pabwj.forum.dto.response.CategoryResponseDto;
import pl.edu.pb.pabwj.forum.mapper.CategoryMapper;
import pl.edu.pb.pabwj.forum.repository.CategoryRepository;

import java.util.Set;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Set<CategoryResponseDto> findAll() {
        return categoryMapper.fromEntityToResponse(
                categoryRepository.findAll());
    }

    public Set<CategoryHomeResponseDto> findAllWithCnt() {
        return categoryMapper.fromEntityToHomeResponse(
                categoryRepository.findAll());
    }

    public Set<CategoryResponseDto> add(CategoryRequestDto categoryRequestDto) {
        var category = categoryRepository.findByName(categoryRequestDto.getName())
                .orElse(null);
        if (category == null) {
            categoryRepository.save(categoryMapper.fromRequestToEntity(categoryRequestDto));
            return findAll();
        } else {
            throw new IllegalArgumentException(String.format("Category with name: %s already exist!", categoryRequestDto.getName()));
        }
    }

    public Set<CategoryResponseDto> deleteByName(String name) {
        var category = categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with name: %s not found!", name)));
        categoryRepository.delete(category);
        return findAll();
    }

}
