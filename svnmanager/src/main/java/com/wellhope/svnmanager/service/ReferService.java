package com.wellhope.svnmanager.service;

import com.wellhope.svnmanager.entity.ReferInfo;

import java.util.List;

/**
 * @author nelsonLee
 * @date 2018/10/23 16:27
 */
public interface ReferService {
    boolean addRefer(ReferInfo referInfo);

    List<ReferInfo> findReferInfoByUser(String userName);

    List<ReferInfo> findReferInfoByStatus(int status);

    boolean reRefer(Long referId, String path, String remark);

    ReferInfo getReferInfoById(Long referId);

    List<ReferInfo> getAuditFinishByUserName(String userName);
}
