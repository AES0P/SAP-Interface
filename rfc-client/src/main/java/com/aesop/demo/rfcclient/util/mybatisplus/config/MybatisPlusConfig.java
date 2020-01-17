package com.aesop.demo.rfcclient.util.mybatisplus.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import net.sf.jsqlparser.statement.delete.Delete;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aesop
 * @since 2019/12/09
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.aesop.demo.rfcclient.app.mapper")
public class MybatisPlusConfig {

    /**
     * 插件配置：
     * 1、分页设置
     * 2、攻击 SQL 阻断解析器
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        //--------------分页设置-------------
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(50);


        //--------------攻击 SQL 阻断解析器 阻止恶意的全表更新删除-------------
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser() {
            @Override
            public void processDelete(Delete delete) {
                // 如果你想自定义做点什么，可以重写父类方法像这样子
                if ("user".equals(delete.getTable().getName())) {
                    // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
                    return;
                }
                super.processDelete(delete);
            }
        });
        paginationInterceptor.setSqlParserList(sqlParserList);


        return paginationInterceptor;
    }

    /**
     * 乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


}
