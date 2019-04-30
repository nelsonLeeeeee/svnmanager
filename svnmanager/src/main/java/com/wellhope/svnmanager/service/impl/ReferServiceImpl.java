package com.wellhope.svnmanager.service.impl;

import com.wellhope.svnmanager.config.ReferConfig;
import com.wellhope.svnmanager.dao.ReferRepository;
import com.wellhope.svnmanager.entity.ReferInfo;
import com.wellhope.svnmanager.service.ReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author nelsonLee
 * @date 2018/10/23 16:27
 */
@Service
@Transactional
public class ReferServiceImpl implements ReferService {

    @Autowired
    private ReferRepository referRepository;

    @Override
    public boolean addRefer(ReferInfo referInfo) {

        Long currTime = System.currentTimeMillis();
        referInfo.setReferTime(currTime);
        referInfo.setExpireTime(currTime+ReferConfig.EXPIRE_TIME);
        referInfo.setStatus(ReferConfig.PRE_AUDIT);
        if(this.referRepository.save(referInfo)!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<ReferInfo> findReferInfoByUser(String userName) {
        return this.referRepository.getReferInfos(userName);
    }

    @Override
    public List<ReferInfo> findReferInfoByStatus(int status) {
        return this.referRepository.findByStatusOrderByReferTimeDesc(status);
    }

    @Override
    public boolean reRefer(Long referId, String path, String remark) {
        ReferInfo referInfo = this.referRepository.getOne(referId);
        if(path!=null){
            referInfo.setPath(path);
        }
        if(remark!=null){
            referInfo.setRemark(remark);
        }
        Long currTime = System.currentTimeMillis();
        referInfo.setReferTime(currTime);

        if(this.referRepository.saveAndFlush(referInfo) != null){
            return true;
        }
        return false;
    }

    @Override
    public ReferInfo getReferInfoById(Long referId) {
        return this.referRepository.getOne(referId);
    }

    @Override
    public List<ReferInfo> getAuditFinishByUserName(String userName) {
        return this.referRepository.getFinishRefer(userName);
    }
}
