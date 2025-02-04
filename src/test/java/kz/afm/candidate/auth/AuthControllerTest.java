package kz.afm.candidate.auth;

import kz.afm.candidate.TestUtils;
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
import org.springframework.test.web.servlet.ResultActions;

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

    @Test
    public void login_shouldReturnToken() throws Exception {
        this.mock_login_Methods();
        final ResultActions result = this.perform_login();
        this.check_login_Result(result);
    }

    private void mock_login_Methods() {
        doNothing().when(this.authService).authenticateUser(any(String.class), any(String.class));
        when(this.userService.getByUsername(any(String.class))).thenReturn(new UserEntity());
        when(this.userService.userIsCandidate(any(UserEntity.class))).thenReturn(false);
        when(this.jwtService.generateTokenFrom(any(UserEntity.class))).thenReturn("test token");
    }

    private ResultActions perform_login() throws Exception {
        return this.mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJsonString(new LoginRequest("admin", "admin")))
        );
    }

    private void check_login_Result(ResultActions result) throws Exception {
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists());
    }

}
