package com.mcw.crowdfunding.component;

import com.mcw.crowdfunding.bean.TAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

/**
 * @author mcw 2019\12\9 0009-19:18
 */
public class TSecurityAdmin extends User {

    TAdmin admin;

    public TSecurityAdmin(TAdmin admin, Set<GrantedAuthority> authorities) {
//        super(admin.getLoginacct(), admin.getUserpswd(), authorities);
        super(admin.getLoginacct(), admin.getUserpswd(), true,
                true, true, true, authorities);
        this.admin=admin;
    }
}
