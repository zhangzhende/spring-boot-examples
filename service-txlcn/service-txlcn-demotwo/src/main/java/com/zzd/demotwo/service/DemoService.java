package com.zzd.demotwo.service;


import com.zzd.demotwo.entity.Test;

import java.util.List;

/**
 * Created by lorne on 2017/6/26.
 */
public interface DemoService  {

    List<Test> list();

    int save();

}
