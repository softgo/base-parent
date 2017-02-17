package ${JavaBeanControllerPath};

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.base.core.obj.Pagination;
import com.application.base.utils.common.StringDefaultValue;
import com.application.base.core.common.BaseController;


import ${JavaBeanPath}.${poName};
import ${JavaBeanServicePath}.${poName}Service;

/**
 * ${poName}Controller实现
 * 
 * @author 系统生成
 *
 */

@Controller
@RequestMapping("/${firstLowerPoName}")
public class ${poName}Controller extends BaseController {
	
	@Autowired
	private ${poName}Service ${firstLowerPoName}Service;

	/**
     * 添加对象.
     *
     * @param request
     * @param response
     */
    @RequestMapping("/add${poName}")
    public void add${poName}(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,null,null); // 根据实际情况填写.
        ${poName} object = ${firstLowerPoName}Service.saveObject(param);
        String resultStr = successResultJSON(object);
        printWriter(response, resultStr);
    }
    
    /**
     * 通过主键获得对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}ByID")
    public void get${poName}ByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,"${tablePKVal}"); // 根据实际情况填写.
        Object objId = param.get("${tablePKVal}");
        //根据实际情况去验证 objId 的类型的合法性。
        ${poName} object = ${firstLowerPoName}Service.getObjectByID(objId);
        String resultStr = successResultJSON(object);
        printWriter(response, resultStr);
    }
    
    <#if existUuid == "0">
    /**
     * 通过UUId获得对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}ByUUID")
    public void get${poName}ByUUID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,"uuid"); // 根据实际情况填写.
        String uuid = param.get("uuid").toString();
        ${poName} object = ${firstLowerPoName}Service.getObjectByUUID(uuid);
        String resultStr = successResultJSON(object);
        printWriter(response, resultStr);
    }
    </#if> 
    
    /**
     * 通过id修改对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/update${poName}ByID")
    public void update${poName}ByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,"${tablePKVal}"); // 根据实际情况填写.
        Object objId = param.get("${tablePKVal}");
        //根据实际情况去验证 objId 的类型的合法性。
        ${firstLowerPoName}Service.updateObjectByID(param, objId);
        printWriter(response, successResultJSON("通过ID="+objId+"修改成功"));
    }
    
    <#if existUuid == "0">
    /**
     * 通过uuid修改对象.
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/update${poName}ByUUID")
    public void update${poName}ByUUID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,"uuid"); // 根据实际情况填写.
        String uuid = param.get("uuid").toString();
        ${firstLowerPoName}Service.updateObjectByUUID(param, uuid);
        printWriter(response, successResultJSON("通过UUID="+uuid+"修改成功"));
    }
    </#if> 
    
    /**
     * 通过ID删除消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/del${poName}ByID")
    public void del${poName}ByID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,"${tablePKVal}"); // 根据实际情况填写.
        Object objId = param.get("${tablePKVal}");
        //根据实际情况去验证 objId 的类型的合法性。
        ${firstLowerPoName}Service.deleteObjectByID(objId);
        printWriter(response, successResultJSON("通过ID="+objId+"删除成功"));
    }
    
    <#if existUuid == "0">
    /**
     * 通过UUID删除消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/del${poName}ByUUID")
    public void del${poName}ByUUID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,"uuid"); // 根据实际情况填写.
        String uuid = param.get("uuid").toString();
        ${firstLowerPoName}Service.deleteObjectByUUID(uuid);
        printWriter(response, successResultJSON("通过UUID="+uuid+"删除成功"));
    }
    </#if> 
    
    /**
     * 获得所有消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}s")
    public void get${poName}s(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,null,null); // 根据实际情况填写.
        String resultStr = successResultJSON(${firstLowerPoName}Service.getObjects(param));
        printWriter(response, resultStr);
    }
    
    /**
     * 分页获得消息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/get${poName}sByPage")
    public void get${poName}sByPage(HttpServletRequest request, HttpServletResponse response) {
      	Map<String, Object> param = nullAbleValidation(request,null,null); // 根据实际情况填写.
        int pageNo = StringDefaultValue.intValue(param.get(PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(PAGE_SIZE));
        Pagination<${poName}> result = ${firstLowerPoName}Service.paginationObjects(param, pageNo, pageSize);
        printWriter(response, successResultJSON(result));
    }
    
}


