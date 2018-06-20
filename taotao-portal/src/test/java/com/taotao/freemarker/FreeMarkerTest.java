package com.taotao.freemarker;/**
 * Created by lenovo on 2018/6/1.
 */


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * @author shishuai
 * @create 2018-06-01 11:36
 **/
public class FreeMarkerTest {

    @Test
    public void testFreeMarker() throws Exception{
        //第一步：把fareemarker的jar包添加到工程中。
        //第二步：freemarker的运行不依赖web容器，可以在java工程中运行。创建一个测试方法进行测试。
        //第三步：创建一个Configuration对象。
        Configuration configuration = new Configuration(Configuration.getVersion());
        //第四步：设置Configuration对象模板文件存放的路径。
        configuration.setDirectoryForTemplateLoading(new File("D:\\notes\\taotao\\taotao-portal\\src\\main\\webapp\\WEB-INF\\ftl"));
        //第五步：设置Configuration的默认字符集。一般是UTF-8。
        configuration.setDefaultEncoding("UTF-8");
        //第六步：从Configuration对象中获得模板对象。需要指定一个模板文件的名字。
        Template template = configuration.getTemplate("second.ftl");
        //第七步：创建模板需要的数据集。可以是一个map对象也可以是一个pojo。将模板需要的数据放入数据集。
        Map root = new HashMap();
        root.put("hello","hello freemarker first.fyl");
//        root.put("title","hello freemarker");
        root.put("student",new Student(1,"张三","北京"));
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"张三1","上海1"));
        students.add(new Student(2,"张三2","上海2"));
        students.add(new Student(3,"张三3","上海3"));
        root.put("students",students);

//        root.put("curdate",new Date());
        //第八步：创建一个Writer对象，指定生成文件所存放的路径及文件名。
        Writer out = new FileWriter(new File("D:\\ftl\\second.html"));
        //第九步：丢哦啊用模板对象的process方法生成静态文件。需要数据集与writer对象。
        template.process(root,out);
        //第十步：关闭writer对象。
        out.flush();
        out.close();
    }


    public class Student{
        private int id;
        private String name;
        private String address;

        public Student(int id, String name, String address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
