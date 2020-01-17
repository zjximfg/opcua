package com.tst.automation.opcua.sms.alarm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.sms.alarm.mapper.UserMapper;
import com.tst.automation.opcua.sms.alarm.pojo.User;
import com.tst.automation.opcua.sms.alarm.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResponse<User> getUserPage(Long roleId, String name, String telephone, String description, Integer pageSize, Integer currentPage) {
        PageHelper.startPage(currentPage, pageSize);

        Condition condition = new Condition(User.class);
        condition.createCriteria().andEqualTo("isDeleted", false);

        if (ObjectUtils.allNotNull(roleId) && !roleId.equals(0L)) {
            condition.createCriteria().andEqualTo("roleId", roleId);
        }
        if (StringUtils.isNotBlank(name)) {
            condition.and().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(telephone)) {
            condition.and().andLike("telephone", "%" + telephone + "%");
        }
        if (StringUtils.isNotBlank(description)) {
            condition.and().andLike("description", "%" + description + "%");
        }
        Page<User> page = (Page<User>)userMapper.selectByExample(condition);
        return new PageResponse<>(page.getTotal(), page.getResult());
    }

    @Override
    public void updateUser(User user) {
        user.setIsDeleted(false);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void createUser(User user) {
        user.setIsDeleted(false);
        userMapper.insertSelective(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setIsDeleted(true);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
