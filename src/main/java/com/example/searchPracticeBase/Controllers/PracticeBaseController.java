package com.example.searchPracticeBase.Controllers;

import com.example.searchPracticeBase.Models.PracticeBase;
import com.example.searchPracticeBase.Models.PracticeManager;
import com.example.searchPracticeBase.Models.Student;
import com.example.searchPracticeBase.Models.VisitLog;
import com.example.searchPracticeBase.Utils.EncryptionUtils;
import com.example.searchPracticeBase.Utils.UserUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/practice-base-panel")
public class PracticeBaseController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EncryptionUtils encryptionUtils = new EncryptionUtils();
    String userEmail = "";

    @Value("${api.url.server}")
    private String apiUrl;

    @GetMapping
    public String getPage(Model model, Authentication authentication, HttpSession session)  throws JsonProcessingException {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User oAuth2User) {
            userEmail = oAuth2User.getAttribute("email");
            ResponseEntity<String> getUserResponse = restTemplate.postForEntity(apiUrl + "/authentication/site/authentication", encryptionUtils.encryptData(userEmail), String.class);
            Map<String, Object> responseBody = objectMapper.readValue(getUserResponse.getBody(), new TypeReference<Map<String, Object>>(){});
            UserUtils.getUserData(model, session, objectMapper, responseBody, encryptionUtils);

            PracticeManager practiceManager = objectMapper.convertValue(responseBody.get("object"), PracticeManager.class);
            String token = (String) session.getAttribute("token");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<PracticeManager> requestEntity = new HttpEntity<>(practiceManager, headers);

            ResponseEntity<PracticeBase> practiceBaseResponseEntity = restTemplate.exchange(apiUrl + "/practiceBase/getAtManager?managerId=" + practiceManager.getId(), HttpMethod.POST, requestEntity, new ParameterizedTypeReference<PracticeBase>() {});
            PracticeBase practiceBase = practiceBaseResponseEntity.getBody();
            if (practiceBase != null){
                practiceBase.setStatusDialing(practiceBase.getStatusDialing().equals("Active") ? null : "checked");

                HttpEntity<PracticeBase> requestEntityVisitLog = new HttpEntity<>(practiceBase, headers);
                ResponseEntity<String> visitLogListResponse =
                        restTemplate.postForEntity(apiUrl + "/practiceBase/getStudentsOnPracticeBase",
                                requestEntityVisitLog,
                                String.class);
                List<VisitLog> visitLogList = objectMapper.readValue(visitLogListResponse.getBody(), new TypeReference<List<VisitLog>>(){});
                List<Student> uniqueStudents = new ArrayList<>();
                for(VisitLog visitLog : visitLogList){
                    if(!uniqueStudents.contains(visitLog.getStudent())){
                        uniqueStudents.add(visitLog.getStudent());
                    }
                }

                model.addAttribute("uniqueStudents", uniqueStudents);
                model.addAttribute("encryptionUtils", encryptionUtils);
            }

            model.addAttribute("practiceBase", practiceBase);
        }
        return "practice_base_panel";
    }

    @PostMapping("/getVisitLogByStudent")
    public ResponseEntity<List<VisitLog>> getAtVisitLogStudent(HttpSession session, @RequestBody int studentId) throws JsonProcessingException {
        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Integer> requestEntityVisitLog = new HttpEntity<>(studentId, headers);
        ResponseEntity<String> visitLogListResponse =
                restTemplate.postForEntity(apiUrl + "/practiceBase/getVisitLogByStudent",
                        requestEntityVisitLog,
                        String.class);
        List<VisitLog> visitLogList = objectMapper.readValue(visitLogListResponse.getBody(), new TypeReference<List<VisitLog>>(){});
        return ResponseEntity.ok(visitLogList);
    }

    @PostMapping("/save")
    public String savePracticeBase(Model model, Authentication authentication, HttpSession session,
                                   @RequestParam("nameBase") String nameBase,
                                   @RequestParam("descriptionAboutBase") String descriptionAboutBase,
                                   @RequestParam("statusDialing") String statusDialing,
                                   @RequestParam(value = "photoPlace", required = false) MultipartFile photoPlace) throws IOException {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User oAuth2User) {
            userEmail = oAuth2User.getAttribute("email");
            ResponseEntity<String> getUserResponse = restTemplate.postForEntity(apiUrl + "/authentication/site/authentication", encryptionUtils.encryptData(userEmail), String.class);
            Map<String, Object> responseBody = objectMapper.readValue(getUserResponse.getBody(), new TypeReference<Map<String, Object>>(){});
            UserUtils.getUserData(model, session, objectMapper, responseBody, encryptionUtils);
            PracticeManager practiceManager = objectMapper.convertValue(responseBody.get("object"), PracticeManager.class);

            File file;
            FileSystemResource fileResource = new FileSystemResource("");
            if(photoPlace != null){
                file = convertMultipartFileToFile(photoPlace);
                fileResource = new FileSystemResource(file);
            }

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            if(photoPlace != null)  body.add("photoPlace", fileResource);
            body.add("nameBase", nameBase);
            body.add("descriptionAboutBase", descriptionAboutBase);
            body.add("practiceManagerID", practiceManager.getId().toString());
            body.add("statusDialing", statusDialing);

            String token = (String) session.getAttribute("token");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/practiceBase/save", requestEntity, String.class);
            model.addAttribute("message", response.getBody());
        }
        return "practice_base_panel";
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getPhotoPracticeBase(HttpSession session, @RequestParam("photoPracticeBase") String photo){
        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        headers.set("Authorization", "Bearer " + token);
        return restTemplate.exchange(apiUrl + "/images/get?isPracticeBase=true&filename=" + photo, HttpMethod.GET, entity, new ParameterizedTypeReference<byte[]>() {});
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            FileCopyUtils.copy(file.getInputStream(), fos);
        }
        return convertedFile;
    }
}
