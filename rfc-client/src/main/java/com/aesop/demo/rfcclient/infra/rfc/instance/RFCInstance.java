package com.aesop.demo.rfcclient.infra.rfc.instance;

import com.aesop.demo.rfcclient.app.bean.rfc.dto.common.FeedBackDto;
import com.aesop.demo.rfcclient.infra.config.constant.JCoConstant;
import com.aesop.demo.rfcclient.infra.rfc.util.RFCAutoFillUtil;
import com.sap.conn.jco.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类可以根据RFC名自动初始化好相关参数和数据
 * 所有RFC都继承于此类，以便减少重复 属性 和 方法 的编码，只注重对应的业务实现
 * 通过 VO DTO等传递数据
 */
@Slf4j
@Component
@Data
public abstract class RFCInstance {

    /**
     * jco function 需要 JCo Destination 驱动
     * 可在已实现的四种 Destination 生产方式中任选
     * 需要注意的是，同时只能存在一个 jco destination的实例，所以只需生产一个 destination bean 即可
     */
    @Autowired
    public JCoDestination jCoDestination;

    /**
     * RFC 各参数值数据填充工具
     */
    @Autowired
    public RFCAutoFillUtil rfcAutoFillUtil;

    /**
     * RFC 函数名
     */
    public String functionName;

    /**
     * 填入SAP中具体存在的RFC名字后，就能获取到该RFC的各参数信息
     */
    public JCoFunction function;

    /**
     * RFC import 列表
     */
    public JCoParameterList importList;

    /**
     * RFC export 列表
     */
    public JCoParameterList exportList;

    /**
     * RFC changing 列表
     */
    public JCoParameterList changingList;

    /**
     * RFC table 列表
     */
    public JCoParameterList tableList;

    /**
     * RFC exception
     */
    public AbapException[] exceptionList;

    /**
     * 构建初始 SAP RFC 结构
     * 不可重写
     *
     * @return 具体 RFC bean
     */
    public final JCoFunction initRFC() {

        try {

            this.jCoDestination.ping();

            // 访问RFC
            function = this.jCoDestination.getRepository().getFunction(this.functionName);
            if (function == null) {
                throw new RuntimeException(this.functionName + " not found in SAP.");
            }

            //获取各参数列表
            importList = function.getImportParameterList();
            exportList = function.getExportParameterList();
            changingList = function.getChangingParameterList();
            tableList = function.getTableParameterList();
            exceptionList = function.getExceptionList();

            //初始化传入参数，约定每一个RFC必定有传入参数 E_MSGID 和 E_SENDR
            initImportParameterList();

            log.info("\r\nInitialize RFC: " + this.functionName + "  successfully!");
            return this.function;

        } catch (JCoException e) {
//            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }

    }

    /**
     * 约定 每一个RFC必定有传入参数 E_MSGID 和 E_SENDR ，并在这里为它们赋初值
     * <p>
     * 其它不在约定范围内的import参数、changing参数，都需要在子类里自行赋值
     * <p>
     * 如果没有固定参数 E_MSGID 和 E_SENDR ，需要在子类重写这个方法，否则执行初始化时会报错
     */
    public void initImportParameterList() {

        // 给import参数赋默认初值
        if (importList != null) {

            // 消息标识,若传入空值表示让SAP自动生成
            fillImportParameter("E_MSGID", "");
            // 发送方，SRM
            fillImportParameter("E_SENDR", JCoConstant.ExternalSystemCode.SRM);

        }

    }

    /**
     * 约定每一个RFC必定有一个反馈表 T_RETURN 和 导出参数 I_MSGID ，这里取得调用RFC后的反馈信息
     * <p>
     * 其它不在约定范围内的export参数、changing参数、exception等，都需要在子类自行处理
     * <p>
     * 如果没有固定表参数 T_RETURN 和 导出参数 I_MSGID ，需要在子类重写这个方法，否则会报错
     *
     * @return SAP 所有反馈信息
     */
    public List<FeedBackDto> getResponseDTOS() {

        List<FeedBackDto> feedBackDtoList = new ArrayList<>();


        try {
            // 执行RFC
            function.execute(jCoDestination);
            // 取得SAP反馈结果
            getOutputParameterTable("T_RETURN", FeedBackDto.class, feedBackDtoList);
            // 自动记录反馈日志
            rfcAutoFillUtil.recordFeedbackLog(feedBackDtoList, this.functionName, getExportParameter("I_MSGID"));

        } catch (Exception e) {
//            e.printStackTrace();
            log.error(e.getMessage());
            feedBackDtoList.add((new FeedBackDto("ALL", "E", e.getMessage())));
        }
        return feedBackDtoList;
    }

    /**
     * 每一个子类必须实现该类
     * 固定初始化方法：
     * step1、使用setFunctionName(JCoConstant.RFCList.XXXX)设定子类对应的RFC名字
     * step2、调用父类的 initRFC()方法；
     *
     * @return JCo Function
     */
    public abstract JCoFunction initFunction();

    /**
     * get export parameter
     * <p>
     * 此方法待加强，因为不是所有参数都是string类型,要能根据paraName去找出其对应sap类型
     * 思路：使用包装类，将JAVA类型与SAP类型自动转换
     *
     * @param paraName SAP 参数名
     */
    public String getExportParameter(String paraName) {
        return exportList.getString(paraName);
    }

    /**
     * get export parameter structure
     *
     * @param structureName SAP 结构名
     */
    public <T> void getExportParameterStructure(String structureName, T dto) {
        rfcAutoFillUtil.fillDtoStructure(structureName, exportList.getStructure(structureName), dto);
    }

    /**
     * get changing parameter
     * <p>
     * 此方法待加强，因为不是所有参数都是string类型,要能根据paraName去找出其对应sap类型
     * 思路：使用包装类，将JAVA类型与SAP类型自动转换
     *
     * @param paraName SAP 参数名
     */
    public String getChangingParameter(String paraName) {
        return changingList.getString(paraName);
    }

    /**
     * get changing parameter structure
     *
     * @param structureName SAP 结构名
     */
    public <T> void getChangingParameterStructure(String structureName, T dto) {
        rfcAutoFillUtil.fillDtoStructure(structureName, changingList.getStructure(structureName), dto);
    }

    /**
     * auto-fill input parameter table
     *
     * @param tableName SAP 表名
     * @param dtoList   java 传入表 数据
     */
    public <T> void getOutputParameterTable(String tableName, Class<?> dtoClass, List<T> dtoList) throws InstantiationException, IllegalAccessException {
        rfcAutoFillUtil.fillDtoTable(tableName, tableList.getTable(tableName), dtoClass, dtoList);
    }


    /**
     * auto-fill import parameter
     *
     * @param paraName SAP 参数名
     * @param value    java import list 传入测试 数据
     */
    public <T> void fillImportParameter(String paraName, T value) {
        importList.setValue(paraName, value);
    }

    /**
     * auto-fill import parameter structure
     *
     * @param structureName SAP 结构名
     * @param dto           java import list 传入结构 数据
     */
    public <T> void fillImportParameterStructure(String structureName, T dto) {
        JCoStructure structure = importList.getStructure(structureName);
        rfcAutoFillUtil.fillJCoStructure(structureName, structure, dto);
        importList.setValue(structureName, structure);
    }


    /**
     * auto-fill changing parameter
     *
     * @param paraName SAP 参数名
     * @param value    java changing list 传入参数 数据
     */
    public <T> void fillChangingParameter(String paraName, T value) {
        changingList.setValue(paraName, value);
    }

    /**
     * auto-fill changing parameter structure
     *
     * @param structureName SAP 结构名
     * @param dto           java changing list 传入结构 数据
     */
    public <T> void fillChangingParameterStructure(String structureName, T dto) {
        JCoStructure structure = changingList.getStructure(structureName);
        rfcAutoFillUtil.fillJCoStructure(structureName, structure, dto);
        changingList.setValue(structureName, structure);
    }

    /**
     * auto-fill input parameter table
     *
     * @param tableName SAP 表名
     * @param dtoList   java 传入表 数据
     */
    public <T> void fillInputParameterTable(String tableName, List<T> dtoList) {
        JCoTable table = tableList.getTable(tableName);
        rfcAutoFillUtil.fillJCoTable(tableName, table, dtoList);
        tableList.setValue(tableName, table);
    }

}
