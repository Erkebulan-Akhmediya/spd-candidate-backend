package kz.afm.candidate.candidate.area_of_activity;

import kz.afm.candidate.TestUtils;
import kz.afm.candidate.auth.AuthFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.LinkedList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AreaOfActivityController.class)
public class AreaOfActivityControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AreaOfActivityService areaOfActivityService;

    @MockitoBean
    private AuthFilter authFilter;

    @Test
    public void getAll_shouldReturnList() throws Exception {
        this.mock_getAll_Methods();
        final ResultActions result = this.perform_getAll();
        this.check_getAll_Result(result);
    }

    private void mock_getAll_Methods() {
        when(this.areaOfActivityService.getAll()).thenReturn(new LinkedList<>());
    }

    private ResultActions perform_getAll() throws Exception {
        return this.mockMvc.perform(
                get("/area_of_activity/all")
        );
    }

    private void check_getAll_Result(ResultActions result) throws Exception {
        TestUtils.checkIfOk(result);
        result.andExpect(
                jsonPath("$.data").isArray()
        );
    }

}
