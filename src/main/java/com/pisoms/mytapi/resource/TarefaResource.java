package com.pisoms.mytapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pisoms.mytapi.event.RecursoCriadoEvent;
import com.pisoms.mytapi.model.Tarefa;
import com.pisoms.mytapi.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaResource {
	
	@Autowired
	private TarefaService tarefaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Tarefa> listar(){
		return tarefaService.listarTodos(); 
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Tarefa> obter(@PathVariable Long codigo){
		Tarefa tarefa = tarefaService.obter(codigo); 
		return tarefa != null ? ResponseEntity.ok(tarefa) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo){
		tarefaService.delete(codigo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa, HttpServletResponse response) {
		Tarefa tarefaSalva = tarefaService.salvar(tarefa);
	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, tarefaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Tarefa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Tarefa tarefa){
		Tarefa tarefaSalva = tarefaService.atualizar(codigo, tarefa);
		return ResponseEntity.ok(tarefaSalva);
	}
}
