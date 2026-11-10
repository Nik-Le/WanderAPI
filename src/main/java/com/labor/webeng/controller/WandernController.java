package com.labor.webeng.controller;

import com.labor.webeng.model.Location;
import com.labor.webeng.service.WandernService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wandern")
public class WandernController {

    private final WandernService service;

    public WandernController(WandernService service) {
        this.service = service;
    }

    @GetMapping("/{location}")
    public Location getCoordinates(@PathVariable String location) {
        return service.getCoordinates(location);
    }

}
