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
// ����DOM��������
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//�����Ǵ�����һ��ʵ��
try {
// DocumentBuilder����
DocumentBuilder builder = factory.newDocumentBuilder();//������ע�������
// ��ȡ�ĵ�����

Document document = builder.parse(is);
// ��ȡ�ĵ������root
Element root = document.getDocumentElement();
// ��ȡpersons���ڵ������е�person�ڵ����
NodeList personNodes = root.getElementsByTagName("person");
// �������е�person�ڵ�
for (int i =0; i < personNodes.getLength(); i++) {
Person person = new Person();
// ����item(index)��ȡ��������Ӧ�Ľڵ����
Element personNode = (Element) personNodes.item(i); // �����person�ڵ�
// ����id����ֵ
person.setId(Integer.parseInt(personNode.getAttribute("id")));
// ��ȡ�ýڵ�����������ֽڵ�
NodeList personChildNodes = personNode.getChildNodes();
// ����person���ֽڵ�
for (int index =0; index < personChildNodes.getLength(); index++) {
// ��ȡ�ӽڵ�
Node node = personChildNodes.item(index);
// �ж�node�ڵ��Ƿ���Ԫ�ؽڵ�
if (node.getNodeType() == Node.ELEMENT_NODE) {
//�ѽڵ�ת����Ԫ�ؽڵ�
Element element = (Element) node;
//�ж�Ԫ�ؽڵ��Ƿ���nameԪ�ؽڵ�
if ("name".equals(element.getNodeName())) {
person.setName(element.getFirstChild()
.getNodeValue());
} else if ("age".equals(element.getNodeName())) {//�ж��Ƿ���age�ڵ�
person.setAge(new Short(element.getFirstChild()
.getNodeValue()));
}
}
}
// ��person������뵽������
list.add(person);
}
//�ر�������
is.close();
} 

catch (Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
return list;
}
}
