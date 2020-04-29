package edu.hfut.dao;


import edu.hfut.pojo.entity.RoleResource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * @author panbaoqiang
 */
public interface TkRoleResourceMapper extends Mapper<RoleResource>, InsertListMapper<RoleResource> {
    /**
     * 根据roleId集合删除批量的roleResource
     * @param ids
     * @return
     */
    Integer deleteMultipleRoleResourceByRoleId(@Param("ids") List<String> ids);

    /**
     * 根据资源ID集合查询角色集合 该查看是否有元素
     * @param ids
     * @return
     */
    Integer queryRoleResourceCountByResourceIds(@Param("ids") List<String> ids);

    /**
     * 根据resourceId集合删除批量的roleResource
     * @param ids
     * @return
     */
    Integer deleteMultipleRoleResourceByResourceId(@Param("ids") List<String> ids);
}
