package com.example.searchPracticeBase.Controllers;

import com.example.searchPracticeBase.Models.PracticeManager;
import com.example.searchPracticeBase.Utils.EncryptionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final EncryptionUtils encryptionUtils = new EncryptionUtils();

    @Value("${api.url.server}")
    private String apiUrl;

    @PostMapping("/save")
    public String saveProfileData(@ModelAttribute PracticeManager practiceManager,
                                  HttpSession session,
                                  HttpServletRequest request){
        String referer = request.getHeader("Referer");
        String token = (String) session.getAttribute("token");

        practiceManager.getContact().setEmail(encryptionUtils.encryptData(practiceManager.getContact().getEmail()));
        practiceManager.getContact().setVkPageData("https://vk.com/" + practiceManager.getContact().getVkPageData());
        practiceManager.getContact().setWhatsAppData("https://wa.me/" + practiceManager.getContact().getWhatsAppData());
        practiceManager.getContact().setTelegramData("https://t.me/" + practiceManager.getContact().getTelegramData().replace("@", ""));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<PracticeManager> requestEntity = new HttpEntity<>(practiceManager, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                apiUrl + "/practice_Manager/save",
                requestEntity,
                String.class);

        session.setAttribute("openPanel", true);
        if(response.getStatusCode() == HttpStatusCode.valueOf(200)){
            session.setAttribute("profileNotify", "Успешное сохранение профиля");
        }else{
            session.setAttribute("profileNotify", "Не удалось сохранить профиль");
        }
        return "redirect:" + referer;
    }

}
