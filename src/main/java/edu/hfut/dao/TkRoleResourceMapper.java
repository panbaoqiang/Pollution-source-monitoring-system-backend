package edu.hfut.dao;


import edu.hfut.pojo.entity.RoleResource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * @author panbaoqiang
 */
public interface TkRoleResourceMapper extends InsertListMapper<RoleResource> { }
