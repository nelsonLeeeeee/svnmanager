package com.wellhope.svnmanager.controller;

import com.wellhope.svnmanager.dao.ReferRepository;
import com.wellhope.svnmanager.entity.ReferInfo;
import com.wellhope.svnmanager.service.ReferService;
import com.wellhope.svnmanager.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 提交SVN审批申请
 * @author nelsonLee
 * @date 2018/10/23 16:26
 */
@Controller
@RequestMapping("refer/api")
public class ReferController {

    @Autowired
    private ReferService referService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ReferRepository referRepository;

    @Value("${manager.name}")
    private String manager;

    @RequestMapping("/toAddReferPage")
    public String toAddReferPage(){
        return "addRefer";
    }

    /**
     * 提交SVN审批申请
     * @param userName
     * @param path
     * @param remark
     * @return
     */
    @RequestMapping("/addRefer")
    @ResponseBody
    public Result addRefer(@RequestParam("userName") String userName,
                           @RequestParam("path") String path,
                           @RequestParam("remark") String remark){

        ReferInfo referInfo = new ReferInfo(userName,path,remark);

        if(this.referService.addRefer(referInfo)){
            return new Result(Result.OK);
        }
        return new Result(Result.ERROR);
    }

    /**
     * 获取当前用户提交的所有申请
     * @param userName
     * @return
     */
    @RequestMapping("/findReferInfo")
    public String findReferInfo(Model model,
                                @RequestParam(required = false,value = "userName") String userName){
        if(userName == null){
            userName = this.getCokieValue("userName");
        }
        List<ReferInfo> referInfos = this.referService.findReferInfoByUser(userName);
        model.addAttribute("referInfos",referInfos);

        //检测是否为管理员
        String[] managers = this.manager.split(",");
        List<String> managerList = new LinkedList<>(Arrays.asList(managers));
        if(managerList.contains(userName)){
            model.addAttribute("isManager","true");
        }
        return "referList";
    }

    /**
     * 查看详细信息
     * @param model
     * @param referId
     * @return
     */
    @RequestMapping("/getReferInfoById")
    public String getReferInfoById(Model model,
                                    @RequestParam("referId") Long referId){
        ReferInfo referInfo  = this.referService.getReferInfoById(referId);
        model.addAttribute("referInfo",referInfo);
        return "referInfo";
    }

    /**
     * 删除申请
     * @param referId
     * @return
     */
    @RequestMapping("/deleteRefer")
    @ResponseBody
    public Result deleteRefer(@RequestParam("referId") Long referId){
        this.referRepository.deleteById(referId);
        return new Result(Result.OK);
    }

    /**
     * 重新提交
     * @param referId
     * @return
     */
    @RequestMapping("reRefer")
    @ResponseBody
    public Result reRefer(@RequestParam("referId") Long referId,
                          @RequestParam(required = false, value = "path") String path,
                          @RequestParam(required = false, value = "remark") String remark){
        if(this.referService.reRefer(referId,path,remark)){
            return new Result(Result.OK);
        }
        return  new Result(Result.ERROR);
    }

    /**
     * 获取已审批列表
     * @param model
     * @param userName
     * @return
     */
    @RequestMapping("/findFinishReferInfo")
    public String findFinishReferInfo(Model model,
                                      @RequestParam(required = false,value = "userName") String userName){
        if(userName == null){
            userName = this.getCokieValue("userName");
        }
        List<ReferInfo> referInfos = this.referService.getAuditFinishByUserName(userName);
        model.addAttribute("referInfos",referInfos);
        return "finishReferList";
    }


    /**
     * 获取已完成记录  此方法可与其它查看方法合并（待重构）
     * @param model
     * @param referId
     * @return
     */
    @RequestMapping("/getFinishReferInfo")
    public String getFinishReferInfo(Model model,
                                   @RequestParam("referId") Long referId){
        ReferInfo referInfo  = this.referService.getReferInfoById(referId);
        model.addAttribute("referInfo",referInfo);
        return "finishReferInfo";
    }



    private String getCokieValue(String key){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(key.equalsIgnoreCase(cookie.getName())){
                return  cookie.getValue();
            }
        }
        return "";
    }

}
