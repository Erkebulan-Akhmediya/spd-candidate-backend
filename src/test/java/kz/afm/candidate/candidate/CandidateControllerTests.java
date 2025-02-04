package kz.afm.candidate.candidate;

import kz.afm.candidate.TestUtils;
import kz.afm.candidate.auth.AuthFilter;
import kz.afm.candidate.candidate.dto.CandidateRequest;
import kz.afm.candidate.candidate.dto.CandidateResponseBody;
import kz.afm.candidate.candidate.dto.CandidateResponseBodyFactory;
import kz.afm.candidate.candidate.status.CandidateStatusService;
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

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CandidateController.class)
public class CandidateControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CandidateStatusService candidateStatusService;

    @MockitoBean
    private CandidateService candidateService;

    @MockitoBean
    private CandidateResponseBodyFactory candidateResponseBodyFactory;

    @MockitoBean
    private AuthFilter authFilter;

    @Test
    public void create_shouldCreate() throws Exception {
        this.mock_create_Methods();

        final String mockData = this.mock_CandidateRequest();
        final ResultActions result = this.perform_create(mockData);
        this.check_create_Result(result);
    }

    private void mock_create_Methods() {
        doNothing().when(this.candidateService).create(any(CandidateRequest.class));
    }

    private String mock_CandidateRequest() {
        return TestUtils.toJsonString(
                new CandidateRequest(
                        "123123123123",
                        "lastName",
                        "firstName",
                        "middleName",
                        new Date(),
                        "birthPlace",
                        -1,
                        "phoneNumber",
                        -1,
                        "education",
                        new HashSet<>(),
                        new HashSet<>(),
                        "sport",
                        "additionalData",
                        -1,
                        "comment",
                        "security check result",
                        new HashSet<>(),
                        "username",
                        "password"
                )
        );
    }

    private ResultActions perform_create(String mockData) throws Exception {
        return this.mockMvc.perform(
                post("/candidate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockData)
        );
    }

    private void check_create_Result(ResultActions result) throws Exception {
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    public void getAll_shouldReturnAll() throws Exception {
        this.mock_getAll_Methods();
        final ResultActions result = this.perform_getAll();
        this.check_getAll_Result(result);
    }

    public void mock_getAll_Methods() {
        when(
                this.candidateService.getAll(
                        any(Integer.class),
                        any(Integer.class),
                        any(Integer.class),
                        any(Integer.class)
                )
        ).thenReturn(new LinkedList<>());

        when(
                this.candidateService.countAll(any(Integer.class), any(Integer.class))
        ).thenReturn(1L);
    }

    private ResultActions perform_getAll() throws Exception {
        return this.mockMvc.perform(
                get("/candidate/all")
                        .param("statusId", "-1")
                        .param("regionId", "-1")
                        .param("pageNumber", "-1")
                        .param("pageSize", "-1")
        );
    }

    private void check_getAll_Result(ResultActions result) throws Exception {
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.candidates").isArray())
                .andExpect(jsonPath("$.data.count").isNumber());
    }

    @Test
    public void getById_shouldReturnCandidate() throws Exception {
        this.mock_getById_Methods();
        final ResultActions result = this.perform_getById();
        this.check_getById_Result(result);
    }

    private void mock_getById_Methods() {
        final CandidateEntity mockCandidateEntity = new CandidateEntity();
        when(this.candidateService.getById(any(String.class))).thenReturn(mockCandidateEntity);

        final CandidateResponseBody mockResponseBody = CandidateResponseBody.builder().build();
        when(this.candidateResponseBodyFactory.createFrom(any(CandidateEntity.class))).thenReturn(mockResponseBody);
    }

    private ResultActions perform_getById() throws Exception {
        return this.mockMvc.perform(get("/candidate/1"));
    }

    private void check_getById_Result(ResultActions result) throws Exception {
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void reject_shouldBeOk() throws Exception {
        this.mock_reject_Methods();
        final ResultActions result = this.perform_reject();
        TestUtils.checkIfOk(result);
    }

    private void mock_reject_Methods() {
        doNothing().when(this.candidateService).reject(any(String.class));
    }

    private ResultActions perform_reject() throws Exception {
        return this.mockMvc.perform(put("/candidate/reject/identificationNumber"));
    }

    @Test
    public void sendToSecurityCheck_shouldBeOk() throws Exception {
        this.mock_sendToSecurityCheck_Methods();
        final String mockCandidateRequest = this.mock_CandidateRequest();
        final ResultActions result = this.perform_sendToSecurityCheck(mockCandidateRequest);
        TestUtils.checkIfOk(result);
    }

    private void mock_sendToSecurityCheck_Methods() {
        doNothing().when(this.candidateService).sendToSecurityCheck(any(CandidateRequest.class));
    }

    private ResultActions perform_sendToSecurityCheck(String mockCandidateRequest) throws Exception {
        return this.mockMvc.perform(
                put("/candidate/to/security")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCandidateRequest)
        );
    }

    @Test
    public void sendToApproval_shouldBeOk() throws Exception {
        this.mock_sendToApproval_Methods();
        final String mockCandidateRequest = this.mock_CandidateRequest();
        final ResultActions result = this.perform_sendToApproval(mockCandidateRequest);
        TestUtils.checkIfOk(result);
    }

    private void mock_sendToApproval_Methods() {
        doNothing().when(this.candidateService).sendToApproval(any(CandidateRequest.class));
    }

    private ResultActions perform_sendToApproval(String mockCandidateRequest) throws Exception {
        return this.mockMvc.perform(
                put("/candidate/to/approval")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCandidateRequest)
        );
    }

    @Test
    public void approve_shouldBeOk() throws Exception {
        this.mock_approve_Methods();
        final ResultActions result = this.perform_approve();
        TestUtils.checkIfOk(result);
    }

    private void mock_approve_Methods() {
        doNothing().when(this.candidateService).approve(any(String.class), any(String.class));
    }

    private ResultActions perform_approve() throws Exception {
        return this.mockMvc.perform(
                put("/candidate/approve/identificationNumber")
                        .param("areaOfActivity", "test")
        );
    }

}
