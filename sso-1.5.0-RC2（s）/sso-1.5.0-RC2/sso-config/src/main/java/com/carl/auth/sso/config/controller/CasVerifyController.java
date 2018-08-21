package com.carl.auth.sso.config.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CasVerifyController extends AbstractController {


    @Override
    @GetMapping("/cc")
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        System.out.println("===================================");
        ModelAndView mav = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();

        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("status", Boolean.TRUE);
        attributes.put("reason", "操作成功");
       // Demo1.a();
        view.setAttributesMap(attributes);
        mav.setView(view);
        return mav;
    }



    @PostMapping("/password/change")
    public Boolean changePassword(String id,String password){

        Boolean result=  Demo1.changPassword(id,password);
        return result;

    }

    @GetMapping("/regist")
    public ReplyInfo regist(HttpServletRequest request){
       /* String userName=request.getParameter("userName");
        String password=request.getParameter("passWord");
        String email=request.getParameter("email");
        password = MD5Helper.getMD5String(request.getParameter("passWord"));
        System.out.println(password);
        //Demo1.savePassWord(userName,password,email);
        try {
            String callUrl=request.getParameter("passUrl");
            String strs=callUrl.substring(callUrl.indexOf("service=")+1).substring(7);
            System.out.println(strs);
            response.sendRedirect("/config/b.html?service="+strs);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ReplyInfo replyInfo=new ReplyInfo();
        Map<String, Object> result = new HashMap<String, Object>();
        String userName=request.getParameter("username");
        String password=request.getParameter("passWord");
        String email=request.getParameter("email");
        String callUrl=request.getParameter("passUrl");
        String strs=callUrl.substring(callUrl.indexOf("service=")+1).substring(7);
        result.put("backUrl",strs);
        Boolean checkUserName=Demo1.checkUserName(userName);
        if(checkUserName==false){
            replyInfo.setSuccess(false);
            result.put("messege","该用户名已被注册,请重新填写！");
            replyInfo.setData(result);
            return replyInfo;
        }

        Boolean checkEmail=Demo1.checkEmali(email);
        if(checkEmail==false){
            replyInfo.setSuccess(false);
            result.put("messege","该邮箱已被注册,请重新填写！");
            replyInfo.setData(result);
            return replyInfo;
        }
        password = MD5Helper.getMD5String(request.getParameter("passWord"));
        try {
            Demo1.savePassWord(userName,password,email);
            result.put("messege","注册成功！请登陆");
            replyInfo.setSuccess(true);
            replyInfo.setData(result);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("messege","注册失败，请重试！");
            replyInfo.setData(result);
            replyInfo.setSuccess(true);
        }

        return  replyInfo;

    }



    @GetMapping("/changePassWord")
    public ReplyInfo changePassWord(HttpServletRequest request){
        ReplyInfo replyInfo=new ReplyInfo();
        Map<String, Object> result = new HashMap<String, Object>();
        String newpassword=request.getParameter("newpassword");
        String passUrl=request.getParameter("passUrl");
        String strs=passUrl.substring(passUrl.indexOf("service=")+1).substring(7);
        String uuid=strs.substring(0,strs.indexOf("&"));
        String backUrlStrs=passUrl.substring(passUrl.indexOf("&")+1).substring(8);
        String backurl=backUrlStrs.substring(0,strs.indexOf("&"));
        result.put("backUrl",backurl);
        String password = MD5Helper.getMD5String(request.getParameter("newpassword"));
        Boolean b=Demo1.changPassword(uuid,password);
        if(b==false){
            replyInfo.setSuccess(false);
            result.put("messege","修改密码失败，请重新填写");
            replyInfo.setData(result);
            return replyInfo;
        }

        replyInfo.setSuccess(true);
        result.put("messege","修改密码成功，请重新登录");
        replyInfo.setData(result);
        return replyInfo;


    }
}