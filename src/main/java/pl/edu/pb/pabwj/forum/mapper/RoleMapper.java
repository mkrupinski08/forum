package pl.edu.pb.pabwj.forum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.edu.pb.pabwj.forum.dto.response.RoleResponseDto;
import pl.edu.pb.pabwj.forum.model.Role;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {

    RoleResponseDto fromEntityToResponse(Role role);

}
