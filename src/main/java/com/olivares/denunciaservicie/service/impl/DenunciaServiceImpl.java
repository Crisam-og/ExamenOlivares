package com.olivares.denunciaservicie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olivares.denunciaservicie.entity.Denuncia;
import com.olivares.denunciaservicie.exceptions.GeneralServiceException;
import com.olivares.denunciaservicie.exceptions.NoDataFoundException;
import com.olivares.denunciaservicie.exceptions.ValidateServiceException;
import com.olivares.denunciaservicie.repository.DenunciaRepository;
import com.olivares.denunciaservicie.service.DenunciaService;
import com.olivares.denunciaservicie.validator.DenunciaValidator;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class DenunciaServiceImpl implements DenunciaService {
	
	@Autowired
	private DenunciaRepository repos;
	
	@Override
	@Transactional(readOnly=true)
	public List<Denuncia> findAll(Pageable page) {
		try {
			return repos.findAll(page).toList();
		} catch (NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Denuncia findById(int id) {
		try {
			Denuncia denuncia =  repos.findById(id).orElseThrow(()->new NoDataFoundException("No exist el registro con ese ID"));
			return denuncia;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}
	@Override
	@Transactional(readOnly=true)
	public Denuncia findByDni(String dni) {
		try {
			Denuncia denuncia =  repos.findByDni(dni);
			return denuncia;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Denuncia> findByTitulo(String titulo, Pageable page) {
		try {
			return repos.findByTituloContaining(titulo, page);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional()
	public Denuncia create(Denuncia obj) {
		try {
			DenunciaValidator.save(obj);
			if(repos.findByTitulo(obj.getTitulo())!=null) {
				throw new ValidateServiceException("Ya existe un registro con ese titulo"+obj.getTitulo());
			}
			
			Denuncia denuncia = repos.save(obj);
			return denuncia;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional()
	public Denuncia update(Denuncia obj) {
		try {
			DenunciaValidator.save(obj);
			Denuncia denuncia = repos.findById(obj.getId()).orElseThrow(()->new NoDataFoundException("No existe el registro con ese ID"));
			Denuncia denunciaD = repos.findByTitulo(obj.getTitulo());
			if(denunciaD !=null && denunciaD.getId() != denuncia.getId()) {
				throw new ValidateServiceException("Ya existe un registro con ese dni: " +obj.getTitulo());
			}
			denuncia.setDni(obj.getDni());
			denuncia.setFecha(obj.getFecha());
			denuncia.setTitulo(obj.getTitulo());
			denuncia.setDireccion(obj.getDireccion());
			denuncia.setDescripcion(obj.getDescripcion());

			repos.save(denuncia);
			
			return denuncia;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional()
	public void delete(int id) {
		try {
			Denuncia denuncia = repos.findById(id).orElseThrow(()->new NoDataFoundException("No exist el registro con ese ID"));
			repos.delete(denuncia);
					
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	
}


