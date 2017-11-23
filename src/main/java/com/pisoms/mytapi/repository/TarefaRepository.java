package com.pisoms.mytapi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pisoms.mytapi.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Serializable> {
	
	@Query("select t from Tarefa t where t.nome = :nome_tarefa")
	public Tarefa findByNome(@Param("nome_tarefa") String nome_tarefa);

}
