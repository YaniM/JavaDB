package productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productshop.domain.dtos.*;
import productshop.domain.entities.Product;
import productshop.domain.entities.User;
import productshop.repository.ProductRepository;
import productshop.repository.UserRepository;
import productshop.util.ValidatorUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public void SeedUsers(UserSeedDTO[] userSeedDTOS) {
        for (UserSeedDTO userSeedDTO : userSeedDTOS) {
            if (!this.validatorUtil.isValid(userSeedDTO)) {
                this.validatorUtil.violations(userSeedDTO).forEach(v -> System.out.println(v.getMessage()));
                continue;
            }

            User entity = this.modelMapper.map(userSeedDTO, User.class);
            this.userRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<UserSoldDTO> findUsersWithBuyer() {
        Set<User> users = this.userRepository.getUserBy();

        List<UserSoldDTO> userSoldDTOS = new ArrayList<>();

        for (User user : users) {

            UserSoldDTO userSoldDTO = this.modelMapper.map(user, UserSoldDTO.class);
            List<Product> products = this.productRepository.findAllBySeller_IdAndBuyerIsNotNull(user.getId());

            List<ProductDetailsDTO> listDTOToADD = new ArrayList<>();

            for (Product product : products) {
                ProductDetailsDTO productDetailsDTO = this.modelMapper.map(product, ProductDetailsDTO.class);

                if (product.getBuyer().getFirstName() != null) {
                    productDetailsDTO.setbFirstName(product.getBuyer().getFirstName());
                }
                productDetailsDTO.setbLastName(product.getBuyer().getLastName());
                listDTOToADD.add(productDetailsDTO);
            }

            userSoldDTO.setProductDetailsDTOS(listDTOToADD);

            userSoldDTOS.add(userSoldDTO);
        }

        return userSoldDTOS;
    }

    @Override
    public List<UserCountDTO> usersAndProducts() {
        Set<User> users = this.userRepository.getUserBy1productSold();

        UserCountDTO userCountDTO = this.modelMapper.map(users, UserCountDTO.class);
        userCountDTO.setCountUsers(users.size());

        Set<UserUsersDTO> usersDetails = new HashSet<>();

        for (User user : users) {
            UserUsersDTO userUsersDTO = this.modelMapper.map(user, UserUsersDTO.class);

            List<Product> products = this.productRepository.findAllBySeller_IdAndBuyerIsNotNull(user.getId());

            ProductSoldDTO productSoldDTO = this.modelMapper.map(products, ProductSoldDTO.class);
            productSoldDTO.setCount(products.size());


            if (userUsersDTO.getFirstName() == null) {
                userUsersDTO.setFirstName("null");
            }

            Set<ProductNamePriceDTO> productNameAndPRiceList = new HashSet<>();

            for (Product product : products) {
                ProductNamePriceDTO productNamePriceDTO = new ProductNamePriceDTO();
                productNamePriceDTO.setName(product.getName());
                BigDecimal price = new BigDecimal(String.valueOf(product.getPrice()));
                productNamePriceDTO.setPrice(price);
                productNameAndPRiceList.add(productNamePriceDTO);
            }

            productSoldDTO.setProductNamePriceDTOS(productNameAndPRiceList);

            userUsersDTO.setProductSoldDTOS(productSoldDTO);

            usersDetails.add(userUsersDTO);

        }
        userCountDTO.setUserUsersDTOS(usersDetails);

        return Arrays.asList(userCountDTO);

    }
}
