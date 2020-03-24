package com.mcw.scw.project.controller;

import com.alibaba.fastjson.JSON;
import com.mcw.scw.enums.ProjectStatusEnume;
import com.mcw.scw.project.bean.TReturn;
import com.mcw.scw.project.component.OssTemplate;
import com.mcw.scw.project.constants.ProjectConstant;
import com.mcw.scw.project.service.TProjectService;
import com.mcw.scw.project.vo.req.*;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Api(tags = "项目发起模块")
@RequestMapping("/project/create")
@RestController
public class ProjectCreateController {

    @Autowired
    OssTemplate ossTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TProjectService projectService;

	@ApiOperation(value = "1-项目初始创建")
	@PostMapping("/init")
	public AppResponse<ProjectRedisStroageVo> init(@RequestBody BaseVo vo) {
        try {
            //1.验证用户是否登录
            String accessToken = vo.getAccessToken();

            if(StringUtils.isEmpty(accessToken)){
                AppResponse resp= AppResponse.fail(null);
                resp.setMsg("请务必提供assessToken值");
                return resp;
            }

            String memberId = stringRedisTemplate.opsForValue().get(accessToken);

            if(StringUtils.isEmpty(memberId)){
                AppResponse resp=AppResponse.fail(null);
                resp.setMsg("请先登录系统，在发布项目");
                return resp;
            }

            //2.初始化 bigVO
            ProjectRedisStroageVo bigVo=new ProjectRedisStroageVo();

            BeanUtils.copyProperties(vo,bigVo);

            String projectToken=UUID.randomUUID().toString().replaceAll("-","");
            bigVo.setProjectToken(projectToken);
            bigVo.setMemberid(Integer.parseInt(memberId));

            //3.数据的存储:将bigVo数据存储到redis中
            String bigStr = JSON.toJSONString(bigVo);//fastjson组件
            stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken,bigStr);

            log.debug("vo的数据--{}",bigVo);
            return AppResponse.ok(bigVo);//jackson组件
        } catch (BeansException e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

	@ApiOperation(value = "2-项目基本信息")
	@PostMapping("/baseinfo")
	public AppResponse<Object> baseinfo(@RequestBody ProjectBaseInfoVo vo) {
        try {
            //1.验证用户是否登录
            /*String accessToken = vo.getAccessToken();

            if(StringUtils.isEmpty(accessToken)){
                AppResponse resp= AppResponse.fail(null);
                resp.setMsg("请务必提供assessToken值");
                return resp;
            }

            String memberId = stringRedisTemplate.opsForValue().get(accessToken);

            if(StringUtils.isEmpty(memberId)){
                AppResponse resp=AppResponse.fail(null);
                resp.setMsg("请先登录系统，在发布项目");
                return resp;
            }*/

            //2.从redis中获取bigVo数据，将小vo封装到大VO中
            String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken());


            log.debug("BIGSTR的数据;{}",bigStr);
            ProjectRedisStroageVo bigVo = JSON.parseObject(bigStr, ProjectRedisStroageVo.class);

            log.debug("项目的基本信息：{}",vo);

            BeanUtils.copyProperties(vo,bigVo);

            bigStr=JSON.toJSONString(bigVo);
            stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+vo.getProjectToken(),bigStr);


            log.debug("大VO的数据:{}",bigVo);

            return AppResponse.ok(bigVo);
        } catch (BeansException e) {
            e.printStackTrace();
            log.error("表单处理失败",e.getMessage());
            return AppResponse.fail(null);
        }
	}
	
	@ApiOperation(value = "3-添加项目回报档位")
	@PostMapping("/return")
	public AppResponse<Object> returnDetail(@RequestBody List<ProjectReturnVo> pro) {
        try {
            //1.验证用户是否登录
            /*String accessToken = pro.get(0).getAccessToken();

            if(StringUtils.isEmpty(accessToken)){
                AppResponse resp= AppResponse.fail(null);
                resp.setMsg("请务必提供assessToken值");
                return resp;
            }

            String memberId = stringRedisTemplate.opsForValue().get(accessToken);

            if(StringUtils.isEmpty(memberId)){
                AppResponse resp=AppResponse.fail(null);
                resp.setMsg("请先登录系统，在发布项目");
                return resp;
            }*/

            //2.从redis中获取bigVo数据，将小vo封装到大VO中
            String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + pro.get(0).getProjectToken());

            ProjectRedisStroageVo bigVo = JSON.parseObject(bigStr, ProjectRedisStroageVo.class);

            List<TReturn> projectReturns = bigVo.getProjectReturns();

            for (ProjectReturnVo projectReturnVo : pro) {
                TReturn returnObj=new TReturn();
                BeanUtils.copyProperties(projectReturnVo,returnObj);
                projectReturns.add(returnObj);
            }

            bigStr=JSON.toJSONString(bigVo);
            stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+pro.get(0).getProjectToken(),bigStr);

            log.debug("大VO的数据:{}",bigVo);

            return AppResponse.ok(bigVo);
        } catch (BeansException e) {
            e.printStackTrace();
            log.error("表单处理失败",e.getMessage());
            return AppResponse.fail(null);
        }
	}


	/**
	 * 文件上传表单要求:
	 *  1.method="post"
	 *  2.enctype="multipart/form-data"
	 *  3.type="file" name="uploadfile"
	 *
	 *  springMvc框架集成了commons-fileupload 和 commons-io组件，完成文件上传操作
	 *  springMvc提供文件上传解析器
	 *  Controller处理文件上传时，通过MultipartFile接受文件
	 * @return
	 */
    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public AppResponse<Object> upload(HttpServletRequest request) {

        List<String> filepathList = new ArrayList<>();
        try {
            //上传文件
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

            // 判断 request 是否有文件上传,即多部分请求
            if(multipartResolver.isMultipart(request)) {

                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

                // 取得request中的所有文件名
                Iterator<String> files = multiRequest.getFileNames();
                while (files.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(files.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String filename = file.getOriginalFilename();
                        filename = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6) + "_" + filename;

                        String upload = ossTemplate.upload(filename, file.getInputStream());
                        filepathList.add(upload);
                    }
                }
            }
            log.debug("上传文件路径--{}",filepathList);
            return AppResponse.ok(filepathList);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("文件上传失败");
            return AppResponse.fail(null);
        }
    }


	@ApiOperation(value = "4-确认项目法人信息")
	@PostMapping("/confirm/legal")
	public AppResponse<Object> legal(@RequestBody ProjectLegalVo vo) {
        try {
            //1.验证用户是否登录
           /* String accessToken = vo.getAccessToken();

            if (StringUtils.isEmpty(accessToken)) {
                AppResponse resp = AppResponse.fail(null);
                resp.setMsg("请务必提供assessToken值");
                return resp;
            }

            String memberId = stringRedisTemplate.opsForValue().get(accessToken);

            if (StringUtils.isEmpty(memberId)) {
                AppResponse resp = AppResponse.fail(null);
                resp.setMsg("请先登录系统，在发布项目");
                return resp;
            }*/

            //2.从redis中获取bigVo数据，将小vo封装到大VO中
             String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX+vo.getProjectToken());

             ProjectRedisStroageVo bigVo=JSON.parseObject(bigStr,ProjectRedisStroageVo.class);

             log.debug("项目临时标识---------{}",vo.getProjectToken());
             BeanUtils.copyProperties(vo,bigVo);

            bigStr=JSON.toJSONString(bigVo);
            stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+vo.getProjectToken(),bigStr);

            log.debug("大VO的数据:{}",bigVo);
            return AppResponse.ok("ok");
        }catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

    @ApiOperation(value = "5-项目提交审核申请")
    @PostMapping("/submit")
    public AppResponse<Object> submit( String accessToken,String projectToken,String ops) {
        try {
            //1.验证用户是否登录
//            String accessToken = pro.get(0).getAccessToken();

            if(StringUtils.isEmpty(accessToken)){
                AppResponse resp= AppResponse.fail(null);
                resp.setMsg("请务必提供assessToken值");
                return resp;
            }

            String memberId = stringRedisTemplate.opsForValue().get(accessToken);

            if(StringUtils.isEmpty(memberId)){
                AppResponse resp=AppResponse.fail(null);
                resp.setMsg("请先登录系统，在发布项目");
                return resp;
            }

            if("0".equals(ops)) {//保存草稿

                projectService.saveProject(accessToken,projectToken,ProjectStatusEnume.DRAFT.getCode());

                return AppResponse.ok("ok");
            }else if("1".equals(ops)) { //保存提交审核

                projectService.saveProject(accessToken,projectToken,ProjectStatusEnume.SUBMIT_AUTH.getCode());
                return AppResponse.ok("ok");
            }else {
                log.debug("");
                return AppResponse.fail("请求方式不支持");
            }

        } catch (BeansException e) {
            e.printStackTrace();
            log.error("项目失败",e.getMessage());
            return AppResponse.fail(null);
        }
    }
	
//	@ApiOperation(value = "删除项目回报档位")
//	@DeleteMapping("/return")
//	public AppResponse<Object> deleteReturnDetail() {
//		return AppResponse.ok("ok");
//	}
//
//	@ApiOperation(value = "项目草稿保存")
//	@PostMapping("/tempsave")
//	public AppResponse<Object> tempsave() {
//		return AppResponse.ok("ok");
//	}
//

//    @ApiOperation(value = "上传图片")
//    @PostMapping("/upload")
//    public AppResponse<Object> upload(@RequestParam("uploadfile") MultipartFile[] files) {
//
//        try {
//            List<String> filepathList = new ArrayList<>();
//            for (MultipartFile file:files){
//                String filename = file.getOriginalFilename();
//
//                filename=UUID.randomUUID().toString().replaceAll("-","").substring(0,6)+"_"+filename;
//
//                String upload = ossTemplate.upload(filename, file.getInputStream());
//                filepathList.add(upload);
//            }
//            log.debug("上传文件路径--{}",filepathList);
//            return AppResponse.ok(filepathList);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.debug("文件上传失败");
//            return AppResponse.fail(null);
//        }
//    }
	
}
