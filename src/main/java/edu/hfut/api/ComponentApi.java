package edu.hfut.api;

import edu.hfut.pojo.vo.RoleVO;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:09
 */
public interface ComponentApi {
    /**
     * 获取所有组件
     * @param request
     * @return
     */
    CommonResponse getAllComponent(CommonRequest<Void> request) throws InterruptedException;
}
