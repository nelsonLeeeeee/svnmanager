package com.wellhope.svnmanager.controller;

import com.wellhope.svnmanager.config.ReferConfig;
import com.wellhope.svnmanager.entity.ReferInfo;
import com.wellhope.svnmanager.service.AuditService;
import com.wellhope.svnmanager.service.ReferService;
import com.wellhope.svnmanager.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author nelsonLee
 * @date 2018/10/23 16:23
 */

@Controller
@RequestMapping("audit/api")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Autowired
    private ReferService referService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取待审核记录
     * @return
     */
    @RequestMapping("/getRefers")
    public String getRefers(Model model){
        List<ReferInfo> referInfos = this.referService.findReferInfoByStatus(ReferConfig.PRE_AUDIT);
        model.addAttribute("referInfos",referInfos);
        return "auditList";
    }


    @RequestMapping("/getReferInfoById")
    public String getReferInfoById(Model model,
                                   @RequestParam("referId") Long referId){
        ReferInfo referInfo  = this.referService.getReferInfoById(referId);
        model.addAttribute("referInfo",referInfo);
        return "auditInfo";
    }


    /**
     * 审核
     * @param status 通过：3  未通过：4
     * @param referId
     * @return
     */
    @RequestMapping("/audit")
    @ResponseBody
    public Result audit(@RequestParam("status") int status,
                        @RequestParam("referId") Long referId,
                        @RequestParam("auditor") String auditor){
        if(this.auditService.audit(status,referId,auditor)){
            return new Result(Result.OK);
        }
        return new Result(Result.ERROR);
    }

    /**
     * 脚本调用检测方法
     * @param path
     * @param userName
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public Result check(@RequestParam(value = "path") String path,
                        @RequestParam(value = "userName") String userName){
        if(this.auditService.check(path,userName)){
            return new Result(Result.OK);
        }
        return new Result(Result.ERROR);
    }

}
