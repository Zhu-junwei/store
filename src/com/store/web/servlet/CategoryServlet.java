package com.store.web.servlet;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.service.impl.CategoryServiceImp;
import com.store.utils.JedisUtil;
import com.store.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 取得所有分类商品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Jedis jedis = JedisUtil.getJedis();
		String jsonStr = jedis.get("allCats");
		if(null==jsonStr | "".equals(jsonStr)) {
			//调用业务层获取全部分类
			CategoryService categoryService = new CategoryServiceImp();
			List<Category> list = categoryService.getAllCats();
			jsonStr = JSONArray.fromObject(list).toString();
			jedis.set("allCats", jsonStr);
//			System.out.println("jedis中没有数据");
		}else {
//			System.out.println("jedis中有数据");
		}
		
		JedisUtil.close(jedis);
//		System.out.println(jsonStr);
		response.setContentType("applicatin/json;charset=utf-8");
		response.getWriter().print(jsonStr);
		//将全部分类转换为JSON格式的数据
		//将全部分类信息相应到客户端
		return null ;
	}
}
	