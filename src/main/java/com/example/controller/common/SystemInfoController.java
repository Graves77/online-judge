package com.example.controller.common;


import com.example.model.JsonResult;
import com.example.utils.State;
import com.example.utils.SystemInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.UnknownHostException;


@RestController
@Slf4j
public class SystemInfoController {
    @GetMapping("/getSysInfo")
    public JsonResult getSysInfo() throws UnknownHostException {
        return new JsonResult(SystemInfoUtils.getInfo(), State.SUCCESS, "获取成功!");
    }

}
