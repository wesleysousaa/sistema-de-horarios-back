package br.edu.ifpb.dac.sistemadehorarios.service;

import br.edu.ifpb.dac.sistemadehorarios.model.ClassModel;
import br.edu.ifpb.dac.sistemadehorarios.model.CorricularComponentModel;

import br.edu.ifpb.dac.sistemadehorarios.repository.ClassRepository;
import br.edu.ifpb.dac.sistemadehorarios.repository.CorricularComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CorricularComponentService extends ServiceAbstract{
    @Autowired
    private CorricularComponentRepository repository;

    @Autowired
    private ClassRepository classRepository;

    public boolean create(CorricularComponentModel corricularComponentModel, String uuid) {
        try {
            ClassModel classModel =  this.classRepository.findByUuid(uuid);
            corricularComponentModel.setClassModel(classModel);

            if(corricularComponentModel.getClassModel()==null){
                return false;
            }

            super.create(corricularComponentModel, this.repository);
            return true;
        }catch (Exception error){
            return false;
        }
    }

    public List<CorricularComponentModel> read() {
       return (List<CorricularComponentModel>) super.read(this.repository);
    }

    public boolean update(CorricularComponentModel corricularComponentModel, String uuid) {
        try {
            CorricularComponentModel result = this.repository.findByUuid(uuid);

            String name = corricularComponentModel.getName()==null? result.getName() : corricularComponentModel.getName();
            byte workload = corricularComponentModel.getWorkload()==0? result.getWorkload() : corricularComponentModel.getWorkload();


            result.setName(name);
            result.setWorkload(workload);
            this.repository.save(result);
            return true;
        }catch (Exception error){
            return false;
        }
    }

    public boolean delete(String uuid) {
       return super.delete(uuid, this.repository);
    }

    public CorricularComponentModel findByUuid(String uuid) {
       return (CorricularComponentModel) super.findByUuid(uuid, this.repository);
    }
}
