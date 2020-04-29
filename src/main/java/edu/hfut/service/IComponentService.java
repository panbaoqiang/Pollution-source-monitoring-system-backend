package edu.hfut.service;

import edu.hfut.pojo.dto.UserDTO;
import edu.hfut.util.comon.CommonResponse;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:11
 */
public interface IComponentService {
    /**
     * 获取所有用户
     * @return
     */
    CommonResponse getAllComponent() ;
}
