package br.edu.ifpb.dac.sistemadehorarios.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.dac.sistemadehorarios.model.ClassroomModel;
import br.edu.ifpb.dac.sistemadehorarios.repository.ClassroomRepository;

@Service
public class ClassroomService extends ServiceAbstract{

	@Autowired
	private ClassroomRepository repository;


	
	public boolean create(ClassroomModel classroom) {
        return super.create(classroom, this.repository);
    }
	
	public List<ClassroomModel> read() {
        return (List<ClassroomModel>) super.read(this.repository);
    }

    public boolean delete(String uuid) {
        return super.delete(uuid,this.repository);
    }

    public ClassroomModel readByUuid(String uuid) {
        return (ClassroomModel) super.findByUuid(uuid, this.repository);
    }
	
	public boolean update(ClassroomModel classroom, String uuid) {
        try {
            ClassroomModel result = this.repository.findByUuid(uuid);
            String name = classroom.getName()==null? result.getName() : classroom.getName();
            String block = classroom.getBlock()==null? result.getBlock() : classroom.getBlock();
            int capacity = classroom.getCapacity()== 0 ? result.getCapacity(): classroom.getCapacity();

            result.setName(name);
            result.setBlock(block);
            result.setCapacity(capacity);
            this.repository.save(result);
            return true;
        }catch (Exception error){
            return false;
        }
    }

}
