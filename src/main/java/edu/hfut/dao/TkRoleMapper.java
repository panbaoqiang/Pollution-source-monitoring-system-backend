package edu.hfut.dao;

import edu.hfut.pojo.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * @author panbaoqiang
 */
public interface TkRoleMapper extends Mapper<Role>{
    /**
     * 根据id删除一批角色
     * @param ids
     * @return
     */
    Integer deleteMultipleRole(@Param("ids") List<String> ids);

    /**
     * 根据roleId集合删除所有有关的资源信息
     * @param ids
     * @return
     */
    Integer clearRoleResourceByRoleId(@Param("ids") List<String> ids);

    /**
     * 根据roleId集合删除所有有关的用户信息
     * @param ids
     * @return
     */
    Integer clearRoleUserByRoleId(@Param("ids") List<String> ids);
}
