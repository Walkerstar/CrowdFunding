package com.mcw.scw.webui.controller;

import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.constant.UserConstants;
import com.mcw.scw.webui.service.TMemberServiceFeign;
import com.mcw.scw.webui.service.TProjectServiceFeign;
import com.mcw.scw.webui.vo.req.*;
import com.mcw.scw.webui.vo.resp.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author mcw 2019\12\19 0019-15:24
 */
@Slf4j
@Controller
@RequestMapping("/project")
public class TProjectController {

    @Autowired
    TProjectServiceFeign projectServiceFeign;

    @Autowired
    TMemberServiceFeign memberServiceFeign;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    //点击关注项目
    @ResponseBody
    @RequestMapping("/addFollow/{projectId}")
    public Integer addFlollow(@PathVariable("projectId") Integer projectId,HttpSession session){
        UserRespVo user = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);
        String accessToken = user.getAccessToken();
        String id = stringRedisTemplate.opsForValue().get(accessToken);
        Integer memberId = Integer.parseInt(id);

        AppResponse addstar = projectServiceFeign.addstar(projectId, memberId);
        Integer follow = (Integer) addstar.getData();

        return follow;
    }

    /**
     * 项目发起结束
     * @return
     */
    @RequestMapping("/tostart-step-4")
    public String start4(ProjectLegalVo vo,HttpSession session){

        String projectToken = (String) session.getAttribute("projectToken");

        vo.setProjectToken(projectToken);

        log.info("法人信息-----------------{}",vo);

        AppResponse<Object> response = projectServiceFeign.legal(vo);
        log.info("redis中ProjectRedisStroageVo的数据-------------{}",response.getData());

        UserRespVo userRespVo = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);
        String accessToken = userRespVo.getAccessToken();

        String ops="1";

        projectServiceFeign.submit(accessToken,projectToken,ops);
        return "project/start-step-4";
    }

    @RequestMapping("/tostart-step-3")
    public String start3(HttpSession session){

        List<ProjectReturnVo> pro = (List<ProjectReturnVo>) session.getAttribute("projectReturnVoList");

        AppResponse<Object> response = projectServiceFeign.returnDetail(pro);
        log.info("redis中ProjectRedisStroageVo的数据-------------{}",response.getData());

        return "project/start-step-3";
    }

    @RequestMapping("/returnDetail")
    public String returnDetail(ProjectReturnVo vo,HttpSession session){

        String projectToken = (String) session.getAttribute("projectToken");

        vo.setProjectToken(projectToken);

        log.info("ProjectReturnVo的信息---------------{}",vo);

        List<ProjectReturnVo> list = (List<ProjectReturnVo>) session.getAttribute("projectReturnVoList");

        list.add(vo);

        session.setAttribute("projectReturnVoList",list);


        log.info("添加的回报----------------{}",list);
        return "project/start-step-2";
    }

    @RequestMapping("/tostart-step-2")
    public String start2(ProjectBaseInfoVo vo, HttpSession session, HttpServletRequest request) throws Exception {

        String projectToken = (String) session.getAttribute("projectToken");
        vo.setProjectToken(projectToken);

        //上传文件，获取文件的路径  ,问题未解决
        /*AppResponse<Object> upload = projectServiceFeign.upload(request);
        ArrayList<String> filePath = (ArrayList<String>) upload.getData();
        vo.setHeaderImage(filePath.get(0));
        filePath.remove(0);
        vo.setDetailsImage(filePath);*/


        log.info("ProjectBaseInfoVo项目基本信息---------------{}",vo);
        AppResponse<Object> response = projectServiceFeign.baseinfo(vo);

        log.info("redis中ProjectRedisStroageVo的数据-------------{}",response.getData());

        //为添加回报做准备
        List<ProjectReturnVo> list = new ArrayList<>();
        session.setAttribute("projectReturnVoList",list);


        return "project/start-step-2";
    }

    @RequestMapping("/tostart-step-1")
    public String start1(HttpSession session){

        BaseVo vo = (BaseVo) session.getAttribute("vo");

        log.info("项目的基础标识---------------{}",vo);

        AppResponse<ProjectRedisStroageVo> init = projectServiceFeign.init(vo);

        ProjectRedisStroageVo data = init.getData();
        String projectToken = data.getProjectToken();

        session.setAttribute("projectToken",projectToken);

        log.info("生成的临时项目标识---------------{}",projectToken);

        return "project/start-step-1";
    }

    /**
     * 开始发起项目
     * @return
     */
    @RequestMapping("/tostart")
    public String start(HttpSession session){

        UserRespVo userRespVo = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);

        if(userRespVo==null) {
            return "redirect:/login";
        }

        String accessToken = userRespVo.getAccessToken();

        log.info("用户的令牌------------{}",accessToken);
        BaseVo vo = new BaseVo();
        vo.setAccessToken(accessToken);

        session.setAttribute("vo",vo);

        log.info("项目的基础标识---------------{}",vo);
        return "project/start";
    }


    /**
     * 查看所有项目
     * @return
     */
    @RequestMapping("/projects")
    public String scanProjects(){
        return "project/projects";
    }

    //去结算
    @RequestMapping("/comfirm/order/{num}")
    public String confirmOrder(@PathVariable("num") Integer num,Model model, HttpSession session){

        //页面数据展示
        UserRespVo userRespVo = (UserRespVo) session.getAttribute(UserConstants.LOGIN_MEMBER);

        if(userRespVo==null){

            session.setAttribute("preUrl","/project/confirm/order"+num);
            return "redirect:/login";
        }
        String accessToken = userRespVo.getAccessToken();

        AppResponse<List<UserAddressVo>> resp=memberServiceFeign.address(accessToken);

        List<UserAddressVo> memberAddressList = resp.getData();

        model.addAttribute("memberAddressList",memberAddressList);

        //回报信息（和上一个页面共享数据）
        ReturnPayConfirmVo returnPayConfirmVo= (ReturnPayConfirmVo) session.getAttribute("returnPayConfirmVoSession");

        returnPayConfirmVo.setNum(num);
        returnPayConfirmVo.setTotalPrice(new BigDecimal(num*returnPayConfirmVo.getPrice()+returnPayConfirmVo.getFreight()));

        session.setAttribute("returnPayConfirmVoSession",returnPayConfirmVo);
        return "project/pay-step-2";
    }


    @RequestMapping("/support/{projectId}/{returnId}")
    public String support(@PathVariable("projectId") Integer projectId,
                          @PathVariable("returnId") Integer returnId,Model model,HttpSession session){
        AppResponse<ReturnPayConfirmVo> resp = projectServiceFeign.returnInfo(projectId, returnId);
        ReturnPayConfirmVo data = resp.getData();

        model.addAttribute("returnPayConfirmVo",data);

        session.setAttribute("returnPayConfirmVoSession",data);

        log.debug("项目的ID------{}",projectId);
        log.debug("项目回报的ID----{}",returnId);
        return "project/pay-step-1";
    }

    @RequestMapping("/projectInfo")
    public String index(Integer id, Model model){
        AppResponse<ProjectDetailVo> resp = projectServiceFeign.detailsInfo(id);
        ProjectDetailVo projectDetailVo = resp.getData();
        model.addAttribute("projectDetailVo",projectDetailVo);
        return "project/index";
    }

}
