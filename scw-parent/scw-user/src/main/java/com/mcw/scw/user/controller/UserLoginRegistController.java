package com.mcw.scw.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.mcw.scw.user.component.SmsTemplate;
import com.mcw.scw.user.service.TMemberService;
import com.mcw.scw.user.vo.req.UserRegistVo;
import com.mcw.scw.user.vo.resp.UserRespVo;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户登陆注册模块")
@RequestMapping("/user")
@RestController
@Slf4j
public class UserLoginRegistController {

	@Autowired
	SmsTemplate smsTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	TMemberService memberService;
	
	@ApiOperation(value="用户登陆")
	@ApiImplicitParams(value={
			@ApiImplicitParam(value="登陆账号",name="loginacct"),
			@ApiImplicitParam(value="用户密码",name="password")
	})
	@PostMapping("/login")
	public AppResponse<UserRespVo> login(@RequestParam("loginacct") String loginacct,
                                         @RequestParam("password") String password){

        try {
            UserRespVo vo=memberService.getUserByLogin(loginacct,password);
            log.debug("登录成功--{}",loginacct);
            return AppResponse.ok(vo);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("登录失败--{}--{}",loginacct,e.getMessage());
            AppResponse fail = AppResponse.fail(null);
            fail.setMsg(e.getMessage());
            return fail;
        }
    }
	
	@ApiOperation(value="用户注册") 
	@PostMapping("/register")
	public AppResponse<Object> register(@RequestBody UserRegistVo vo) {

        String loginacct = vo.getLoginacct();
        if(!StringUtils.isEmpty(loginacct)){
            String code = stringRedisTemplate.opsForValue().get(loginacct);

            if(!StringUtils.isEmpty(code)){
                if(code.equals(vo.getCode())){
                    //需要校验账号是否唯一
                    //需要校验邮箱地址是否被占用
                    //保存数据
                    int i=memberService.saveTMember(vo);

                    if(i==1){
                        stringRedisTemplate.delete(loginacct);//清理缓存
                        return AppResponse.ok("ok");
                    }else {
                        return AppResponse.fail(null);
                    }
                }else {
                    AppResponse<Object> fail = AppResponse.fail(null);
                    fail.setMsg("验证码不一致，请重新输入");
                    return fail;
                }
            }else {
                AppResponse<Object> fail = AppResponse.fail(null);  //存在漏洞，短信可一直重复发送，需做限制
                fail.setMsg("验证码已失效，请重新发送");
                return fail;
            }
        }else {
            AppResponse<Object> fail = AppResponse.fail(null);
            fail.setMsg("用户名不能为空");
            return fail;
        }
	}
	
	@ApiOperation(value="发送短信验证码") 
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(String loginacct){

	    String code=UUID.randomUUID().toString().replaceAll("-","a").substring(0,6);

        Map<String, String> querys = new HashMap<>();
        querys.put("mobile", loginacct);
        querys.put("param", "code:"+code);
        querys.put("tpl_id", "TP1711063");

	    smsTemplate.sendSms(querys);

	    stringRedisTemplate.opsForValue().set(loginacct,code,5,TimeUnit.MINUTES);
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="验证短信验证码") 
	@PostMapping("/valide")
	public AppResponse<Object> valide(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="重置密码") 
	@PostMapping("/reset")
	public AppResponse<Object> reset(){
		return AppResponse.ok("ok");
	} 
	
	

}
