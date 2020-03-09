package com.mcw.scw.webui.controller;

import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.constant.ProjectConstants;
import com.mcw.scw.webui.constant.UserConstants;
import com.mcw.scw.webui.service.TMemberServiceFeign;
import com.mcw.scw.webui.service.TProjectServiceFeign;
import com.mcw.scw.webui.vo.resp.UserFollowProjectVo;
import com.mcw.scw.webui.vo.resp.UserProjectVo;
import com.mcw.scw.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author mcw 2019\12\20 0020-14:45
 */
@Slf4j
@Controller
public class TMemberController {

    @Autowired
    TMemberServiceFeign memberServiceFeign;

    @Autowired
    TProjectServiceFeign projectServiceFeign;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //取消关注项目
    @RequestMapping("/member/unmyfollow/{projectId}")
    public String unmyfollow(@PathVariable("projectId") Integer projectId, HttpSession session){

        UserRespVo user = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);
        String accessToken = user.getAccessToken();
        String id = stringRedisTemplate.opsForValue().get(accessToken);
        Integer memberId = Integer.parseInt(id);

        log.info("要取消关注的项目ID和会员ID-------------------------------------------------------------{},{}",projectId,memberId);
        projectServiceFeign.unstar(projectId,memberId);

        return "forward:/member/minecrowdfunding";

    }

    @ResponseBody
    @RequestMapping("/member/myfollow")
    public void myfollow(HttpSession session){
        session.removeAttribute(ProjectConstants.FOLLOW_PROJECT_LIST);
        UserRespVo user = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);
        String accessToken = user.getAccessToken();

        AppResponse<List<UserFollowProjectVo>> response = memberServiceFeign.star(accessToken);
        List<UserFollowProjectVo> data = response.getData();
        session.setAttribute(ProjectConstants.FOLLOW_PROJECT_LIST,data);

    }

    @RequestMapping("/member/minecrowdfunding")
    public String myOrderList(HttpSession session){

        log.debug("支付后，同步请求处理。。。。。");


        UserRespVo user = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);
        if (user == null) {
            return "redirect:/login";
        }
        if(session.getAttribute(ProjectConstants.SUPPORT_PROJECT_LIST)==null){
            String accessToken = user.getAccessToken();
            log.info("用户的令牌--------------{}",accessToken);
            AppResponse<List<UserProjectVo>> response = memberServiceFeign.support(accessToken);

            List<UserProjectVo> supportPro = response.getData();
            session.setAttribute(ProjectConstants.SUPPORT_PROJECT_LIST, supportPro);

            log.info("我支持的项目的信息------------{}", supportPro);
        }

        return "member/minecrowdfunding";


    }


    @RequestMapping("/member")
    public String member(){
        return "member/member";
    }

    @RequestMapping("/member/accttype")
    public String accttype(){
        return "member/accttype";
    }

    @RequestMapping("/member/apply")
    public String apply(){
        return "member/apply";
    }

    @RequestMapping("/member/apply-1")
    public String apply1(){
        return "member/apply-1";
    }

    @RequestMapping("/member/apply-2")
    public String apply2(){
        return "member/apply-2";
    }

    @RequestMapping("/member/apply-3")
    public String apply3(){
        return "member/apply-3";
    }



}
