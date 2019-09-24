package com.zhrb.testDemo.singletonDemoUse;

import com.zhrb.entity.Province;
import org.dom4j.*;
import org.dom4j.io.*;

import java.io.File;

import java.util.*;



/**
 * @ClassName SelectProvinceUtil
 * @Description TODO 测试注释是否有效
 * @Author zhrb
 * @Date 2019/9/24 10:22
 * @Version
 */
public class SelectProvinceUtil {
    //1、私有静态变量
    private static volatile SelectProvinceUtil selectProvinceUtil;
    private Map<String,List<Province>> selectMap;//业务对象
    private Document document;

    //2、私有构造方法
    private SelectProvinceUtil(String filePath){
        List<Province> listProvince = new ArrayList<Province>();
        selectMap = new HashMap<String, List<Province>>();
        try {
            //载入文件
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filePath));
            //获取根节点
            Element root = document.getRootElement();
            Iterator<Element> eleBrands = root.elementIterator();
            //读取节点
            while(eleBrands.hasNext()){
                Element brand = (Element)eleBrands.next();
                String key = brand.attributeValue("name");
                String description = brand.attributeValue("description");
                if(key.equals("province")){
                    Iterator<Element> eleTypes = brand.elementIterator();
                    while(eleTypes.hasNext()){
                        Element element = (Element)eleTypes.next();
                        Province province = new Province(element.attributeValue("id"), element.attributeValue("name"));
                        listProvince.add(province);
                    }
                    //把取到的值放进Maps
                    key = key +","+ description;
                    selectMap.put(key, listProvince);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
    *3、公有静态方法，获取实例对象
    * @Description 3、公有静态方法，获取实例对象
    * @Param filePath
    * @Returu selectProvinceUtil
    * @Author zhrb
    * @Date   16:12 2019/9/24
    **/
    public static synchronized SelectProvinceUtil getInstance(String filePath){
        if(null == selectProvinceUtil){
            selectProvinceUtil = new SelectProvinceUtil(filePath);
        }
        return selectProvinceUtil;
    }

    //4、公有业务方法
    public Map<String, List<Province>> getSelectMap(){
        return  selectMap;
    }
}
