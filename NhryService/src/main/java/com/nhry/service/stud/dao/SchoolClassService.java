package com.nhry.service.stud.dao;

import java.util.List;

import com.nhry.data.stud.domain.TMdSchool;
import com.nhry.model.stud.SchoolClassModel;

/**
 * @author zhaoxijun
 */
public interface SchoolClassService {
    int addSchoolClass(SchoolClassModel schoolClassModel);
    
    int delSchoolClassBySalesOrg(SchoolClassModel schoolClassModel);

    List<TMdSchool> findAllClassBySchool(SchoolClassModel schoolClassModel);
    
    List<TMdSchool> findNoneClassBySchool(SchoolClassModel schoolClassModel);
}
