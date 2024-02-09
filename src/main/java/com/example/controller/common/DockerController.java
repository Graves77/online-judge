package com.example.controller.common;


import com.example.utils.DockerClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class DockerController {

    @Autowired
    private DockerClientUtils dockerClientUtils;

    @GetMapping("/getDockerImageInfo/{prefix}/{dockerName}")
    public Object getDockerImageInfo(@PathVariable String prefix,@PathVariable String dockerName){
        return dockerClientUtils.inspectImage(prefix + "/" +dockerName);
    }


    @GetMapping("/runContainer/{prefix}/{dockerName}")
    public Object runContainer(@PathVariable String prefix,@PathVariable String dockerName) throws IOException, InterruptedException {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("g++ test.cpp");
        dockerClientUtils.createContainer((prefix + "/" + dockerName),
                "/d/docker_windows/293616896/287716992/412340643394883584",
                "/home/judge_machine",
                    objects
                );
        return true;
    }


}
