package br.edu.ifpb.dac.sistemadehorarios.service.interval;

import br.edu.ifpb.dac.sistemadehorarios.exception.interval.ShiftInvalidException;
import br.edu.ifpb.dac.sistemadehorarios.exception.interval.WeekDayInvalidException;
import br.edu.ifpb.dac.sistemadehorarios.model.interval.ShiftModel;
import br.edu.ifpb.dac.sistemadehorarios.model.interval.WeekDayModel;
import br.edu.ifpb.dac.sistemadehorarios.repository.interval.WeekDayRepository;
import br.edu.ifpb.dac.sistemadehorarios.service.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeekDayService extends ServiceTemplate {
    @Autowired
    private WeekDayRepository repository;
    public boolean create(WeekDayModel weekDayModel) throws WeekDayInvalidException {
        try{
            return super.create(weekDayModel, this.repository);
        }catch (Exception error){
            throw new WeekDayInvalidException("Houve um problema para criar um Gap. Erro: "+error.getMessage(), 400);
        }
    }

    public List<WeekDayModel> read() {
        return (List<WeekDayModel>) super.read(this.repository);
    }
    public boolean delete(String uuid) {
        return super.delete(uuid, this.repository);
    }

    public WeekDayModel findByUuid(String uuid) {
        return (WeekDayModel) super.findByUuid(uuid, this.repository);
    }
}
