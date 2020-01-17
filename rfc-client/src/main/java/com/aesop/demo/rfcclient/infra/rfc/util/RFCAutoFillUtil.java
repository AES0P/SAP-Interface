package com.aesop.demo.rfcclient.infra.rfc.util;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.app.bean.rfc.entity.RfcLogFeedback;
import com.aesop.demo.rfcclient.app.service.rfc.RfcLogFeedbackService;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoRecordFieldIterator;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class RFCAutoFillUtil {

    @Resource(name = "rfcLogFeedbackServiceImpl")
    private RfcLogFeedbackService feedbackService;

    /**
     * 根据传入的 sap structure name ，自动为 sap structure 对应的 dto 填充数据
     *
     * @param structureName sap structure name
     * @param structure     jco structure
     * @param dto           java dto
     */
    public <T> void fillDtoStructure(String structureName, JCoStructure structure, T dto) {

        log.info("\r\nConvert JCoStructure: 【" + structureName + "】 to Dto Structure 【" + dto.getClass().getName() + "】");

        Map<String, String> kvList = getFiledName(dto, 2);
        if (kvList.isEmpty()) {
            log.info("\r\nConvert failed,kvList is empty ");
            return;
        }

        //获取jcoTable字段迭代器
        JCoRecordFieldIterator fieldIterator = structure.getRecordFieldIterator();

        //判断是否还有下一个字段
        while (fieldIterator.hasNextField()) {

            //获取Jco字段名
            JCoField jcoField = fieldIterator.nextField();
            String jcoFieldName = jcoField.getName();

            Iterator<Map.Entry<String, String>> entries = kvList.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();

                if (entry.getKey().toUpperCase().equals(jcoFieldName.toUpperCase())) {
                    setObjectValue(dto, entry.getKey(), jcoField.getValue());
                    entries.remove();//同一个结构不可能存在同名字段，用到一个就减去一个提高效率
                }
            }

        }

        log.info("\r\n Dto: " + dto.getClass().getName() + " transfered!" + "\n");

    }


    /**
     * 根据传入的 sap structure name ，自动为 dto 对应的 sap structure 填充数据
     *
     * @param structureName sap structure name
     * @param structure     jco structure
     * @param dto           java dto
     */
    public <T> void fillJCoStructure(String structureName, JCoStructure structure, T dto) {

        log.info("\r\nBegin to fill JCo Structure 【" + structureName + "】");

        structure.clear();

        Map<String, String> kvList = getFiledName(dto, 1);
        if (kvList.isEmpty()) {
            log.info("\r\nConvert failed,kvList is empty ");
            return;
        }

        //获取jcoTable字段迭代器
        JCoRecordFieldIterator fieldIterator = structure.getRecordFieldIterator();

        //判断是否还有下一个字段
        while (fieldIterator.hasNextField()) {

            //获取Jco字段名
            JCoField jcoField = fieldIterator.nextField();
            String jcoFieldName = jcoField.getName();

            Iterator<Map.Entry<String, String>> entries = kvList.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();

                if (entry.getKey().toUpperCase().equals(jcoFieldName.toUpperCase())) {
                    structure.setValue(jcoFieldName, entry.getValue());
                    entries.remove();//同一个结构不可能存在同名字段，用到一个就减去一个提高效率
                }
            }


        }

        log.info("\r\n Structure: " + structureName + " transfered!" + "\n");
    }

    /**
     * 根据传入的 sap table name ，自动为 sap table 对应的 java dto list填充数据
     *
     * @param tableName sap table name
     * @param table     jco table
     * @param dtoClass  dto class type
     * @param dtoList   java dto list
     */
    @SuppressWarnings({"unchecked"})
    public <T> void fillDtoTable(String tableName, JCoTable table, Class<?> dtoClass, List<T> dtoList) throws IllegalAccessException, InstantiationException {

        log.info("\r\nConvert JCoTable: 【" + tableName + "】 to Dto list 【" + dtoClass.getName() + "】");

        //每一个 DTO 都是 T 类型，故只需要获取一次 K 列表
        Map<String, String> kvList = getFiledName(dtoClass.newInstance(), 2);
        if (kvList.isEmpty()) {
            log.info("\r\nConvert failed,kvList is empty ");
            return;
        }

        //遍历 table 一行一行进行插入
        table.firstRow();
        for (int i = 0; i < table.getNumRows(); i++, table.nextRow()) {

            //一行数据对应一个dto对象
            Object o = dtoClass.newInstance();

            //获取jcoTable字段迭代器
            JCoRecordFieldIterator fieldIterator = table.getRecordFieldIterator();

            //判断是否还有下一个字段
            while (fieldIterator.hasNextField()) {

                //获取Jco字段名
                JCoField jcoField = fieldIterator.nextField();
                String jcoFieldName = jcoField.getName();

                for (Map.Entry<String, String> entry : kvList.entrySet()) {
                    if (entry.getKey().toUpperCase().equals(jcoFieldName.toUpperCase())) {
                        setObjectValue(o, entry.getKey(), jcoField.getValue());
                        //log.info("entry.getKey():" + entry.getKey() + "jcoField.getValue()" + jcoField.getValue());
                    }
                }

            }
            if (!(o == null)) {
                dtoList.add((T) o);
            }
        }

        log.info("\r\nDto Table: " + dtoClass.getName() + " transfered!" + "\n");
    }

    /**
     * 根据传入的 sap table name ，自动为 java dto list 对应的 sap table 填充数据
     *
     * @param tableName sap table name
     * @param table     jco table
     * @param dtoList   java dto list
     */
    public <T> void fillJCoTable(String tableName, JCoTable table, List<T> dtoList) {

        log.info("\r\nBegin to fill JCo Table 【" + tableName + "】,rows【" + dtoList.size() + "】");

        Map<String, String> kvList;

        //jcoTable设置行数
        table.clear();
        table.appendRows(dtoList.size());

        //遍历dtoList一行一行进行插入
        for (T dto : dtoList) {

            //每一个 DTO 对象都不一样，所以每次需要重新获取KV对
            kvList = getFiledName(dto, 1);
            if (kvList.isEmpty()) {
                log.info("\r\nConvert failed,kvList is empty ");
                return;
            }

            //获取jcoTable字段迭代器
            JCoRecordFieldIterator fieldIterator = table.getRecordFieldIterator();

            //判断是否还有下一个字段
            while (fieldIterator.hasNextField()) {

                //获取Jco字段名
                JCoField jcoField = fieldIterator.nextField();
                String jcoFieldName = jcoField.getName();

                for (Map.Entry<String, String> entry : kvList.entrySet()) {
                    if (entry.getKey().toUpperCase().equals(jcoFieldName.toUpperCase())) {
                        table.setValue(jcoFieldName, entry.getValue());
                    }
                }


            }
            table.nextRow();
        }

        log.info("\r\n Table: " + tableName + " transfered!" + "\n" + table.toString());
    }

    /**
     * 根据传入的 FeedBackDto(可定义泛型，这里默认为FeedBackDto) ，自动存储SAP接口反馈日志到日志表
     *
     * @param list    FeedBackDto list
     * @param rfcName rfc name
     * @param msgId   msg id
     */
    public void recordFeedbackLog(List<FeedBackDto> list, String rfcName, String msgId) {

        log.info("\r\nBegging to auto-generate log...rfcName: " + rfcName + " msgId:" + msgId);

        if (!list.isEmpty()) {

            List<RfcLogFeedback> feedbacks = new ArrayList<>();

            for (FeedBackDto dto : list) {

//                log.info("\r\ndto: " + dto.toString());

                //未填写的字段交给mybatis plus自动填充
                RfcLogFeedback feedback = new RfcLogFeedback(
                        msgId,
                        rfcName,
                        dto.getDataKey(),
                        dto.getIfflg(),
                        dto.getIfmsg()
                );

                feedbacks.add(feedback);

//                log.info("feedback: " + feedback.toString());

                //single insert
                //feedbackService.insert(feedback);
            }
            //batch insert
            feedbackService.batchInsertOrUpdate(feedbacks);

        } else {
            log.info("\r\nFeedback dto list is null...");
        }
        log.info("\r\nLog generated.");
    }


    /**
     * 根据传入的object，获取其对应的键/键值信息并以MAP的形式返回
     *
     * @param o    要获取字段名和对应属性值的对象
     * @param type 1：获取键-值对map ，dto to jco 时使用,因为此时dto数据是已知的
     *             2：只获取字段map，jco to dto 时使用，因为此时dto对象必定不会有 value
     * @return filed map
     */
    private Map<String, String> getFiledName(Object o, int type) {
        Field[] fields = o.getClass().getDeclaredFields();
        Map<String, String> kvList = new HashMap<>();
        for (Field field : fields) {
            switch (type) {
                case 1:
                    //log.info("name:" + field.getName() + " value:" + getFieldValueByName(field.getName(), o).toString());
                    kvList.put(field.getName(), getFieldValueByName(field.getName(), o).toString());
                    break;
                case 2:
                    //log.info("name:" + field.getName());
                    kvList.put(field.getName(), "");
                    break;
            }
        }
        return kvList;
    }

    /**
     * 根据传入的object，获取指定字段对应的值
     *
     * @param fieldName field name
     * @param o         要获取属性值的对象
     */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            return "\r\nNO VALUE FOUND";
        }
    }

    /**
     * 根据 filed name，拿到对应setter方法，并将传入的 value 赋予它
     *
     * @param obj       要设置属性值的对象
     * @param filedName filed name
     * @param value     value
     */
    private void setObjectValue(Object obj, String filedName, Object value) {
        filedName = removeLine(filedName);
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, obj.getClass().getDeclaredField(filedName).getType());
            method.invoke(obj, getClassTypeValue(obj.getClass().getDeclaredField(filedName).getType(), value));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的初始值，防止传空值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }

    /**
     * 处理字符串:下划线转驼峰
     * 如：  abc_dex ---> abcDex
     *
     * @param str str
     */
    public String removeLine(String str) {
        if (!str.isEmpty() && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            return newStr.replace("_", "");
        }
        return str;
    }

}
