package it.unict.davidemilazzo.claire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.config.JwtUtil;
import it.unict.davidemilazzo.claire.dto.AuthenticationResponseDto;
import it.unict.davidemilazzo.claire.dto.UserLoginDto;
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
public class AuthenticationControllerTest {

    private final String BASE_API_URL = "/api/auth";

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

    private UserEntity mainUser;

    @BeforeAll
    void init() {
        userEntityRepository.deleteAll();

        UserEntity user1 = new UserEntity();
        user1.setNickname("User1");
        user1.setName("UserName1");
        user1.setSurname("UserSurname1");
        user1.setEmail("user1@email.com");
        user1.setPassword(passwordEncoder.encode("password1"));
        user1.setRole(Role.USER);
        mainUser = userEntityRepository.save(user1);
    }

    @Test
    @DisplayName("TEST User login successful")
    void userLogin_successful() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(mainUser.getEmail());
        userLoginDto.setPassword("password1");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responseJson = result.getResponse().getContentAsString();
        AuthenticationResponseDto authenticationResponseDto = objectMapper.readValue(responseJson, AuthenticationResponseDto.class);

        String jwtToken = authenticationResponseDto.getJwtToken();

        Assertions.assertNotNull(jwtToken);
        Assertions.assertFalse(jwtToken.isEmpty());
        Assertions.assertTrue(jwtUtil.isTokenValid(jwtToken, mainUser.getEmail()));

        String username = jwtUtil.extractUsername(jwtToken);
        Assertions.assertEquals(mainUser.getEmail(), username);
    }

    @Test
    @DisplayName("TEST User login not registered email")
    void userLogin_not_registered_email() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("999999999@email.com");
        userLoginDto.setPassword("password");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginDto))
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        Exception resolvedException = result.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(EmailNotFoundException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.EMAIL_NOT_FOUND, resolvedException.getMessage());

        Assertions.assertEquals(1, userEntityRepository.count());
        Assertions.assertTrue(userEntityRepository.findByEmail(userLoginDto.getEmail()).isEmpty());
    }

    @Test
    @DisplayName("TEST User login wrong password")
    void userLogin_wrong_password() throws Exception {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(mainUser.getEmail());
        userLoginDto.setPassword("wrongPassword");

        Assertions.assertEquals(userLoginDto.getEmail(), mainUser.getEmail());
        Assertions.assertNotEquals(userLoginDto.getPassword(), mainUser.getPassword());

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginDto))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();

        Exception resolvedException = result.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(AuthenticationFailedException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.AUTHENTICATION_ERROR, resolvedException.getMessage());

        Assertions.assertEquals(1, userEntityRepository.count());
        Assertions.assertNotNull(userEntityRepository.findByEmail(userLoginDto.getEmail()));
    }
}
