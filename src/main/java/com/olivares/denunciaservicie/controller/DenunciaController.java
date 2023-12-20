package com.olivares.denunciaservicie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olivares.denunciaservicie.converter.DenunciaConverter;
import com.olivares.denunciaservicie.dto.DenunciaDTO;
import com.olivares.denunciaservicie.entity.Denuncia;
import com.olivares.denunciaservicie.service.DenunciaService;
import com.olivares.denunciaservicie.utils.WrapperResponse;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {
	@Autowired
    private DenunciaService service;
    
	

	@Autowired
	private DenunciaConverter converter;
	
    @GetMapping()
    public ResponseEntity<List<DenunciaDTO>> findAll(
            @RequestParam(value = "titulo", required = false, defaultValue = "") String titulo,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Denuncia> denuncia;
        
        if(titulo == null) {
        	denuncia = service.findAll(page);
        } else {
        	denuncia = service.findByTitulo(titulo, page);
        }
        /*
        if(productos.isEmpty()) {
        	return ResponseEntity.noContent().build();
        }*/
        List<DenunciaDTO> denunciasDTO = converter.fromEntity(denuncia);
        return new WrapperResponse(true,"success", denunciasDTO).createResponse(HttpStatus.OK);
    }
    @GetMapping(value="/{id}")
	public ResponseEntity<WrapperResponse<DenunciaDTO>> findById(@PathVariable("id") int id){
    	Denuncia denuncias = service.findById(id);
		if (denuncias ==null) {
			return ResponseEntity.notFound().build();
		}
		DenunciaDTO denunciasDTO=converter.fromEntity(denuncias);
		return new WrapperResponse<DenunciaDTO>(true,"success", denunciasDTO).createResponse(HttpStatus.OK);
	}
    
    @PostMapping
	public ResponseEntity<DenunciaDTO> create(@RequestBody DenunciaDTO denunciaDTO){
		Denuncia denuncias = service.create(converter.fromDTO(denunciaDTO));
		DenunciaDTO denunciasDTO = converter.fromEntity(denuncias);
		return new WrapperResponse(true,"success", denunciasDTO).createResponse(HttpStatus.CREATED);
	}
    
    @PutMapping(value="/{id}")
	public ResponseEntity<DenunciaDTO> update(@PathVariable("id") int id, @RequestBody DenunciaDTO denunciaDTO){
		Denuncia denuncias = service.update(converter.fromDTO(denunciaDTO));
		if(denuncias == null) {
			return ResponseEntity.notFound().build();
		}
		DenunciaDTO denunciasDTO = converter.fromEntity(denuncias);
		return new WrapperResponse(true,"success", denunciasDTO).createResponse(HttpStatus.OK);		
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<DenunciaDTO> delete(@PathVariable("id") int id){
		service.delete(id);
		return new WrapperResponse(true,"success", null).createResponse(HttpStatus.OK);
	}
	
}
 
