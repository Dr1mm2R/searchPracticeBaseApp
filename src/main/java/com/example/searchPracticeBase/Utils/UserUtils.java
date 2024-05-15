package com.example.searchPracticeBase.Utils;

import com.example.searchPracticeBase.Models.PracticeManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.Map;

public class UserUtils {
    public static void getUserData(Model model, HttpSession session, ObjectMapper objectMapper, Map<String, Object> responseBody, EncryptionUtils encryptionUtils) {
        PracticeManager practiceManager = objectMapper.convertValue(responseBody.get("object"), PracticeManager.class);

        practiceManager.getContact().setVkPageData(practiceManager.getContact().getVkPageData().replace("https://vk.com/", ""));
        practiceManager.getContact().setWhatsAppData(practiceManager.getContact().getWhatsAppData().replace("https://wa.me/", ""));
        practiceManager.getContact().setTelegramData(practiceManager.getContact().getTelegramData().replace("https://t.me/", "@"));

        session.setAttribute("token", responseBody.get("token"));
        model.addAttribute("practiceManager", practiceManager);
        model.addAttribute("encryptionUtils", encryptionUtils);
        model.addAttribute("isNewAcc", false);
        model.addAttribute("openPanel", session.getAttribute("openPanel"));
        model.addAttribute("profileNotify", session.getAttribute("profileNotify"));

        session.removeAttribute("openPanel");
        session.removeAttribute("profileNotify");
    }

    public static Map<String, String> getAdminData(){
        return Map.of("login", "Q11cQA==", "password", "QUBaQlRCUmJWXlQAAwQBFwQH");
    }
}
