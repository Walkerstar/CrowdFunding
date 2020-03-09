package com.mcw.scw.project.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mcw.scw.project.bean.*;
import com.mcw.scw.project.component.OssTemplate;
import com.mcw.scw.project.service.MemberServiceFeign;
import com.mcw.scw.project.service.ProjectInfoService;
import com.mcw.scw.project.vo.resp.ProjectDetailVo;
import com.mcw.scw.project.vo.resp.ProjectVo;
import com.mcw.scw.project.vo.resp.ReturnPayConfirmVo;
import com.mcw.scw.project.vo.resp.TMember;
import com.mcw.scw.vo.resp.AppResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "项目基本功能模块（文件上传、项目信息获取等）")
@Slf4j
@RequestMapping("/project")
@RestController
public class ProjectInfoController {

	@Autowired
	OssTemplate ossTemplate;

	@Autowired
	ProjectInfoService projectInfoService;

	@Autowired
	MemberServiceFeign memberServiceFeign;

	@ApiOperation("[+]获取项目信息详情")
	@GetMapping("/details/info/{projectId}")
	public AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId) {
		TProject p = projectInfoService.getProjectInfo(projectId);
        ProjectDetailVo projectVo = new ProjectDetailVo();

		// 1、查出这个项目的所有图片
		List<TProjectImages> projectImages = projectInfoService.getProjectImages(p.getId());
		for (TProjectImages tProjectImages : projectImages) {
			if (tProjectImages.getImgtype() == 0) {
				projectVo.setHeaderImage(tProjectImages.getImgurl());
			} else {
				List<String> detailsImage = projectVo.getDetailsImage();
				detailsImage.add(tProjectImages.getImgurl());
			}
		}

		// 2、项目的所有支持档位；
		List<TReturn> returns = projectInfoService.getProjectReturns(p.getId());
		projectVo.setProjectReturns(returns);

		BeanUtils.copyProperties(p, projectVo);
		return AppResponse.ok(projectVo);
	}

    @ApiOperation("[+]获取某个项目信息详情")
    @GetMapping("/details/info")
    public AppResponse<ProjectDetailVo> getdetailsInfo(@RequestParam("projectId") Integer projectId) {
        TProject p = projectInfoService.getProjectInfo(projectId);
        ProjectDetailVo projectVo = new ProjectDetailVo();

        BeanUtils.copyProperties(p, projectVo);
        log.debug("某个项目的详情-------------------------------------{}",projectVo);
        return AppResponse.ok(projectVo);
    }

	@ApiOperation("[+]获取项目回报列表")
	@GetMapping("/details/returns/{projectId}")
	public AppResponse<List<TReturn>> detailsReturn(@PathVariable("projectId") Integer projectId) {

		List<TReturn> returns = projectInfoService.getProjectReturns(projectId);
		return AppResponse.ok(returns);
	}

	@ApiOperation("[+]获取项目某个回报档位信息")
	@GetMapping("/details/returns/info/{returnId}")
	public AppResponse<TReturn> getreturnInfo(@PathVariable("returnId") Integer returnId) {
		TReturn tReturn = projectInfoService.getProjectReturnById(returnId);
		return AppResponse.ok(tReturn);
	}

    @ApiOperation("[+]确认回报信息")
    @GetMapping("/confim/returns/{projectId}/{returnId}")
    public AppResponse<ReturnPayConfirmVo> returnInfo(@PathVariable("projectId") Integer projectId,
                                                      @PathVariable("returnId") Integer returnId) {
        ReturnPayConfirmVo vo = new ReturnPayConfirmVo();

        //回报数据
        TReturn tReturn = projectInfoService.getProjectReturnById(returnId);
        vo.setReturnId(tReturn.getId());
        vo.setReturnContent(tReturn.getContent());
        vo.setNum(1);
        vo.setPrice(tReturn.getSupportmoney());
        vo.setFreight(tReturn.getFreight());
        vo.setSignalpurchase(tReturn.getSignalpurchase());
        vo.setPurchase(tReturn.getPurchase());


        //项目数据
        TProject project = projectInfoService.getProjectInfo(projectId);
        vo.setProjectId(project.getId());
        vo.setProjectName(project.getName());
        vo.setProjectRemark(project.getRemark());

        log.error("项目名字为--------------{}",project.getName());

        //发起人信息
        Integer memberid = project.getMemberid();
        AppResponse<TMember> resp = memberServiceFeign.getMemberById(memberid);
        TMember member = resp.getData();

        vo.setMemberId(memberid);
        vo.setMemberName(member.getLoginacct());

        vo.setTotalPrice(new BigDecimal(vo.getNum()*vo.getPrice()+vo.getFreight()));

        return AppResponse.ok(vo);
    }

	@ApiOperation("[+]获取系统所有的项目分类")
	@GetMapping("/types")
	public AppResponse<List<TType>> types() {

		List<TType> types = projectInfoService.getProjectTypes();
		return AppResponse.ok(types);
	}

	@ApiOperation("[+]获取系统所有的项目标签")
	@GetMapping("/tags")
	public AppResponse<List<TTag>> tags() {
		List<TTag> tags = projectInfoService.getAllProjectTags();
		return AppResponse.ok(tags);
	}

	@ApiOperation("[+]获取系统所有的项目")
	@GetMapping("/all")
	public AppResponse<List<ProjectVo>> all() {

		// 1、分步查询，先查出所有项目
		// 2、再查询这些项目图片
		List<ProjectVo> prosVo = new ArrayList<>();

		// 1、连接查询，所有的项目left join 图片表，查出所有的图片
		// left join：笛卡尔积 A*B 1000万*6 = 6000万
		// 大表禁止连接查询；
		List<TProject> pros = projectInfoService.getAllProjects();

		for (TProject tProject : pros) {
			Integer id = tProject.getId();
			List<TProjectImages> images = projectInfoService.getProjectImages(id);
			ProjectVo projectVo = new ProjectVo();
			BeanUtils.copyProperties(tProject, projectVo);

			for (TProjectImages tProjectImages : images) {
				if (tProjectImages.getImgtype() == 0) {
					projectVo.setHeaderImage(tProjectImages.getImgurl());
					log.debug("头部图片的路径-----{}",tProjectImages.getImgurl());
				}
			}
			prosVo.add(projectVo);

		}

		return AppResponse.ok(prosVo);
	}

	// 查热门推荐、分类推荐
	@ApiOperation("[+]获取系统所有的热点项目")
	@GetMapping("/all/host")
	public AppResponse<List<ProjectVo>> allHost() {

		// 1、分步查询，先查出所有项目
		// 2、再查询这些项目图片
		List<ProjectVo> prosVo = new ArrayList<>();

		// 1、连接查询，所有的项目left join 图片表，查出所有的图片
		// left join：笛卡尔积 A*B 1000万*6 = 6000万
		// 大表禁止连接查询；
		List<TProject> pros = projectInfoService.getAllHostProjects();

		for (TProject tProject : pros) {
			Integer id = tProject.getId();
			List<TProjectImages> images = projectInfoService.getProjectImages(id);
			ProjectVo projectVo = new ProjectVo();
			BeanUtils.copyProperties(tProject, projectVo);
			for (TProjectImages tProjectImages : images) {
				if (tProjectImages.getImgtype() == 0) {
					projectVo.setHeaderImage(tProjectImages.getImgurl());
				}
			}
			prosVo.add(projectVo);

		}

		return AppResponse.ok(prosVo);
	}

	/**
	 * <input type='file' name='file'/>
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * 
	 *                     /resources/bootstrap/xxxxx /static/css/xxx
	 */
//	@ApiOperation("文件上传功能")
//	@PostMapping("/upload")
//	public AppResponse<Map<String, Object>> upload(@RequestParam("file") MultipartFile[] file) throws IOException {
//		Map<String, Object> map = new HashMap<>();
//		List<String> list = new ArrayList<>();
//		if (file != null && file.length > 0) {
//			for (MultipartFile item : file) {
//				if (!item.isEmpty()) {
//					String upload = ossTemplate.upload(item.getOriginalFilename(),item.getInputStream());
//					list.add(upload);
//				}
//			}
//		}
//		map.put("urls", list);
//		log.debug("ossTemplate信息：{},文件上传成功访问路径：{}", ossTemplate, list);
//
//		// 文件上传
//		return AppResponse.ok(map);
//	}


}
