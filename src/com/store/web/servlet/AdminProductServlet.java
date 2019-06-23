package com.store.web.servlet;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.service.CategoryService;
import com.store.service.ProductService;
import com.store.service.impl.CategoryServiceImp;
import com.store.service.impl.ProductServiceImp;
import com.store.utils.PageModel;
import com.store.utils.UUIDUtils;
import com.store.utils.UploadUtils;
import com.store.web.base.BaseServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	ProductService productService = new ProductServiceImp();

	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取得当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用service层取得PageModel对象
		PageModel<Product> pageModel = productService.findAllProductsWithPage(curNum);
		//将pageModel放入request中
		request.setAttribute("page", pageModel);
		//转发/admin/producr/list.jsp
		return "/admin/product/list.jsp" ;
	}
	public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取全部分类信息
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> allCats = categoryService.getAllCats();
		//将全部细腻些放入request
		request.setAttribute("allCats", allCats);
		
		return "/admin/product/add.jsp" ;
	}
	public String addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//存放键值对数据
		Map<String,String> map = new HashMap<>();
		Product product = new Product();
		
		try {
			//利用req.getInputStream();获取到请求体中全部数据，进行拆分和封装
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			List<FileItem> list = fileUpload.parseRequest(request);
			
			//遍历集合
			for (FileItem item : list) {
				if(item.isFormField()) {
					//普通项
					map.put(item.getFieldName(), item.getString("utf-8"));
				} else {
					//上传项
					//获取到原始的文件名称
					String oldFileName = item.getName();
					String newFileName = UploadUtils.getUUIDName(oldFileName);
					//通过FileItem获取到输入流对象，通过输入流可以获取到图片的二进制数据
					InputStream is = item.getInputStream();
					//获取到当前项目下products/3下的真实路径
					String realPath = getServletContext().getRealPath("/products/3/");
					String dir = UploadUtils.getDir(newFileName);
					String path = realPath+dir;
					//内存中声明一个目录
					File newDir = new File(path);
					if(!newDir.exists()) {
						newDir.mkdirs();
					}
					//在服务端创建一个空文件(后缀必须和上传到服务端的文件名后缀一致)
					File finalFile = new File(newDir, newFileName);
					if(!finalFile.exists()) {
						finalFile.createNewFile();
					}
					//建立和空文件对应的输出流
					OutputStream os = new FileOutputStream(finalFile);
					IOUtils.copy(is, os);
					
					
					//释放资源
					/**
					 * 这两个方法在io 2.6被弃用了
					 * 有人解释可以自动关闭，使用try-with-resource
					 */
//					IOUtils.closeQuietly(is);
//					IOUtils.closeQuietly(os);
					is.close();
					os.close();
					
					//c存放图片路径
					map.put("pimage", "/products/3/"+dir+"/"+newFileName);
					
				}
			}
			BeanUtils.populate(product, map);
//			product = MyBeanUtils.populate(Class<Product.class>, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			
			productService.saveProduct(product);
			
			response.sendRedirect("/store/AdminProductServlet?method=findAllProductsWithPage&num=1");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
}
