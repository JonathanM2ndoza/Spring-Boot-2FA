package com.jmendoza.springboot.twofa.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jmendoza.springboot.twofa.model.User;
import com.jmendoza.springboot.twofa.security.TOTPAuthenticator;
import com.jmendoza.springboot.twofa.service.UserService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/2fa")
public class TOTPController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users")
    public @ResponseBody
    User createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        savedUser.setPassword("");
        return savedUser;
    }

    @GetMapping(value = "/qrcode/get/{username}")
    public String generateQRCode(@PathVariable("username") String userName) throws Throwable {
        String otpProtocol = userService.generateOTPProtocol(userName);
        System.out.println(otpProtocol);
        return userService.generateQRCode(otpProtocol);
    }

    @PostMapping(value = "/qrcode/validate/{username}")
    public boolean validateTotp(@PathVariable("username") String userName, @Valid @RequestBody String requestJson) {
        JSONObject json = new JSONObject(requestJson);
        return userService.validateTotp(userName, Integer.parseInt(json.getString("totpKey")));
    }

}
