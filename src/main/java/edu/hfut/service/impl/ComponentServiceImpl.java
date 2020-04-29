package edu.hfut.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hfut.dao.TkComponentMapper;
import edu.hfut.dao.TkUserMapper;
import edu.hfut.dao.TkUserRoleMapper;
import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.pojo.entity.Component;
import edu.hfut.pojo.entity.Resource;
import edu.hfut.pojo.entity.User;
import edu.hfut.pojo.entity.UserRole;
import edu.hfut.service.IComponentService;
import edu.hfut.service.IUserService;
import edu.hfut.util.comon.CommonResponse;
import edu.hfut.util.comon.JwtUtil;
import edu.hfut.util.comon.SnowFlakeUtil;
import edu.hfut.util.comon.StatusCode;
import edu.hfut.util.exception.BussinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
@Service
public class ComponentServiceImpl implements IComponentService {
    @Autowired
    private TkComponentMapper tkComponentMapper;
    @Override
    public CommonResponse getAllComponent() {
        List<Component> components = tkComponentMapper.selectAll();
        return new CommonResponse(StatusCode.SUCCEED.getCode(),StatusCode.SUCCEED.getMsg(),components);
    }
}
