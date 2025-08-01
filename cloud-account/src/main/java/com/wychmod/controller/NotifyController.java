package com.wychmod.controller;

import com.google.code.kaptcha.Producer;
import com.wychmod.controller.request.SendCodeRequest;
import com.wychmod.enums.BizCodeEnum;
import com.wychmod.enums.SendCodeEnum;
import com.wychmod.service.NotifyService;
import com.wychmod.utils.CommonUtil;
import com.wychmod.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: wychmod
 * @date: 2025-07-15
 */
@RestController
@RequestMapping("/api/notify/v1")
@Slf4j
public class NotifyController {

    @Resource
    public Producer captchaProducer;

    @Autowired
    public StringRedisTemplate redisTemplate;

    @Resource
    public NotifyService notifyService;

    /**
     * 验证码过期时间
     */
    private static final long CAPTCHA_CODE_EXPIRED = 1000 * 10 *  60;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String captchaProducerText = captchaProducer.createText();
        log.info("验证码：{}", captchaProducerText);

        redisTemplate.opsForValue().set(getCaptchaKey(request),captchaProducerText,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = captchaProducer.createImage(captchaProducerText);

        try (ServletOutputStream outputStream = response.getOutputStream()){
            ImageIO.write(bufferedImage,"jpg",outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("获取流出错:{}",e.getMessage());
        }
    }

    @PostMapping("/send_code")
    public JsonData sendCode(@RequestBody SendCodeRequest sendCodeRequest, HttpServletRequest request){

        String key = getCaptchaKey(request);

        String cacheCaptcha = redisTemplate.opsForValue().get(key);

        String captcha = sendCodeRequest.getCaptcha();

        if(captcha!=null && cacheCaptcha !=null && cacheCaptcha.equalsIgnoreCase(captcha)){
            //成功
            JsonData jsonData = notifyService.sendCode(SendCodeEnum.USER_REGISTER,sendCodeRequest.getTo());
            redisTemplate.delete(key);
            return jsonData;
        }else {
            log.info("captcha:{}, cacheCaptcha:{}", captcha, cacheCaptcha);
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA_ERROR);
        }


    }


    private String getCaptchaKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "account-service:captcha:"+CommonUtil.MD5(ip+userAgent);
        log.info("验证码key:{}",key);
        return key;
    }


}
