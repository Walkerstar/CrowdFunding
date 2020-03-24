package com.mcw.scw.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcw.scw.user.bean.TMember;
import com.mcw.scw.user.bean.TMemberAddress;
import com.mcw.scw.user.bean.TMemberProjectFollow;
import com.mcw.scw.user.service.TMemberService;
import com.mcw.scw.user.service.TOrderServiceFeign;
import com.mcw.scw.user.service.TProjectServiceFeign;
import com.mcw.scw.user.vo.resp.*;
import com.mcw.scw.util.AppDateUtils;
import com.mcw.scw.vo.resp.AppResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mcw 2019\12\19 0019-20:14
 */
@Api(tags = "用户个人信息模块")
@RequestMapping("/user/info")
@RestController
@Slf4j
public class TMbmberController {

    @Autowired
    TMemberService memberService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TOrderServiceFeign orderServiceFeign;

    @Autowired
    TProjectServiceFeign projectServiceFeign;

    @ApiOperation(value="获取用户收货地址")
    @ApiImplicitParams(value={ @ApiImplicitParam(value="访问令牌",name="accessToken",required=true)})
    @GetMapping("/address")
    public AppResponse<List<UserAddressVo>> address(String accessToken){

        String memberId = stringRedisTemplate.opsForValue().get(accessToken);
        if(StringUtils.isEmpty(memberId)) {
            return AppResponse.fail(null);
        }

        List<TMemberAddress> addressList=memberService.listAddress(Integer.parseInt(memberId));

        List<UserAddressVo> addressVoList=new ArrayList<>();

        for (TMemberAddress tMemberAddress : addressList) {
            UserAddressVo vo = new UserAddressVo();
            vo.setId(tMemberAddress.getId());
            vo.setAddress(tMemberAddress.getAddress());
            addressVoList.add(vo);
        }
        return AppResponse.ok(addressVoList);
    }

    @ApiOperation(value="获取我支持的项目")
    @ApiImplicitParams(value={ @ApiImplicitParam(value="访问令牌",name="accessToken",required=true)})
    @GetMapping("/support/project")
    public AppResponse<List<UserProjectVo>> support(@RequestParam("accessToken") String accessToken){


        String memberID = stringRedisTemplate.opsForValue().get(accessToken);

        Integer memberid = Integer.parseInt(memberID);

        AppResponse<List<UserProjectVo>> resp = orderServiceFeign.listSupportProject(memberid);

        List<UserProjectVo> supportProject=resp.getData() ;

        log.debug("supportProject------------------{}",supportProject);
        return AppResponse.ok(supportProject);

    }

    @ApiOperation(value="获取我关注的项目")
    @ApiImplicitParams(value={ @ApiImplicitParam(value="访问令牌",name="accessToken",required=true)})
    @GetMapping("/star/project")
    public AppResponse<List<UserFollowProjectVo>> star(String accessToken){

        try {
            String id = stringRedisTemplate.opsForValue().get(accessToken);
            Integer memberId = Integer.parseInt(id);

            List<TMemberProjectFollow> followList= memberService.listFollowProject(memberId);

            List<UserFollowProjectVo> list=new ArrayList<>();

            for (TMemberProjectFollow follow : followList) {
                Integer projectid = follow.getProjectid();
                UserFollowProjectVo vo = new UserFollowProjectVo();

                AppResponse<ProjectDetailVo> response = projectServiceFeign.getdetailsInfo(projectid);
                ProjectDetailVo data = response.getData();

                vo.setCompletion(data.getCompletion());
                vo.setDay(data.getDay());
                vo.setDeploydate(data.getDeploydate());
                vo.setFollower(data.getFollower());
                vo.setProName(data.getName());
                vo.setSupporter(data.getSupporter());
                vo.setSupportmoney(data.getSupportmoney());
                vo.setMoney(data.getMoney());
                vo.setDaysRemaining(AppDateUtils.getdays(data.getDeploydate(),data.getDay()));
                vo.setId(data.getId());
                list.add(vo);
            }
            return AppResponse.ok(list);
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("用户模块获取关注项目失败");
            return AppResponse.ok(null);
        }
    }

    @ApiOperation(value="往项目关注表里面插入数据")
    @ApiImplicitParams(value={
            @ApiImplicitParam(value = "项目ID", name = "projectId", required = true),
            @ApiImplicitParam(value = "用户ID", name = "memberId", required = true)})
    @GetMapping("/save/projectFollow")
    public AppResponse<Object> saveProjectFollow(Integer projectId,Integer memberId){
        try {
            List<TMemberProjectFollow> proFollowList = memberService.listFollowProject(memberId);

            boolean flag=true;
            for (TMemberProjectFollow follow : proFollowList) {
                if(follow.getProjectid().equals(projectId)){
                    flag=false;
                    break;
                }
            }

            if(flag){
                memberService.saveMemberFollow(projectId,memberId);
                return AppResponse.ok("ok");
            }
            return AppResponse.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<Object> fail = AppResponse.fail(null);
            fail.setMsg("往项目关注表里面插入数据 插入失败");
            log.info("往项目关注表里面插入数据 插入失败");
            return fail;
        }
    }

    @ApiOperation(value="往项目关注表里面删除数据")
    @ApiImplicitParams(value={
            @ApiImplicitParam(value = "项目ID", name = "projectId", required = true),
            @ApiImplicitParam(value = "用户ID", name = "memberId", required = true)})
    @GetMapping("/delete/projectFollow")
    public AppResponse<Object> deleteProjectFollow(Integer projectId,Integer memberId){
        try {
            memberService.deleteMemberFollow(projectId,memberId);
            return AppResponse.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<Object> fail = AppResponse.fail(null);
            fail.setMsg("往项目关注表里面删除数据 删除失败");
            log.info("往项目关注表里面删除数据 删除失败");
            return fail;
        }
    }

    @ApiOperation(value="获取我发起的项目")
    @ApiImplicitParams(value={@ApiImplicitParam(value="访问令牌",name="accessToken",required=true)})
    @GetMapping("/create/project")
    public AppResponse<List<UserProjectVo>> create(String accessToken){

        UserProjectVo vo = new UserProjectVo();
        vo.setId(780);vo.setName("BAVOSN便携折叠移动电源台灯");
        List<UserProjectVo> list = new ArrayList<>();
        list.add(vo);

        return AppResponse.ok(list);
    }

    @ApiOperation(value="查询个人信息")
    @ApiImplicitParams(value={@ApiImplicitParam(value="会员id",name="id",required=true) })
    @GetMapping("/getMemberById")
    public AppResponse<TMember> getMemberById(@RequestParam("id") Integer id){
        TMember member=memberService.getMemberById(id);
        return AppResponse.ok(member);
    }

    @ApiOperation(value="获取个人信息")
    @ApiImplicitParams(value={ @ApiImplicitParam(value="访问令牌",name="accessToken",required=true)})
    @GetMapping("/")
    public AppResponse<UserDetailVo> info(String accessToken){
        UserDetailVo detailVo = new UserDetailVo();

        return AppResponse.ok(detailVo);
    }


    @ApiOperation(value="更新个人信息")
    @PostMapping("/")
    public AppResponse<Object> updateInfo(UserUpdateVo updateVo){
        return AppResponse.ok(null);
    }




    @ApiOperation(value="新增用户收货地址")
    @PostMapping("/address")
    public AppResponse<Object> addAddress(UserAddressAddVo addressAddVo){
        return AppResponse.ok("ok");
    }


    @ApiOperation(value="修改用户收货地址")
    @PutMapping("/address")
    public AppResponse<Object> updateAddress(UserAddressUpdateVo updateVo){
        return AppResponse.ok("ok");
    }


    @ApiOperation(value="删除用户收货地址")
    @DeleteMapping("/address")
    public AppResponse<Object> deleteAddress(UserAddressDeleteVo vo){
        return AppResponse.ok("ok");
    }

    @ApiOperation(value="查看我的订单")
    @GetMapping("/order")
    public AppResponse<Object> order(){
        return AppResponse.ok("ok");
    }
    @ApiOperation(value="删除我的订单")
    @DeleteMapping("/order")
    public AppResponse<Object> deleteOrder(){
        return AppResponse.ok("ok");
    }
    @ApiOperation(value="获取我的系统消息")
    @GetMapping("/message")
    public AppResponse<Object> message(){
        return AppResponse.ok("ok");
    }
}
