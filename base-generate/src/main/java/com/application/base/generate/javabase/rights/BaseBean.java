package com.application.base.generate.javabase.rights;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.application.base.utils.common.UUIDProvider;

/**
 * 基类
 */
public class BaseBean implements Serializable,Cloneable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/** 唯一标识 */
	private int id = -1 ;
	public static String FIELD_ID = "id";
	/** 唯一标识uuid */
	private String uuid = null;
	public static String FIELD_UUID = "uuid";
	/** 创建人 */
	private int createUser = -1;
	public static String FIELD_CREATE_USER = "createUser";
	/** 创建时间 */
	private Date createTime = null;
	public static String FIELD_CREATE_TIME = "createTime";
	/** 修改人 */
	private int updateUser = -1;
	public static String FIELD_UPDATE_USER = "updateUser";
	/** 修改人时间 */
	private Date updateTime = null;
	public static String FIELD_UPDATE_TIME = "updateTime";
	/** 是否启用 */
	private Integer disabled = -1;
	public static String FIELD_DISABLED = "disabled";
	/** 描述 */
	private String remark = null;
	public static String FIELD_REMARK = "remark";
	/** 排序 */
	private int orderBy = -1;
	public static String FIELD_ORDER_BY = "orderBy";

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public int getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public static <T> T getInstance(Class<T> clazz) {
		BaseBean o = null;
		try {
			o = (BaseBean) clazz.newInstance();
			o.setUuid(UUIDProvider.uuid());
			o.setDisabled(0);
			o.setCreateTime(new Date());
			o.setUpdateTime(new Date());
			return clazz.cast(o);
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取基本信息接口.
	 * 
	 * @param bean
	 * @return
	 */
	public String getBeanInfos(BaseBean bean) {
		StringBuffer buffer = new StringBuffer(bean.getClass().getName() + ",infos : \n");
		try {

			Class<?> cls1 = bean.getClass();
			Field[] fields1 = cls1.getDeclaredFields();
			Class<?> cls2 = bean.getClass().getSuperclass();
			Field[] fields2 = cls2.getDeclaredFields();

			// 添加集合中去.
			ArrayList<Field> fields = new ArrayList<Field>();
			fields.addAll(Arrays.asList(fields1));
			fields.addAll(Arrays.asList(fields2));

			// 排查...
			int index = 0;
			String name = null;
			for (Field field : fields) {
				name = field.getName();
				index++;
				field.setAccessible(true);
				Object val = field.get(bean);
				if (name.startsWith("serial") || name.startsWith("FIELD") || name.startsWith("tableName")
						|| name.startsWith("orderBy")) {
					continue;
				}
				if (name.startsWith("createTime") || name.startsWith("updateTime") || name.startsWith("createDate")
						|| name.startsWith("updateDate")) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (val != null) {
						val = (Date) val;
						val = format.format(val);
					}
				}
				if (index == fields.size() - 1) {
					buffer.append(name + ":" + val);
				}
				else {
					buffer.append(name + ":" + val + ",");
				}
			}
		}
		catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

}
