package com.mcw.scw.webui.service;

import com.mcw.scw.vo.resp.AppResponse;
import com.mcw.scw.webui.service.exp.handler.TProjectServiceFeignExceptionHandler;
import com.mcw.scw.webui.vo.req.*;
import com.mcw.scw.webui.vo.resp.ProjectDetailVo;
import com.mcw.scw.webui.vo.resp.ProjectVo;
import com.mcw.scw.webui.vo.resp.ReturnPayConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author mcw 2019\12\19 0019-14:03
 */
@FeignClient(value = "SCW-PROJECT", fallback = TProjectServiceFeignExceptionHandler.class)
public interface TProjectServiceFeign {

    @GetMapping("/project/all")
    AppResponse<List<ProjectVo>> all();

    @GetMapping("/project/all/host")
    AppResponse<List<ProjectVo>> allHost();

    @GetMapping("/project/details/info/{projectId}")
    AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId);

    @GetMapping("/project/confim/returns/{projectId}/{returnId}")
    AppResponse<ReturnPayConfirmVo> returnInfo(@PathVariable("projectId") Integer projectId,
                                               @PathVariable("returnId") Integer returnId);


    @PostMapping("/project/create/init")
    AppResponse<ProjectRedisStroageVo> init(@RequestBody BaseVo vo);

    @PostMapping("/project/create/baseinfo")
    AppResponse<Object> baseinfo(@RequestBody ProjectBaseInfoVo vo);

    @PostMapping("/project/create/return")
    AppResponse<Object> returnDetail(@RequestBody List<ProjectReturnVo> pro);

    @PostMapping("/project/create/confirm/legal")
    AppResponse<Object> legal(@RequestBody ProjectLegalVo vo);

    @PostMapping("/project/create/submit")
    AppResponse<Object> submit(@RequestParam("accessToken") String accessToken,
                               @RequestParam("projectToken")String projectToken,
                               @RequestParam("ops")String ops);

    @RequestMapping("/project/create/upload")
    AppResponse<Object> upload(HttpServletRequest request);


    @PostMapping("/project/operation/addstar")
    AppResponse addstar(@RequestParam("projectId") Integer projectId,@RequestParam("memberId")Integer memberId);

    @PostMapping("/project/operation/unstar")
    AppResponse<Object> unstar(@RequestParam("projectId") Integer projectId,@RequestParam("memberId")Integer memberId);
}
