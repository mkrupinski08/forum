package pl.edu.pb.pabwj.forum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.pabwj.forum.dto.request.UserAccountRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.UserAccountResponseDto;
import pl.edu.pb.pabwj.forum.model.UserAccount;

import java.util.List;
import java.util.Set;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {RoleMapper.class})
public interface UserAccountMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserAccount fromRequestToEntity(UserAccountRequestDto userAccountRequestDto);

    @Mapping(target = "full", ignore = true)
    @Mapping(target = "admin", ignore = true)
    @Mapping(target = "roleResponseDtos", source = "roles")
    UserAccountResponseDto fromEntityToResponse(UserAccount userAccount);

    Set<UserAccountResponseDto> fromEntityToResponse(List<UserAccount> userAccounts);

}
