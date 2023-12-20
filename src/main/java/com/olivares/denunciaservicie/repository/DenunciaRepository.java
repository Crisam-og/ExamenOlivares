package com.olivares.denunciaservicie.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olivares.denunciaservicie.entity.Denuncia;

@Repository
public interface DenunciaRepository extends JpaRepository <Denuncia, Integer> {
	List<Denuncia> findByTituloContaining(String titulo, Pageable page);
	Denuncia findByTitulo(String titulo);
	Denuncia findByDni(String dni);
}
