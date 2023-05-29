package timnekk.orderflow.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import timnekk.orderflow.user.dto.UserDTO;
import timnekk.orderflow.user.entity.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

}
