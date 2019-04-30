package com.wellhope.svnmanager.entity;

import javax.persistence.*;

/**
 * @author nelsonLee
 * @date 2018/10/23 16:55
 */
@Entity
@Table(name = "refer")
public class ReferInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "userName",nullable = false)
    private String userName;

    @Column(name = "path",nullable = false)
    private String path;

//    private String[] paths;

    @Column(name = "remark")
    private String remark;

    @Column(name = "auditor")
    private String auditor;

    @Column(name = "referTime")
    private Long referTime;

    /**
     * 原本用于提交的过期时间  暂时可不用
     */
    @Column(name = "expireTime")
    private Long expireTime;

    @Column(name = "auditTime")
    private Long auditTime;

    @Column(name = "status")
    private int status;

    public  ReferInfo(){}

    public ReferInfo(String userName, String path, String remark) {
        this.userName = userName;
        this.path = path;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public String[] getPaths() {
//        return paths;
//    }
//
//    public void setPaths(String[] paths) {
//        this.paths = paths;
//    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getReferTime() {
        return referTime;
    }

    public void setReferTime(Long referTime) {
        this.referTime = referTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
}
