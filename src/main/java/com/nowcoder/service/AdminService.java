package com.nowcoder.service;

import com.nowcoder.dao.AdminDAO;
import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.model.Admin;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        Admin admin = adminDAO.selectByName(username);

        if (admin != null) {
            map.put("msgname", "用户名已经被注册");
            return map;
        }

        // 密码强度
        admin = new Admin();
        admin.setUsername(username);
        admin.setSalt(UUID.randomUUID().toString().substring(0, 5));
       // String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        //admin.setHeadUrl(head);
        admin.setPassword(ToutiaoUtil.MD5(password + admin.getSalt()));
        adminDAO.addAdmin(admin);

        // 登陆
        String ticket = addLoginTicket(admin.getId());
        map.put("ticket", ticket);
        return map;
    }

    public Map<String, Object> login(String username, String password) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isBlank(username)) {
                map.put("msgname", "用户名不能为空");
                return map;
            }

            if (StringUtils.isBlank(password)) {
                map.put("msgpwd", "密码不能为空");
                return map;
            }

            Admin admin = adminDAO.selectByName(username);

            if (admin == null) {
                map.put("msgname", "用户名不存在");
                return map;
            }

            if (!ToutiaoUtil.MD5(password+admin.getSalt()).equals(admin.getPassword())) {
                map.put("msgpwd", "密码不正确");
                return map;
            }

            String ticket = addLoginTicket(admin.getId());
            map.put("ticket", ticket);
            return map;
        }

        private String addLoginTicket(int userId) {
            LoginTicket ticket = new LoginTicket();
            ticket.setUserId(userId);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*24);
            ticket.setExpired(date);
            ticket.setStatus(0);
            ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
            loginTicketDAO.addTicket(ticket);
            return ticket.getTicket();
        }

        public Admin getAdmin(int id) {
            return adminDAO.selectById(id);
        }

        public void logout(String ticket) {
            loginTicketDAO.updateStatus(ticket, 1);
        }
}
