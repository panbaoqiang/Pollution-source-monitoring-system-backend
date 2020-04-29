package edu.hfut.dao;

import edu.hfut.pojo.entity.Component;
import edu.hfut.pojo.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author panbaoqiang
 */
public interface TkComponentMapper extends Mapper<Component>{ }
