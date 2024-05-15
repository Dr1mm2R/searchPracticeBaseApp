package com.example.searchPracticeBase.Controllers;

import com.example.searchPracticeBase.Models.WorkApiLogs;
import com.example.searchPracticeBase.Utils.EncryptionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.searchPracticeBase.Utils.UserUtils.getAdminData;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final EncryptionUtils encryptionUtils = new EncryptionUtils();

    @Value("${api.url.server}")
    private String apiUrl;

    @GetMapping
    public String getPage(Model model){
        return "admin_auth";
    }

    @PostMapping
    public String authAdmin(@RequestParam("login") String login,
                            @RequestParam("password") String pass,
                            Model model, HttpSession session){
        Map<String, String> adminData = getAdminData();
        String loginText = encryptionUtils.encryptData(login);
        String passwordText = encryptionUtils.encryptData(pass);
        if(adminData.get("login").equals(loginText) && adminData.get("password").equals(passwordText)){
            session.setAttribute("role", "admin");

            ResponseEntity<List<String>> logList = restTemplate.exchange(apiUrl + "/firebase_files/getLogs", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<String>>() {});
            ResponseEntity<List<String>> backupList = restTemplate.exchange(apiUrl + "/firebase_files/getBackUps", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<List<String>>() {});
            ResponseEntity<WorkApiLogs> apiLogsList = restTemplate.exchange(apiUrl + "/apiLogs", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<WorkApiLogs>() {});
            WorkApiLogs workApiLogs = apiLogsList.getBody();
            model.addAttribute("log_list", logList.getBody());
            model.addAttribute("backup_list", backupList.getBody());
            model.addAttribute("api_work_logs", workApiLogs);
            return "admin_panel";
        }
        else{
            model.addAttribute("info", "Не удалось войти");
            return "admin_auth";
        }
    }

    @PostMapping("/download-file")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("nameFile") String nameFile, @RequestParam("isLogFile") boolean isLogFile){
        Map<String, Object> map = new HashMap<>();
        map.put("nameFile", nameFile);
        map.put("isLogFile", isLogFile);
        ResponseEntity<byte[]> fileBytesResponse = restTemplate.postForEntity(apiUrl + "/firebase_files/download-file", map, byte[].class);
        byte[] fileBytes = fileBytesResponse.getBody();

        if (fileBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", nameFile);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileBytes.length)
                    .body(fileBytes);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке файла".getBytes());
        }
    }
}
