package com.zhrb.testDemo.singletonDemoUse;

import com.zhrb.entity.Province;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TestSelectProvinceUtil
 * @Description TODO
 * @Author zhrb
 * @Date 2019/9/24 10:23
 * @Version
 */
public class TestSelectProvinceUtil {
    public static void main(String[] args) {
        //xml文件绝对路径
        String filePath = new File("src/main/resources/xml/Province").getAbsolutePath();

        Map<String, List<Province>> map = SelectProvinceUtil.getInstance(filePath).getSelectMap();

        //遍历输出
        Set<String> keys = map.keySet();
        String name = keys.toArray()[0].toString().split(",")[0];
        String description = keys.toArray()[0].toString().split(",")[1];
        for(String key : keys){
            System.out.println("节点名称："+ name + "\t" + "节点描述：" + description);
            List<Province> list = (List<Province>)map.get(key);
            for(Province p:list){
                System.out.println("\t"+p.getId()+"\t"+p.getName());
            }
        }
    }
}
