package com.mcw.crowdfunding.component;

import com.mcw.crowdfunding.bean.TAdmin;
import com.mcw.crowdfunding.bean.TAdminExample;
import com.mcw.crowdfunding.bean.TPermission;
import com.mcw.crowdfunding.bean.TRole;
import com.mcw.crowdfunding.mapper.TAdminMapper;
import com.mcw.crowdfunding.mapper.TPermissionMapper;
import com.mcw.crowdfunding.mapper.TRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mcw 2019\12\9 0009-15:48
 */

@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService {

    Logger log=LoggerFactory.getLogger(SecurityUserDetailServiceImpl.class);

    @Autowired
    TAdminMapper adminMapper;

    @Autowired
    TRoleMapper roleMapper;

    @Autowired
    TPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1.查询用户对象
        TAdminExample example = new TAdminExample();
        example.createCriteria().andLoginacctEqualTo(username);
        List<TAdmin> list = adminMapper.selectByExample(example);

        if (list != null && list.size() == 1) {
            TAdmin admin = list.get(0);
            Integer adminId = admin.getId();

            log.debug("用户信息={}",admin);
            //2.查询角色集合
            List<TRole> roleList = roleMapper.listRoleByAdminId(adminId);

            //3.查询权限集合
            List<TPermission> permisionList = permissionMapper.listPermissionByAdminId(adminId);

            //4.构建用户所有权限集合=>(ROLE_角色+权限)
            Set<GrantedAuthority> authorities=new HashSet<>();

            for (TRole role : roleList) {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            }

            for (TPermission p : permisionList) {
                authorities.add(new SimpleGrantedAuthority(p.getName()));
            }

            log.debug("用户总权限集合={}",authorities);
//            return new User(admin.getLoginacct(), admin.getUserpswd(), authorities);
            return new TSecurityAdmin(admin,authorities);
        } else {
            return null;
        }

    }
}
