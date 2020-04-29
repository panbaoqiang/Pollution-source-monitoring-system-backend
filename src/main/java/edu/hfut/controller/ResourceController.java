package edu.hfut.controller;

import edu.hfut.api.ResourceApi;
import edu.hfut.controller.base.BaseController;
import edu.hfut.pojo.dto.ResourceDTO;
import edu.hfut.pojo.vo.ResourceVO;
import edu.hfut.service.IResourceService;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
        logger.info(request.toString());
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(request.getData(),resourceDTO);
        return service.getAllResource(resourceDTO);
    }

    @Override
    @PostMapping("getResourceChildrenByParentId")
    public CommonResponse getResourceChildrenByParentId(@RequestBody CommonRequest<ResourceVO> request) {
        logger.info(request.toString());
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(request.getData(),resourceDTO);
        return service.getResourceChildrenByParentId(resourceDTO,request.getStartPage(),request.getPageSize());
    }

    @Override
    @PostMapping("addResource")
    public CommonResponse addResource(@RequestBody CommonRequest<ResourceVO> request) {
        logger.info(request.toString());
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(request.getData(),resourceDTO);
        return service.addResource(resourceDTO, request.getOperatorId());
    }

    @Override
    @PostMapping("updateResource")
    public CommonResponse updateResource(@RequestBody CommonRequest<ResourceVO> request) {
        logger.info(request.toString());
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(request.getData(),resourceDTO);
        return service.updateResource(resourceDTO, request.getOperatorId());
    }

    @Override
    @PostMapping("deleteResource")
    public CommonResponse deleteResource(@RequestBody CommonRequest<ResourceVO> request) {
        logger.info(request.toString());
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(request.getData(),resourceDTO);
        return service.deleteResource(resourceDTO);
    }

    @Override
    @PostMapping("deleteMultipleResource")
    public CommonResponse deleteMultipleResource(@RequestBody CommonRequest<ResourceVO> request) {
        logger.info(request.toString());
        ResourceDTO resourceDTO = new ResourceDTO();
        BeanUtils.copyProperties(request.getData(),resourceDTO);
        return service.deleteMultipleResource(resourceDTO);
    }

    @Override
    @PostMapping("getAllMenuResource")
    public CommonResponse getAllMenuResource(@RequestBody  CommonRequest<Void> request) {
        return service.getAllMenuResource();
    }
}
