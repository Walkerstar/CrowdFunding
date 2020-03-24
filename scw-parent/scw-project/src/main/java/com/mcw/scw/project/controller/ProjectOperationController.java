package com.mcw.scw.project.controller;

import com.alibaba.fastjson.JSON;
import com.mcw.scw.project.bean.TProject;
import com.mcw.scw.project.mapper.TProjectMapper;
import com.mcw.scw.project.service.MemberServiceFeign;
import com.mcw.scw.project.service.ProjectInfoService;
import com.mcw.scw.project.service.TProjectService;
import com.mcw.scw.vo.resp.AppResponse;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "项目操作模块")
@RequestMapping("/project/operation")
@RestController
@Slf4j
public class ProjectOperationController {

    @Autowired
    ProjectInfoService projectInfoService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MemberServiceFeign memberServiceFeign;

	@ApiOperation(value = "项目关注")
	@PostMapping("/addstar")
	public AppResponse addstar(Integer projectId,Integer memberId) {
        try {
            TProject projectInfo = projectInfoService.getProjectInfo(projectId);
            Integer follower = projectInfo.getFollower();
            follower=follower+1;
            //修改项目表关注人数
            projectInfoService.updateProjectFollow(projectId,follower);

            //调user端方法向用户关注表中插入数据
            memberServiceFeign.saveProjectFollow(projectId,memberId);

            //可将关注人数存放在redis缓存中，定时收取，调user端方法向用户关注表中插入数据
            AppResponse ok = AppResponse.ok(null);
            ok.setMsg("ok");
            ok.setData(follower);
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<Object> fail = AppResponse.fail(null);
            fail.setMsg("项目关注失败");
            return fail;
        }
    }
	
	@ApiOperation(value = "项目取消关注")
	@PostMapping("/unstar")
	public AppResponse<Object> unstar(Integer projectId,Integer memberId) {
        try {
            TProject projectInfo = projectInfoService.getProjectInfo(projectId);
            Integer follower = projectInfo.getFollower();
            follower=follower-1;
            //修改项目表关注人数
            projectInfoService.updateProjectFollow(projectId,follower);

            //调user端方法向用户关注表中删除数据
            memberServiceFeign.deleteProjectFollow(projectId,memberId);

            //前端发送请求，调user端方法删除用户项目关注表中的数据

//        String pro = stringRedisTemplate.opsForValue().get("projectFollows::" + projectId);
//        TProject projectinfo = JSON.parseObject(pro, TProject.class);
//
//        projectinfo.setFollower(projectinfo.getFollower()-1);
//
//        String proStr = JSON.toJSONString(projectinfo);
//        stringRedisTemplate.opsForValue().set("projectFollows::"+projectId,proStr);
            return AppResponse.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<Object> fail = AppResponse.fail(null);
            fail.setMsg("项目取消关注失败");
            return fail;
        }
    }
	
	@ApiOperation(value = "项目预览")
	@GetMapping("/preshow")
	public AppResponse<Object> preshow() {
		return AppResponse.ok("ok");
	}
	
	
	@ApiOperation(value = "项目修改")
	@PutMapping("/update")
	public AppResponse<Object> update() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目删除")
	@PutMapping("/delete")
	public AppResponse<Object> delete() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目问题查看")
	@GetMapping("/question")
	public AppResponse<Object> question() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目问题添加")
	@PostMapping("/question")
	public AppResponse<Object> addQuestion() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目问题答案添加")
	@PostMapping("/question/answer")
	public AppResponse<Object> addAnswer() {
		return AppResponse.ok("ok");
	}
	
}
