package br.edu.ifpb.dac.sistemadehorarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.dac.sistemadehorarios.DTO.ClassroomDTO;
import br.edu.ifpb.dac.sistemadehorarios.model.ClassroomModel;
import br.edu.ifpb.dac.sistemadehorarios.service.ClassroomService;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {
	
	@Autowired
	private ClassroomService service;
	
	@PostMapping
	public ResponseEntity<ClassroomDTO> create(@RequestBody ClassroomModel classroom){
		boolean result = this.service.create(classroom);
		if(result) {
			return ResponseEntity.status(201).body(new ClassroomDTO(classroom));
		}
		return ResponseEntity.status(400).body(null);
	}
	
	@GetMapping
	public ResponseEntity <List<ClassroomDTO>> read(){
		List<ClassroomModel> result = this.service.read();
		return ResponseEntity.status(200).body(ClassroomDTO.convert(result));
	}
	
	@GetMapping("/get-by-uuid/{uuid}")
	public ResponseEntity<ClassroomDTO> findByUuid(@PathVariable("uuid") String uuid) {
        ClassroomModel result = this.service.readByUuid(uuid);
        if(result !=  null){
            return  ResponseEntity.status(200).body(new ClassroomDTO(result));
        }
        return ResponseEntity.status(404).body(null);
    }
	
	
	@PutMapping("/{uuid}")
	public ResponseEntity<ClassroomDTO> update(@RequestBody ClassroomModel classroom, @PathVariable("uuid") String uuid){
		boolean result = this.service.update(classroom, uuid);
		if (result) {
			return ResponseEntity.status(200).body(new ClassroomDTO(classroom));
		}
		return ResponseEntity.status(404).body(null);
	}
	
	@DeleteMapping("/{uuid}")
	public ResponseEntity<String> delete(@PathVariable("uuid") String uuid){
		boolean result = this.service.delete(uuid);
		if (result) {
			return ResponseEntity.status(200).body("OK");
		}
		return ResponseEntity.status(404).body("NOT OK");

	}
	
}
