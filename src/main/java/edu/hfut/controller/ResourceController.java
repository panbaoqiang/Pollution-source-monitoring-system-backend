package edu.hfut.controller;

import edu.hfut.api.ResourceApi;
import edu.hfut.controller.base.BaseController;
import edu.hfut.pojo.vo.ResourceVO;
import edu.hfut.service.IResourceService;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-27 23:09
 */
@RestController
@RequestMapping("resource")
public class ResourceController extends BaseController implements ResourceApi {

    private Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private IResourceService service;

    @Override
    @PostMapping("getAllResource")
    public CommonResponse getAllResource(@RequestBody CommonRequest<ResourceVO> request) {
        return null;
    }

    @Override
    @PostMapping("getResourceChildrenByParentId")
    public CommonResponse getResourceChildrenByParentId(@RequestBody CommonRequest<ResourceVO> request) {
        return null;
    }

    @Override
    @PostMapping("addResource")
    public CommonResponse addResource(@RequestBody CommonRequest<ResourceVO> request) {
        return null;
    }

    @Override
    @PostMapping("updateResource")
    public CommonResponse updateResource(@RequestBody CommonRequest<ResourceVO> request) {
        return null;
    }

    @Override
    @PostMapping("deleteResource")
    public CommonResponse deleteResource(@RequestBody CommonRequest<ResourceVO> request) {
        return null;
    }

    @Override
    @PostMapping("deleteMultipleResource")
    public CommonResponse deleteMultipleResource(@RequestBody CommonRequest<ResourceVO> request) {
        return null;
    }
}
