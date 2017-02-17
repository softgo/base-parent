package ${JavaBeanPath};

import com.application.base.core.obj.BaseBean;

<#list importList as importStr>
	<#if importStr?? >
import ${importStr};
	</#if>
</#list>
/**
 * ${poName}实体
 * 
 * @author 系统生成
 *
 */
public class ${poName} extends BaseBean {

	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "${tableName}";
	
<#list poColumnList as obj>
	/**${obj.remarks}*/
	private ${obj.type}  ${obj.name} = ${obj.defaultValue};
	/**${obj.remarks} 对应的静态变量值*/
	public static final String FIELD_${obj.staticFinalName} = "${obj.name}";
</#list>

	public ${poName} () {
		super();
	}
	
	public ${poName} (<#list poColumnList as obj>${obj.type} ${obj.name} <#if obj_has_next>,</#if></#list>) {
		super();
		<#list poColumnList as obj>
		 this.${obj.name} = ${obj.name};
		</#list>
	}
	
<#list poColumnList as obj>
	public ${obj.type} get${obj.firstUpperName}() {
		return ${obj.name};
	}
	public void set${obj.firstUpperName}(${obj.type} ${obj.name}) {
		this.${obj.name} = ${obj.name};
	}
</#list>
}
