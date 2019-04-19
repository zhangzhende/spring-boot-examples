package com.zzd.spring.mongodb.controller;

import com.mongodb.client.result.DeleteResult;
import com.zzd.spring.common.entity.User;
import com.zzd.spring.mongodb.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2019/4/1 0001.
 */
@RestController
@RequestMapping(value = "/mongo")
public class Controller {

    @Autowired
    private MongoService service;

    /**
     * 查询返回库1全部
     *
     * @return
     */
    @RequestMapping(value = "/primary/all")
    public List<Object> getPrimaryAll() {
        List<Object> list = service.findPrimaryAll();
        System.out.println("test");
        return list;
    }

    /**
     * 存库1
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/primary/save")
    public String save4Primary(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = new User(username, password);
        user.setId(UUID.randomUUID().toString());
        service.save4Primary(user);
        return "ok";
    }

    /**
     * 更新库1符合条件的第一个文档
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/primary/updatefirst", method = RequestMethod.POST)
    public Object update4PrimaryFirst(@RequestBody User user) {
        return service.updatePrimaryOne(user);
    }

    /**
     * 更新库1的所有文档
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/primary/updateall", method = RequestMethod.POST)
    public Object update4PrimaryAll(@RequestBody User user) {
        return service.updatePrimaryAll(user);
    }

    /**
     * 删除文档
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/primary/delete")
    public Object delete4Primary(@RequestBody User user) {
        DeleteResult result = service.deletePrimary(user);
        return result;
    }

    /**
     * 查询返回库2全部
     *
     * @return
     */
    @RequestMapping(value = "/secondary/all")
    public List<Object> getSecondaryAll() {
        List<Object> list = service.findSecondaryAll();
        return list;
    }

    /**
     * 更新库2中所有文档
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/secondary/updateall")
    public Object update4SecondaryAll(@RequestBody User user) {
        return service.updateSecondaryAll(user);

    }

    /**
     * 存库2
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/secondary/save")
    public String save4Secondary(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = new User(username, password);
        service.save4Secondary(user);
        return "ok";
    }


}
