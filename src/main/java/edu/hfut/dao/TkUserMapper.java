package edu.hfut.dao;


import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.pojo.entity.Resource;
import edu.hfut.pojo.entity.User;
import edu.hfut.pojo.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

/**
 * @author Administrator
 */
public interface TkUserMapper extends Mapper<User>, IdsMapper<Resource>{
    /**
     * 根据用户id获取角色集合
     * @param id
     * @return
     */
    @Select("select t_role.name from t_user_role , t_role where t_user_role.role_id = t_role.id and t_user_role.user_id=#{id}")
    List<String> queryUserRole(String id);

    /**
     * 根据用户iD获取roleId集合,必须是status 等于1 的值
     * @param id
     * @return
     */
    @Select("select t_user_role.role_id from t_user_role,t_role  where  t_user_role.user_id=#{id} and t_role.id = t_user_role.role_id and t_role.status = 1")
    List<String> queryUserRoleIds(String id);

    /**
     * 根据角色集合查询 该角色拥有的权限，默认资源没有被禁用
     * @param ids
     * @return
     */
    List<Resource> queryUserResourceByRoleIds(@Param("ids") List<String> ids);


    /**
     * 这个函数是根据一个name和code用户集合，这里会将所有的role都包含进去
     * @param name
     * @param code
     * @return
     */
    List<UserDTO> queryCurrentPageUserList(@Param("name") String name, @Param("code") String code);


    /**
     * 根据id删除一批用户
     * @param ids
     * @return
     */
    Integer deleteMultipleUser(@Param("ids") List<String> ids);

    /**
     * 根据id删除一批用户的所有角色
     * @param ids
     * @return
     */
    Integer clearUserRoleByUserId(@Param("ids") List<String> ids);

}
