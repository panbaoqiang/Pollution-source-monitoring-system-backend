package edu.hfut.api;


import edu.hfut.pojo.vo.ResourceVO;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:09
 */
public interface ResourceApi {
    /**
     * 获取所有的资源，不需要startPage，还有pageSize，不管禁不禁用
     * @param request
     * @return
     */
    CommonResponse getAllResource( CommonRequest<ResourceVO> request) ;

    /**
     * 根据ParentId获取这个id下的资源树，包含startPage,pageSize,可能还有resourceId
     * @param request
     * @return
     */
    CommonResponse getResourceChildrenByParentId( CommonRequest<ResourceVO> request) ;

    /**
     * 增加一个资源，一般都是先更新父亲资源的叶子节点，然后在将自己添加进去，默认都是非禁用的，status为1
     * @param request
     * @return
     */
    CommonResponse addResource( CommonRequest<ResourceVO> request) ;

    /**
     * 更新资源信息，父亲节点也可以更新，查看version可以观察到是否更新成功
     * @param request
     * @return
     */
    CommonResponse updateResource( CommonRequest<ResourceVO> request);

    /**
     * 删除单个资源，这个时候要看是否删除成功
     * @param request
     * @return
     */
    CommonResponse deleteResource( CommonRequest<ResourceVO> request);

    /**
     * 删除多个资源，通过资源id
     * @param request
     * @return
     */
    CommonResponse deleteMultipleResource( CommonRequest<ResourceVO> request);
}
