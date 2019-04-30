package com.wellhope.svnmanager.service.impl;

import com.wellhope.svnmanager.util.SvnUtil;
import com.wellhope.svnmanager.service.LoginService;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;

/**
 * @author nelsonLee
 * @date 2018/10/23 16:02
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public boolean nameLogin(String userName, String password) {
        String url = "http://4c75q9qlveqpyd7/svn/wellhope";
        try {
            SvnUtil.isURLExist(SVNURL.parseURIEncoded(url),userName,password);
        } catch (SVNException e) {
            if(e.getMessage().contains("svn: E170001")){
                return false;
            }
        }
        return true;
    }
}
