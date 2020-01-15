package com.tst.automation.opcua.sms.alarm.controller;

import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.sms.alarm.pojo.User;
import com.tst.automation.opcua.sms.alarm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sms/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("page")
    public ResponseEntity<PageResponse<User>> getUserPage(@RequestParam(name = "roleId", required = false) Long roleId,
                                                          @RequestParam(name = "name", required = false) String name,
                                                          @RequestParam(name = "telephone", required = false) String telephone,
                                                          @RequestParam(name = "description", required = false) String description,
                                                          @RequestParam(name = "pageSize", required = true) Integer pageSize,
                                                          @RequestParam(name = "currentPage", required = true) Integer currentPage) {
        PageResponse<User> userPageResponse = userService.getUserPage(roleId, name, telephone, description, pageSize, currentPage);
        return ResponseEntity.ok(userPageResponse);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok().build();
    }
}
