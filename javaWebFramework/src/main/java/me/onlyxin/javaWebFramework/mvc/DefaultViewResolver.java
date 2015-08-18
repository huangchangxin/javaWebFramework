package me.onlyxin.javaWebFramework.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.onlyxin.javaWebFramework.configuration.Constant;
import me.onlyxin.javaWebFramework.utils.MapUtil;
import me.onlyxin.javaWebFramework.utils.WebUtil;

//视图解析器
public class DefaultViewResolver implements ViewResolver {

	public void resolveView(HttpServletRequest request,
			HttpServletResponse response, Object actionMethodResult) {
		// TODO Auto-generated method stub
		if (actionMethodResult != null) {
			if (actionMethodResult instanceof View) {
				View view = (View) actionMethodResult;
				if (view.isRedirect()) {
					String path = view.getPath();
					WebUtil.redirectRequest(path, request, response);
				} else {
					String path = Constant.JSP_PATH + view.getPath();
					Map<String, Object> data = view.getData();
					if (MapUtil.isNotEmpty(data)) {
						for (Map.Entry<String, Object> entry : data.entrySet()) {
							request.setAttribute(entry.getKey(), entry.getValue());
						}
					}
					WebUtil.forwardRequest(path, request, response);
				}
			} else {
				Result result = (Result) actionMethodResult;
				WebUtil.writeJSON(response, result);
			}
		}
	}

}
