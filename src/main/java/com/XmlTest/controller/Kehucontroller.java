package com.XmlTest.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.XmlTest.bean.Aehu;
import com.XmlTest.bean.PageHelper;
import com.XmlTest.bean.Aehu;
import com.XmlTest.service.Kehuservice;

import net.sf.json.JSONObject;

@Controller
public class Kehucontroller {

	@Autowired
	private Kehuservice k;

	JSONObject json = new JSONObject();

	@RequestMapping("/")
	public String index() {

		return "index";
	}

	@RequestMapping("/getshuju")
	@ResponseBody
	public PageHelper<Aehu> page4list(Aehu ke, HttpSession request) {

		// 在service通过条件查询获取指定页的数据的list
		PageHelper<Aehu> pageHelper = new PageHelper<Aehu>();
		List<Aehu> list = k.SelectKehuAll(ke.getLimit(), ke.getOffset(), ke.getName());
		// 根据查询条件，获取符合查询条件的数据总量
		Integer total = k.selectKehuBycount();
		pageHelper.setRows(list);
		pageHelper.setTotal(total);
		System.out.println(ke.getLimit() + "===" + ke.getOffset() + ke.getName());
		request.setAttribute("limit", ke.getLimit());
		request.setAttribute("offset", ke.getOffset());
		request.setAttribute("Name", ke.getName());
		System.out.println(pageHelper.toString());
		return pageHelper;
	}

	// 导出excel
	@RequestMapping(value = "/exportMyTables")
	public ModelAndView exportMyTables(HttpSession request, ModelMap model) {
		String newstr="{rows:[{pname:aa, dno:987654, sex:男, age:12, phone:12}]}";
		@SuppressWarnings("unchecked")
		List<Map> newnew=(List<Map>)JSONObject.fromObject(newstr).get("rows");
		ExpExcelView tabLsExcelView = new ExpExcelView();
		
			// 获取页面传回参数
			// Map<String, Object> params = GetAndSetParameters(request);
			// 特殊参数处理
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

			// params.put("v_page_num", "-99");
			// 调用查询
			// iSQLService.call_Query_My_Tables(params);
			// List<Map<String, Object>> tabLs = (ArrayList<Map<String,
			// Object>>) params.get("v_tab");
			Integer limit = (Integer) request.getAttribute("limit");
			Integer offset = (Integer) request.getAttribute("offset");
			String Name = (String) request.getAttribute("Name");
			System.out.println(limit + "=========");
			List<Aehu> lista = k.SelectKehuAll(limit, offset, Name);
			System.out.println("0000117777============="+lista.size());
			List<Map> tabLs=new ArrayList<Map>();
			for(Aehu kehu:lista){
				try {
					tabLs.add(this.objectToMap(kehu));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			for (Map<String, Object> map : tabLs) {
//				System.out.println(map);
//			}
			System.out.println(tabLs);
			
			model.put("tabLs", newnew);
			// 获取表头和列名
			// 表头
			String xls_head = "客户编号|药品编号|性别|年龄|电话";
			// 对应字段
			String xls_col = "pname|dno|sex|age|phone";
			// request.getParameter("xls_col") 传中文乱码
			model.put("xls_head", xls_head);
			model.put("xls_col", xls_col);

			// 设置导出默认文件名
			model.put("exp_file", "我的临时表清单_" + sdf2.format(new Date()) + ".xls");
		
		return new ModelAndView(tabLsExcelView, model);
	}
	public   Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null)  
            return null;      
        Map<String, Object> map = new HashMap<String, Object>();   
  
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();  
            Object value = getter!=null ? getter.invoke(obj) : null;  
            map.put(key, value);  
        }    
  
        return map;  
    } 

}
