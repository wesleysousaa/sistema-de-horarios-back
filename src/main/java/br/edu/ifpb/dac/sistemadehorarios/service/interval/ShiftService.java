package br.edu.ifpb.dac.sistemadehorarios.service.interval;

import br.edu.ifpb.dac.sistemadehorarios.exception.interval.GapInvalidException;
import br.edu.ifpb.dac.sistemadehorarios.exception.interval.ShiftInvalidException;
import br.edu.ifpb.dac.sistemadehorarios.model.interval.GapModel;
import br.edu.ifpb.dac.sistemadehorarios.model.interval.ShiftModel;
import br.edu.ifpb.dac.sistemadehorarios.repository.interval.ShiftRepository;
import br.edu.ifpb.dac.sistemadehorarios.service.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService extends ServiceTemplate {
    @Autowired
    private ShiftRepository repository;

    public boolean create(ShiftModel shiftModel) throws ShiftInvalidException {
        try{
            return super.create(shiftModel, this.repository);
        }catch (Exception error){
            throw new ShiftInvalidException("Houve um problema para criar um Gap. Erro: "+error.getMessage(), 400);
        }
    }

    public List<ShiftModel> read() {
        return (List<ShiftModel>) super.read(this.repository);
    }
    public boolean delete(String uuid) {
        return super.delete(uuid, this.repository);
    }

    public ShiftModel findByUuid(String uuid) {
        return (ShiftModel) super.findByUuid(uuid, this.repository);
    }
}
