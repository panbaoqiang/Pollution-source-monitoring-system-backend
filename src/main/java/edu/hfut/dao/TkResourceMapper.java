package edu.hfut.dao;


import edu.hfut.pojo.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
public interface TkResourceMapper extends Mapper<Resource> {
    /**
     *     根据父亲id获取子孩子的id
     */
    @Select("select id from t_resource where  parent_id=#{parentId}")
    List<String> getChildrenIdsByParentId(String parentId);

    /**
     * 根据id集合获取资源集合
     */
    List<Resource> queryResourceByIds(@Param("ids") List<String> ids);

    /**
     * 根据id集合获取资源集合
     */
    Integer deleteMultpleResource(@Param("ids") List<String> ids);
}
