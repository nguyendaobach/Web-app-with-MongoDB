package com.LQD.service;

import com.LQD.entity.LoginResponse;
import com.LQD.entity.MailBody;
import com.LQD.entity.UserLogin;
import com.LQD.entity.UserPrinciple;
import com.LQD.entity.pojo.OTP;
import com.LQD.entity.pojo.Users;
import com.LQD.repository.UsersRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OtpService otpService;

    @Autowired
    EmailService emailService;

    private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);

    public ResponseEntity<?> login(UserLogin userLogin) {

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword())
            );

            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            Users user = userPrinciple.getUser();


            String token = jwtService.generateToken(user.getEmail(), user.getId());

            LoginResponse response = new LoginResponse(token,user.getFullName(),user.getRole());
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password. Please try again.");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Your session has expired. Please log in again.");
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed. Please check your credentials and try again.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }

    public ResponseEntity<?> register(String email) {
        if(email==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mail can not be null");
        }
        Users users=userRepository.findByEmail(email);
        if(users!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conflict mail");
        }
        Integer otp = otpService.generateOTP();
        otpService.save(email, otp);
        sendOTPEmail(email, otp);
        return ResponseEntity.ok("Send mail success");
    }

    private void sendOTPEmail(String email, Integer otp) {
        String APP_NAME = "DEMANDS APP";
        String APP_DESCRIPTION = "Nền tảng dịch vụ trực tuyến của bạn";
        String subject = "Your OTP Code for Registration";
        StringBuilder body = new StringBuilder();
        body.append("<!DOCTYPE html>")
                .append("<html lang=\"vi\">")
                .append("<head><meta charset=\"UTF-8\" />")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />")
                .append("<title>OTP Email</title>")
                .append("<style>")
                .append("h1, p, div { margin: 0; padding: 0; font-family: \"Arial\", sans-serif; box-sizing: border-box; }")
                .append(".box { display: flex; justify-content: center; align-items: center; height: 100vh; width: 100%; }")
                .append(".container { max-width: 600px; width: 100%; background-color: white; border-radius: 8px; box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1); padding: 24px; }")
                .append(".header { text-align: center; }")
                .append(".header h1 { font-size: 28px; font-weight: bold; color: #b41712; }")
                .append(".header p { font-size: 14px; color: #6b7280; margin-top: 8px; }")
                .append(".content { margin-top: 24px; }")
                .append(".content p { color: #374151; font-size: 16px; margin-bottom: 16px; }")
                .append(".otp-box { background-color: #f9fafb; padding: 24px; margin-bottom: 16px; text-align: center; border-radius: 8px; border: 1px solid #e5e7eb; }")
                .append(".otp-code { font-size: 36px; font-family: \"Courier New\", Courier, monospace; font-weight: bold; color: #374151; letter-spacing: 4px; }")
                .append(".otp-expiration { font-size: 14px !important; color: #374151bd !important; margin-top: 8px; }")
                .append(".footer { font-size: 12px; color: #9ca3af; text-align: center; margin-top: 24px; border-top: 1px solid #e5e7eb; padding-top: 16px; }")
                .append(".footer p { margin-bottom: 8px; font-size: 14px; opacity: 0.5; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class=\"box\">")
                .append("<div class=\"container\">")
                .append("<div class=\"header\">")
                .append("<h1>").append(APP_NAME).append("</h1>")
                .append("<p>").append(APP_DESCRIPTION).append("</p>")
                .append("</div>")
                .append("<div class=\"content\">")
                .append("<p>Chào bạn,</p>")
                .append("<p>Mã OTP của bạn là: <span class=\"otp-code\">")
                .append(otp)
                .append("</span></p>")
                .append("<p class=\"otp-expiration\">Mã OTP sẽ hết hạn sau 5 phút.</p>")
                .append("</div>")
                .append("<div class=\"footer\">")
                .append("<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.</p>")
                .append("</div>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        emailService.sendHtmlMessage(new MailBody(email, subject, body.toString()));
    }

    public ResponseEntity<?> save(Users user, int otp) {
        OTP register = otpService.getOTPByEmail(user.getEmail());
        if (register == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
        if (register.getOtp()==otp) {
            if(user.getRole()=="User"||user.getRole()=="Support"){
                user.setPassword(encoder.encode(user.getPassword()));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body("Register success");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role must User or Staff");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }
}
