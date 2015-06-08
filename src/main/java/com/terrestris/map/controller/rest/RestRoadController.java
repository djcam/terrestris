package com.terrestris.map.controller.rest;

import com.terrestris.map.domain.entity.Latitude;
import com.terrestris.map.domain.entity.Longitude;
import com.terrestris.map.service.inter.RoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by dcampbell2 on 5/28/2015.
 */

@RestController
public class RestRoadController {

    private RoadService roadService;

    @Autowired
    RestRoadController(RoadService roadService) {
        this.roadService = roadService;
    }

    @RequestMapping("/api/roads")
    public RoadsResponseObject getAllRoads() {
        return new RoadsResponseObject(roadService.getAllLatitudes(), roadService.getAllLongitudes());
    }

    @ResponseBody
    class RoadsResponseObject {
        private Collection<Latitude> latitudes;
        private Collection<Longitude> longitudes;

        public RoadsResponseObject(Collection<Latitude> latitudes, Collection<Longitude> longitudes) {
            this.longitudes = longitudes;
            this.latitudes = latitudes;
        }

        public Collection<Latitude> getLatitudes() {
            return latitudes;
        }

        public Collection<Longitude> getLongitudes() {
            return longitudes;
        }
    }
}
