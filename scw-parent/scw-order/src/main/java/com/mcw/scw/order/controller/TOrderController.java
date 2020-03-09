package com.mcw.scw.order.controller;

import com.alibaba.fastjson.JSON;
import com.mcw.scw.order.bean.TOrder;
import com.mcw.scw.order.service.TOrderService;
import com.mcw.scw.order.service.TProjectServiceFeign;
import com.mcw.scw.order.vo.req.OrderInfoSubmitVo;
import com.mcw.scw.order.vo.resp.ProjectDetailVo;
import com.mcw.scw.order.vo.resp.UserProjectVo;
import com.mcw.scw.util.AppDateUtils;
import com.mcw.scw.vo.resp.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mcw 2019\12\20 0020-15:58
 */
@Slf4j
@RestController
public class TOrderController {

    @Autowired
    TOrderService orderService;

    @Autowired
    TProjectServiceFeign projectServiceFeign;

//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
    @Autowired
    RedisTemplate redisTemplate;



    @PostMapping("/order/saveOrder")
    public AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo vo){

        AppResponse<TOrder> resp = null;
        try {
            TOrder order = orderService.saveOrder(vo);

            resp = AppResponse.ok(order);
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<Object> fail = AppResponse.fail(null);
            fail.setMsg("保存订单失败");
            log.error("保存订单失败");
            return resp;

        }

        return resp;

    }

    @GetMapping("/order/listSupportProject")
    public AppResponse<List<UserProjectVo>> listSupportProject(@RequestParam("memberid") Integer memberid){
        try {
            List<TOrder> orders = orderService.listSupportProject(memberid);

            List<UserProjectVo> list=new ArrayList<>();

            for (TOrder order : orders) {
                Integer projectid = order.getProjectid();

                AppResponse<ProjectDetailVo> resp = projectServiceFeign.getdetailsInfo(projectid);
                ProjectDetailVo data = resp.getData();

                log.info("所有支持的项目的信息----------------------{}",data);

                UserProjectVo pro=new UserProjectVo();
                pro.setName(data.getName());
                pro.setMoney(data.getMoney());
                pro.setDay(data.getDay());
                pro.setDeploydate(data.getDeploydate());
                pro.setSupportmoney(data.getSupportmoney());

                pro.setId(order.getProjectid());
                pro.setOrderId(order.getOrdernum());
                pro.setOrderCreateTime(order.getCreatedate());
                pro.setReturnId(order.getReturnid());
                pro.setOrderMoney(order.getMoney());
                pro.setReturnNum(order.getRtncount());
                pro.setOrderStatus(order.getStatus());
                pro.setCompletion(data.getCompletion());
                pro.setDaysRemaining(AppDateUtils.getdays(data.getDeploydate(),data.getDay()));

                list.add(pro);
            }


//            redisTemplate.opsForValue().set("supportProject",list,30,TimeUnit.MINUTES);

            log.info("该用户支持的项目回报为------------{}",list);
            return AppResponse.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<List<UserProjectVo>> fail = AppResponse.fail(null);
            fail.setMsg("查询支持的项目失败");
            log.info("查询支持的项目失败");
            return fail;
        }

    }
}
