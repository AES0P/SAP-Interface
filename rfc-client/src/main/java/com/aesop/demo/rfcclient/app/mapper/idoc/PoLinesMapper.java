package com.aesop.demo.rfcclient.app.mapper.idoc;

import com.aesop.demo.rfcclient.app.bean.idoc.entity.po.PoLines;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Aesop
 * @since 2020-01-17
 */
@Mapper
@Component
public interface PoLinesMapper extends BaseMapper<PoLines> {

}
