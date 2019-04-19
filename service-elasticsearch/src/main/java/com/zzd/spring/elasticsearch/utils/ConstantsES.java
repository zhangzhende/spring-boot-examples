package com.zzd.spring.elasticsearch.utils;

/**
 * 自定义常量
 * 
 * @author zhangzhende
 * @version 2018年9月12日
 * @see ConstantsES
 * @since
 */
public interface ConstantsES {

    /**
     * 
     */
    int BATCH = 1000;

    /**
     * 高亮前标签
     */
    String HIGHT_LIGHT_PRE = "<span style=\"color:red\">";

    /**
     * 高亮后标签
     */
    String HIGHT_LIGHT_POST = "</span>";

    /**
     * 高亮字段名key
     */
    String KEY_HIGHLIGHTFIELDS = "highLightFields";

    /**
     * 查询字段的组合类型，not，and ，or 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @author zhangzhende
     * @version 2018年11月6日
     * @see SearchType
     * @since
     */
    class SearchType {
        /**
         * NOT
         */
        public static final String NOT = "NOT";

        /**
         * AND
         */
        public static final String AND = "AND";

        /**
         * OR
         */
        public static final String OR = "OR";
        /**
         * 不存在
         */
        public static final String NOTEXIST = "NOTEXIST";

        /**
         * 资讯type
         */
        public static final String DEFAULT = "AND";

    }

    /**
     * 排序方式，逆序desc，升序asc
     * 
     * @author zhangzhende
     * @version 2018年11月19日
     * @see SortType
     * @since
     */
    class SortType {
        /**
         * 降序
         */
        public static final String DESC = "desc";

        /**
         * 升序
         */
        public static final String ASC = "asc";
    }

    /**
     * 
     * 错误code
     * @author zhangzhende
     * @version 2018年11月27日
     * @see Error
     * @since
     */
    class Error {
        /**
         * 错误code
         */
        public static final String ERR_CODE_100 = "100";
    }

    /**
     * 成功code
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * @author zhangzhende
     * @version 2018年11月27日
     * @see Success
     * @since
     */
    class Success {
        /**
         * 成功返回code
         */
        public static final String DATA_SUCC = "0";
    }

    /**
     * 删除标记 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @author zhangzhende
     * @version 2018年11月7日
     * @see DelFlag
     * @since
     */
    class DelFlag {
        /**
         * 有效，
         */
        public static final String Y = "Y";

        /**
         * 已删除
         */
        public static final String X = "X";
    }
}
