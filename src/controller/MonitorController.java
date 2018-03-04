package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MonitorController {
	@RequestMapping(value = "/status")
	public String test() {
		return "status";
	}
}
