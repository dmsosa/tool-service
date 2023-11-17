package vuttr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import vuttr.service.ToolService;

@RestController
public class ToolController {
    @Autowired
    ToolService toolService;

}
