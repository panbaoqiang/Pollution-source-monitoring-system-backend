package edu.hfut.dao;

import edu.hfut.pojo.entity.UserRole;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author panbaoqiang
 */
public interface TkUserRoleMapper extends Mapper<UserRole>, InsertListMapper<UserRole> { }
