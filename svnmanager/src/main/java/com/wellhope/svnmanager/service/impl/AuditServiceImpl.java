package com.wellhope.svnmanager.service.impl;

import com.wellhope.svnmanager.dao.ReferRepository;
import com.wellhope.svnmanager.entity.ReferInfo;
import com.wellhope.svnmanager.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author nelsonLee
 * @date 2018/10/23 16:24
 */
@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    @Autowired
    private ReferRepository referRepository;

    @Override
    public boolean audit(int status, Long referId, String auditor) {
        int result = this.referRepository.audit(status,referId,auditor,System.currentTimeMillis());
        if(result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean check(String path, String userName) {
        //先查出该用户未过期并且通过的提交
        long currTime = System.currentTimeMillis();
        List<ReferInfo> referInfos = this.referRepository.findByUserNameAndStatusAndExpireTimeLessThan(userName,3,currTime);
        if(referInfos == null || referInfos.size() == 0){
            return false;
        }
        //提交的路径
        String[] paths = path.split("/n");
        boolean result;
        for (ReferInfo referInfo : referInfos) {
            result = true;
            String tempPath = referInfo.getPath();
            String[] tempPaths = tempPath.split("/n");
            List<String> tempPathList = new LinkedList<>(Arrays.asList(tempPaths));
            for (String checkPath : paths) {
                //检测提交的路径 如果没有，退出当前
                if(!tempPathList.contains(checkPath)){
                    result = false;
                    break;
                }
            }
            if(result){
                return true;
            }
        }
        return false;
    }
}
