package com.heitian.ssm.controller;

import com.heitian.ssm.excel.ExcelParseUtils;
import com.heitian.ssm.model.User;
import com.heitian.ssm.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger log = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public HttpServletResponse response;

    /*@RequestMapping("/showUser")
    public String showUser(HttpServletRequest request, Model model){
        log.info("查询所有用户信息");
        List<User> userList = userService.getAllUser();
        model.addAttribute("userList",userList);
        return "showUser";
    }*/

    @RequestMapping("")
    public String showUser(Model model) {
        return "copshowUser";
    }

    /**
     * 列表查询
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public List<User> showUserList(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        log.info("查询所有用户信息");
        log.info(userName);
        List<User> userList = userService.getAllUser(userName);
        //result.setContent(userList);
        return userList;
    }

    /*@ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8, text/plain;charset=UTF-8")
    public String save(@RequestBody String body) {
        User user = JsonUtils.toObject(body,User.class);
        userService.saveUser(user.getUserName(),user.getUserPhone(),user.getUserEmail());
        return null;
    }*/

    /**
     * 保存用户
     *
     * @param request request
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request) {
        String name = request.getParameter("updateUserName");
        String userPhone = request.getParameter("userPhone");
        String email = request.getParameter("userEmail");
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            userService.saveUser(name, userPhone, email);
        } else {
            userService.updateUser(name, userPhone, email, Long.valueOf(id));
        }
    }

    /**
     * 删除用户
     *
     * @param request
     * @param id
     */
    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public Map<String, Object> deleteUser(HttpServletRequest request, @PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();
        userService.delete(id);
        map.put("errorCode", "0");
        map.put("msg", "成功！");
        return map;
    }

    /**
     * 导入excel处理数据
     *
     * @param file 文件
     * @return Json
     */
    @ResponseBody
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Map<String, String>> list = ExcelParseUtils.parseCommonExcel(file.getInputStream());
            if (list != null && !list.isEmpty()) {
                //去掉标题一行
                list.remove(0);
                List<User> reqList = new ArrayList<>();
                //获取excel每行数据
                list.forEach(item -> {
                    User req = new User();
                    String userName = item.get("0");
                    String userPhone = item.get("1");
                    String userEmail = item.get("2");
                    userService.saveUser(userName, userPhone, userEmail);
                });
                map.put("errorCode", "0");
                map.put("msg", "导入成功");
            }
        } catch (Exception e) {
            map.put("errorCode", "-1");
            map.put("msg", "导入失败!");
        }

        return map;
    }
}
