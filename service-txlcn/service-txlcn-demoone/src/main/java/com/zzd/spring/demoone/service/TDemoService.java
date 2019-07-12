package com.zzd.spring.demoone.service;


import com.zzd.spring.demoone.entity.Test;

import java.util.List;

/**
 * Created by lorne on 2017/6/26.
 */
public interface TDemoService {

    List<Test> list();

    int save();

}
