package com.application.base.core.result;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.application.base.utils.common.StringDefaultValue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


/**
 * 全局结果信息容器
 *
 * @ClassName: ResultMessageContext
 * @Description:TODO
 */

public class ResultMessageContext implements MessageContext {
	
    private static Logger logger = LoggerFactory.getLogger(ResultMessageContext.class);

    private final String ELEMENT_NAME = "resultMessage";
    
    private final String ATTRIBUTE_KEY = "key";
    
    private final String ATTRIBUTE_CODE = "code";
    
    private final String ATTRIBUTE_MESSAGE = "message";
    
    private Map<String, ResultInfo> context = null;
    
    private String paths; //消息加载的 paths 


	public void init() {
        if (context == null){
            context = new HashMap<String, ResultInfo>();
        }
        logger.debug("[<============初始化基础结果信息开始============>]");
        if (!StringDefaultValue.isEmpty(paths)) {
        	String[] msgs = paths.split(",");
        	for (String msg : msgs) {
        		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(msg);
        		addMessageResourceFromStream(in);
			}
		}
        
        logger.debug("[<============初始化基础结果信息结束============>]");
    }

    public void addMessageResource(String path) {
		Resource resource = new DefaultResourceLoader().getResource(path);
        try {
            File file = resource.getFile();
            Element root = XMLUtils.getRoot(file);
            for (Element resultMessage : XMLUtils.childrenElements(root, ELEMENT_NAME)) {
                logger.debug("[key:{},code:{},message:{}]",
                        XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_KEY).getText(),
                        Integer.valueOf(XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_CODE).getText()),
                        XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_MESSAGE).getText());

                ResultInfo info = new ResultInfo(
                        XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_KEY).getText(),
                        XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_CODE).getText(),
                        XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_MESSAGE).getText());
                context.put(XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_KEY).getText(), info);
            }
        } catch (IOException e) {
            logger.error("初始化全局结果信息容器失败", e);
        }
    }

    public void addMessageResourceFromStream(InputStream in) {
        Element root = XMLUtils.getDoc(in).getRootElement();
        for (Element resultMessage : XMLUtils.childrenElements(root, ELEMENT_NAME)) {
            logger.debug("[key:{},code:{},message:{}]",
                    XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_KEY).getText(),
                    Integer.valueOf(XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_CODE).getText()),
                    XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_MESSAGE).getText());
            ResultInfo info = new ResultInfo(
            		XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_KEY).getText(),
                    XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_CODE).getText(),
                    XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_MESSAGE).getText());
            context.put(XMLUtils.getElementAttribute(resultMessage, ATTRIBUTE_KEY).getText(), info);
        }
    }

    public ResultInfo getResultInfo(String key) {
        return context.get(key);
    }

    public String getPaths() {
		return paths;
	}

	public void setPaths(String paths) {
		this.paths = paths;
	}
	
}

class XMLUtils{
	
    private static Logger logger = LoggerFactory.getLogger(XMLUtils.class);
    
    /**
     * 根据文件获取xml的文档对象
     *
     * @param file
     * @return Document
     *
     */
    public static Document getDoc(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            logger.info("解析xml File出错！", e);
        }
        return document;
    }

    public static Document getDoc(InputStream in) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(in);
        } catch (DocumentException e) {
            logger.info("解析xml File出错！", e);
        }
        return document;
    }

    /**
     * 根据文件，获取文件的根节点
     *
     * @param file
     * @return Element
     *
     */
    public static Element getRoot(File file) {
        SAXReader reader = new SAXReader();
        Element root = null;
        try {
            root = reader.read(file).getRootElement();
        } catch (DocumentException e) {
            logger.info("解析xml File出错！");
            e.printStackTrace();
        }
        return root;
    }

    /**
     * 获得某个节点下所有名字为elementName的子节点
     *
     * @param element
     * @param elementName
     * @return Element
     *
     */
    @SuppressWarnings("unchecked")
    public static List<Element> childrenElements(Element element, String elementName) {
        return element.elements(elementName);
    }

    /**
     * 获得某个节点的某个子节点
     *
     * @param element
     * @param elementName
     * @return Element
     *
     */
    public static Element childrenElement(Element element, String elementName) {
        return element.element(elementName);
    }

    /**
     * 获取某个节点下名字为attributeName的属性
     *
     * @param element
     * @param attributeName
     * @return Attribute
     *
     */
    public static Attribute getElementAttribute(Element element, String attributeName) {
        return element.attribute(attributeName);
    }

}