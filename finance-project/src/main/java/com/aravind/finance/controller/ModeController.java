package com.aravind.finance.controller;

import com.aravind.finance.models.Mode;
import com.aravind.finance.services.ModeService;
import com.aravind.finance.services.MapValidationErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/finance/mode")
@CrossOrigin
@Slf4j
public class ModeController {

    @Autowired
    private MapValidationErrorService errorService;

    @Autowired
    private ModeService modeService;

    @PostMapping("/add")
    public ResponseEntity<?> saveOrUpdateMode(@Valid @RequestBody Mode mode, BindingResult result) {
        log.info("Triggering the POST Model method");
        ResponseEntity<?> errorMap = errorService.mapValidationErrorService(result);
        if(errorMap!=null) return errorMap;

        Mode mode1 = modeService.saveOrUpdateMode(mode);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/finance/mode/get/{modeId}")
                .buildAndExpand(mode1.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/get/{modeId}")
    public EntityModel<Mode> getModeById(@PathVariable("modeId") int modeId) {
        log.info("Inside getModeById method");
        Mode mode = modeService.getModeById(modeId);
        EntityModel<Mode> entityModel = EntityModel.of(mode);
        entityModel.add(linkTo(methodOn(this.getClass()).getAllModes()).withRel("get-all-modes"));
        return entityModel;
    }

    @GetMapping("/get/all")
    public CollectionModel<EntityModel<Mode>> getAllModes(){
        log.info("Inside getAllModes method");
        List<Mode> allModes = modeService.getAllModes();
        List<EntityModel<Mode>> collect = allModes.stream().map(mode ->
                EntityModel.of(mode,
                        linkTo(methodOn(this.getClass()).getModeById(mode.getId())).withRel("get-mode-details")))
                .collect(Collectors.toList());
        return CollectionModel.of(collect);
    }
}
