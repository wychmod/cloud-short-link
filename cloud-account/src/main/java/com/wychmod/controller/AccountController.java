package com.wychmod.controller;

import com.wychmod.controller.request.AccountLoginRequest;
import com.wychmod.controller.request.AccountRegisterRequest;
import com.wychmod.enums.BizCodeEnum;
import com.wychmod.service.AccountService;
import com.wychmod.service.FileService;
import com.wychmod.utils.JsonData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @description:
 * @author: wychmod
 * @date: 2025-06-28
 */
@RestController
@RequestMapping("/api/account/v1")
public class AccountController {

    @Resource
    private FileService fileService;

    @Resource
    private AccountService accountService;

    /**
     * 用户头像文件上传接口，最大支持1M大小的文件上传
     *
     * @param file 用户上传的文件对象，通过表单中的"file"字段传递
     * @return JsonData 响应结果，包含上传成功后的文件访问URL或失败状态码
     */
    @PostMapping("/upload")
    public JsonData uploadUserImg(@RequestPart("file") MultipartFile file) {

        String result = fileService.uploadUserImg(file);

        return result != null ? JsonData.buildSuccess(result) : JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }

    /**
     * 用户注册接口
     * @param registerRequest 注册请求参数对象，包含用户注册所需的信息
     * @return JsonData 注册结果，包含注册成功或失败的状态信息
     */
    @PostMapping("/register")
    public JsonData register(@RequestBody AccountRegisterRequest registerRequest) {

        // 调用账户服务执行注册逻辑
        JsonData jsonData = accountService.register(registerRequest);

        return jsonData;
    }

    @PostMapping("/login")
    public JsonData login(@RequestBody AccountLoginRequest loginRequest) {

        // 调用账户服务执行登录逻辑
        JsonData jsonData = accountService.login(loginRequest);

        return jsonData;
    }


}
