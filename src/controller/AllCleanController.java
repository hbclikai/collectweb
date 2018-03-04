package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import io.thread.Server;

@Controller
@RequestMapping(value = "/all_clean")
public class AllCleanController {
	//private static final Log logger = LogFactory.getLog(AllCleanController.class);

	@RequestMapping(value = "/list.do", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> list(@RequestBody Object addDevice_NotifyMessage) throws Exception {
		if (addDevice_NotifyMessage != null) {
			// 直接加入到内存消息队列中,不做任何业务处理
			Server.addMessage((JSONObject) addDevice_NotifyMessage);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
}
