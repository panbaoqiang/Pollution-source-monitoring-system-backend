package edu.hfut.controller;

import edu.hfut.api.ComponentApi;
import edu.hfut.controller.base.BaseController;
import edu.hfut.service.IComponentService;
import edu.hfut.util.comon.CommonRequest;
import edu.hfut.util.comon.CommonResponse;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-29 15:40
 */
@RestController
@RequestMapping("component")
public class ComponentController extends BaseController implements ComponentApi {
    @Autowired
    private IComponentService componentService;
    @Override
    @PostMapping("getAllComponent")
    public CommonResponse getAllComponent(CommonRequest<Void> request) {
        return componentService.getAllComponent();
    }
}
