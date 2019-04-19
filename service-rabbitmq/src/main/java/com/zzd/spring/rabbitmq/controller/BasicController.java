package com.zzd.spring.rabbitmq.controller;

import com.zzd.spring.rabbitmq.service.MsgSender;
import com.zzd.spring.rabbitmq.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class BasicController {
	@Autowired
	private MsgSender msgSender;

	@RequestMapping(value = "/basicSend")
	public Object basicSend(@RequestParam(value = "msg", required = false) String msg) {
		if (StringUtils.isEmpty(msg)) {
			msg = Constant.DEFAULT_MSG;
		}
		msgSender.sendMsg(msg);
		return "ok!";
	}

	@RequestMapping(value = "/basicSendOneToMany")
	public Object basicSendOneToMany(@RequestParam(value = "msg", required = false) String msg) {
		if (StringUtils.isEmpty(msg)) {
			msg = Constant.DEFAULT_MSG;
		}
		msgSender.sendMsgOneToMany(msg);
		return "ok!";
	}

	@RequestMapping(value = "/basicSendTopic")
	public Object basicSendTopic(@RequestParam(value = "msg", required = false) String msg) {
		if (StringUtils.isEmpty(msg)) {
			msg = Constant.DEFAULT_MSG;
		}
		msgSender.sendTopic(msg);
		return "ok!";
	}

	@RequestMapping(value = "/basicSendFanout")
	public Object basicSendFanout(@RequestParam(value = "msg", required = false) String msg) {
		if (StringUtils.isEmpty(msg)) {
			msg = Constant.DEFAULT_MSG;
		}
		msgSender.sendFanoutMany(msg);
		return "ok!";
	}
}
