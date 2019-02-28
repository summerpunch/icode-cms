package com.icode.cms.controller.base;

import com.icode.cms.common.constant.PathFinal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

    @RequestMapping("/")
    public String index() {
        return PathFinal.PAGE_INDEX;
    }
}
