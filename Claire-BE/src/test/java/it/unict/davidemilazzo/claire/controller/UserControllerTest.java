package it.unict.davidemilazzo.claire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.config.JwtUtil;
import it.unict.davidemilazzo.claire.dto.UserDto;
import it.unict.davidemilazzo.claire.dto.UserRegistrationDto;
import it.unict.davidemilazzo.claire.exception.*;
import it.unict.davidemilazzo.claire.model.Role;
import it.unict.davidemilazzo.claire.model.UserEntity;
import it.unict.davidemilazzo.claire.respository.UserEntityRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    private final String BASE_API_URL = "/api/user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void resetDatabase() {
        userEntityRepository.deleteAll();
    }

    @Test
    @DisplayName("TEST user registration success")
    void userRegistration_success() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setNickname("User1");
        userRegistrationDto.setName("UserName");
        userRegistrationDto.setSurname("UserSurname");
        userRegistrationDto.setEmail("user@email.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setRole(Role.USER);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegistrationDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String responseJson = result.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(responseJson, UserDto.class);

        Assertions.assertEquals(1, userEntityRepository.count());
        Assertions.assertTrue(userEntityRepository.existsById(userDto.getId()));

        Assertions.assertEquals(userRegistrationDto.getNickname(), userDto.getNickname());
        Assertions.assertEquals(userRegistrationDto.getName(), userDto.getName());
        Assertions.assertEquals(userRegistrationDto.getSurname(), userDto.getSurname());
        Assertions.assertEquals(userRegistrationDto.getEmail(), userDto.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("password",
                userEntityRepository.findById(userDto.getId()).get().getPassword()));
    }

    @Test
    @DisplayName("TEST user registration email already registered")
    void userRegistration_email_already_registered() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname("User1");
        userEntity.setName("UserName");
        userEntity.setSurname("UserSurname");
        userEntity.setEmail("user@email.com");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(Role.USER);
        userEntityRepository.save(userEntity);

        UserRegistrationDto userRegistrationDto2 = new UserRegistrationDto();
        userRegistrationDto2.setNickname("User2");
        userRegistrationDto2.setName("UserName2");
        userRegistrationDto2.setSurname("UserSurname2");
        userRegistrationDto2.setEmail("user@email.com");
        userRegistrationDto2.setPassword("password2");
        userRegistrationDto2.setRole(Role.USER);

        MvcResult result2 = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegistrationDto2))
                )
                .andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();

        Exception resolvedException = result2.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(EmailAlreadyRegisteredException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.EMAIL_ALREADY_REGISTERED, resolvedException.getMessage());

        Assertions.assertEquals(1, userEntityRepository.count());
        Assertions.assertTrue(userEntityRepository.findByNickname("User2").isEmpty());
    }

    @Test
    @DisplayName("TEST user registration username already registered")
    void userRegistration_username_already_registered() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname("User1");
        userEntity.setName("UserName");
        userEntity.setSurname("UserSurname");
        userEntity.setEmail("user@email.com");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(Role.USER);
        userEntityRepository.save(userEntity);

        UserRegistrationDto userRegistrationDto2 = new UserRegistrationDto();
        userRegistrationDto2.setNickname("User1");
        userRegistrationDto2.setName("UserName2");
        userRegistrationDto2.setSurname("UserSurname2");
        userRegistrationDto2.setEmail("user2@email.com");
        userRegistrationDto2.setPassword("password2");
        userRegistrationDto2.setRole(Role.USER);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegistrationDto2))
                )
                .andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();

        Exception resolvedException = result.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(UsernameAlreadyRegisteredException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.USERNAME_ALREADY_REGISTERED, resolvedException.getMessage());

        Assertions.assertEquals(1, userEntityRepository.count());
        Assertions.assertTrue(userEntityRepository.findByEmail("user2@email.com").isEmpty());
    }

    @Test
    @DisplayName("TEST user get his profile information")
    void userInformation_get_information_success() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname("User1");
        userEntity.setName("UserName");
        userEntity.setSurname("UserSurname");
        userEntity.setEmail("user@email.com");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(Role.USER);
        Long userId = userEntityRepository.save(userEntity).getId();

        String jwtToken = jwtUtil.generateToken(userEntity.getEmail());

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_API_URL + "/me")
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responseJson = result.getResponse().getContentAsString();
        UserDto userDtoRes = objectMapper.readValue(responseJson, UserDto.class);

        Assertions.assertEquals(1, userEntityRepository.count());
        Assertions.assertTrue(userEntityRepository.existsById(userId));

        Assertions.assertEquals(userEntity.getNickname(), userDtoRes.getNickname());
        Assertions.assertEquals(userEntity.getName(), userDtoRes.getName());
        Assertions.assertEquals(userEntity.getSurname(), userDtoRes.getSurname());
        Assertions.assertEquals(userEntity.getEmail(), userDtoRes.getEmail());
        Assertions.assertEquals(userEntity.getRole(), userDtoRes.getRole());
    }

    @Test
    @DisplayName("TEST user update info; email and password should not change")
    void userInformation_update_information_success() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname("User1");
        userEntity.setName("UserName");
        userEntity.setSurname("UserSurname");
        userEntity.setEmail("user@email.com");
        userEntity.setPassword(passwordEncoder.encode("password"));
        userEntity.setRole(Role.USER);
        Long userId = userEntityRepository.save(userEntity).getId();

        String originalPasswordHash = userEntity.getPassword();
        String originalEmail = userEntity.getEmail();
        String jwtToken = jwtUtil.generateToken(userEntity.getEmail());

        UserDto userUpdate = new UserDto();
        userUpdate.setNickname("UserEdited");
        userUpdate.setName("UserNameEdited");
        userUpdate.setSurname("UserSurnameEdited");
        userUpdate.setRole(Role.USER);
        userUpdate.setEmail("newemail@domain.com");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.put(BASE_API_URL + "/update")
                                .header("Authorization", "Bearer " + jwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdate))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        UserDto userDtoRes = objectMapper.readValue(responseJson, UserDto.class);

        Assertions.assertEquals(userId, userDtoRes.getId());
        Assertions.assertEquals(userUpdate.getNickname(), userDtoRes.getNickname());
        Assertions.assertEquals(userUpdate.getName(), userDtoRes.getName());
        Assertions.assertEquals(userUpdate.getSurname(), userDtoRes.getSurname());
        Assertions.assertEquals(userUpdate.getRole(), userDtoRes.getRole());

        UserEntity updatedEntity = userEntityRepository.findById(userId).get();
        Assertions.assertEquals(originalEmail, updatedEntity.getEmail());
        Assertions.assertEquals(originalPasswordHash, updatedEntity.getPassword());
    }


}
