package io.renren.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**   
 * @ClassName:  Obj2Map   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: jgl
 * @date:   2019年10月13日 下午5:40:58   
 */
public class ToMap {
	

public static Map<String, String> obj2Map(Object obj) {
 
		Map<String, String> map = new HashMap<String, String>();
		// System.out.println(obj.getClass());
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
//			varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o.toString());
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	//引入的Jar包
	//import ty.cbos.framework.mybatis.annotation.IdField;
	//import ty.cbos.framework.mybatis.annotation.TableField;
	//import ty.cbos.framework.mybatis.annotation.TableName;
	//import java.lang.reflect.Field;
	
	public static Map<String ,String> getProxyPojoValue(Object object,List<String> key){
	     String id = null;
	     // 返回参数
	     HashMap<String,String> hashMap = new HashMap<>(16);
	     for (String s : key) {
	    	 System.out.println(s);
	         Field[] fields = object.getClass().getDeclaredFields();
	         for (Field field : fields) {
	             field.setAccessible(true);
	            
	             // 获取主键id
	             if (id == null) {
	                 boolean isIdField = field.isAnnotationPresent(TableId.class);
	                 if (isIdField) {
	                	 TableId tableField = field.getAnnotation(TableId.class);
	                     if(field.getName()!=null) {
	                    	 if (s.toLowerCase().equals(field.getName().toLowerCase())) {
	                    		 if(tableField!=null) {
	                    			 String tableId = tableField.value();
	                    			 hashMap.put(s,tableId);
	                    			 id = tableId;
	                    			 break;
	                    		 }
		                     }
	                     }
	                     
	                 }
	             }
	
	             // 获取字段的值
	             boolean isTableField = field.isAnnotationPresent(TableField.class);
	             if (isTableField) {
	                 TableField tableField = field.getAnnotation(TableField.class);
	                 if (s.toLowerCase().equals(field.getName().toLowerCase())) {
	                     String fieldValue = tableField.value();
	                     hashMap.put(s,fieldValue);
	                     break;
	                 }
	             }
	         }
	     }
	     
	     return hashMap;
	 }
	
	
	public static Map<String ,Object> getProxyPojoValue2(Object object,List<String> key){
	     String id = null;
	     // 返回参数
	     HashMap<String,Object> hashMap = new HashMap<>(16);
	     for (String s : key) {
	    	 System.out.println(s);
	         Field[] fields = object.getClass().getDeclaredFields();
	         for (Field field : fields) {
	             field.setAccessible(true);
	
	             // 获取表名
	             TableName table = object.getClass().getAnnotation(TableName.class);
	             if (table != null) {
	                 String tableName = table.value();
	                 hashMap.putIfAbsent("tableName", tableName);
	             }
	             // 获取主键id
	             if (id == null) {
	                 boolean isIdField = field.isAnnotationPresent(TableId.class);
	                 if (isIdField) {
	                     TableField tableField = field.getAnnotation(TableField.class);
	                     if(field.getName()!=null) {
	                    	 if (s.toLowerCase().equals(field.getName().toLowerCase())) {
	                    		 if(tableField!=null) {
	                    			 String tableId = tableField.value();
	                    			 hashMap.put(s,tableId);
	                    			 id = tableId;
	                    		 }
		                     }
	                     }
	                     
	                 }
	             }
	
	             // 获取字段的值
	             boolean isTableField = field.isAnnotationPresent(TableField.class);
	             if (isTableField) {
	                 TableField tableField = field.getAnnotation(TableField.class);
	                 if (s.toLowerCase().equals(field.getName().toLowerCase())) {
	                     String fieldValue = tableField.value();
	                     hashMap.put(s,fieldValue);
	                 }
	             }
	         }
	     }
	     
	     return hashMap;
	 }

}
