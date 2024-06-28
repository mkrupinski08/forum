package pl.edu.pb.pabwj.forum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.pabwj.forum.dto.request.CategoryRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.CategoryHomeResponseDto;
import pl.edu.pb.pabwj.forum.dto.response.CategoryResponseDto;
import pl.edu.pb.pabwj.forum.model.Category;

import java.util.List;
import java.util.Set;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    CategoryResponseDto fromEntityToResponse(Category category);

    @Mapping(target = "topics", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category fromResponseToEntity(CategoryResponseDto categoryResponseDto);

    Set<CategoryResponseDto> fromEntityToResponse(List<Category> categories);

    Set<CategoryHomeResponseDto> fromEntityToHomeResponse(List<Category> categories);

    @Mapping(target = "cnt", expression = "java(category.getTopics().size())")
    CategoryHomeResponseDto fromEntityToHomeResponse(Category category);

    @Mapping(target = "topics", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category fromRequestToEntity(CategoryRequestDto categoryRequestDto);

}
