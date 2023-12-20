package com.olivares.denunciaservicie.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.olivares.denunciaservicie.entity.Denuncia;


public interface DenunciaService {
	public List<Denuncia> findAll(Pageable page);
	public Denuncia findById(int id);
	public Denuncia findByDni(String dni);
	public List<Denuncia> findByTitulo(String titulo, Pageable page);
    public Denuncia create(Denuncia obj);
    public Denuncia update(Denuncia obj);
    public void delete(int id);
}
