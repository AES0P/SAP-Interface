package com.aesop.demo.rfcclient.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * JAXB（Java Architecture for XML Binding) 根据XML Schema映射到JavaBean的技术
 * JAXB将XML实例文档反向生成Java对象树的方法，并能将Java对象树的内容重新写到 XML实例文档。
 */
@Component
public class JaxbUtil {

    /**
     * 将XML文件转换为string
     *
     * @param filePath xml file path
     * @return xml string
     */
    public String loadXmlFile2XmlString(String filePath) {
        return loadXmlDocumentFromPath(filePath).asXML();
    }

    /**
     * 将URL对应的XML转换为string
     *
     * @param url xml file url
     * @return xml string
     */
    public String loadUrl2XmlString(URL url) {
        return loadXmlDocumentFromPath(url).asXML();
    }

    /**
     * 读取指定的xml文件之后返回一个Document对象，这个对象代表了整个XML文档，用于各种Dom运算。
     * 依照XML文件头所定义的编码来转换。
     *
     * @param object URL or file path
     * @return xml document
     */
    public Document loadXmlDocumentFromPath(Object object) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();

            if (object instanceof URL) {
                document = saxReader.read((URL) object); // 读取URL,获得document对象
            } else if (object instanceof String) {
                document = saxReader.read(new File((String) object)); // 读取XML文件,获得document对象
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    public boolean string2XmlFile(String xmlStr, String filename) {
        boolean flag = true;
        try {
            doc2XmlFile(DocumentHelper.parseText(xmlStr), filename);
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }


    /**
     * Dom4j通过XMLWriter将Document对象表示的XML树写入指定的文件，并使用OutputFormat格式对象指定写入的风格和编码方法。
     * 调用OutputFormat.createPrettyPrint()方法可以获得一个默认的pretty print风格的格式对象。
     * 对OutputFormat对象调用setEncoding()方法可以指定XML文件的编码方法。
     *
     * @param document xml document
     * @param filename file name
     * @return success or failed
     */
    public boolean doc2XmlFile(Document document, String filename) {
        boolean flag = true;
        try {
            XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8));
            writer.write(document);
            writer.close();
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }


    @SuppressWarnings("unchecked")
    public <T> T xmlStr2Object(String xmlStr, Class<T> c) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(new StringReader(xmlStr));

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }

    }

    @SuppressWarnings("unchecked")
    public <T> T xmlFile2Object(String filePath, Class<T> c) {
        try {

            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(new StringReader(loadXmlFile2XmlString(filePath)));

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param object 对象
     * @return 返回xmlStr
     */
    public Document object2XmlDocument(Object object) throws DocumentException {
        return DocumentHelper.parseText(object2XmlString(object));
    }

    /**
     * @param object 对象
     * @return 返回xmlStr
     */
    public String object2XmlString(Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();

            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
            marshal.setProperty("jaxb.encoding", "utf-8");
            marshal.marshal(object, writer);

            return new String(writer.getBuffer());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @SuppressWarnings("unchecked")
    public <T> T readString(Class<T> clazz, String context) throws JAXBException, IOException {
        try {
            InputStream inputStream = ClassLoader.getSystemResource(context).openStream();
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            return (T) u.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw e;
        }
    }


}
