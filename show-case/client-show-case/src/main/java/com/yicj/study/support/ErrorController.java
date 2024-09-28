//package com.yicj.study.support;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class ErrorController {
//
//	@RequestMapping(path = "/error")
//	public Map<String, Object> handle(HttpServletRequest request) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("status", request.getAttribute("jakarta.servlet.error.status_code"));
//		map.put("reason", request.getAttribute("jakarta.servlet.error.message"));
//		return map;
//	}
//}