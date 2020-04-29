package edu.hfut.dao;

import edu.hfut.pojo.entity.Resource;
import edu.hfut.pojo.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * @author panbaoqiang
 */
public interface TkUserRoleMapper extends Mapper<UserRole>, InsertListMapper<UserRole> {
    /**
     * 根据用户ID集合查询角色集合 该查看是否有元素
     * @param ids
     * @return
     */
    Integer queryUserRoleCountByUserIds(@Param("ids") List<String> ids);

    /**
     * 根据roleId集合删除批量的userRole
     * @param ids
     * @return
     */
    Integer deleteMultipleUserRoleByRoleId(@Param("ids") List<String> ids);

}
