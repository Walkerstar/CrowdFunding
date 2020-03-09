package com.mcw.scw.webui.service.exp.handler;

import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.service.TProjectServiceFeign;
import com.mcw.scw.webui.vo.req.*;
import com.mcw.scw.webui.vo.resp.ProjectDetailVo;
import com.mcw.scw.webui.vo.resp.ProjectVo;
import com.mcw.scw.webui.vo.resp.ReturnPayConfirmVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author mcw 2019\12\19 0019-14:05
 */
@Slf4j
@Component
public class TProjectServiceFeignExceptionHandler implements TProjectServiceFeign {
    @Override
    public AppResponse<List<ProjectVo>> all() {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【查询所有项目】失败");

        log.error("调用远程服务【查询所有项目】失败");
        return resp;
    }

    @Override
    public AppResponse<List<ProjectVo>> allHost() {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【查询首页热点项目】失败");

        log.error("调用远程服务【查询首页热点项目】失败");
        return resp;
    }

    @Override
    public AppResponse<ProjectDetailVo> detailsInfo(Integer projectId) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【查询项目详情】失败");

        log.error("调用远程服务【查询项目详情】失败");
        return resp;
    }

    @Override
    public AppResponse<ReturnPayConfirmVo> returnInfo(Integer projectId, Integer returnId) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【查询项目回报】失败");

        log.error("调用远程服务【查询项目回报】失败");
        return resp;
    }

    @Override
    public AppResponse<ProjectRedisStroageVo> init(BaseVo vo) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【项目初始创建】失败");

        log.error("调用远程服务【项目初始创建】失败");
        return resp;
    }

    @Override
    public AppResponse<Object> baseinfo(ProjectBaseInfoVo vo) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【项目基本信息】失败");

        log.error("调用远程服务【项目基本信息】失败");
        return resp;
    }

    @Override
    public AppResponse<Object> upload(HttpServletRequest request) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【上传图片】失败");

        log.error("调用远程服务【上传图片】失败");
        return resp;
    }

    @Override
    public AppResponse addstar(Integer projectId, Integer memberId) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【添加关注】失败");

        log.error("调用远程服务【添加关注】失败");
        return resp;
    }

    @Override
    public AppResponse<Object> unstar(Integer projectId, Integer memberId) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【取消关注】失败");

        log.error("调用远程服务【取消关注】失败");
        return resp;
    }

    @Override
    public AppResponse<Object> returnDetail(List<ProjectReturnVo> pro) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【添加回报】失败");

        log.error("调用远程服务【添加回报】失败");
        return resp;
    }

    @Override
    public AppResponse<Object> legal(ProjectLegalVo vo) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【确认法人信息】失败");

        log.error("调用远程服务【确认法人信息】失败");
        return resp;
    }

    @Override
    public AppResponse<Object> submit(String accessToken, String projectToken, String ops) {
        AppResponse resp=AppResponse.fail(null);
        resp.setMsg("调用远程服务【保存项目】失败");

        log.error("调用远程服务【保存项目】失败");
        return resp;
    }
}
