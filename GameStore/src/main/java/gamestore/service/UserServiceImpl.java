package gamestore.service;

import gamestore.domain.dtos.GameAddDTO;
import gamestore.domain.dtos.UserLoginDTO;
import gamestore.domain.dtos.UserLogoutDTO;
import gamestore.domain.dtos.UserRegisterDTO;
import gamestore.domain.entities.Role;
import gamestore.domain.entities.User;
import gamestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();

        StringBuilder sb = new StringBuilder();

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            sb.append("Passwords don't match.");
        } else if (validator.validate(userRegisterDTO).size() > 0) {
            for (ConstraintViolation<UserRegisterDTO> violation : validator.validate(userRegisterDTO)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else
        {
            User entity = this.userRepository.findByEmail(userRegisterDTO.getEmail()).orElse(null);

            if(entity !=null)
            {
                return sb.append("User already exists.").toString();
            }
             entity = this.modelMapper.map(userRegisterDTO,User.class);
            if(this.userRepository.count()==0)
            {
                entity.setRole(Role.ADMIN);
            }else
            {
                entity.setRole(Role.USER);
            }

            this.userRepository.saveAndFlush(entity);
            sb.append(String.format("%s was registered.",entity.getFullName()));
        }


        return sb.toString().trim();

    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
        StringBuilder sb = new StringBuilder();

        Set<ConstraintViolation<UserLoginDTO>> violations = validator.validate(userLoginDTO);
        if(violations.size()>0)
        {
            for (ConstraintViolation<UserLoginDTO> violation : violations) {
                    sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        }else
        {
            User entity = this.userRepository.findByEmail(userLoginDTO.getEmail()).orElse(null);

            if(entity==null)
            {
               return sb.append("User does not exist.").append(System.lineSeparator()).toString();
            }else if(!entity.getPassword().equals(userLoginDTO.getPassword()))
            {
                return sb.append("Wrong password.").append(System.lineSeparator()).toString();
            }

            sb.append(String.format("Successfully logged in %s",entity.getFullName())).append(System.lineSeparator());

        }
        return sb.toString().trim();
    }

    @Override
    public String logoutUser(UserLogoutDTO userLogoutDTO) {
        StringBuilder sb = new StringBuilder();
        User entity = this.userRepository.findByEmail(userLogoutDTO.getEmail()).orElse(null);

        if(entity==null)
        {
            return sb.append("User does not exist.").append(System.lineSeparator()).toString();
        }

        sb.append(String.format("User %s successfully logged out.",entity.getFullName()));
        return sb.toString();
    }

    @Override
    public boolean isAdmin(String email)
    {
        User entity = this.userRepository.findByEmail(email).orElse(null);

        if(entity!=null)
        {
            return true;
        }

        return false;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        return null;
    }
}
