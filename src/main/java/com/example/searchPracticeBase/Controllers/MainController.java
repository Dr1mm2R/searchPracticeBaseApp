package com.example.searchPracticeBase.Controllers;

import com.example.searchPracticeBase.Models.PracticeManager;
import com.example.searchPracticeBase.Utils.EncryptionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.searchPracticeBase.Utils.UserUtils.getUserData;

@Controller
@RequestMapping("/main")
public class MainController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final EncryptionUtils encryptionUtils = new EncryptionUtils();

    @Value("${api.url.server}")
    private String apiUrl;

    @GetMapping
    public String profilePage(Model model, Authentication authentication, HttpSession session) throws JsonProcessingException {
        String userEmail = "";
        if (authentication.getPrincipal() instanceof DefaultOAuth2User oAuth2User) {
            userEmail = oAuth2User.getAttribute("email");

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/authentication/site/authentication", encryptionUtils.encryptData(userEmail), String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>(){});
            if(responseBody.size() != 0){
                getUserData(model, session, objectMapper, responseBody, encryptionUtils);
            }else{
                model.addAttribute("isNewAcc", true);
                model.addAttribute("email", userEmail);
            }
        }
        return "main";
    }

    @PostMapping("/logout")
    public String logoutMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        SecurityContextHolder.clearContext();
        request.logout();
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @PostMapping("/registration")
    public String saveRegistrationData(
            @RequestParam("email") String email,
            @RequestParam("position") String position,
            @RequestParam("direction") String direction,
            @RequestParam("experience") int experience,
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName,
            @RequestParam(value = "middleName", required = false) String middleName,
            @RequestParam("role") String role){

        Map<String, String> sentData = new HashMap<>();
        sentData.put("email", encryptionUtils.encryptData(email));
        sentData.put("position", position);
        sentData.put("direction", direction);
        sentData.put("experience", String.valueOf(experience));
        sentData.put("lastName", lastName);
        sentData.put("firstName", firstName);
        sentData.put("middleName", middleName);
        sentData.put("role", role);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/authentication/site/registration", sentData, String.class);

        if(response.getStatusCode() != HttpStatusCode.valueOf(200)){
            System.out.println("test");
        }else{
            System.out.println("test");
        }
        return "redirect:/main";
    }
}
