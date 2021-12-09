package com.aravind.finance.repositories;

import com.aravind.finance.models.Mode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ModeRepository extends CrudRepository<Mode, Integer> {

    Mode findById(int mode_id);

    @Override
    List<Mode> findAll();

}
