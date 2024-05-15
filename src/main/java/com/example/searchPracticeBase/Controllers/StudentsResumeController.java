package com.example.searchPracticeBase.Controllers;

import com.example.searchPracticeBase.Models.*;
import com.example.searchPracticeBase.Utils.EncryptionUtils;
import com.example.searchPracticeBase.Utils.UserUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/students-resume")
public class StudentsResumeController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EncryptionUtils encryptionUtils = new EncryptionUtils();
    String userEmail = "";
    PracticeManager practiceManager;
    PracticeBase practiceBase = new PracticeBase();

    @Value("${api.url.server}")
    private String apiUrl;

    @GetMapping("/view")
    String getPage(@RequestParam("findOrApply") String param, Model model, Authentication authentication, HttpSession session) throws JsonProcessingException {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User oAuth2User) {
            userEmail = oAuth2User.getAttribute("email");

            model.addAttribute("applyOrNot", param);

            String token = (String) session.getAttribute("token");
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            headers.set("Authorization", "Bearer " + token);

            ResponseEntity<List<Student>> response = restTemplate.exchange(apiUrl + "/student/not-in-internship", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Student>>() {});
            List<Student> listStudents = objectMapper.convertValue(response.getBody(), new TypeReference<List<Student>>(){});

            ResponseEntity<String> getUserResponse = restTemplate.postForEntity(apiUrl + "/authentication/site/authentication", encryptionUtils.encryptData(userEmail), String.class);
            Map<String, Object> responseBody = objectMapper.readValue(getUserResponse.getBody(), new TypeReference<Map<String, Object>>(){});
            UserUtils.getUserData(model, session, objectMapper, responseBody, encryptionUtils);

            model.addAttribute("students_list", listStudents);
            model.addAttribute("openPanel", false);
            try {
                practiceManager = (PracticeManager) model.getAttribute("practiceManager");
                HttpEntity<PracticeManager> entityPM = new HttpEntity<>(practiceManager, headers);ResponseEntity<PracticeBase> responsePM = restTemplate.postForEntity(apiUrl + "/practiceBase/getAtManager", entityPM, PracticeBase.class);
                practiceBase = objectMapper.convertValue(responsePM.getBody(), new TypeReference<PracticeBase>(){});
                model.addAttribute("practiceBase", practiceBase);
            }catch (Exception e) {
                model.addAttribute("practiceBaseNotFound", null);
            }

        }
        return "students_resume";
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getPhotoStudent(HttpSession session, @RequestParam("photoStudent") String photo){
        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        headers.set("Authorization", "Bearer " + token);
        return restTemplate.exchange(apiUrl + "/images/get?isPracticeBase=false&filename=" + photo, HttpMethod.GET, entity, new ParameterizedTypeReference<byte[]>() {});
    }

    @PostMapping("/invite-interview")
    public String inviteStudentForInterview(@RequestParam("studentId") String studentId,
                                            @RequestParam("comment") String comment,
                                            HttpSession session,
                                            Model model) throws JsonProcessingException {
        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<RequestStatus>> response = restTemplate.exchange(apiUrl + "/request_status/all", HttpMethod.GET, entity, new ParameterizedTypeReference<List<RequestStatus>>() {});
        List<RequestStatus> listStatus = objectMapper.convertValue(response.getBody(), new TypeReference<List<RequestStatus>>(){});

        RequestSubmitted requestSubmitted = new RequestSubmitted();
        requestSubmitted.setComment(comment);
        requestSubmitted.setStudent(new Student(Long.valueOf(studentId), null, null, null, null, false, null, null));
        requestSubmitted.setSubmissionDateTime(LocalDateTime.now());
        requestSubmitted.setCanceled(false);
        requestSubmitted.setPracticeBase(practiceBase);
        for(RequestStatus status : listStatus){
            if(status.getStatusName().equalsIgnoreCase("Оставлен комментарий")){
                requestSubmitted.setStatus(status);
                break;
            }
        }

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<RequestSubmitted> requestEntity = new HttpEntity<>(requestSubmitted, headers);
        ResponseEntity<String> responseSave = restTemplate.postForEntity(
                apiUrl + "/requests/save",
                requestEntity,
                String.class);

        if(responseSave.getStatusCode().is2xxSuccessful()){
            model.addAttribute("message", "Успешно");
            return "students_resume";
        }else{
            model.addAttribute("message", "Не удалось отправить");
            return "students_resume";
        }
    }
}
