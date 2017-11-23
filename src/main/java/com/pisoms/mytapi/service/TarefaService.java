package com.pisoms.mytapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pisoms.mytapi.model.Tarefa;
import com.pisoms.mytapi.repository.TarefaRepository;
import com.pisoms.mytapi.service.exception.RegistroIdenticoException;

@Service
public class TarefaService {
	
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	/**
	 * Lista todas as tarefas
	 * @return List<Tarefa>
	 */
	public List<Tarefa> listarTodos() {
		return tarefaRepository.findAll();
	}
	
	/**
	 * Obtem uma Tarefa apartir do codigo de cadastro
	 * @param codigo
	 * @return Tarefa
	 */
	public Tarefa obter(Long codigo) {
		return tarefaRepository.findOne(codigo);
	}
	
	/**
	 * Deleta uma Tarefa
	 * @param codigo
	 */
	public void delete(Long codigo) {
		Tarefa tarefa = tarefaRepository.findOne(codigo);

		if (tarefa == null) {
			throw new EmptyResultDataAccessException(1);
		}

		tarefaRepository.delete(tarefa);
	}
	
	/**
	 * Atualiza o Local caso exista
	 * 
	 * @param codigo
	 * @param local
	 * @return Local
	 */
	public Tarefa atualizar(Long codigo, Tarefa tarefa) {
		Tarefa tarefaSalva = tarefaRepository.findOne(codigo);
		
		if(tarefaSalva ==  null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		tarefaSalva.setNome(tarefa.getNome());
		tarefaSalva.setDescricao(tarefa.getDescricao());
		tarefaSalva.setTipoTarefa(tarefa.getTipoTarefa());
		tarefaSalva.setDataInicio(tarefa.getDataInicio());
		
		tarefaRepository.save(tarefaSalva);
		
		return tarefaSalva;
	}
	
	/**
	 * Salva uma terefa 
	 * 
	 * @param tarefa
	 * @return Tarefa
	 */
	public Tarefa salvar(Tarefa tarefa) {
		
		
		Tarefa tarefaSearch = tarefaRepository.findByNome(tarefa.getNome());
		
		if(tarefaSearch == null) {
			return tarefaRepository.save(tarefa);
		}else {
			throw new RegistroIdenticoException();
		}
	}

}
