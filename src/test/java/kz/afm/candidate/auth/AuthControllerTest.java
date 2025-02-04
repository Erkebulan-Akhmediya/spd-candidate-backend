package kz.afm.candidate.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.afm.candidate.auth.dto.LoginRequest;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.user.UserEntity;
import kz.afm.candidate.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private CandidateService candidateService;

    @MockitoBean
    private AuthFilter authFilter;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void login_shouldReturnToken() throws Exception {
        this.mockLoginServices();

        this.mockMvc.perform(
                        post("/auth/login")
                                .header("Origin", "http://localhost")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(new LoginRequest("admin", "admin")))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());
    }

    private void mockLoginServices() {
        doNothing().when(this.authService).authenticateUser(any(String.class), any(String.class));
        when(this.userService.getByUsername(any(String.class))).thenReturn(new UserEntity());
        when(this.userService.userIsCandidate(any(UserEntity.class))).thenReturn(false);
        when(this.jwtService.generateTokenFrom(any(UserEntity.class))).thenReturn("test token");
    }

}
