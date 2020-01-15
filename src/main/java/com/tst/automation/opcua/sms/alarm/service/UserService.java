package com.tst.automation.opcua.sms.alarm.service;

import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.sms.alarm.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    PageResponse<User> getUserPage(Long roleId, String name, String telephone, String description, Integer pageSize, Integer currentPage);

    void updateUser(User user);

    void createUser(User user);

    void deleteUser(Long id);
}
