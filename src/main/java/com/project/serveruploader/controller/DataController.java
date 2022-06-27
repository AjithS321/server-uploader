package com.project.serveruploader.controller;

import com.project.serveruploader.dto.IncomingRecDto;
import com.project.serveruploader.dto.OutgoingRecDto;
import com.project.serveruploader.entity.OutgoingRec;
import com.project.serveruploader.service.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blender")
public class DataController {

    @Autowired
    private UploaderService uploaderService;


    @PostMapping(
            value = "/data", consumes = "application/json", produces = "application/json")
    public OutgoingRecDto dataAsync(@RequestBody IncomingRecDto incomingRecDto) {

        return uploaderService.uploadDataAsyc(incomingRecDto);
    }

}
