package cn.mzmzup.inteceptor;

import cn.hutool.extra.spring.SpringUtil;
import cn.mzmzup.awt.TokenAwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * //TODO
 * @author tmz
 * @description
 * @date 9:26 2021/4/8
 */
public class ApiLockUnlockInteceptor implements HandlerInterceptor {
	private TokenAwt tokenAwt;
	public ApiLockUnlockInteceptor(){
		this.tokenAwt = SpringUtil.getBean(TokenAwt.class);
	}
	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!checklockreq(request)) {
			response.setHeader("error_req_time_wait","访问过于频繁，等待60秒后再次访问");
			return false;
		}
		return true;
	}
	/**
	 * 计数校验
	 * @author tmz
	 * @description
	 * @date 9:26 2021/4/8
	 * @param request
	 * @return boolean
	 */
	protected boolean checklockreq(HttpServletRequest request){
		return tokenAwt.addReqCount(tokenAwt.getRemortIP(request));
	}
}
