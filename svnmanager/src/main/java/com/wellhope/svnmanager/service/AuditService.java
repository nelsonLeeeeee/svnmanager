package com.wellhope.svnmanager.service;

/**
 * @author nelsonLee
 * @date 2018/10/23 16:24
 */
public interface AuditService {

    boolean audit(int status, Long referId, String auditor);

    boolean check(String path, String userName);
}
