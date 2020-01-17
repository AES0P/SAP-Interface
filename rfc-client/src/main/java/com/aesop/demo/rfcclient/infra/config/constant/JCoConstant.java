package com.aesop.demo.rfcclient.infra.config.constant;

public class JCoConstant {

    private JCoConstant() {
    }

    /**
     * SAP中对应的RFC列表
     * 这个类相当于一个函数列表
     */
    public static class RFCList {

        private RFCList() {
        }

        public static final String ZZF_IF001 = "ZZF_IF001_LOG_EXAMPLE";

        public static final String ZZF_IF002 = "ZZF_IF002_DELETE_PR_ITEM";

        public static final String ZZF_IF003 = "ZZF_IF003_";

        public static final String ZZF_IF004 = "ZZF_IF004_";

        public static final String ZZF_IF005 = "ZZF_IF005_";

        public static final String ZZF_IF006 = "ZZF_IF006_";

        public static final String ZZF_IF007 = "ZZF_IF007_";

        public static final String ZZF_IF008 = "ZZF_IF008_";

        public static final String ZZF_IF009 = "ZZF_IF009_";

        public static final String ZZF_IF010 = "ZZF_IF010_";
    }

    /**
     * SAP中对应的IDoc列表
     */
    public static class IDocList {

        private IDocList() {
        }

        public static final String IDOC_001 = "ZIDOC_PO";

        public static final String IDOC_002 = "ZIDOC_";

        public static final String IDOC_003 = "ZIDOC_";

        public static final String IDOC_004 = "ZIDOC_";

        public static final String IDOC_005 = "ZIDOC_";

        public static final String IDOC_006 = "ZIDOC_";

        public static final String IDOC_007 = "ZIDOC_";

        public static final String IDOC_008 = "ZIDOC_";

    }

    /**
     * JCo 连接信息配置类
     */
    public static class JCoConn {

        private JCoConn() {
        }

        /**
         * 直连方式
         */
        public static final int DIRECT = 1000;

        /**
         * 直连方式的连接名称
         */
        public static final String ABAP_AS_WITHOUT_POOL = "ABAP_AS";

        /**
         * 连接池连接方式
         */
        public static final int WITH_POOL = 1001;

        /**
         * 连接池方式的连接名称
         */
        public static final String ABAP_AS_WITH_POOL = "ABAP_AS_WITH_POOL";


    }


    /**
     * 业务异常
     */
    public static class ErrorCode {

        private ErrorCode() {
        }

        public static final String CREATE_DESTINATION_FILE_ERROR = "create.destination.file.error";

        public static final String ERP_RESULT_ERROR_FLAG = "erp.result.error.flag";

        public static final String UNKNOWN_ERROR = "unknown.error";

        public static final String FRONT_EXECUTE_ERROR = "Data execute exception in front";

    }

    /**
     * SITF接口数据反馈状态
     */
    public static class FeedBackStatus {

        private FeedBackStatus() {
        }

        /**
         * 新建状态
         */
        public static final String NEW = "NEW";

        /**
         * 错误状态
         */
        public static final String ERROR = "ERROR";

        /**
         * 成功状态
         */
        public static final String SUCCESS = "SUCCESS";

    }

    /**
     * sap 标志常量
     */
    public static class SapFlagType {

        private SapFlagType() {

        }

        /**
         * SAP 成功 标志
         */
        public static final String SUCCESS_FLAG = "S";

        /**
         * SAP 错误 标志
         */
        public static final String ERROR_FLAG = "E";

        /**
         * SAP 警告 标志
         */
        public static final String WARNING_FLAG = "W";

        /**
         * SAP 中断 标志
         */
        public static final String INTERRUPT_FLAG = "A";

        /**
         * SAP 信息 标志
         */
        public static final String INFO_FLAG = "I";

    }

    /**
     * sap 外部系统常量
     */
    public static class ExternalSystemCode {

        private ExternalSystemCode() {
        }

        /**
         * SAP
         */
        public static final String SAP = "SAP";

        /**
         * SAP
         */
        public static final String SRM = "SRM";

        /**
         * EBS
         */
        public static final String EBS = "EBS";

    }

    public static class JCoCustomRepository {

        private JCoCustomRepository() {
        }

        /**
         * REPOSITORY NAME
         */
        public static final String REPOSITORY_NAME = "MyCustomizedRepository";


    }


}
