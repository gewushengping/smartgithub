package com.example.smart;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.util.List;
public class DMOXmlService {


public List<Person> parseXML(InputStream is) 
{
List<Person> list = new ArrayList<Person>();
// 创建DOM工厂对象
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//这里是创建了一个实例
try {
// DocumentBuilder对象
DocumentBuilder builder = factory.newDocumentBuilder();//这里是注册解析器
// 获取文档对象

Document document = builder.parse(is);
// 获取文档对象的root
Element root = document.getDocumentElement();
// 获取persons根节点中所有的person节点对象
NodeList personNodes = root.getElementsByTagName("person");
// 遍历所有的person节点
for (int i =0; i < personNodes.getLength(); i++) {
Person person = new Person();
// 根据item(index)获取该索引对应的节点对象
Element personNode = (Element) personNodes.item(i); // 具体的person节点
// 设置id属性值
person.setId(Integer.parseInt(personNode.getAttribute("id")));
// 获取该节点下面的所有字节点
NodeList personChildNodes = personNode.getChildNodes();
// 遍历person的字节点
for (int index =0; index < personChildNodes.getLength(); index++) {
// 获取子节点
Node node = personChildNodes.item(index);
// 判断node节点是否是元素节点
if (node.getNodeType() == Node.ELEMENT_NODE) {
//把节点转换成元素节点
Element element = (Element) node;
//判断元素节点是否是name元素节点
if ("name".equals(element.getNodeName())) {
person.setName(element.getFirstChild()
.getNodeValue());
} else if ("age".equals(element.getNodeName())) {//判断是否是age节点
person.setAge(new Short(element.getFirstChild()
.getNodeValue()));
}
}
}
// 把person对象加入到集合中
list.add(person);
}
//关闭输入流
is.close();
} 

catch (Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
return list;
}
}
