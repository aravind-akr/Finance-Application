package com.aravind.finance.services;

import com.aravind.finance.exceptions.ModeException;
import com.aravind.finance.models.Mode;
import com.aravind.finance.repositories.ModeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ModeService {

    @Autowired
    private ModeRepository modeRepository;

    public Mode saveOrUpdateMode(Mode mode){
        log.info("Inside the saveOrUpdateMode method");
        try{
            log.info("Trying to save the mode");
            return modeRepository.save(mode);
        } catch (Exception e) {
            log.debug("There's some issue in saving the Mode:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Mode getModeById(int modeId) {
        log.info("Inside the getModeById method");
        Mode mode = modeRepository.findById(modeId);
        if(mode == null){
            log.warn("No mode found with "+modeId);
            throw new ModeException("There is no mode with "+ modeId);
        }
        log.info("The mode details are " + mode);
        return mode;
    }

    public List<Mode> getAllModes() {
        log.info("Inside the getAllModes method");
        return modeRepository.findAll();
    }
}
