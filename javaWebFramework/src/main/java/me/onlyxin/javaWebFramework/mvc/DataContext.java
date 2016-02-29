package me.onlyxin.javaWebFramework.mvc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.onlyxin.javaWebFramework.utils.ArrayUtil;
import me.onlyxin.javaWebFramework.utils.CodeUtil;

//封装与每个请求相关的数据,使controller与servlet api 解耦
public class DataContext {
	//保证线程安全
	private static final ThreadLocal<DataContext> dataContextContainer = new ThreadLocal<DataContext>();

	private HttpServletRequest request;
	private HttpServletResponse response;

	public static void init(HttpServletRequest request,
			HttpServletResponse response) {
		DataContext dataContext = new DataContext();
		dataContext.request = request;
		dataContext.response = response;
		dataContextContainer.set(dataContext);
	}

	public static void destroy() {
		dataContextContainer.remove();
	}

	public static DataContext getInstance() {
		return dataContextContainer.get();
	}

	public static HttpServletRequest getRequest() {
		return getInstance().request;
	}

	public static HttpServletResponse getResponse() {
		return getInstance().response;
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static javax.servlet.ServletContext getServletContext() {
		return getRequest().getServletContext();
	}

	// 封装Request
	public static class Request {

		public static void put(String key, Object value) {
			getRequest().setAttribute(key, value);
		}

		@SuppressWarnings("unchecked")
		public static <T> T get(String key) {
			return (T) getRequest().getAttribute(key);
		}

		public static void remove(String key) {
			getRequest().removeAttribute(key);
		}

		public static Map<String, Object> getAll() {
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration<String> names = getRequest().getAttributeNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				map.put(name, getRequest().getAttribute(name));
			}
			return map;
		}

		// 封装Response
		public static class Response {

			public static void put(String key, Object value) {
				getResponse().setHeader(key, String.valueOf(value));
			}

			@SuppressWarnings("unchecked")
			public static <T> T get(String key) {
				return (T) getResponse().getHeader(key);
			}

			public static Map<String, Object> getAll() {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String name : getResponse().getHeaderNames()) {
					map.put(name, getResponse().getHeader(name));
				}
				return map;
			}

			// 封装Session
			public static class Session {

				public static void put(String key, Object value) {
					getSession().setAttribute(key, value);
				}

				@SuppressWarnings("unchecked")
				public static <T> T get(String key) {
					return (T) getSession().getAttribute(key);
				}

				public static void remove(String key) {
					getSession().removeAttribute(key);
				}

				public static Map<String, Object> getAll() {
					Map<String, Object> map = new HashMap<String, Object>();
					Enumeration<String> names = getSession()
							.getAttributeNames();
					while (names.hasMoreElements()) {
						String name = names.nextElement();
						map.put(name, getSession().getAttribute(name));
					}
					return map;
				}

				public static void removeAll() {
					getSession().invalidate();
				}
			}

			// 封装Cookie
			public static class Cookie {

				public static void put(String key, Object value) {
					String strValue = CodeUtil.encodeURL(String.valueOf(value));
					javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(
							key, strValue);
					getResponse().addCookie(cookie);
				}

				@SuppressWarnings("unchecked")
				public static <T> T get(String key) {
					T value = null;
					javax.servlet.http.Cookie[] cookieArray = getRequest()
							.getCookies();
					if (ArrayUtil.isNotEmpty(cookieArray)) {
						for (javax.servlet.http.Cookie cookie : cookieArray) {
							if (key.equals(cookie.getName())) {
								value = (T) CodeUtil.decodeURL(cookie
										.getValue());
								break;
							}
						}
					}
					return value;
				}

				public static Map<String, Object> getAll() {
					Map<String, Object> map = new HashMap<String, Object>();
					javax.servlet.http.Cookie[] cookieArray = getRequest()
							.getCookies();
					if (ArrayUtil.isNotEmpty(cookieArray)) {
						for (javax.servlet.http.Cookie cookie : cookieArray) {
							map.put(cookie.getName(), cookie.getValue());
						}
					}
					return map;
				}
			}

			// 封装ServletContext
			public static class ServletContext {

				public static void put(String key, Object value) {
					getServletContext().setAttribute(key, value);
				}

				@SuppressWarnings("unchecked")
				public static <T> T get(String key) {
					return (T) getServletContext().getAttribute(key);
				}

				public static void remove(String key) {
					getServletContext().removeAttribute(key);
				}

				public static Map<String, Object> getAll() {
					Map<String, Object> map = new HashMap<String, Object>();
					Enumeration<String> names = getServletContext()
							.getAttributeNames();
					while (names.hasMoreElements()) {
						String name = names.nextElement();
						map.put(name, getServletContext().getAttribute(name));
					}
					return map;
				}
			}
		}
	}
}
