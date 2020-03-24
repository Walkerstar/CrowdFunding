package com.mcw.scw.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.mcw.scw.project.bean.*;
import com.mcw.scw.project.constants.ProjectConstant;
import com.mcw.scw.project.mapper.*;
import com.mcw.scw.project.service.TProjectService;
import com.mcw.scw.project.vo.req.ProjectRedisStroageVo;
import com.mcw.scw.util.AppDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mcw 2019\12\16 0016-20:22
 */
@Slf4j
@Service
public class TProjectServiceImpl implements TProjectService {

    @Autowired
    TProjectMapper projectMapper;
    
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TProjectImagesMapper projectImagesMapper;

    @Autowired
    TReturnMapper returnMapper;

    @Autowired
    TProjectTypeMapper projectTypeMapper;

    @Autowired
    TProjectTagMapper projectTagMapper;

    @Autowired
    TProjectInitiatorMapper projectInitiatorMapper;

    @Autowired
    TAccountMapper accountMapper;

    @Transactional
    @Override
    public void saveProject(String accessToken, String projectToken, byte code) {

        String memberid = stringRedisTemplate.opsForValue().get(accessToken);

        //1.从redis中获取bigVo数据
        String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
        ProjectRedisStroageVo bigVo = JSON.parseObject(bigStr, ProjectRedisStroageVo.class);

        //2.保存项目
        TProject project=new TProject();
        project.setName(bigVo.getName());
        project.setRemark(bigVo.getRemark());
        project.setMoney(bigVo.getMoney());
        project.setDay(bigVo.getDay());
        project.setStatus(code+"");
        project.setMemberid(Integer.parseInt(memberid));
        project.setCreatedate(AppDateUtils.getFormatTime());

        projectMapper.insertSelective(project);

        Integer projectId = project.getId();
        log.debug("保存项目的id--{}",projectId);

        //3.保存图片

        //保存头图
        TProjectImages projectImage=new TProjectImages();
        projectImage.setProjectid(projectId);
        projectImage.setImgurl(bigVo.getHeaderImage());
        projectImage.setImgtype((byte)0);
        projectImagesMapper.insertSelective(projectImage);

        //保存详情图
        List<String> detailsImage = bigVo.getDetailsImage();
        for (String imgpath : detailsImage) {
            TProjectImages pi=new TProjectImages();
            pi.setProjectid(projectId);
            pi.setImgurl(imgpath);
            pi.setImgtype((byte)1);
            projectImagesMapper.insertSelective(pi);
        }


        //4.保存回报

        List<TReturn> projectReturns = bigVo.getProjectReturns();
        for (TReturn retObj : projectReturns) {
            retObj.setProjectid(projectId);
            returnMapper.insertSelective(retObj);
        }
        //5.保存项目和分类关系
        List<Integer> typeids = bigVo.getTypeids();
        for (Integer typeid : typeids) {
            TProjectType pt=new TProjectType();
            pt.setProjectid(projectId);
            pt.setTypeid(typeid);
            projectTypeMapper.insertSelective(pt);
        }
        //6.保存项目和标签关系
        List<Integer> tagids = bigVo.getTagids();
        for (Integer tagid : tagids) {
            TProjectTag pt=new TProjectTag();
            pt.setProjectid(projectId);
            pt.setTagid(tagid);
            projectTagMapper.insertSelective(pt);
        }
        //7.保存发起人（省略）
        TProjectInitiator projectInitiator=new TProjectInitiator();
        projectInitiator.setSelfintroduction(bigVo.getSelfintroduction());
        projectInitiator.setDetailselfintroduction(bigVo.getDetailselfintroduction());
        projectInitiator.setTelphone(bigVo.getTelphone());
        projectInitiator.setHotline(bigVo.getHotline());
        projectInitiator.setProjectid(projectId);
        projectInitiatorMapper.insertSelective(projectInitiator);

        log.error("发起人信息:------{}",projectInitiator);
        //8.保存法人（省略）

        TAccount account=new TAccount();
        account.setEntaccount(bigVo.getEntaccount());
        account.setIdnumber(bigVo.getIdnumber());
        account.setMemberid(bigVo.getMemberid());
        accountMapper.insertSelective(account);

        log.error("账户信息:------{}",account);

        //9.清理redis
        stringRedisTemplate.delete(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
    }
}
